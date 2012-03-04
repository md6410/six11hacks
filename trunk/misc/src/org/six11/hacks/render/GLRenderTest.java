package org.six11.hacks.render;

import static java.lang.System.out;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import static java.lang.Math.*;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

public class GLRenderTest implements GLEventListener, MouseListener, MouseMotionListener {

  public static void main(String[] args) {
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    GLJPanel canvas = new GLJPanel(caps);

    JFrame frame = new JFrame("AWT Window Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(canvas);

    boolean fullScreen = false;
    if (fullScreen) {

      frame.setUndecorated(true);
      GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment()
          .getDefaultScreenDevice();
      if (device.isFullScreenSupported()) {
        device.setFullScreenWindow(frame);
      }
    } else {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      int w = 500;
      int h = 400;
      int centerX = screen.width / 2;
      int centerY = screen.height / 2;
      frame.setSize(w, h);
      frame.setLocation(centerX - w / 2, centerY - h / 2);
    }
    frame.setVisible(true);
    GLRenderTest test = new GLRenderTest();
    canvas.addMouseListener(test);
    canvas.addMouseMotionListener(test);
    canvas.addGLEventListener(test);
    test.installKeyboardEvents(frame.getRootPane());

    FPSAnimator animator = new FPSAnimator(canvas, 60);
    animator.add(canvas);
    animator.start();
  }

  protected long ms;
  protected TextRenderer textRenderer;
  protected List<GLBall> balls;
  protected List<Point2D> points;
  protected MouseEvent click;
  private GLU glu;
  private MouseEvent drag;

  public GLRenderTest() {
    ms = 0;
    balls = new ArrayList<GLBall>();
    for (int i = 0; i < 10; i++) {
      balls.add(new GLBall());
    }
    points = new ArrayList<Point2D>();
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    long start = System.currentTimeMillis();
    handleMouseEvents(drawable);
    update();
    render(drawable);
    long end = System.currentTimeMillis();
    ms = end - start;
  }

  private void handleMouseEvents(GLAutoDrawable drawable) {
    if (click != null) {
      int x = click.getX();
      int y = click.getY();
      float[] spot = unproject(drawable, x, y);
      balls.add(new GLBall(spot[0], spot[1]));
      click = null;
    }
    if (drag != null) {
      float[] spot = unproject(drawable, drag.getX(), drag.getY());
      points.add(new Point2D.Float(spot[0], spot[1]));
      out.println("got drag. there are now " + points.size() + " points");
      drag = null;
    }
  }

  private float[] unproject(GLAutoDrawable drawable, int x, int y) {
    int viewport[] = new int[4];
    float mvmatrix[] = new float[16];
    float projmatrix[] = new float[16];
    int realy = 0;// GL y coord pos
    float wcoord[] = new float[4];// wx, wy, wz
    GL2 gl = drawable.getGL().getGL2();
    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
    gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, mvmatrix, 0);
    gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projmatrix, 0);
    realy = viewport[3] - (int) y - 1; // have to invert y values because Java and OpenGL disagree which way is up.
    System.out.println("Coordinates at cursor are (" + x + ", " + realy + ")");
    glu.gluUnProject((float) x, (float) realy, 0.0f, //
        mvmatrix, 0, projmatrix, 0, viewport, 0, wcoord, 0);
    System.out.println("World coords at z=0.0 are ( " //
        + wcoord[0] + ", " + wcoord[1] + ", " + wcoord[2] + ")");
    //    glu.gluUnProject((float) x, (float) realy, 1.0f, //
    //        mvmatrix, 0,
    //        projmatrix, 0,
    //        viewport, 0, 
    //        wcoord, 0);
    //    System.out.println("World coords at z=1.0 are (" //
    //                       + wcoord[0] + ", " + wcoord[1] + ", " + wcoord[2]
    //                       + ")");
    return wcoord;
  }

  void installKeyboardEvents(JRootPane rp) {
    Map<String, Action> actions = new HashMap<String, Action>();

    actions.put("add ball", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        balls.add(new GLBall());
      }
    });
    actions.put("remove ball", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        GLBall[] ballArr = balls.toArray(new GLBall[0]);
        balls.remove(ballArr[RenderTest.randIntBetween(0, ballArr.length - 1)]);
      }
    });
    KeyStroke s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_C, 0);
    rp.registerKeyboardAction(actions.get("toggle clip"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_I, 0);
    rp.registerKeyboardAction(actions.get("toggle image"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
    rp.registerKeyboardAction(actions.get("add ball"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_R, 0);
    rp.registerKeyboardAction(actions.get("remove ball"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    glu = new GLU();
    GL2 gl = drawable.getGL().getGL2();
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(-1, 1, 1, -1, 0, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glClearColor(1f, 1f, 1f, 1f);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_BLEND);
    gl.glEnable(GL2.GL_LINE_SMOOTH);
    gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
    textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 18));
    textRenderer.setSmoothing(true);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
  }

  private void update() {

    for (GLBall ball : balls) {
      ball.move();
    }

  }

  private void render(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT); // clear screen.

    for (GLBall ball : balls) { // draw all balls.
      ball.renderBall(drawable);
    }
    
    // draw the one scribble.
    gl.glLineWidth(2.6f);
    gl.glColor3f(0f, 0f, 0f);
    gl.glBegin(GL2.GL_LINE_STRIP);
    {
      for (Point2D pt : points) {
        gl.glVertex2d(pt.getX(), pt.getY());
      }
    }
    gl.glEnd();
    
    // draw info.
    textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
    textRenderer.setColor(0.0f, 0.8f, 0.8f, 1f);
    textRenderer.draw(ms + " ms", drawable.getWidth() - 140, 20);
    textRenderer.draw(balls.size() + " balls", drawable.getWidth() - 140, 40);
    textRenderer.draw(points.size() + " points", drawable.getWidth() - 140, 60);
    textRenderer.endRendering();
  }

  @Override
  public void mouseClicked(MouseEvent ev) {
    click = ev;
  }

  @Override
  public void mouseEntered(MouseEvent ev) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent ev) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(MouseEvent ev) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseReleased(MouseEvent ev) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(MouseEvent ev) {
    out.println("drag");
    drag = ev;
  }

  @Override
  public void mouseMoved(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }
}

class GLBall {

  private float x, y;
  private float dx, dy;
  private float r;
  private float[] color;

  float twoPi = (float) (2f * PI);
  float step = (float) twoPi / 36f;

  GLBall(float x, float y) {
    this();
    this.x = x;
    this.y = y;
  }

  GLBall() {
    x = RenderTest.randFloatBetween(-1 + r, 1 - r);
    y = RenderTest.randFloatBetween(-1 + r, 1 - r);
    r = 0.18f;
    dx = RenderTest.randFloatBetween(0.001f, 0.007f);
    if (RenderTest.randBool()) {
      dx = -dx;
    }
    dy = RenderTest.randFloatBetween(0.001f, 0.007f);
    if (RenderTest.randBool()) {
      dy = -dy;
    }
    color = new float[4];
    for (int i = 0; i < color.length; i++) {
      color[i] = RenderTest.randFloatBetween(0.1f, 0.9f);
    }
  }

  void move() {
    x = x + dx;
    y = y + dy;
    if (x + r > 1 && dx > 0) {
      dx = -dx;
    }
    if (x - r < -1 && dx < 0) {
      dx = -dx;
    }
    if (y + r > 1 && dy > 0) {
      dy = -dy;
    }
    if (y - r < -1 && dy < 0) {
      dy = -dy;
    }
  }

  void renderBall(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();

    gl.glColor4fv(color, 0);
    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    {
      gl.glVertex2f(x, y);
      for (float t = 0; t < twoPi; t = t + step) {
        float px = x + (r * (float) cos(t));
        float py = y + (r * (float) sin(t));
        gl.glVertex2f(px, py);
      }
    }
    gl.glEnd();
    gl.glLineWidth(3.4f);
    gl.glColor3fv(color, 0);
    gl.glBegin(GL2.GL_LINE_LOOP);
    {
      for (float t = 0; t < twoPi; t = t + step) {
        float px = x + (r * (float) cos(t));
        float py = y + (r * (float) sin(t));
        gl.glVertex2f(px, py);
      }
    }
    gl.glEnd();

  }
}