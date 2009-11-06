package org.six11.flatcad.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import org.six11.flatcad.command.BranchCursor;

import org.six11.util.Debug;
import static org.lwjgl.opengl.GL11.*;

// import org.six11.flatcad.geom.Pyramid;

/**
 * 
 **/
public class TurtleModel extends Model {
  
  List<Turtle> actions = new ArrayList<Turtle>();
  float matThickness = 0.1f;
  Stack<Map<String,String>> variableStack;
  Map<String, BranchCursor> callableCursors;
  BranchCursor codeCursor;

  public TurtleModel() {
    variableStack = new Stack<Map<String,String>>();
    variableStack.push(new HashMap<String,String>());
    callableCursors = new HashMap<String, BranchCursor>();
    codeCursor = new BranchCursor(this, null, new ArrayList<String>());
  }

  public BranchCursor getCodeCursor() {
    return codeCursor;
  }

  public void setCodeCursor(BranchCursor bc) {
    if (bc == null) {
      Debug.out("TurtleModel", "Unable to escape out of the top level of execution.");
    } else {
      codeCursor = bc;
    }
  }

  public void addAction(Turtle t) {
    actions.add(t);
  }

  public void addCallableCursor(String name, BranchCursor c) {
    callableCursors.put(name, c);
  }

  public boolean knowsCall(String name) {
    return callableCursors.containsKey(name);
  }
  
//   public void call(String name, List<String> args) {
//     // probably need to push on the variable stack, but whatever.
//     //    callableCursors.get(name).execute(args);
//   }

  public void setMatThickness(float t) {
    this.matThickness = t;
  }

  public float getMatThickness() {
    return matThickness;
  }

  public void draw() {
    glPushMatrix();
    glColor3f(1f, 1f, 1f);
    for (Turtle t : actions) {
      t.go();
    }
    glPopMatrix();
  }

  public static boolean isVariable(String what) {
    return what.startsWith(":");
  }

  public int getVariableStackDepth() {
    return variableStack.size();
  }

  public String getVariableValue(String symbolName) {
    String ret = "";
    for (int i=variableStack.size()-1; i >= 0; i--) {
      Map<String, String> lookupTable = variableStack.get(i);
      if (lookupTable.containsKey(symbolName)) {
	ret = lookupTable.get(symbolName);
	break;
      }
    }
    return ret;
  }
}
