package org.six11.hacks.render;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import static java.lang.Math.*;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

public class GLRenderTest implements GLEventListener {

  private double theta = 0;
  private double s = 0;
  private double c = 0;

  public static void main(String[] args) {
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    GLCanvas canvas = new GLCanvas(caps);

    JFrame frame = new JFrame("AWT Window Test");
    frame.add(canvas);

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setUndecorated(true);
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
            if (device.isFullScreenSupported()) {
              device.setFullScreenWindow(frame);
            }
    //    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    //    int w = 500;
    //    int h = 400;
    //    int centerX = screen.width / 2;
    //    int centerY = screen.height / 2;
    //    frame.setSize(w, h);
    //    frame.setLocation(centerX - w / 2, centerY - h / 2);

    frame.setVisible(true);
    GLRenderTest test = new GLRenderTest();
    canvas.addGLEventListener(test);
    test.installKeyboardEvents(frame.getRootPane());

    FPSAnimator animator = new FPSAnimator(canvas, 60);
    animator.add(canvas);
    animator.start();
  }

  protected long ms;
  protected TextRenderer textRenderer;
  protected List<GLBall> balls;
  private long msSum;
  private int msSumN;
  private long lastMean;

  public GLRenderTest() {
    ms = 0;
    lastMean = 0;
    balls = new ArrayList<GLBall>();
    balls.add(new GLBall());
//    for (int i=0; i <10; i++) {
//      balls.add(new GLBall());
//    }
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    long start = System.currentTimeMillis();
    update();
    render(drawable);
    long end = System.currentTimeMillis();
    ms = end - start;
    msSum = msSum + ms;
    msSumN = msSumN + 1;
    if (msSumN > 40) {
      long mean = msSum / msSumN;
      lastMean = mean;
      System.out.println(balls.size() + "\t" + mean);
      balls.add(new GLBall());
      msSum = 0;
      msSumN = 0;
      if (mean > 20) {
        System.out.println("Reached 20ms end goal. Quitting.");
      }
      if (balls.size() > 1000) {
        System.out.println("Reached 1000 balls. Quitting.");
      }
    }
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
        Ball[] ballArr = balls.toArray(new Ball[0]);
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
    GL2 gl = drawable.getGL().getGL2();
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(-1, 1, 1, -1, 0, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_BLEND);
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
    textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 18));
    textRenderer.setSmoothing(true);
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
  }

  private void update() {
    theta += 0.01;
    s = sin(theta);
    c = cos(theta);
    
    for (GLBall ball : balls) {
      ball.move();
    }
    
  }

  private void render(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    for (GLBall ball : balls) {
      ball.renderBall(drawable);
    }
    
    textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
    textRenderer.setColor(0.0f, 0.8f, 0.8f, 1f);
    textRenderer.draw(ms + " ms", drawable.getWidth() - 140, 20);
    textRenderer.draw(balls.size() + " balls", drawable.getWidth() - 140, 40);
    textRenderer.draw("Last mean: " + lastMean, drawable.getWidth() - 140, 60);
    textRenderer.endRendering();
  }
}

class GLBall {

  private float x, y;
  private float dx, dy;
  private float r;
  private float[] color;

  float twoPi = (float) (2f * PI);
  float step = (float) twoPi / 36f;
  
  GLBall() {
    r = 0.08f;
    x = RenderTest.randFloatBetween(-1 + r, 1 - r);
    y = RenderTest.randFloatBetween(-1 + r, 1 - r);
    dx = RenderTest.randFloatBetween(0.001f, 0.007f);
    if (RenderTest.randBool()) {
      dx = -dx;
    }
    dy = RenderTest.randFloatBetween(0.001f, 0.007f);
    if (RenderTest.randBool()) {
      dy = -dy;
    }
    color = new float[4];
    for (int i=0; i < color.length; i++) {
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
    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    {
      gl.glColor4fv(color, 0);
      gl.glVertex2f(x, y);
      for (float t = 0; t < twoPi; t = t + step) {
        float px = x + (r * (float) cos(t));
        float py = y + (r * (float) sin(t));
        gl.glVertex2f(px, py);
      }
    }
    gl.glEnd();
  }
}