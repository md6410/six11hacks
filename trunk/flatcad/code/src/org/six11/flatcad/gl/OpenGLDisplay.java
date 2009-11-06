package org.six11.flatcad.gl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ComponentEvent;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas; 
import static org.lwjgl.opengl.GL11.*;    // all are GL_* or gl*
import static org.lwjgl.opengl.glu.GLU.*; // all are GLU_* or glu*
import static org.six11.flatcad.gl.GLApp.buildFont;
import static org.six11.flatcad.gl.GLApp.glPrint;

import org.six11.util.Debug;
import org.six11.flatcad.geom.MatrixStack;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.geom.Namable;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.LineSegment;
import org.six11.flatcad.geom.Polyhedron;

import org.six11.flatcad.model.Model;
import org.six11.flatcad.model.Camera;

public class OpenGLDisplay extends AWTGLCanvas {

  // GL housekeeping vars
  protected boolean initialize = false;

  // size vars
  protected int w, h;
  protected boolean sizeChanged;

  // mouse
  protected Mouse mouse;

  // camera
  protected Camera camera;

  // things to draw
  //  protected GLDrawable scene;
  protected Model model;

  protected Thread jitterThread;
  protected boolean jitterStop;

  public OpenGLDisplay() throws LWJGLException {
    super();

    this.w           = -1;
    this.h           = -1;
    this.sizeChanged = false;

    this.camera = new Camera();

    this.mouse = new Mouse(this);
    this.model = new Model();

    addMouseMotionListener(mouse);
    addMouseListener(mouse);
  }

  public void jitter(final float dx, final float dy) {
    if (jitterThread != null) {
      jitterStop = true;
      try {
	Thread.sleep(60);
      } catch (Exception ignore) { }
      jitterStop = false;
    }

    jitterThread = new Thread() {
	public void run() {

	  while (!jitterStop) {
	    try {
	      Thread.sleep(30);
	      camera.rotate((double) dx, (double) dy);
	      repaint();
	    } catch (Exception ignore) {;}
	  }
	}
      };
    jitterThread.start();
  }


  public Camera getCamera() {
    return camera;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public Model getModel() {
    return model;
  }
  
  public void componentResized(ComponentEvent ev) {
    sizeChanged = true;
  }

  private void init() {
    if (!initialize) {
      glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
      //      glClearColor (1.0f, 1.0f, 1.0f, 0.0f);
      
      //      glEnable(GL_CULL_FACE); // don't draw things that face away

      glEnable(GL_DEPTH_TEST); // default: glDepthFunc(GL_LESS)

      glEnable(GL_TEXTURE_2D);
      // Enable alpha transparency (so text will have transparent background)
      glEnable(GL_BLEND);
      glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
      
      glPointSize(4f);

      // This is commented out because at the moment I am not using
      // it, and I'd rather not wait for it to load every time since
      // it takes a few seconds.

      // prepare character set for text rendering
      //      buildFont("resources/font_tahoma.png", 12);

      // allocate a bunch of buffers -- this needs to be cleaned up,
      // because I don't want to know anything about how GLApp works
      // on the inside.
      GLApp.initBuffers();

      initialize = true;
    }
  }

  private void reshape() {
    if (sizeChanged) {
      this.w = getSize().width;
      this.h = getSize().height;
      GLApp.viewportX = 0; // update the static vars in GLApp
      GLApp.viewportY = 0;
      GLApp.viewportW = w;
      GLApp.viewportH = h;
      glViewport (0, 0, w, h); 
    }
    sizeChanged = false;
  }


  private void setupProjection() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45f, ((float) w) / ((float) h), 0.001f, 30.0f);
  }


  private void drawScene() {
    glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
    if (model != null) {
      model.draw();
    } else {
      Debug.out("OpenGLDisplay", "model is totally null");
    }
  }

  public void paintGL() {
    init();
    synchronized(this) {
      try  {               
	makeCurrent();
	reshape();
	setupProjection();
	camera.glSetupCamera();
	drawScene();
	swapBuffers();	
      } catch (LWJGLException ex) {
	ex.printStackTrace();
      }
    }   
  }

}
