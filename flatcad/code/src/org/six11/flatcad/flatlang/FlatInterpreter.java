package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import org.six11.util.io.FileUtil;
import org.six11.util.thread.WorkerThread;
import org.six11.flatcad.TextEntry.UserInputListener;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.geom.ColorMeister;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.gl.OpenGLDisplay;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.*;
import static org.six11.flatcad.flatlang.FlatLangLexer.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Stack;

/**
 * 
 **/
public class FlatInterpreter implements UserInputListener {
  
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("Syntax: ./run fileName [ --tree ] [ --quit ]");
      System.out.println("  The --tree arg causes it to spit out the AST.");      
      System.out.println("  The --quit arg makes it only print out the tree (if it is on), but doesn't run the program.");
      System.exit(0);
    } else {
      FlatInterpreter interp = new FlatInterpreter();
      interp.useArguments(args);
    }
  }

  public String useArguments(String[] args) throws Exception {
    boolean stayAlive = true;
    boolean tree = false;
    List<File> dirs = new ArrayList<File>();
    String file = null;
    boolean limitedInterpretation = false;

    for (String s : args) {
      if (s.equals("--tree")) {
	tree = true;
      } else if (s.equals("--quit")) {
	stayAlive = false;
      } else if (s.startsWith("--flatpath=")) {
	String bigPath = s.substring("--flatpath=".length());
	StringTokenizer tok = new StringTokenizer(bigPath, ":");
	while (tok.hasMoreTokens()) {
	  dirs.add(new File(tok.nextToken()));
	}
      } else {
	file = s;
      }
    }
    
    if (dirs.size() == 0) {
      dirs.add(new File("."));
    }
    setPaths(dirs);
    loadFile(true, "builtin.fl", "fl/builtin.fl");
    
    machine.rememberBuiltinVars();

    String program = FileUtil.loadStringFromFile(file);
    
    if (tree) {
      // show me the parse tree instead of interpreting
      Tree root = /*interp.*/makeTree(program);
      FlatDebugger.processTree(root, 1);
    } 
    if (stayAlive) {
      /*interp.*/handleUserInput(program, false);
    }
    return file;
  }


  protected FileUtil.FileFinder fileFinder;
  protected MachineState machine;
  protected GeometrySoup soup;
  protected Map<String, Builtin> builtins;
  protected OpenGLDisplay redrawMe;
  protected DebugMonitor debugMonitor;
  protected WorkerThread interpThread;

  List<String> automaticLoadFiles;
  int lvalCount = 0;
  boolean debugEval = false;

  public FlatInterpreter() {
    machine = new MachineState();
    soup = new GeometrySoup();
    automaticLoadFiles = new ArrayList<String>();
    
    builtins = Builtin.getBuiltins(this);

    interpThread = new WorkerThread("FlatLang Interpreter");
    interpThread.start();
  }
  
  public DebugMonitor getDebugMonitor() {
    if (debugMonitor == null) {
      debugMonitor = new DebugMonitor();
    }
    
    return debugMonitor;
  }

  public MachineState getMachine() {
    return machine;
  }
  
  public boolean loadFile(boolean persistent, String... fileNames) {
    boolean ret = false;
    for (String fileName : fileNames) {
      try {
	File f = fileFinder.search(fileName);
	if (f != null) {
	  String progStr = FileUtil.loadStringFromFile(f);
	  interpret(progStr, false, true);
	  if (persistent) {
	    automaticLoadFiles.add(fileName);
	  }
	  //	  Debug.out("FlatInterpreter", "loaded from file: " + f);
	  ret = true;
	  break;
	}
      } catch (FileNotFoundException ex) {
	Debug.out("FlatInterpreter", "Can't find the file '" + fileName + "'");
      } catch (Exception ex) {
	Debug.out("FlatInterpreter", "Some kind of weird exception, as follows...");
	ex.printStackTrace();
      }
    }

    if (!ret) {
      Debug.out("FlatInterpreter", "Can't find any of the files: " + 
		Debug.num(fileNames, ","));
    }

    return ret;
  }

  public void setPaths(List<File> dirs) {
    fileFinder = new FileUtil.FileFinder(dirs);
  }

  public void setComponent(OpenGLDisplay c) {
    redrawMe = c;
  }
  
  public GeometrySoup getSoup() {
    return soup;
  }
  
  private void reportPoint() {
    Debug.out("FlatInterpreter", "The turtle is now at SOME UNKNOWN LOCATION!");//: " + machine.getPoint());
  }
  
  public FlatInterpreter(File f) throws Exception {
    this();
    BufferedReader reader = new BufferedReader(new FileReader(f));
    StringBuffer buf = new StringBuffer();
    while(reader.ready()) {
      buf.append(reader.readLine() + "\n");
    }
    handleUserInput(buf.toString(), false);
  }

  public void reset(boolean limited) {
    if (!limited) {
      machine.reset();
    }
    soup.reset();

    // make sure the interpThread cancels any existing process.
    Debug.out("FlatInterpreter", "Stopping all tasks. This should block until it really has happened.");
    interpThread.stopAllTasks();
    Debug.out("FlatInterpreter", "All tasks have obsensibly stopped.");

//     for (String fn: automaticLoadFiles) {
//       loadFile(false, fn);
//     }
  }


  public void handleUserInput(String input, boolean limited) {
    try {
      reset(limited);
      interpret(input, true, false);
    } catch (Exception ex) {
      Debug.out("FlatInterpreter", "Caught exception, consuming it and staying alive.");
      ex.printStackTrace();
    }
  }

  public Tree makeTree(String programString) throws Exception {
    CharStream cs = new ANTLRStringStream(programString);
    FlatLangLexer flatLexer = new FlatLangLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(flatLexer);
    FlatLangParser flatParser = new FlatLangParser(tokens);
    
    FlatLangParser.prog_return r = flatParser.prog();
    CommonTree root = ((CommonTree) r.tree);

    CommonTreeNodeStream nodes = new CommonTreeNodeStream(root);
    nodes.setTokenStream(tokens);
    return root;
  }

  public void interpret(final String programString, final boolean isComplete, 
			final boolean isPersistent) throws Exception {
    if (programString.length() == 0) return;
    try {
      if (debugMonitor != null) {
	Debug.out("FlatInterpreter", "resetting debug monitor.");
	debugMonitor.reset();
      }
      Tree root = makeTree(programString);
      discover(root, isPersistent, true);
      if (isComplete) {
	soup.done();
      }
      repaint();
    } catch (Exception ex) {
      Debug.out("FlatInterpreter", "Caught unexpected exception in interpreting your program.");
      ex.printStackTrace();
    }
  }

  public void discover(Tree t) {
    if (Thread.interrupted()) {
      Debug.out("FlatInterpreter", "Interrupted. Stopping discovery.");
    } else {
      discover(t, false, false);
    }
  }

  public void discover(final Tree t, final boolean isPersistent, final boolean isTop) {
    if (Thread.currentThread() != interpThread) {
      Runnable r = new Runnable() {
	  public void run() {
	    discover(t, isPersistent, isTop);
	    repaint();
	  }
	};
      interpThread.add(r);
      return;
    }
    if (debugMonitor != null && t.getType() == STATEMENT) {
      synchronized (debugMonitor) {
	while (!debugMonitor.consumeStatement()) {
	  try {
	    repaint();
	    debugMonitor.wait(200);
	  } catch (InterruptedException ex) { ; }
	}
	Register reginald = quote(t);
	if (reginald instanceof Register.List) {
	  String code = unlist((Register.List) reginald, true);
	  debugMonitor.setCurrentDebugInfo(code, t.getLine(), 
					   t.getCharPositionInLine(), 
					   machine.getReturnValue(), debugMonitor.isAborted());
	}
      }
    }
    
    if (debugMonitor != null && debugMonitor.isAborted()) {
      Debug.out("FlatInterpreter", "debugMonitor tells me to abort. Returning void");
      interpThread.stopAllTasks();
      return;
    }

    switch (t.getType()) {
    case PROG:
    case BLOCK:
    case STATEMENT:
      discoverAllChildren(t);
      break;
    case EXPR:
      eval(t);
      break;
    case FUNCTION_DEF_SIGNATURE:
      stash(t);
      break;
    case REPLACE: 
      doReplace(t, false);
      break;
    case REPLACE_ALL: 
      doReplace(t, true);
      break;
    case FROM:
      doFrom(t);
      break;
    case SHAPE:
      doShape(t);
      break;
    case WHILE_STATEMENT:
      doWhile(t);
      break;
    case REPEAT_STATEMENT:
      doRepeat(t);
      break;
    case IF_STATEMENT:
      doIf(t);
      break;
    case FOR_EACH_STATEMENT:
      doForEach(t);
      break;
    default:
      Debug.out("FlatInterpreter", "Unknown tree node in discover(): " + FlatDebugger.bug(t, true));
    }

    if (isPersistent) {
      machine.rememberBuiltinVars();
    }
    if (isTop) {
      soup.done();
    }

  }

  public boolean isRegisterTrue(Register r) {
    boolean ret = false;
    switch (r.type) {
    case Register.TYPE_STRING:
      ret = r.toString().length() > 0;//MathUtils.toTruth(r.toString());
      break;
    case Register.TYPE_FUNCTION:
    case Register.TYPE_CODE:
      ret = true;
      break;
    case Register.TYPE_LIST:
      ret = ((Register.List) r).root != null;
      break;
    case Register.TYPE_BOOLEAN:
      ret = ((Register.Boolean) r).b;
      break;
    case Register.TYPE_NIL:
      ret = false;
      break;
    case Register.TYPE_NUMBER:
      ret = ((Register.Num) r).v != 0d;
      break;
      //    case Register.TYPE_OBJECT:
    case Register.TYPE_OBJECT_REF:
      ret = true;
      break;
    default:
      Debug.out("FlatInterpreter", "Warning: isRegisterTrue on unknown type: " + r.type);
      break;
    }
    
    return ret;
  }


  public void doForEach(Tree t) { 
    // 'for each' '(' v=ID ':' l=ID ')' ( '(' n_assignExpression ')' )? block? 'done'
    //   -> ^(FOR_EACH_STATEMENT $v $l n_assignExpression? block?)
    // [item, myList, N_LT_EXPR, BLOCK]
    Tree varT = t.getChild(0);
    Tree listT = t.getChild(1);
    Tree conditionalT = null;
    Tree codeT = null;
    if (t.getChildCount() > 2) {
      if (t.getChild(2).getType() == BLOCK) {
	codeT = t.getChild(2);
      } else {
	conditionalT = t.getChild(2);
      }
    }
    if (t.getChildCount() > 3) {
      codeT = t.getChild(3);
    }

    // setting up:
    // get value of 'list'.
    Register.List list = null;
    Register listR = eval(listT);
    
    if (listR.type != Register.TYPE_LIST) {
      System.out.println("expected list in foreach but got " + Register.typeStr(listR));
      return;
    } else {
      list = (Register.List) listR;
    }

    boolean runCode;

    // for each iteration:
    // if list is empty, stop.
    while(list.root != null) {
      // push machine context
      machine.pushContext("for");

      // add the variable to context
      machine.addParamToContext(varT.getText());

      // set the variable value
      machine.setValue(varT.getText(), list.root);

      // set runCode = true
      runCode = true;

      // if conditional is non-null, run it and set it's truth value to runCode
      if (conditionalT != null) {
	Register result = eval(conditionalT);
	runCode = isRegisterTrue(result);
      }

      // if code is non-null and runCode is true, run the code
      if (runCode && codeT != null) {
	discoverAllChildren(codeT);
      }
      
      // pop machine context
      machine.popContext();

      // set list = rest(list)
      list = new Register.List(list.root.next);
    }
    
  }

  public void doIf(Tree t) {
    // (IF_STATEMENT ifStatementStart altStatement* elseStatement?)
    // 0 to penultimate: IF_ALT nodes
    // ultimate: ELSE_ALT node
    // An IF_ALT node is: ^(IF_ALT parExpression block?)
    // AN ELSE_ALT node is: ^(ELSE_ALT block?)
    Tree child;
    for (int i=0; i < t.getChildCount(); i++) {
      child = t.getChild(i);
      if (child.getType() == IF_ALT) {
	//	if (MathUtils.toTruth(eval(child.getChild(0)).toString())) {
	if (isRegisterTrue(eval(child.getChild(0)))) {
	  if (child.getChildCount() > 1) {
	    discover(child.getChild(1));
	  }
	  break;
	}
      } else {
	// child must be an ELSE_ALT
	if (child.getChildCount() > 0) {
	  discover(child.getChild(0));
	}
      }
    }
  }

  public void doWhile(Tree t) {
    // 0: n_assignExpression, 1: block?
    while (MathUtils.toTruth(eval(t.getChild(0)).toString())) {
      if (t.getChildCount() > 1) {
	discoverAllChildren(t.getChild(1));
      }
    }
  }

  public void doRepeat(Tree t) {
    // 0: n_assignExpression, 1: block?
    float timesF = MathUtils.toFloat(eval(t.getChild(0)).toString());
    int times = (int) timesF;
    for (int i=0; i < times; i++) {
      if (t.getChildCount() > 1) {
	discoverAllChildren(t.getChild(1));
      }
    }
  }
  
  public void doShape(Tree t) {
    // 0: shape name, 1: block?
    String name = eval(t.getChild(0)).toString();
    soup.enterShapeDef(name);
    if (t.getChildCount() > 1) {
      discoverAllChildren(t.getChild(1));
    }
    soup.exitShapeDef();
  }

  public void doFrom(Tree t) {
    // 0 through next to last: expressions
    // last: block
    int numExpr = t.getChildCount() - 1;
    String[] expressions = new String[numExpr];
    Turtle[] startingPoints = new Turtle[expressions.length];
    Turtle cursor = soup.getSourceList().getCursor();
    Tree blockOfCode = t.getChild(numExpr);

    for (int i=0; i < numExpr; i++) {
      expressions[i] = eval(t.getChild(i)).toString();
      startingPoints[i] = cursor.searchBackwards(expressions[i]);
    }
    for (Turtle myrtle : startingPoints) {
      soup.getSourceList().setCursor(myrtle);
      discoverAllChildren(blockOfCode);
    }
    soup.getSourceList().setCursor(cursor);
  }

  public void doReplace(Tree t, boolean replaceAll) {
    // 0: exprA, 1: exprB, 2: block
    String exprA = eval(t.getChild(0)).toString();
    String exprB = eval(t.getChild(1)).toString();
    soup.enterReplacement(exprA, exprB, replaceAll);
    if (t.getChildCount() > 2) {
      discoverAllChildren(t.getChild(2));
    }
    soup.exitReplacement();
  }

  public void discoverAllChildren(Tree t) {
    for (int i=0; i < t.getChildCount(); i++) {
      discover(t.getChild(i));
    }
  }

  public Register.List quoteChildren(Tree t, boolean quoteFirst, 
				     String optionalSeparator) {
    Register root = null;
    Register curs = null;
    int startIdx = quoteFirst ? 0 : 1;
    for (int i = startIdx; i < t.getChildCount(); i++) {
      if (root == null) {
	root = quote(t.getChild(i));
	curs = root;
      } else {
	if (optionalSeparator != null) {
	  curs.next = rs(optionalSeparator);
	  curs = curs.next;
	}
	curs.next = quote(t.getChild(i));
	curs = curs.next;
      }
    }
    Register.List ret = new Register.List(root);
//     boolean isListExpr = false;
//     if (t.getChildCount() > startIdx && t.getChild(startIdx).getType() == LIST_EXPR) {
//       isListExpr = true;
//     }
//     Debug.out("FlatInterpreter", "Is tree a LIST_EXPR? " + isListExpr);
//    Debug.out("FlatInterpreter", "source code string: " + unlist(ret, true));
    return ret;
  }


//   public Register.List cons(Register nubeOrig, Register.List list) {
//     Register nube = nubeOrig.copy();
//     Register.List ret = new Register.List(nube);
//     nube.next = list.root;
//     return ret;
//   }
  


  public Register.List makeList(boolean makeCopy, Register... registers) {
    Register root = null;
    Register curs = null;
    for (Register r : registers) {
      if (makeCopy) {
	r = r.copy();
      }
      if (root == null) {
	root = r;
	curs = root;
      } else {
	curs.next = r;
	curs = r;
      }
    }
    return new Register.List(root);
  }

  public Register q(Tree t, int idx) {
    return quote(t.getChild(idx));
  }

  public String qs(Tree t, int idx) {
    return t.getChild(idx).getText();
  }

  public Register.Str rs(String... strings) {
    StringBuffer buf = new StringBuffer();
    for (String s : strings) {
      buf.append(s + " ");
    }
    return new Register.Str(buf.toString().trim());
  }

  public String unlist(Register.List list, boolean topLevel) {
    StringBuffer buf = new StringBuffer();
    Register curs = list.root;
    boolean first = true;
    while (curs != null) {
      if (first) {
	first = false;
      } else {
	buf.append(" ");
      }
      
      if (curs instanceof Register.List) {
	buf.append(unlist((Register.List)curs, false));
      } else {
	buf.append(curs.toString());
      }
      curs = curs.next;
    }
    return buf.toString();
  }

  public Register.List qlist(Tree t, int idx, String sep) {
    // t is a LIST_EXPR node, which just has a bunch of children, all
    // of which are expressions.
    Register root = rs("[");
    Register curs = root;
    boolean first = true;
    for (int i=idx; i < t.getChildCount(); i++) {
      if (first) {
	first = false;
      } else {
	curs.next = rs(sep);
	curs = curs.next;
      }
      curs.next = q(t,i);
      curs = curs.next;
    }
    curs.next = rs("]");
    
    return new Register.List(root);
  }

  public Register quote(Tree t) {
    //    Debug.out("FlatInterpreter", "quote this thing: " + FlatDebugger.bug(t, true));
    Register ret = Register.getNil();
    switch (t.getType()) {
    case ID: ret = rs(t.getText()); break;
    case STR_LITERAL: ret = rs(t.getText()); break;
    case NUM: ret = rs(t.getText()); break;
    case TRUE: ret = rs("true"); break;
    case FALSE: ret = rs("false"); break;
    case PROG: ret = list("program{{broken}}"); break;
    case FUNCTION_DEF_SIGNATURE: ret = list("fundef", "{{broken}}"); break;
    case FUNCTION_CALL: ret = list(q(t,0), "(", quoteChildren(t,false, ", "), ")"); break;
    case STATEMENT: ret = q(t,0); break;
    case PARAM_LIST: ret = list("paramlist", "{{broken}}"); break;
    case PARAM: ret = list("param", "{{broken}}"); break;
    case EXPR: ret = ret = q(t,0); break; 
    case BLOCK: ret = quoteChildren(t, true, null); break;
    case REPLACE: ret = list("replace", "{{broken}}"); break;
    case REPLACE_ALL: ret = list("replaceAll", "{{broken}}"); break;
    case SHAPE: ret = list("shape", "{{broken}}"); break;
    case FROM: ret = list("from", "{{broken}}"); break;
    case N_ADD_EXPR: ret = list("(", q(t,0), " + ", q(t,1), ")"); break;
    case N_MINUS_EXPR: ret = list("(", q(t,0), " - ", q(t,1), ")"); break;
    case N_MULT_EXPR: ret = list("(", q(t,0), " * ", q(t,1), ")"); break;
    case N_DIV_EXPR: ret = list("(", q(t,0), " / ", q(t,1), ")"); break;
    case N_MODULO_EXPR: ret = list("(", q(t,0), " % ", q(t,1), ")"); break;
    case N_ASSIGN_EXPR: ret = list("(", q(t,0), " = ", q(t,1), ")"); break;
    case N_UNARY_EXPR: ret = list("unary", "{{broken}}"); break;
    case N_UNARY_NEG_EXPR: ret = list("-", qs(t,0)); break;
    case N_UNARY_POS_EXPR: ret = q(t,0); break;
    case N_LT_EXPR: ret = list("(", q(t,0), " < ", q(t,1), ")"); break;
    case N_LTEQ_EXPR: ret = list("(", q(t,0), " <= ", q(t,1), ")"); break;
    case N_GTEQ_EXPR: ret = list("(", q(t,0), " >= ", q(t,1), ")"); break;
    case N_GT_EXPR: ret = list("(", q(t,0), " > ", q(t,1), ")"); break;
    case N_EQ_EXPR: ret = list("(", q(t,0), " == ", q(t,1), ")"); break;
    case N_NEQ_EXPR:ret = list("(", q(t,0), " != ", q(t,1), ")"); break;
    case N_AND_EXPR: ret = list("(", q(t,0), " and ", q(t,1), ")"); break;
    case N_OR_EXPR: ret = list("(", q(t,0), " or ", q(t,1), ")"); break;
    case WHILE_STATEMENT: ret = list("while", "{{broken}}"); break;
    case REPEAT_STATEMENT: ret = list("repeat", "(", q(t,0), ")", q(t, 1), "done") ; break;
    case IF_STATEMENT: ret = list("if", "{{broken}}"); break;
    case IF_ALT: ret = list("alt", "{{broken}}"); break;
    case ELSE_ALT: ret = list("else", "{{broken}}"); break;
    case LITERAL: ret = list("literal", "{{broken}}"); break;
    case INDEX_EXPR: ret = list("index", "{{broken}}"); break;
    case LIST_EXPR: ret = qlist(t, 0, ", "); break;
    case MEMBER_EXPR: ret = list(q(t,0), ".", q(t,1)); break;
    case MEMBER_EXPR_DOT: ret = q(t,0); break;
    default:
      Debug.out("FlatInterpreter", "Unknown tree node in quote(): " + FlatDebugger.bug(t, true));
    }

    return ret;
  }

  public Register eval(Tree t) {
    Register ret = Register.getNil();
    if (Thread.interrupted()) {
      Debug.out("FlatInterpreter", "Interrupted. Stopping eval.");
    } else {
      boolean reportBack = false;
      if (debugMonitor != null && t.getType() == EXPR) {
	reportBack = true;
	synchronized (debugMonitor) {
	  while (!debugMonitor.consumeExpression()) {
	    try {
	      repaint();
	      debugMonitor.wait(200);
	    } catch (InterruptedException ex) { ; }
	  }
	  Register reginald = quote(t);
	  if (reginald instanceof Register.List) {
	    String code = unlist((Register.List) reginald, true);
	    debugMonitor.setCurrentDebugInfo(code, t.getLine(), 
					     t.getCharPositionInLine(), 
					     machine.getReturnValue(), debugMonitor.isAborted());
	  }
	}
      }
      
      if (debugMonitor != null && debugMonitor.isAborted()) {
	Debug.out("FlatInterpreter", "Looks like the show is cancelled. Returning nil.");
	return ret;
      }

      switch (t.getType()) {
      case EXPR:
	ret = eval(t.getChild(0));
	machine.setReturnValue(ret);
	break;
      case FUNCTION_CALL:
	ret = doFunctionCall(t);
	break;
      case PARAM:
	ret = machine.getValue(t.getChild(0).getText());
	break;
      case INFINITY:
	ret = new Register.Num(Double.MAX_VALUE);
	break;
      case OBJECT:
	ret = new Register.ObjRef();
	break;
      case NUM:
	ret = new Register.Num(t.getText());
	break;
      case ID:
	ret = machine.getValue(t.getText());
	break;
      case STR_LITERAL:
	ret = new Register.Str(MachineState.stripQuotes(t.getText()));
	break;
      case N_UNARY_POS_EXPR:
	ret = eval(t.getChild(0));
	break;
      case N_UNARY_NEG_EXPR:
	ret = new Register.Num("-" + eval(t.getChild(0)));
	break;
      case N_ASSIGN_EXPR:
	ret = doNAssignment(t.getChild(0), t.getChild(1));
	break;
      case N_ADD_EXPR:
	ret = doNArithmeticExpr(t.getChild(0), t.getChild(1), '+');
	break;
      case N_MINUS_EXPR:
	ret = doNArithmeticExpr(t.getChild(0), t.getChild(1), '-');
	break;
      case N_MULT_EXPR:
	ret = doNArithmeticExpr(t.getChild(0), t.getChild(1), '*');
	break;
      case N_DIV_EXPR:
	ret = doNArithmeticExpr(t.getChild(0), t.getChild(1), '/');
	break;
      case N_MODULO_EXPR:
	ret = doNArithmeticExpr(t.getChild(0), t.getChild(1), '%');
	break;
      case N_LT_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), '<');
	break;
      case N_LTEQ_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), '[');
	break;
      case N_GT_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), '>');
	break;
      case N_GTEQ_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), ']');
	break;
      case N_EQ_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), '=');
	break;
      case N_NEQ_EXPR:
	ret = doNEqualityExpr(t.getChild(0), t.getChild(1), '!');
	break;
      case N_AND_EXPR:
	ret = doNBooleanExpr(t.getChild(0), t.getChild(1), '&');
	break;
      case N_OR_EXPR:
	ret = doNBooleanExpr(t.getChild(0), t.getChild(1), '|');
	break;
      case TRUE:
	ret = new Register.Boolean(true);
	break;
      case FALSE:
	ret = new Register.Boolean(false);
	break;
      case INDEX_EXPR:
	ret = doIndexExpr(t);
	break;
      case LIST_EXPR:
	ret = doList(t);      
	break;
      case MEMBER_EXPR:
	ret = doMemberExpr(t);
	break;
      default:
	Debug.out("FlatInterpreter", "Unknown tree node in eval(): " + FlatDebugger.bug(t, true));
      }
      
      if (debugEval) {
	Debug.out("FlatInterpreter", t + " evals to the value " + Register.debug(ret));
      }
      
      if (ret.flagClear) {
	ColorMeister.reset();
	soup.reset();
      }
      
      if (ret.flagRedraw && redrawMe != null) {
	soup.done();
	repaint();
      }
      
      if (reportBack) {
	Register reginald = quote(t);
	if (reginald instanceof Register.List) {
	  String code = unlist((Register.List) reginald, true);
	  debugMonitor.setCurrentDebugInfo(code, t.getLine(), 
					   t.getCharPositionInLine(), 
					   machine.getReturnValue(), debugMonitor.isAborted());
	}
      }
    }

    return ret;
  }

  public void repaint() {
    if (redrawMe != null) {
      redrawMe.repaint();
    }
  }

  public Register doMemberExpr(Tree t) {
    // t's children will always be:
    // 0: LEFT
    // 1: RIGHT
    // left and right are either n_literal, n_indexExpr,
    // parExpression, or listExpression.

    Register ret = Register.getNil();
    
    // Get the l-value
    Register left = eval(t.getChild(0));

    // If the l-value is nil, it means we are referencing an object
    // that doesn't exist. Add it to the machine context.
    if (left.isNil()) {
      machine.addParamToContext(t.getChild(0).getText());
      left = new Register.Str("{object}");
      machine.setValue(t.getChild(0).getText(), left);
    }

    // Push the l-value member context. This prevents the machine from
    // looking anywhere besides the direct context of the l-value.
    machine.pushMemberContext(left);

    // Resolve the r-value inside the constrained lookup environment
    ret = eval(t.getChild(1));
    if (t.getChild(1).getType() == ID) {
      ret.setReferent(t.getChild(1).getText(), left);
    }
    // pop the member context
    machine.popMemberContext();
// Debug.out("FlatInterpreter", "Returning member expression with referent " + ret.referentParent + "." + ret.referentID + " whose value is " + ret);
    
    return ret;
  }
  
  public Register doMemberExprOld(Tree t, boolean includeLast) {
    // 0: an object reference
    // 1: a member expression dot list, resolve in order they appear
    Register parent = eval(t.getChild(0));
    Debug.out("FlatInterpreter", "found parent: " + Register.debug(parent));
    Register ref = parent;
    // cheat sheet: aRegister.setMember(aString, anotherRegister)
    String dot;
    Tree dots = t.getChild(1);
    int limit = includeLast ? dots.getChildCount() : dots.getChildCount() - 1;
    Debug.out("FlatInterpreter", "Looking through " + limit + " of the children of " + 
	      FlatDebugger.bug(dots, true));
    for (int i=0; i < limit; i++) {
      dot = dots.getChild(i).getText();
      ref = parent.getMember(dot);
      Debug.out("FlatInterpreter", "Looking in parent for '" + dot + "'. Parent's member map is:");
      Debug.out("FlatInterpreter", parent.getMemberMapString());
      if (ref.isNil()) {
	// if we're on the LEFT side of an assignment, it's OK to
	// create this value.
 	if (lvalCount > 0) {
 	  Debug.out("FlatInterpreter", "Good, i'm in the lvalue -- it is " + lvalCount);
 	  ref = new Register.Str("");
 	  parent.setMember(dot, ref);
 	} else {
 	  Debug.out("FlatInterpreter", "Bad, i'm not in the lvalue -- it is " + lvalCount);
	  break;
 	}
      }
      parent = ref;
    }
    return ref;
  }

  public Register doMemberExprOld2(Tree t, boolean includeLast) {
    // 0: an object reference
    // 1: a member expression dot list, resolve in order they appear
    Debug.out("FlatInterpreter", "doMemberExpr with tree: " + FlatDebugger.bug(t, true));
    Register parent = eval(t.getChild(0));
    Register ref = parent;
    String dot;
    Tree dots = t.getChild(1);
    int limit = includeLast ? dots.getChildCount() : dots.getChildCount() - 1;
    for (int i=0; i < limit; i++) {
      dot = dots.getChild(i).getText();
      ref = parent.getMember(dot);
      if (ref.isNil()) {
	// if we're on the LEFT side of an assignment, it's OK to
	// create this value.
 	if (lvalCount > 0) {
 	  Debug.out("FlatInterpreter", "Good, i'm in the lvalue -- it is " + lvalCount);
 	  ref = new Register.Str("");
 	  parent.setMember(dot, ref);
 	} else {
 	  Debug.out("FlatInterpreter", "Bad, i'm not in the lvalue -- it is " + lvalCount);
	  break;
 	}
      }
      parent = ref;
    }
    return ref;
  }
  
  public Register doIndexExpr(Tree t) {
    Register ret = Register.getNil();
    Register r = eval(t.getChild(0));
    if (r instanceof Register.List) {
      outer:
      for (int i=1; i < t.getChildCount(); i++) {
	Register idxReg = eval(t.getChild(1));
	int idx = (int) MathUtils.toFloat(idxReg.toString());
	r = ((Register.List)r).root;
	for (int j=0; j < idx; j++) {
	  if (r == null) {
	    Debug.out("FlatInterpreter", "index out of bounds");
	    r = Register.getNil();
	    break outer;
	  }
	  r = r.next;
	}
      }
      ret = r;
    }
    return ret;
  }

  public Register doList(Tree root) {
    Register rootOfList = null;
    Register cursor = null;
    for (int i=0; i < root.getChildCount(); i++) {
      Register child = eval(root.getChild(i)).copy();
      if (rootOfList == null) {
	rootOfList = child;
	cursor = child;
      } else {
	cursor.setNext(child);
	cursor = child;
      }
    }
    Register newList = new Register.List(rootOfList); // may be null!
    return newList;
  }

  public Register doAssignment(String setMe, Tree expr) {
    Debug.out("Error", "I don't believe this is called any longer");
    Register ret = eval(expr);
    machine.setValue(setMe, ret);
    return ret;
  }

  public Register doNAssignment(Tree lVal, Tree expr) {
    Register ret = eval(expr);
    Register bucket = eval(lVal);

//     Debug.out("FlatInterpreter", "doNAssignment ------- (start)");
//     Debug.out("FlatInterpreter", "doNAssignment ret: " + MachineState.stFull(ret));
//     Debug.out("FlatInterpreter", "doNAssignment = ");
//     Debug.out("FlatInterpreter", "doNAssignment bucket: " + MachineState.stFull(bucket));
//     Debug.out("FlatInterpreter", "doNAssignment ------- (end)");

    if (bucket.getReferentID() == null) {
      machine.setValue(evalToSymbol(lVal), ret);
    } else {
//       Debug.out("FlatInterpreter", "doNAssignment -------");
//       Debug.out("FlatInterpreter", "doNAssignment found that lVal gives a bucket with references: " + 
// 		FlatDebugger.bug(lVal, true));
//       Debug.out("FlatInterpreter", "doNAssignment bucket.referentParent = " + bucket.referentParent);
//       Debug.out("FlatInterpreter", "doNAssignment bucket.referentID = " + bucket.referentID);
//       Debug.out("FlatInterpreter", "doNAssignment ret = " + ret);
//       Debug.out("FlatInterpreter", "doNAssignment -------");
      machine.setValue(bucket.getReferentParent(), bucket.getReferentID(), ret);
      bucket.clearReferent();
    }
    return ret;
  }

  public Register doNArithmeticExpr(Tree a, Tree b, char op) {
    Register ret = Register.getNil();
    Register ra = eval(a);
    Register rb = eval(b);
    if (ra.type == rb.type && ra.type == Register.TYPE_NUMBER) {
      float aVal = MathUtils.toFloat(ra.toString());
      float bVal = MathUtils.toFloat(rb.toString());
      float result = 0f;
      switch (op) {
      case '+' : result = aVal + bVal; break;
      case '-' : result = aVal - bVal; break;
      case '*' : result = aVal * bVal; break;
      case '/' : result = aVal / bVal; break;
      case '%' : result = ((int)aVal) % ((int)bVal); break;
      default:
	Debug.out("FlatInterpreter", "Unknown arithmetic operator: " + op);
      }
      ret = new Register.Num(result);
    } else if ( (ra.type == Register.TYPE_VEC && rb.type == Register.TYPE_NUMBER) 
		||
		(ra.type == Register.TYPE_NUMBER && rb.type == Register.TYPE_VEC) ) {
      Register.Num number = (Register.Num) ((ra.type == Register.TYPE_NUMBER) ? ra : rb);
      Register.Vec vector = (Register.Vec) ((ra.type == Register.TYPE_VEC) ? ra : rb);
      switch (op) {
      case '*' : ret = vector.mult(number.v); break;
      case '/' : ret = vector.mult(1.0 / number.v); break;
      case '+' : 
      case '-' : 
      case '%' : 
      default  :
	Debug.out("FlatInterpreter", "Unknown operator with a number and vector: " + op);
      }
    } else if (ra.type == rb.type && ra.type == Register.TYPE_VEC) {
      Register.Vec va = (Register.Vec) ra;
      Register.Vec vb = (Register.Vec) rb;
      switch (op) {
      case '+' : ret = va.add(vb); break;
      case '-' : ret = va.add(vb.flip()); break;
      case '*' : 
      case '/' : 
      case '%' : 
      default  :
	Debug.out("FlatInterpreter", "Unknown operator with two vectors: " + op);
      }
    } else if (op == '+') {
      ret = new Register.Str(ra.toString() + rb.toString());
    }
    return ret;
  }

  public Register doNBooleanExpr(Tree at, Tree bt, char op) {
    boolean a = MathUtils.toTruth(eval(at).toString());
    boolean b = MathUtils.toTruth(eval(bt).toString());
    boolean result = false;
    switch (op) {
    case '&' : result = a && b; break;
    case '|' : result = a || b; break;
    default:
      Debug.out("FlatInterpreter", "Unknown boolean operator: " + op);
    }
    return new Register.Boolean(result);
  }

  public Register doNEqualityExpr(Tree at, Tree bt, char op) {
    debugEval = false;
    Register regA = eval(at);
    Register regB = eval(bt);
    //    Debug.out("FlatInterpreter", "equality '" + op + "' of " + Register.debug(regA) + " and " + Register.debug(regB));
    boolean result = false;
    if (regA.type == regB.type && regA.type == Register.TYPE_NUMBER) {
      // comparing numbers
      float a = MathUtils.toFloat(regA.toString());
      float b = MathUtils.toFloat(regB.toString());
      switch (op) {
      case '<' : result = a <  b; break;
      case '[' : result = a <= b; break;
      case '>' : result = a >  b; break;
      case ']' : result = a >= b; break;
      case '=' : result = a == b; break;
      case '!' : result = a != b; break;
      }
    } else if (regA.type == regB.type && regA.type == Register.TYPE_STRING) {
      String a = ((Register.Str)regA).v;
      String b = ((Register.Str)regB).v;
      switch (op) {
      case '=' : result = a.equals(b); break;
      case '!' : result = !a.equals(b); break;
      }
    } else if (regA.type == regB.type && regA.type == Register.TYPE_BOOLEAN) {
      boolean a = ((Register.Boolean)regA).b;
      boolean b = ((Register.Boolean)regB).b;
      switch (op) {
      case '=' : result = a == b; break;
      case '!' : result = a != b; break;
      }
    } else if (regA.type == regB.type && regA.type == Register.TYPE_OBJECT_REF) {
      Register.Obj obA = (Register.Obj) ((Register.ObjRef)regA).obj;
      Register.Obj obB = (Register.Obj) ((Register.ObjRef)regB).obj;
      switch (op) {
      case '=' : result = obA == obB; break;
      case '!' : result = obA != obB; break;
      }
    } else if (regA.type == Register.TYPE_NIL ^ regB.type == Register.TYPE_NIL) {
      switch (op) {
      case '=' : result = false; break;
      case '!' : result = true; break;
      }
    } else {
      Debug.out("FlatInterpreter", "Can not equate types " + Register.typeStr(regA) + " and " + Register.typeStr(regB));
    }
      debugEval = false;

    return new Register.Boolean(result);
  }

  public Object evalToSymbol(Tree t) {
    Object ret = "could not eval to symbol";
    switch (t.getType()) {
    case ID:
      ret = t.getText();
      break;
    case MEMBER_EXPR:
      ret = doMemberExpr(t);
      break;
    default:
      ret = evalToSymbol(t.getChild(0));      
    }
    
    //    Register ret = eval(t);
    //    Debug.out("FlatInterpreter", "Evaluated this to a symbol: " + 
    //	      FlatDebugger.bug(t, true) + " == " + ret);
    
    return ret;
  }

  public Register doFunctionCall(Tree t) {

    Register ret = Register.getNil();
    Register.Function f = null;
    Builtin b = null;
    String funcName = t.getChild(0).getText();
    
    boolean pushed = false;
    
    if (funcName.equals("quote")) {
      //      machine.setReturnValue(quoteChildren(t, false, null));
      machine.setReturnValue(q(t,1));
    } else {
      if (machine.getValue(funcName) instanceof Register.Function) {
	f = (Register.Function) machine.getValue(funcName);
      } else {
	b = builtins.get(funcName);
      }
      
      if (b == null && f == null) {
	Debug.out("FlatInterpreter", "No function named '" + 
		  t.getChild(0).getText() + "'");
      } else {
	List<Register> args = new ArrayList<Register>();
	for (int i=1; i < t.getChildCount(); i++) {
	  args.add(eval(t.getChild(i)));
	  args.get(args.size()-1).clearReferent();
	}

	if (b != null) {
	  pushed = true;
	  machine.pushContext("function call: " + funcName);
	  ret = b.eval(args);
	  machine.setReturnValue(ret);
	} else { // user-defined function
	  if (args.size() == f.params.size()) {
 	    machine.pushContext("function call: " + funcName);
 	    machine.setSafeMode(f.isSafe);
	    for (int i=0; i < args.size(); i++) {
	      machine.addParamToContext(f.params.get(i));
	      machine.setValue(f.params.get(i), (args.get(i)));
	    }
	    pushed = true;
// 	    machine.pushContext("function call: " + funcName);
// 	    machine.setSafeMode(f.isSafe);
	    discover(f.block);
	    machine.setSafeMode(false);
	  } else {
	    Debug.out("FlatInterpreter", "Warning: wrong argcount for '" + f.name + 
		      "'. Got " + args.size() + ", exp. " + f.params.size());
	  }
	}
      }
    }
    if (pushed) {
      ret = machine.getReturnValue();
      machine.popContext();
    }
    return ret;
  }

  public void stash(Tree t) {
    if (t.getType() != FUNCTION_DEF_SIGNATURE) {
      Debug.out("FlatInterpreter", "wth are you doing? you can't stash this: " + FlatDebugger.bug(t, true));
      return;
    }

    Register.Function fun = new Register.Function(t);
    machine.setValue(fun.name, fun);
    if (Builtin.evalCount > 0) {
      System.out.println("Defined function '" + fun.name + "'");
    }
  }


  static boolean onFreshLine(StringBuffer buf) {
    boolean ret = true;
    for (int i=buf.length() - 1; i >= 0; i--) {
      if (buf.charAt(i) == '\n') {
	break;
      } else if (buf.charAt(i) != ' ') {
	ret = false;
	break;
      }
    }
    return ret;
  }

  public Register.List list(Object... items) {
    List better = new ArrayList();
    for (Object item : items) {
      better.add(item);
    }
    return listFromList(better);
  }

  public Register.List listFromList(List items) {
    Register root = null;
    Register curs = null;
    Register r;
    for (Object item : items) {
      if (item instanceof String) {
	r = new Register.Str((String)item);
      } else if (item instanceof Register) {
	r = ((Register)item);
      } else {
	Debug.out("FlatInterpreter", "No idea what " + item + " is (class=" + item.getClass().getName() + ")");
	break;
      }
      if (root == null) {
	root = r;
	curs = root;
      } else {
	curs.next = r;
	curs = curs.next;
      }
    }
    return new Register.List(root);
  }


}
