package org.six11.flatcad.flatlang;

// shotgun imports FTW
import java.io.*;
import antlr.Token;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.*;


public class FlatDebugger {
  public static void main(String[] args) throws Exception {

    CharStream cs = new ANTLRFileStream(args[0]);
    FlatLangLexer flatLexer = new FlatLangLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(flatLexer);
    FlatLangParser flatParser = new FlatLangParser(tokens);

    FlatLangParser.prog_return r = flatParser.prog();
    CommonTree root = ((CommonTree) r.tree);
    
    CommonTreeNodeStream nodes = new CommonTreeNodeStream(root);
    nodes.setTokenStream(tokens);

    processTree(root, 1);
  }

  public static String spaces(int n) {
    String ret = "";
    switch (n) {
    case 0: break;
    case 1: ret = " ";              break;
    case 2: ret = "  ";             break;
    case 3: ret = "   ";            break;
    case 4: ret = "    ";           break;
    case 5: ret = "     ";          break;
    case 6: ret = "      ";         break;
    case 7: ret = "       ";        break;
    case 8: ret = "        ";       break;
    case 9: ret = "         ";      break;
    case 10: ret = "         ";     break;
    case 11: ret = "          ";    break;
    case 12: ret = "           ";   break;

    default:
      StringBuffer buf = new StringBuffer();
      for (int i=0; i < n; i++) {
	buf.append("  ");
      }
      ret = buf.toString();
    }
    return ret;
  }

  public static String bug(Tree t, boolean bugChildrenTypes) {
    String childString = "" + t.getChildCount();

    if (bugChildrenTypes) {
      childString = "[";
      for (int i=0; i < t.getChildCount(); i++) {
	childString += t.getChild(i).getText();
	if (i < (t.getChildCount() - 1)) {
	  childString += ", ";
	}
      }
      childString += "]";
    }

    return t.getText() + " line: " + t.getLine() + ", " +
      "col: " + t.getCharPositionInLine() + " type: " +
      t.getType() + " children: " + childString;
  }
  
  public static void processTree(Tree t, int indent) {
        
    System.out.println(spaces(indent) + bug(t, false));

    for (int i=0; i < t.getChildCount(); i++) {
      processTree(t.getChild(i), indent + 1);
    }
  }
}
