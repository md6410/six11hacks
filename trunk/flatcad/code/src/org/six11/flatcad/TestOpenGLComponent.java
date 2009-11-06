/* TestOpenGLComponent.java */

package org.six11.flatcad;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas; 
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.GLU; 

import org.six11.flatcad.geom.MatrixStack;
import org.six11.flatcad.geom.Box;
import org.six11.flatcad.geom.Polyhedron;
import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Line;
import org.six11.flatcad.geom.LineSegment;
import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.MathUtils;

import org.six11.flatcad.gl.GLDrawable;

import org.six11.flatcad.model.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.glu.GLU.*;
import org.six11.flatcad.geom.Direction;
import org.six11.util.Debug;

import java.util.List;
import java.util.ArrayList;

public class TestOpenGLComponent extends AWTGLCanvas  {

  protected boolean initialize = false;
  protected int w, h;
  protected boolean sizeChanged;
  protected int lastX;
  protected int lastY;
  protected float camRotX;
  protected float camRotY;
  protected double camRadius;

  protected Box testBox;
  protected List<GLDrawable> drawables;

  public TestOpenGLComponent() throws LWJGLException {
    super();
    this.drawables = new ArrayList<GLDrawable>();

    this.w = -1;
    this.h = -1;
    this.sizeChanged = false;
    this.lastX = -1;
    this.lastY = -1;

    // create a box that is thin in the z-dimension
    this.testBox = new Box(1.0, 0.8, 0.05);
    //    creaseRecursively(testBox, 5);
    //    foldDiagonally(testBox);
    //    foldStrangely(testBox);
    drawSceneGraph();

    camRadius = 2.0;
    
    addMouseMotionListener(new MouseMotionAdapter() {
	public void mouseDragged(MouseEvent ev) {
	  if (lastX >= 0 && lastY >= 0) {
	    TestOpenGLComponent.this.mouseDragged(ev.getPoint().x - lastX,
						  ev.getPoint().y - lastY,
						  ev.isShiftDown());
	  }
	  lastX = ev.getPoint().x;
	  lastY = ev.getPoint().y;
	}
      });
  }

  private final void creaseRecursively(Polyhedron body, int ttl) {
    // cut it into two
    LineSegment cutLine = body.findLargestFace().findShortestMidline();
    Polyhedron[] halves = body.cut(cutLine.getStart(), cutLine.getEnd());
    drawables.add(halves[1]);
    if (ttl == 0) {
      // simply add the other half and allow recursion to end.
      drawables.add(halves[0]);
    } else {
      // rotate one of them by 45 degrees
      MatrixStack max = new MatrixStack();
      max.push(MathUtils.getTranslationMatrix(cutLine.getStart().getReverse()));
      max.push(MathUtils.getRotationMatrix(cutLine.getDirection(), MathUtils.degToRad(-45)));
      max.push(MathUtils.getTranslationMatrix(cutLine.getStart()));
      halves[0].transform(max.getCurrent());
      creaseRecursively(halves[0], --ttl);
    }
  }

  private void drawSceneGraph() {
    SceneGraph sampleSceneGraph = new SceneGraph();
    drawables.add(sampleSceneGraph);
  }

  private void foldStrangely(Polyhedron body) {
    Polygon face = body.findLargestFace();
    HalfEdge he = face.getEdge();
    Vertex a = he.getPoint();
    he = he.getNext();
    Vertex b = he.getPoint();
    he = he.getNext();
    Vertex c = he.getPoint();
    he = he.getNext();
    Vertex d = he.getPoint();
    LineSegment sideOne = new LineSegment(a,b);
    LineSegment sideTwo = new LineSegment(c,d);
    
    Vertex cut1 = sideOne.getParameterizedVertex(0.4);
    Vertex cut2 = sideTwo.getParameterizedVertex(0.2);

    Polyhedron[] halves = body.cut(cut1, cut2);
    drawables.add(halves[0]);
    drawables.add(halves[1]);
    MatrixStack max = new MatrixStack();
    LineSegment hinge = halves[0].getCursor().findLongestMidline();
    max.push(MathUtils.getTranslationMatrix(hinge.getStart().getReverse()));
    max.push(MathUtils.getRotationMatrix(hinge.getDirection(), MathUtils.degToRad(-45)));
    max.push(MathUtils.getTranslationMatrix(hinge.getStart()));
    halves[0].transform(max.getCurrent());
  }

  private void foldDiagonally(Polyhedron body) {
    Polygon face = body.findLargestFace();
    HalfEdge he = face.getEdge();
    Vertex a = he.getPoint();
    he = he.getNext().getNext();
    Vertex b = he.getPoint();
    Polyhedron[] halves = body.cut(a, b);
    drawables.add(halves[0]);
    drawables.add(halves[1]);
    MatrixStack max = new MatrixStack();
    LineSegment seg = new LineSegment(a,b);
    max.push(MathUtils.getTranslationMatrix(a.getReverse()));
    max.push(MathUtils.getRotationMatrix(seg.getDirection(), MathUtils.degToRad(-45)));
    max.push(MathUtils.getTranslationMatrix(a));
    halves[0].transform(max.getCurrent());
  }

  public void mouseDragged(int dx, int dy, boolean changeRadius) {
    if (changeRadius) {
      camRadius += ((float) dy) / 100f;
    } else {
      camRotX += (float) dx;
      camRotY += (float) dy;
    }
    repaint();
  }

  public void componentResized(ComponentEvent ev) {
    sizeChanged = true;
  }

  private void init() {
    if (!initialize) {
      glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
      
      // CULL_FACE: I think what this does is not paint the polygon if
      // it's surface normal is facing the wrong direction.
      glEnable(GL_CULL_FACE);

      // Make sure that objects obscurred by others in the foreground
      // are not drawn. Also make sure to glClear() using bitwise or
      // with GL_DEPTH_BUFFER_BIT.
      glEnable(GL_DEPTH_TEST); // default: glDepthFunc(GL_LESS)
      
    }
  }

  private void reshape() {
    if (sizeChanged) {
      this.w = getSize().width;
      this.h = getSize().height;
      glViewport (0, 0, w, h); 
    }
    sizeChanged = false;
  }


  private void setupProjection() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45f, ((float) w) / ((float) h), 0.001f, 3.0f);
  }

  private void setupCamera() {
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslated(0d, 0d, -1.0 * camRadius);
    glRotatef(camRotY, 1f, 0f, 0f);
    glRotatef(camRotX, 0f, 1f, 0f);    
  }

  private void drawScene() {
    glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
    //    testBox.drawGL();
    for (GLDrawable drawme : drawables) {
      drawme.drawGL();
    }
  }

  public void paintGL() {
    init();
    synchronized(this) {
      try  {               
	makeCurrent();
	reshape();
	setupProjection();
	setupCamera();
	drawScene();
	swapBuffers();	
      } catch (LWJGLException ex) {
	ex.printStackTrace();
      }
    }   
  }

  public void foldVert() {
    Debug.out("TestOGCom", "Not implemented yet");
  }

  public void foldHoriz() {
    Debug.out("TestOGCom", "Fold along y=0 by 5 degrees");    
  }

}
