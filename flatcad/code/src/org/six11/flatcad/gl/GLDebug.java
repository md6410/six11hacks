package org.six11.flatcad.gl;

import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.six11.flatcad.gl.GLApp.allocInts;
import java.util.List;
import java.util.ArrayList;
import org.six11.util.Debug;

/**
 * Debugging OpenGL programs is a pain in the ass. This just numbs
 * your ass a little bit.
 */
public class GLDebug {
  
  IntBuffer buf;
  List<GLDebugEntry> states = new ArrayList<GLDebugEntry>();

  public GLDebug() {
    buf = allocInts(16);
    states = new ArrayList<GLDebugEntry>();

    states.add(new GLDebugEntry(GL_LIGHTING, "GL_LIGHTING"));
    states.add(new GLDebugEntry(GL_BLEND, "GL_BLEND"));
    states.add(new GLDebugEntry(GL_CULL_FACE, "GL_CULL_FACE"));
    states.add(new GLDebugEntry(GL_DEPTH_TEST, "GL_DEPTH_TEST"));
    states.add(new GLDebugEntry(GL_SCISSOR_TEST, "GL_SCISSOR_TEST"));
    states.add(new GLDebugEntry(GL_STENCIL_TEST, "GL_STENCIL_TEST"));
    states.add(new GLDebugEntry(GL_TEXTURE_1D, "GL_TEXTURE_1D"));
    states.add(new GLDebugEntry(GL_TEXTURE_2D, "GL_TEXTURE_2D"));
    populate(false);
  }

  public void populate(boolean warn) {
      for (GLDebugEntry entry : states) {
	try {
	  entry.populate(buf, warn);
	} catch (Exception ex) {
	  Debug.out("GLDebug", "Got exception while on " + entry + ".");
	  ex.printStackTrace();
	}
      }
  }

  public String getComparison(GLDebug other) {
    StringBuffer buf = new StringBuffer();
    for (int i=0; i < states.size(); i++) {
      if(states.get(i).value != other.states.get(i).value) {
	buf.append(states.get(i).humanName + " changed from " + 
		   states.get(i).value + " to " + other.states.get(i).value + "\n");
      }
    }
    return buf.toString();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    for (GLDebugEntry entry : states) {
      buf.append(entry + "\n");
    }
    return buf.toString();
  }

  class GLDebugEntry {
    int propertyCode;
    String humanName;
    int value;
    int oldValue;
    boolean populated;

    GLDebugEntry(int propertyCode, String humanName) {
      this.propertyCode = propertyCode;
      this.humanName = humanName;
      this.populated = false;
    }

    void populate(IntBuffer buf, boolean warn) {
      oldValue = value;
      glGetInteger(propertyCode, buf);
      value = buf.get(0);
      if (warn && populated && value != oldValue) {
	Debug.out("GLDebug", humanName + " has changed from " + oldValue + " to " + value);
      }
      populated = true;
    }

    public String toString() {
      return humanName + "\t" + value;
    }
  }
}
