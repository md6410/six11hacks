package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import org.antlr.runtime.tree.*;
import static org.six11.flatcad.flatlang.FlatLangLexer.*;
import java.util.List;
import java.util.ArrayList;

// /**
//  * 
//  **/
// public class Function {

//   public String name;
//   public List<String> params;
//   public Tree block;

//   public Function(Tree t) {
//     // child 0: an ID, the name of the function
//     // child 1: a param list
//     // child 2: (optional) a block of code

//     name = t.getChild(0).getText();
    
//     params = new ArrayList<String>();
//     for (int i=0; i < t.getChild(1).getChildCount(); i++) {
//       params.add(t.getChild(1).getChild(i).getText());
//     }
    
//     if (t.getChildCount() > 1) {
//       block = t.getChild(2);
//     }
//   }

// }
