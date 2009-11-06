package org.six11.flatcad.model;

import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.hpgl.HPGL;
import org.six11.flatcad.gl.GLApp;
import org.six11.util.Debug;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 **/
public class Turtle {

  public static boolean global_pen = true; // pen starts down
  public static final int PEN_DOWN = 0;
  public static final int PEN_USE_CURRENT = 1;
  public static final int PEN_UP = 2;

  public float move_amt;
  public float rotZdir; // left/right
  public float rotYdir; // roll
  public float rotXdir; // pitch
  public int penState;
  
  protected Vertex startVertex;
  protected Vertex endVertex;
  public boolean h_down;

  public Turtle () {
    this.move_amt = 0f;
    this.rotXdir = 0f;
    this.rotYdir = 0f;
    this.rotZdir = 0f;
    this.penState = PEN_USE_CURRENT;
  }

  public Turtle(float move_amt, float yawDir, float rollDir, float pitchDir, int penState) {
    this.move_amt = move_amt;
    this.rotZdir = yawDir;
    this.rotYdir = rollDir;
    this.rotXdir = pitchDir;
    this.penState = penState;
  }

  public Vertex getOpenGLStartVertex() {
    return startVertex;
  }

  public Vertex getOpenGLEndVertex() {
    return endVertex;
  }

  public String toString() {
    return "Turtle[" + Debug.num(move_amt) + " (yaw/roll/pitch) = (" + 
      Debug.num(rotXdir) + "/" +
      Debug.num(rotYdir) + "/" +
      Debug.num(rotZdir) + ")" +
      "(" + penState + ")]";
  }

  public Turtle copy() {
    return new Turtle(move_amt, rotXdir, rotYdir, rotZdir, penState);
  }

  /**
   * Do whatever this particular turtle command says to do: move,
   * rotate, blah blah.
   */
  public void go() {
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

    // draw a line?
    if (move_amt != 0.0 && isDown) {
      glBegin(GL_LINE_STRIP); {
	glVertex3f(0f, 0f, 0f);
	glVertex3f(0f, move_amt, 0f);
      } glEnd();

      if (startVertex == null) {
	startVertex = MathUtils.multiplyWithModelview(new Vertex(0d, 0d, 0d));
	h_down = isDown;
      }
    }

    // move to endpoint?
    if (move_amt != 0.0) {
      glTranslated(0f, move_amt, 0f);
    }

    if (endVertex == null) {
      endVertex = MathUtils.multiplyWithModelview(new Vertex(0d, 0d, 0d));
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

    // tell the print job about us, if there is one
    HPGL h = HPGL.getInstance();
    if (h.isPrinting()) {
      h.addTurtleAction(this); // it internally avoids duplicates
    }
  }
  
}
