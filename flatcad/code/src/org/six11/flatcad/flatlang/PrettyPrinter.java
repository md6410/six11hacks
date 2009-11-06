package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.*;
import static org.six11.flatcad.flatlang.FlatLangLexer.*;
import java.util.Stack;

/**
 * 
 **/
public class PrettyPrinter {

  private static Stack<Boolean> ifs = new Stack<Boolean>();
  private static boolean first = true;
  
  public static void zip(StringBuffer buf, int ilev) {
    buf.append("\n");
    buf.append(FlatDebugger.spaces(ilev * 2));
    first = true;
  }

  public static void append(StringBuffer buf, String addMe) {
    buf.append(addMe);
    first = false;
  }

  public static String getOpString(int type) {
    switch (type) {
    case N_ASSIGN_EXPR: return " = ";
    case N_ADD_EXPR: return "+";
    case N_MINUS_EXPR: return "-";
    case N_MULT_EXPR: return "*";
    case N_DIV_EXPR: return "/";
    case N_MODULO_EXPR: return "%";
    case N_LT_EXPR: return " < ";
    case N_LTEQ_EXPR: return " <= ";
    case N_GT_EXPR: return " > ";
    case N_GTEQ_EXPR: return " >= ";
    case N_EQ_EXPR: return " == ";
    case N_NEQ_EXPR: return " != ";
    case N_AND_EXPR: return " and ";
    case N_OR_EXPR: return " or ";
    }
    return "Unknown";
  }

  public static String pprint(Register.Function fun) {
    //      PrettyPrinter.pprint(buf, ((Register.Function) r).block, 0);
    //    FlatDebugger.processTree(fun.block, 0);
    StringBuffer buf = new StringBuffer();
    append(buf, "define " + fun);
    pprint(buf, fun.block, 0);
    append(buf, "\ndone");
    zip(buf, 0);
    return buf.toString();
  }

  public static void pprintChildrenRange(StringBuffer buf, Tree t, 
					 int ilev, String optionalSeparator, 
					 int start, int endNotIncluded) {
    for (int i=start; i < endNotIncluded; i++) {
      if (optionalSeparator != null && i > start) {
	append(buf, optionalSeparator);
      }
      pprint(buf, t.getChild(i), ilev);
    }
  }

  public static void pprintChildren(StringBuffer buf, Tree t, int ilev) {
    pprintChildrenRange(buf, t, ilev, null, 0, t.getChildCount());
  }

  public static void pprint(StringBuffer buf, Tree t, int ilev) {
    
    switch (t.getType()) {
    case PROG:
    case BLOCK:
      pprintChildren(buf, t, ilev+1);
      break;
    case STATEMENT:
      zip(buf, ilev);
      pprint(buf, t.getChild(0), ilev);
      break;
    case EXPR:
      pprint(buf, t.getChild(0), ilev);
      break;
    case FUNCTION_DEF_SIGNATURE:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case REPLACE: 
      append(buf, FlatDebugger.bug(t, true));
      break;
    case REPLACE_ALL: 
      append(buf, FlatDebugger.bug(t, true));
      break;
    case FROM:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case SHAPE:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case WHILE_STATEMENT:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case REPEAT_STATEMENT:
      zip(buf, ilev);
      append(buf, "repeat");
      append(buf, "(");
      pprint(buf, t.getChild(0), ilev);
      append(buf, ")");
      pprint(buf, t.getChild(1), ilev);
      zip(buf, ilev);
      append(buf, "done");
      break;
    case IF_STATEMENT:
      ifs.push(true);
      pprintChildren(buf, t, ilev);
      zip(buf, ilev);
      append(buf, "done");
      ifs.pop();
      break;
    case IF_ALT:
      zip(buf, ilev);
      if (ifs.peek()) {
	append(buf, "if ");
	ifs.pop();
	ifs.push(false);
      } else {
	append(buf, "alt ");
      }
      pprint(buf, t.getChild(0), ilev);
      pprint(buf, t.getChild(1), ilev);
      break;
    case ELSE_ALT:
      zip(buf, ilev);
      append(buf, "else ");
      pprint(buf, t.getChild(0), ilev);
      break;
    case FUNCTION_CALL:
      pprint(buf, t.getChild(0), ilev);
      append(buf, "(");
      pprintChildrenRange(buf, t, ilev, ", ", 1, t.getChildCount());
      append(buf, ")");
      break;
    case PARAM:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case NUM:
      append(buf, t.getText());
      break;
    case ID:
      append(buf, t.getText());
      break;
    case STR_LITERAL:
      append(buf, t.getText());
      break;
    case N_UNARY_POS_EXPR:
      pprint(buf, t.getChild(0), ilev);
      break;
    case N_UNARY_NEG_EXPR:
      append(buf, "-");
      pprint(buf, t.getChild(0), ilev);
      break;
    case N_ASSIGN_EXPR:
    case N_ADD_EXPR:
    case N_MINUS_EXPR:
    case N_MULT_EXPR:
    case N_DIV_EXPR:
    case N_MODULO_EXPR:
    case N_LT_EXPR:
    case N_LTEQ_EXPR:
    case N_GT_EXPR:
    case N_GTEQ_EXPR:
    case N_EQ_EXPR:
    case N_NEQ_EXPR:
    case N_AND_EXPR:
    case N_OR_EXPR:
      boolean doParens = true;
      if (t.getType() == N_ASSIGN_EXPR && first) {
	doParens = false;
      }
      if (doParens) append(buf, "(");
      pprint(buf, t.getChild(0), ilev);
      append(buf, getOpString(t.getType()));
      pprint(buf, t.getChild(1), ilev);
      if (doParens) append(buf, ")");
      break;
    case TRUE:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case FALSE:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case INDEX_EXPR:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case LIST_EXPR:
      append(buf, FlatDebugger.bug(t, true));
      break;
    case MEMBER_EXPR:
      append(buf, FlatDebugger.bug(t, true));
      break;
    default:
      Debug.out("FlatInterpreter", "Unknown tree node in pprint(): " + FlatDebugger.bug(t, true));

    }
  }
}
