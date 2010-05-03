package org.six11.understand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.python.antlr.PythonLexer;
import org.python.antlr.PythonParser;
import org.python.antlr.PythonTokenSource;
import org.python.antlr.PythonTree;
import org.python.antlr.PythonTreeAdaptor;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.Name;
import org.python.core.AstList;
import org.six11.util.Debug;

/**
 * 
 **/
public class Main {
  public static void main(String[] args) throws RecognitionException, IOException {
    Debug.useColor = false;
    CharStream cs = new ANTLRFileStream(args[0]);
    PythonLexer lexer = new PythonLexer(cs);
    lexer.single = true;
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    PythonTokenSource indentedSource = new PythonTokenSource(tokens, args[0], true);
    tokens = new CommonTokenStream(indentedSource);
    PythonParser parser = new PythonParser(tokens, "utf-8");
    parser.setTreeAdaptor(new PythonTreeAdaptor());
    PythonParser.file_input_return r = parser.file_input();
    PythonTree root = (PythonTree) r.getTree();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream(root);
    nodes.setTokenStream(tokens);
    processTree(root, 1);
    // System.out.println(root.toStringTree());
  }

  private static void bug(String what) {
    Debug.out("Main", what);
  }

  public static Map<Integer, String> spaceMap = new HashMap<Integer, String>();

  public static String spaces(int n) {
    if (!spaceMap.containsKey(n)) {
      StringBuffer buf = new StringBuffer();
      for (int i = 0; i < n; i++) {
        buf.append(" ");
      }
      spaceMap.put(n, buf.toString());
    }
    return spaceMap.get(n);
  }

  public static String bug(PythonTree t, boolean bugChildrenTypes) {
    String childString = "" + t.getChildCount();

    if (bugChildrenTypes) {
      childString = "[";
      for (int i = 0; i < t.getChildCount(); i++) {
        childString += t.getChild(i).getToken().getText();
        if (i < (t.getChildCount() - 1)) {
          childString += ", ";
        }
      }
      childString += "]";
    }

    String id = t.getText();

    if (t instanceof Expr) {
      Expr ex = (Expr) t;
      // bug("blah " + ex.toStringTree());
      // bug("attributes of expr: " + ex.get_attributes());
      bug("Value of Expr: " + ex.getValue() + " (of class: " + ex.getValue().getClass() + ")");
      if (ex.getValue() instanceof Call) {
        Call c = (Call) ex.getValue();
        bug("In call: func is: " + c.getFunc() + ("of class: " + c.getFunc().getClass()) + ")");
        if (c.getFunc() instanceof Attribute) {
          Attribute att = (Attribute) c.getFunc();
          bugAttribute(att);
        }
      }
    }
    return t.getClass().toString() + " " + id + " line: " + t.getLine() + ", "
        + t.getCharStartIndex() + "-" + t.getCharStopIndex() + " col: " + t.getCharPositionInLine()
        + " type: " + PythonParser.tokenNames[t.getAntlrType()] + " children: " + childString;
  }

  public static void bugAttribute(Attribute att) {
    bug(att.getAttr() + " (of class " + att.getAttr().getClass() + ") = " + att.getValue()
        + " (of class: " + att.getValue().getClass() + ")");
    if (att.getAttr() != null && att.getAttr() instanceof Attribute) {
      bugAttribute((Attribute) att.getAttr());
    }
    if (att.getValue() != null && att.getValue() instanceof Attribute) {
      bugAttribute((Attribute) att.getValue());
    }
  }

  public static void processTree(PythonTree t, int indent) {
    if (t instanceof Call) {
      Call c = (Call) t;

    }
    System.out.println(spaces(3 * indent) + bug(t, true));

    for (int i = 0; i < t.getChildCount(); i++) {
      processTree(t.getChild(i), indent + 1);
    }
  }

}
