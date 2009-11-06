package org.six11.flatcad.flatlang;

import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class DebugTreeModel extends DefaultMutableTreeNode {

  private FlatInterpreter interp;
  //  private List<TreeModelListener> listeners;

  public DebugTreeModel(FlatInterpreter interp) {
    super("FlatLang Machine State");
    this.interp = interp;
    //    listeners = new ArrayList<TreeModelListener>();
  }

  private MachineState m() {
    return interp.getMachine();
  }

//   public Object getRoot() {
//     return new N("FlatLang Machine State");
//   }

//   public Object getChild(Object parent, int index) {
    
//   }

//   public int getChildCount(Object parent) {

//   }

//   public boolean isLeaf(Object node) {

//   }

//   public void valueForPathChanged(TreePath path, Object newValue) {

//   }

//   public int getIndexOfChild(Object parent, Object child) {

//   }

//   public void addTreeModelListener(TreeModelListener l) {
//     if (!listeners.contains(l)) {
//       listeners.add(l);
//     }
//   }

//   public void removeTreeModelListener(TreeModelListener l) {
//     listeners.remove(l);
//   }

//   private class N extends DefaultMutableTreeNode {
//     public N(String s) {
//       super(s, true);
//     }
//   }
}
