package org.six11.flatcad.flatlang;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//import org.six11.util.pen.MultiSequence;
import org.six11.util.pen.Pt;
import java.io.IOException;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Direction;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.geom.PartDescription;
import org.six11.flatcad.hpgl.HPGL;
import org.six11.flatcad.gl.GLApp;
import org.six11.util.Debug;
import static org.lwjgl.opengl.GL11.*;
import org.apache.commons.math.linear.RealMatrix;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class Turtle {


  public static int INSTANCE_COUNT = 0;

  public static boolean global_pen = true; // pen starts down
  public static final int PEN_DOWN = 0;
  public static final int PEN_USE_CURRENT = 1;
  public static final int PEN_UP = 2;
  public static final int PEN_UNKNOWN_THIS_IS_AN_ERROR = 3;
  public static final double[] zeros = new double[] { 0d, 0d, 0d, 1d };

  public int instanceNumber;

  public boolean isDrawto = false;
  public float move_amt;
  public float rotZdir; // left/right
  public float rotYdir; // roll
  public float rotXdir; // pitch
  public int penState;
  public int permPenState = PEN_UNKNOWN_THIS_IS_AN_ERROR; // set when everything is final
  public String name;
  public String partName;
  
  protected Vertex glStartVertex;
  protected Vertex glEndVertex;
  public boolean h_down;

  protected RealMatrix localTransform;
  
  // globalTransform represents the model matrix state AFTER the
  // turtle has done its thing. This is the result of multiplying the
  // parent turtle's global state with our own local transform.
  protected RealMatrix globalTransform;
  protected Vertex globalPoint;

  protected Turtle parentTurtle = null;
  protected List<Turtle> childTurtles;
  protected boolean special; // used in debugging
  protected boolean debugGset;
  protected boolean xor;

  public Turtle () {
    this(0f, 0f, 0f, 0f, PEN_USE_CURRENT, null, null);
  }

  public static Turtle xor() {
    Turtle t = new Turtle();
    t.xor = true;
    return t;
  }

  public static Turtle forward(float amt) {
    return new Turtle(amt, 0f, 0f, 0f, PEN_USE_CURRENT, null, null);
  }

  public static Turtle yaw(float amt) {
    return new Turtle(0f, amt, 0f, 0f, PEN_USE_CURRENT, null, null);
  }

  public static Turtle roll(float amt) {
    return new Turtle(0f, 0f, amt, 0f, PEN_USE_CURRENT, null, null);
  }

  public static Turtle pitch(float amt) {
    return new Turtle(0f, 0f, 0f, amt, PEN_USE_CURRENT, null, null);
  }

  public static Turtle mark(String name) {
    return new Turtle(0f, 0f, 0f, 0f, PEN_USE_CURRENT, name, null);
  }

  public static Turtle part(String name) {
    return new Turtle(0f, 0f, 0f, 0f, PEN_USE_CURRENT, null, name);    
  }

  public static Turtle down() {
    return new Turtle(0f, 0f, 0f, 0f, PEN_DOWN, null, null);
  }

  public static Turtle up() {
    return new Turtle(0f, 0f, 0f, 0f, PEN_UP, null, null);
  }

  public Turtle(RealMatrix localTransform) {
    instanceNumber = INSTANCE_COUNT++;
    this.localTransform = localTransform;
    globalTransform = MathUtils.getIdentityMatrix();
    isDrawto = true;
    this.penState = PEN_USE_CURRENT;
  }

//   public Turtle(double ux, double uy, double uz,
// 		double vx, double vy, double vz,
// 		double wx, double wy, double wz,
// 		double tx, double ty, double tz) {
    
//   }

  public Turtle(float move_amt, float yawDir, float rollDir,
		float pitchDir, int penState, String name, String partName) {
    instanceNumber = INSTANCE_COUNT++;
    this.move_amt = move_amt;
    this.rotZdir = yawDir;
    this.rotYdir = rollDir;
    this.rotXdir = pitchDir;
    this.penState = penState;
    this.name = name;
    this.partName = partName;
    localTransform = MathUtils.getIdentityMatrix();
    globalTransform = MathUtils.getIdentityMatrix();

    if (move_amt != 0f) {
      localTransform = MathUtils.getTranslationMatrix
	(new Vertex(0d, (double) move_amt, 0d));
    } else {
      if (rotXdir != 0f) {
	localTransform = MathUtils.getRotationMatrix
	  (new Direction(1d, 0d, 0d), MathUtils.degToRad( -1d * (double) rotXdir));
      } else if (rotYdir != 0f) {
	localTransform = MathUtils.getRotationMatrix
	  (new Direction(0d, 1d, 0d), MathUtils.degToRad( -1d * (double) rotYdir));
      } else if (rotZdir != 0f) {
	localTransform = MathUtils.getRotationMatrix
	  (new Direction(0d, 0d, 1d), MathUtils.degToRad( -1d * (double) rotZdir));
      }
    }
  }

  public void setSpecial(boolean state) {
    special = state;
  }

  public void setParent(Turtle daddy) {
    parentTurtle = daddy;
  }

  public RealMatrix getGlobalTransform() {
    return globalTransform;
  }

  public int getPermPenState() {
    return permPenState;
  }

  public void setGlobalTransform(RealMatrix rm) {
    globalTransform = rm;
  }

  public RealMatrix calculateGlobalTransform() {
    boolean otherStuff = globalTransform == null || globalPoint == null;
    if (parentTurtle != null && otherStuff) {
      globalTransform = parentTurtle.getGlobalTransform().multiply(localTransform);
      globalPoint = new Vertex(globalTransform.operate(new Vertex(0,0,0).getData()));
      permPenState = (penState == PEN_USE_CURRENT) ? parentTurtle.getPermPenState() : penState;
    } else {
      permPenState = PEN_DOWN;
    }
    debugGset = true;
    return globalTransform;
  }

  public void addChild(Turtle baby) {
    if (childTurtles == null) {
      childTurtles = new ArrayList<Turtle>();
    }
    childTurtles.add(baby);
  }

  /**
   * Replaces a branch off of this turtle. Replace 'oldTurtle'
   * (preserving its position in the child list) with 'baby'. 
   */
  public void replaceChild(Turtle oldTurtle, Turtle baby) {
    int idx = childTurtles.indexOf(oldTurtle);
    if (idx >= 0) {
      oldTurtle.setParent(null);
      baby.setParent(this);
      childTurtles.set(idx, baby);
    } else {
      Debug.out("Turtle", "Can't find index of oldTurtle");
    }
  }

  public boolean isNamed() {
    return name != null;
  }

  public String getName() {
    return name;
  }

  public boolean isPart() {
    return partName != null;
  }

  public String getPartName() {
    return partName;
  }

  public Vertex getOpenGLStartVertex() {
    return glStartVertex;
  }

  public Vertex getOpenGLEndVertex() {
    return glEndVertex;
  }

  public String toString() {
    String ret;
    if (isNamed()) {
      ret = "Turtle " + instanceNumber + " stream mark \"" + name + "\"";
    } else if (isDrawto) {
      return "Turtle " + instanceNumber + " drawto";
    } else {
      ret = "Turtle " + instanceNumber + 
	" [" + Debug.num(move_amt) + " (yaw/roll/pitch) = (" + 
	Debug.num(rotZdir) + "/" +
	Debug.num(rotYdir) + "/" +
	Debug.num(rotXdir) + ")" +
	"(" + penState + ")]";
    }
    return ret;
  }

  public String toShortString() {
    String ret = "?";
    if (isNamed()) {
      ret = "-- " + name + " --";
    } else if (isPart()) {
      ret = "Part " + partName;
    } else {
      if (move_amt != 0f) {
	ret = "move";
      } else if (rotZdir != 0f) {
	ret = "turn";
      } else if (rotYdir != 0f) {
	ret = "roll";
      } else if (rotXdir != 0f) {
	ret = "pitch";
      } else if (penState == PEN_DOWN) {
	ret = "down";
      } else if (penState == PEN_UP) {
	ret = "up";
      }
    }
    if (special) {
      ret = "<<<" + ret + ">>>";
    }
    return ret;
  }

  public void treeString(int level, boolean showHashCode, StringBuffer buf) {
    buf.append(Debug.spaces(level));
    buf.append(toShortString());
    if (showHashCode) {
      buf.append("(" + hashCode() + ")");
    }
    for (int i=0; i < getChildCount(); i++) {
      buf.append("\n");
      getNextTurtle(i).treeString(level+4, showHashCode, buf);
    }
  }

  /**
   * Provides a copy of the local geometry of this turtle. The
   * returned Turtle does not have a parent, children, or any
   * knowledge of it's matrix transform or global position.
   */
  public Turtle copy() {
    return new Turtle(move_amt, rotZdir, rotYdir, rotXdir, penState, name, partName);
  }


  public void draw() {
    try {
      drawUntil(null, null);
    } catch (IOException ex) {
      // will never happen
    }
  }

  public void draw(PartDescription pd) {
    try {
      drawUntil(null, pd);
    } catch (IOException ex) {
      Debug.out("Turtle", "Pd is " + pd);
      ex.printStackTrace();
    }
  }

  private void writeLaser(boolean up, double[] data, PartDescription pd) {
//     if (up) {
//       mseq.startNew();
//     }
//     double[] glob = globalTransform.operate(zeros);
//     Pt pt = new Pt(((int) 1016 * glob[0]), ((int) 1016 * glob[1]));
//     mseq.add(pt);
  }

  /**
   * Send OpenGL commands down the pipe. This is used to draw this
   * particular turtle operation, which leaves things in the proper
   * state for drawing the next turtle op.
   *
   * When laser cutting you can write HPGL instructions to the
   * optional buffered writer. Just ignore the z coordinate.
   *
   * @param optionalEndPoint - If non-null, indicates a descendant
   * turtle operation where progress should stop. 
   * @param pd - An optional buffer for storing multi-line
   * geometry. It is ignored when null.
   */
  public void drawUntil(Turtle optionalEndPoint, PartDescription pd) throws IOException {

    boolean isDown;
    switch (penState) {
    case PEN_DOWN:
      isDown = true;
      global_pen = true;
      break;
    case PEN_UP:
      isDown = false;
      global_pen = false;
      break;
    default:
      isDown = global_pen;
    }

    if (pd != null && penState == PEN_UP) {
      pd.current().startSequence();
    }

    // draw a line directly from where we are to another location
    // indicated by localTransform? (the flatlang 'drawto()' function)
    if (isDrawto) {
      double[] other = localTransform.operate(zeros);

      if (isDown) {
	glBegin(GL_LINE_STRIP); {
	  glVertex3f(0f, 0f, 0f);
	  glVertex3f((float)other[0], (float)other[1], (float)other[2]);
	} glEnd();
      }

      if (pd != null && isDown) {
	Vertex a = new Vertex(parentTurtle.getGlobalTransform().operate(new Vertex(0,0,0).getData()));
	Vertex b = new Vertex(globalTransform.operate(new Vertex(0,0,0).getData()));
	pd.current().addSegment(a, b);
      }

      if (glStartVertex == null) {
	glStartVertex = MathUtils.multiplyWithModelview(new Vertex(0d, 0d, 0d));
	h_down = isDown;
      }

      DoubleBuffer dbuf = GLApp.allocDoubles(16);
      MathUtils.fillDoubleBuffer(localTransform, dbuf);
      glMultMatrix(dbuf);
    }

    // draw a line?
    if (move_amt != 0.0 && isDown) {
      glBegin(GL_LINE_STRIP); {
	glVertex3f(0f, 0f, 0f);
	glVertex3f(0f, move_amt, 0f);
      } glEnd();

      if (pd != null) {
	Vertex b = new Vertex(globalTransform.operate(new Vertex(0,0,0).getData()));
	Vertex transamt = new Vertex(globalTransform.operate(new double[] { 0d, -move_amt, 0d, 0d }));
	Vertex a = b.getTranslated(transamt);
	pd.current().addSegment(a, b);
      }

      if (glStartVertex == null) {
	glStartVertex = MathUtils.multiplyWithModelview(new Vertex(0d, 0d, 0d));
	h_down = isDown;
      }
    }

    if (move_amt != 0.0 && !isDown && pd != null) {

//       double[] other = localTransform.operate(new double[] { 0d, move_amt, 0d, 1d });
//       writeLaser(true, other, mseq);
    }

    // move to endpoint?
    if (move_amt != 0.0) {
      glTranslated(0f, move_amt, 0f);
    }

    if (glEndVertex == null) {
      glEndVertex = MathUtils.multiplyWithModelview(new Vertex(0d, 0d, 0d));
    }

    // turn?
    if (rotXdir != 0.0) {
      glRotatef(rotXdir, 1f, 0f, 0f);
    }

    if (rotYdir != 0.0) {
      glRotatef(rotYdir, 0f, 1f, 0f);
    }

    if (rotZdir != 0.0) {
      glRotatef(rotZdir, 0f, 0f, 1f);
    }

    if (xor) {
      if (glIsEnabled(GL_COLOR_LOGIC_OP)) {
	Debug.out("Turtle", "xor is currently enabled. Disabling it");
	glDisable(GL_COLOR_LOGIC_OP);
      } else {
	Debug.out("Turtle", "xor is currently disabled. Enabling it");
	glEnable(GL_COLOR_LOGIC_OP);
	glLogicOp(GL_XOR);    
      }
    }

    // is this the beginning of a named part?
    if (partName != null && pd != null) {
      pd.newPart(partName);
    }

    drawChildren(optionalEndPoint, pd);
  }

  public Vertex getGlobalPoint() {
    if (globalPoint == null) {
      globalPoint = new Vertex(globalTransform.preMultiply(new Vertex(0,0,0).getData()));
    }
    return globalPoint;
  }

  private static int PUSH_COUNT = 0;
  private void debugPush(boolean push) {
    if (push) {
      PUSH_COUNT++;
    } else {
      PUSH_COUNT--;
    }
    Debug.out("Turtle", "Push/Pop now at level " + PUSH_COUNT);
  }

  private void drawChildren(Turtle optionalEndPoint, PartDescription pd) {
    if (childTurtles != null) {
      boolean pushPop = childTurtles.size() > 1;
      
      synchronized (childTurtles) {
	for (Turtle t : childTurtles) {
	  if (t == optionalEndPoint) continue;
	  
	  if (pushPop) {
	    //	  debugPush(true);
	    if (pd != null) {
	      // 	    writeLaser(true, zeros, mseq);
	    }
	    glPushMatrix();
	  }
	  t.draw(pd);
	  if (pushPop) {
	    //	  debugPush(false);
	    glPopMatrix();
	  }
	}
      }
    }
  }

  /**
   * Gives you the 'next' turtle after this one. Since each turtle
   * could be the parent of any number of turtles (see
   * getChildCount()), you use the index to tell me which one you
   * want. If there isn't a turtle at that index this returns null
   * without complaining.
   */
  public Turtle getNextTurtle(int idx) {
    Turtle ret = null;
    if (childTurtles != null && childTurtles.size() > idx) {
      ret = childTurtles.get(idx);
    }
    return ret;
  }

  public int getChildCount() {
    int ret = 0;
    if (childTurtles != null) {
      ret = childTurtles.size();
    }
    return ret;
  }

  public int getPathIndex(Turtle other) {
    int ret = -1;
    for (int i=0; i < getChildCount(); i++) {
      if (getNextTurtle(i).getPathIndex(other) <= 0) {
	ret = i;
	break;
      }
    }
    return ret;
  }

  public Turtle search(String targetName, int childNumber, boolean serialOnly) {
    Turtle ret = null;
    if (targetName.equals(name)) {
      ret = this;
    } else {
      if (serialOnly && getChildCount() == 1) {
	ret = getNextTurtle(0).search(targetName, 0, serialOnly);
      } else if (!serialOnly) {
	for (int i=0; i < getChildCount(); i++) {
	  ret = getNextTurtle(i).search(targetName, 0, serialOnly);
	  if (ret != null) break;
	}
      } else {
	ret = null;
      }
    }
    return ret;
  }

  /**
   * Search forwards (starting from the root) and returns the FIRST
   * turtle in the tree with the given target name. (preorder search)
   */
  public Turtle search(String targetName) {
    Turtle ret = null;
    if (targetName.equals(name)) {
      ret = this;
    } else {
      for (Turtle t : childTurtles) {
	ret = t.search(targetName);
	if (ret != null) {
	  break;
	}
      }
    }
    return ret;
  }

  /**
   * Search backwards (in the parent direction) for a Turtle with the
   * given name. This search includes this turtle. The return value is
   * null if there is no turtle in the ancestor direction that goes by
   * that name.
   */
  public Turtle searchBackwards(String predName) {
    Turtle ret = null;
    if (predName.equals(getName())) {
      ret = this;
    } else if (parentTurtle != null) {
      ret = parentTurtle.searchBackwards(predName);
    }
    return ret;
  }

  /**
   * This is almost just like 'searchBackwards(predName)', except this
   * returns the child whose parent is predName, rather than the
   * parent itself. This is necessary if you need to know which branch
   * of a tree to take.
   */
  public Turtle searchBackwardsChildWithParent(String predName) {
    Turtle ret = null;
    if (parentTurtle != null && predName.equals(parentTurtle.getName())) {
      ret = this;
    } else if (parentTurtle != null) {
      ret = parentTurtle.searchBackwardsChildWithParent(predName);
    }
    return ret;
  }
  
  public Turtle getParent() {
    return parentTurtle;
  }

  public boolean hasParent() {
    return (parentTurtle != null);
  }
  
}
