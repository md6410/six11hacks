package org.six11.cardinal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.six11.util.Debug;
import org.six11.util.args.Arguments;
import org.six11.util.args.Arguments.ArgType;
import org.six11.util.args.Arguments.ValueType;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.gui.shape.ShapeFactory;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;

public class Arc2DDemo extends JPanel {
  final static BasicStroke stroke = new BasicStroke(2.0f);
  int warble = 1;
  Random random = new Random(System.currentTimeMillis());
  Pt s;
  Pt mid;
  Pt e;
  boolean debuggingEnabled = false;

  public Arc2DDemo() {
    setBackground(Color.white);
    setForeground(Color.white);
  }

  private void setPoints(Pt s, Pt mid, Pt e) {
    this.s = s;
    this.mid = mid;
    this.e = e;
    repaint();
  }

  private void makeRandomPoints() {
    s = makeRandomPoint(null);
    mid = makeRandomPoint(null);
    e = makeRandomPoint(null);
  }

  public void paint(Graphics g) {
    if (s == null || mid == null || e == null) {
      try {
        makeRandomPoints();
      } catch (Exception ex) {
        return;
      }
    }
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setPaint(Color.WHITE);
    g2.fill(getBounds());
    g2.setPaint(Color.BLACK);
    Pt c = Functions.getCircleCenter(s, mid, e);
    if (c == null) { // on the off-chance the points are colinear or coincidental
      return;
    }
    double radius = c.distance(s);

    Line leftLine = new Line(c, s);
    Line midLine = new Line(c, mid);
    Line rightLine = new Line(c, e);

    double startAngle = Functions.makeAnglePositive(Math.toDegrees(-Math.atan2(s.y - c.y, s.x - c.x)));
    double midAngle = Functions.makeAnglePositive(Math.toDegrees(-Math.atan2(mid.y - c.y, mid.x - c.x)));
    double endAngle = Functions.makeAnglePositive(Math.toDegrees(-Math.atan2(e.y - c.y, e.x - c.x)));
    bug("---");
    bug("points (copy for command line replay): " + Debug.num(new double[] {
        s.x, s.y, mid.x, mid.y, e.x, e.y
    }));
    bug("startAngle: " + Debug.num(startAngle));
    bug("midAngle: " + Debug.num(midAngle));
    bug("endAngle: " + Debug.num(endAngle));

    double extent = 0;

    // Now compute the phase-adjusted angles begining from startAngle, moving positive and negative.
    double midDecreasing = Functions.getNearestAnglePhase(startAngle, midAngle, -1);
    double midIncreasing = Functions.getNearestAnglePhase(startAngle, midAngle, 1);
    double endDecreasing = Functions.getNearestAnglePhase(midDecreasing, endAngle, -1);
    double endIncreasing = Functions.getNearestAnglePhase(midIncreasing, endAngle, 1);

    // The correct answer will be the distance from the end to the start point, but there are two
    // possible paths. Choose the one that moves through the midpoint by seeing which path is
    // monotonic.
//    boolean dec = Functions.isMonotonicDecreasing(startAngle, midDecreasing, endDecreasing);
//    boolean inc = Functions.isMonotonicIncreasing(startAngle, midIncreasing, endIncreasing);
//    if (dec && inc) {
      if (Math.abs(endDecreasing - startAngle) < Math.abs(endIncreasing - startAngle)) {
        extent = endDecreasing - startAngle;
      } else {
        extent = endIncreasing - startAngle;
      }
//    }
//    } else if (dec) {
//      new RuntimeException("Only decreasing path is monotonic. This should not happen.")
//          .printStackTrace();
//      bug("The decreasing path is monotonic! " + Debug.num(new double[] {
//          startAngle, midDecreasing, endDecreasing
//      }));
//      extent = endDecreasing - startAngle;
//    } else if (inc) {
//      new RuntimeException("Only increasing path is monotonic. This should not happen.")
//          .printStackTrace();
//      bug("The increasing path is monotonic! " + Debug.num(new double[] {
//          startAngle, midIncreasing, endIncreasing
//      }));
//      extent = endIncreasing - startAngle;
//    } else {
//      bug("Couldn't find a path.");
//    }

    bug("extent: " + Debug.num(extent));

    double topLeftX = c.x - radius;
    double topLeftY = c.y - radius;
    double d = radius * 2.0;

    g2.setPaint(Color.LIGHT_GRAY);
    line(g2, midLine);
    Rectangle2D rect = new Rectangle2D.Double(topLeftX, topLeftY, d, d);
    g2.draw(rect);
    g2.draw(new Ellipse2D.Double(topLeftX, topLeftY, d, d));
    g2.draw(new Line(c, new Pt(c.x + d / 2, c.y)));

    Arc2D arc = // ShapeFactory.makeArc(s, mid, e);
    new Arc2D.Double(topLeftX, topLeftY, d, d, startAngle, extent, Arc2D.OPEN);
    g2.setPaint(Color.BLUE);
    g2.draw(arc);

    g2.setPaint(Color.RED);
    line(g2, leftLine, rightLine);

    g2.setPaint(Color.GREEN);
    dot(g2, s);

    g2.setPaint(Color.RED);
    dot(g2, e);

    g2.setPaint(Color.BLACK);
    dot(g2, c, mid);
  }


  private void bug(String what) {
    if (debuggingEnabled) {
      Debug.out("Arc2DDemo", what);
    }
  }

  private void dot(Graphics2D g2, Pt... locations) {
    double r = 3.0;
    for (Pt pt : locations) {
      g2.fill(new Ellipse2D.Double(pt.x - r, pt.y - r, 2 * r, 2 * r));
    }
  }

  private void line(Graphics2D g2, Line... lines) {
    for (Line line : lines) {
      g2.draw(new Line2D.Double(line.getP1(), line.getP2()));
    }
  }

  public static void main(String in[]) {
    Debug.useColor = false;
    ApplicationFrame af = new ApplicationFrame("Arc2D Demo");
    af.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    Arc2DDemo demo = new Arc2DDemo();
    Arguments args = new Arguments();
    args.setProgramName("Arc 2D Demo");
    args.setDocumentationProgram("Shows a graphic demonstrating Arc2D, with some bells/whistles.");
    args.addFlag("help", ArgType.ARG_OPTIONAL, ValueType.VALUE_IGNORED, "Shows help and exits.");
    args.addFlag("timeout", ArgType.ARG_OPTIONAL, ValueType.VALUE_REQUIRED,
        "Specify a millisecond timeout for redrawing with slightly jumbled points.");
    args.addFlag("warble", ArgType.ARG_OPTIONAL, ValueType.VALUE_REQUIRED,
        "Specify an integer amount to jumble the points by (default is 1).");
    args.addFlag("debug", ArgType.ARG_OPTIONAL, ValueType.VALUE_IGNORED,
        "Enable some potentially helpful debugging messages to console.");
    args.addPositional(0, "startX", ValueType.VALUE_OPTIONAL, "start x coordinate");
    args.addPositional(1, "startY", ValueType.VALUE_OPTIONAL, "start y coordinate");
    args.addPositional(2, "midX", ValueType.VALUE_OPTIONAL, "mid x coordinate");
    args.addPositional(3, "midY", ValueType.VALUE_OPTIONAL, "mid y coordinate");
    args.addPositional(4, "endX", ValueType.VALUE_OPTIONAL, "end x coordinate");
    args.addPositional(5, "endY", ValueType.VALUE_OPTIONAL, "end y coordinate");

    args.parseArguments(in);
    if (args.hasFlag("help")) {
      System.out.println(args.getDocumentation());
      System.exit(0);
    }
    try {
      args.validate();
    } catch (Exception ex) {
      System.out.println(args.getUsage());
      System.exit(-1);
    }

    if (args.hasFlag("debug")) {
      demo.debuggingEnabled = true;
    }

    if (args.hasFlag("timeout")) {
      demo.setTimer(Integer.parseInt(args.getValue("timeout")));
    }

    if (args.hasFlag("warble")) {
      demo.warble = Integer.parseInt(args.getValue("warble"));
    }

    if (args.getPositionCount() == 6) {
      Pt start = new Pt(Double.parseDouble(args.getPosition(0)), Double.parseDouble(args
          .getPosition(1)));
      Pt mid = new Pt(Double.parseDouble(args.getPosition(2)), Double.parseDouble(args
          .getPosition(3)));
      Pt end = new Pt(Double.parseDouble(args.getPosition(4)), Double.parseDouble(args
          .getPosition(5)));
      demo.setPoints(start, mid, end);
    }
    af.getContentPane().add("Center", demo);
    af.setSize(new Dimension(400, 400));
    af.center();
    af.setVisible(true);
  }

  private void setTimer(int timeout) {
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        s = makeRandomPoint(s);
        mid = makeRandomPoint(mid);
        e = makeRandomPoint(e);
        bug("Points: " + Debug.num(s.x) + " " + Debug.num(s.y) + " " + Debug.num(mid.x) + " "
            + Debug.num(mid.y) + " " + Debug.num(e.x) + " " + Debug.num(e.y));
        repaint();
      }
    };
    new Timer(timeout, actionListener).start();
  }

  /**
   * Jiggle the given point a bit and return something nearby. But if it gets too far away from the
   * middle, make a new point that is somewhere near the middle and return it.
   */
  private Pt makeRandomPoint(Pt inspiration) {
    Pt panelCenter = new Pt(getWidth() / 2, getHeight() / 2);
    Pt ret = null;
    if (inspiration != null) {
      double dx = (random.nextInt(warble) - random.nextInt(warble * 2)) + 0.5;
      double dy = (random.nextInt(warble) - random.nextInt(warble * 2)) + 0.5;
      ret = new Pt(inspiration.x + dx, inspiration.y + dy);
    }
    int maxDist = Math.min(getWidth(), getHeight()) / 4;
    if (ret == null || panelCenter.distance(ret) > maxDist) {
      int x = random.nextInt(maxDist / 2) - random.nextInt(maxDist);
      int y = random.nextInt(maxDist / 2) - random.nextInt(maxDist);
      ret = new Pt(panelCenter.x + x, panelCenter.y + y);
    }
    return ret;
  }

}