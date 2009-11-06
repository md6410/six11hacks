package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class TurtleTree {
  Turtle root;
  Turtle insertionCursor;

  public TurtleTree(Turtle root) {
    this.root = root;
    this.insertionCursor = root;
  }

  public void add(Turtle baby) {
    insertionCursor.addChild(baby);
    baby.setParent(insertionCursor);
    insertionCursor = baby;
  }

  public Turtle getRoot() {
    return root;
  }

  public Turtle getCursor() {
    return insertionCursor;
  }

  public void setCursor(Turtle t) {
    insertionCursor = t;
    if (insertionCursor == null) {
      Debug.out("TurtleTree", "Note, you just set the insertion cursor to null.");
      new RuntimeException("forced error").printStackTrace();
    }
  }

  public void debug() {
    Turtle cursor = root;
    List beenThere = new ArrayList();
    while (cursor != null) {
      if (beenThere.contains(cursor)) {
	System.out.println("Found infinite loop in tree. " + cursor + " appears twice");
	System.exit(0);
      }
      Debug.out("TurtleTree", cursor + " (" + cursor.getChildCount() + " children)");
      beenThere.add(cursor);
      cursor = cursor.getNextTurtle(0);
    }
  }

  public void zipToEnd() {
    Turtle cursor = root;
    while (cursor.getChildCount() > 1) {
      cursor = cursor.getNextTurtle(cursor.getChildCount() - 1);
    }
    setCursor(cursor);
  }
  
  public TurtleTree copy() {
    Cp retRoot = copy(root);
    TurtleTree ret = new TurtleTree(retRoot.turtle);
    if (retRoot.curs != null) {
      ret.setCursor(retRoot.curs);
    } else {
      Debug.out("TurtleTree", "Could not locate the cursor for the copied tree!");
    }
    //    ret.zipToEnd();
    return ret;
  }

  private Cp copy(Turtle orig) {
    Turtle ret = orig.copy();
    Cp babyCp;
    Turtle baby;
    Turtle cursCopy = (orig == insertionCursor ? ret : null);
    for (int i=0; i < orig.getChildCount(); i++) {
      babyCp = copy(orig.getNextTurtle(i));
      baby = babyCp.turtle;
      if (babyCp.curs != null) {
	cursCopy = babyCp.curs;
      }
      ret.addChild(baby);
      baby.setParent(ret);
    }
    return new Cp(ret, cursCopy);
  }

  public String toString() {
    insertionCursor.setSpecial(true);
    StringBuffer buf = new StringBuffer();
    root.treeString(0, true, buf);
    String ret = buf.toString();
    insertionCursor.setSpecial(false);
    return ret;
  }

  static private class Cp {
    Turtle turtle;
    Turtle curs;
    Cp(Turtle t, Turtle c) {
      turtle = t;
      curs = c;
    }
  }

  public void calculateGlobalGeometry() {
    List<Turtle> undone = new ArrayList<Turtle>();
    undone.add(root);
    Turtle myrtle;
    while (undone.size() > 0) {
      myrtle = undone.remove(0);
      myrtle.calculateGlobalTransform();
      for (int i=0; i < myrtle.getChildCount(); i++) {
	undone.add(myrtle.getNextTurtle(i));
      }
    }
  }
  
}
