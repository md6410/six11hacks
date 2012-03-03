package org.six11.hacks.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import static java.lang.System.out;

public class RenderTest extends JPanel {

  public static void main(String[] args) {
    int numBalls = 10;

    JFrame f = new JFrame("RenderTest");
    RenderTest rt;
    boolean isSim = args.length > 0;
    if (isSim) {
      rt = new RenderTest();
    } else {
      rt = new RenderTest(numBalls);
    }
    f.add(rt);
    rt.installKeyboardEvents();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    if (isSim) {
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setUndecorated(true);
      GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment()
          .getDefaultScreenDevice();
      if (device.isFullScreenSupported()) {
        device.setFullScreenWindow(f);
      }
    } else {
      int w = 500;
      int h = 400;
      int centerX = screen.width / 2;
      int centerY = screen.height / 2;
      f.setSize(w, h);
      f.setLocation(centerX - w / 2, centerY - h / 2);
      f.setVisible(true);
    }
  }

  public static Random rand = new Random(System.currentTimeMillis());
  protected boolean useClip;
  protected boolean useImage;
  private Set<Ball> balls;
  private long msSum;
  private int msSumN;
  private boolean sim = false;
  private long[] simResults;
  private long ms;

  public RenderTest(int numBalls) {
    init(numBalls);
  }

  public RenderTest() {
    init(1);
    this.sim = true;
    this.simResults = new long[2];
    out.println("Simulation begins at " + new Date());
  }

  private void init(int numBalls) {
    this.sim = false;
    this.ms = 0;
    this.balls = new HashSet<Ball>();
    for (int i = 0; i < numBalls; i++) {
      balls.add(new Ball());
    }
    Timer timer = new Timer(16, new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        moveAll();
        repaint();
      }
    });
    timer.setRepeats(true);
    timer.start();
  }

  void installKeyboardEvents() {
    Map<String, Action> actions = new HashMap<String, Action>();
    actions.put("toggle clip", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        useClip = !useClip;
      }
    });
    actions.put("toggle image", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        useImage = !useImage;
      }
    });
    actions.put("add ball", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        balls.add(new Ball());
      }
    });
    actions.put("remove ball", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        Ball[] ballArr = balls.toArray(new Ball[0]);
        balls.remove(ballArr[randIntBetween(0, ballArr.length - 1)]);
      }
    });
    JRootPane rp = getRootPane();
    KeyStroke s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_C, 0);
    rp.registerKeyboardAction(actions.get("toggle clip"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_I, 0);
    rp.registerKeyboardAction(actions.get("toggle image"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
    rp.registerKeyboardAction(actions.get("add ball"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
    s = (KeyStroke) KeyStroke.getKeyStroke(KeyEvent.VK_R, 0);
    rp.registerKeyboardAction(actions.get("remove ball"), s, JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  public static float randFloatBetween(float lower, float higher) {
    float diff = higher - lower;
    return lower + (rand.nextFloat() * diff);
  }

  public static int randIntBetween(int lower, int higher) {
    int diff = higher - lower;
    return lower + rand.nextInt(diff);
  }

  protected void moveAll() {
    for (Ball b : balls) {
      b.move(getSize());
    }
  }

  @Override
  protected void paintComponent(Graphics g1) {
    long start = System.currentTimeMillis();
    Graphics2D g = (Graphics2D) g1;
    Dimension s = getSize();
    g.setColor(Color.BLACK);
    g.fill(getVisibleRect());
    for (Ball ball : balls) {
      if (useClip) {
        g.setClip(ball.getClip());
      }
      if (useImage) {
        Image im = ball.renderImage();
        g.drawImage(im, (int) ball.getLocation().getX(), (int) ball.getLocation().getY(), null);
      } else {
        ball.render(g);
      }
    }
    g.setClip(null);
    g.setColor(Color.GREEN);
    g.drawString(balls.size() + " balls", s.width - 200, s.height - 80);
    g.drawString("useImage: " + useImage, s.width - 200, s.height - 60);
    g.drawString("useClip: " + useClip, s.width - 200, s.height - 40);
    g.drawString(ms + " ms", s.width - 200, s.height - 20);

    long end = System.currentTimeMillis();
    ms = end - start;
    if (sim) {
      msSum = msSum + ms;
      msSumN = msSumN + 1;
      if (msSumN > 100) {
        if (!useImage) {
          simResults[0] = msSum / msSumN;
          useImage = true;
          msSum = 0;
          msSumN = 0;
        } else {
          simResults[1] = msSum / msSumN;
          useImage = false;
          msSum = 0;
          msSumN = 0;
          balls.add(new Ball());
          System.out.println(balls.size() - 1 + "\t" + simResults[0] + "\t" + simResults[1]);
          if (simResults[1] > 20) {
            System.exit(0);
          }
        }
      }
    }
  }
}
