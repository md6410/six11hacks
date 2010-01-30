//
//  N O T E
//
//    The code here is probably flawed. Use at your own risk.
//
package org.six11.cardinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.six11.util.Debug;
import org.six11.util.args.Arguments;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.gui.Components;
import org.six11.util.gui.Strokes;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;

import static java.lang.Math.*;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class CardinalSpline extends JApplet {

  public static void main(String[] in) {
    ApplicationFrame af = new ApplicationFrame("CardinalSpline");
    JPanel holder = new JPanel();
    holder.setLayout(new BorderLayout());
    final CardinalSpline cr = new CardinalSpline();
    af.add(cr);
    af.setSize(600, 400);
    af.center();
    af.setVisible(true);
  }

  private List<Pt> points;
  private List<Pt> interp;
  double tightness;

  public CardinalSpline() {
    setPoints(new ArrayList<Pt>());
    this.interp = new ArrayList<Pt>();
    setTightness(0.5);
    setLayout(new BorderLayout());
    add(makeDrawingPanel(), BorderLayout.CENTER);
    JSlider slider = new JSlider(0, 100, 50);
    slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ev) {
        JSlider src = (JSlider) ev.getSource();
        if (!src.getValueIsAdjusting()) {
          setTightness((double) src.getValue() / 100.0);
        }
      }
    });
    add(slider, BorderLayout.SOUTH);
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ev) {
        addPoint(new Pt(ev));
      }
    });
  }

  public void setPoints(List<Pt> data) {
    this.points = data;
  }

  public void addPoint(Pt pt) {
    points.add(pt);
    CardinalSpline.calculateSlopesCardinal(points, tightness);
    CardinalSpline.interpolateCardinal(interp, points);
    repaint();
  }

  public static double h1(double t) {
    return (2 * pow(t, 3) - 3 * pow(t, 2) + 1);
  }

  public static double h2(double t) {
    return (pow(t, 3) - 2 * pow(t, 2) + t);
  }

  public static double h3(double t) {
    return (-2 * pow(t, 3) + 3 * pow(t, 2));
  }

  public static double h4(double t) {
    return (pow(t, 3) - pow(t, 2));
  }

  public static void calculateSlopesCardinal(List<Pt> points, double tightness) {
    // calculate the tangent vector passing through each point. The magnitude will be somewhere
    // between zero and half the distance between the surrounding points.
    for (int i = 1; i < points.size() - 1; i++) {
      Vec d = new Vec(points.get(i - 1), points.get(i + 1));
      Vec slope = d.getVectorOfMagnitude(tightness * (d.mag() / 2));
      points.get(i).setVec("slope", slope);
    }
    // don't forget the start/end points.
    if (points.size() > 2) {
      Pt first = points.get(0);
      Pt last = points.get(points.size() - 1);
      Vec d = new Vec(first, points.get(1));
      first.setVec("slope", d.getVectorOfMagnitude(tightness * (d.mag() / 2)));
      d = new Vec(points.get(points.size() - 2), last);
      last.setVec("slope", d.getVectorOfMagnitude(tightness * (d.mag() / 2)));
    }
  }

  public static void interpolateCardinal(List<Pt> interpolatedPoints, List<Pt> controlPoints) {
    interpolatedPoints.clear();
    for (int i = 0; i < controlPoints.size() - 1; i++) {
      interpolatedPoints.addAll(CardinalSpline.interpolateCardinalPatch(controlPoints.get(i),
          controlPoints.get(i + 1)));
    }
  }

  public static List<Pt> interpolateCardinalPatch(Pt a, Pt b) {
    List<Pt> ret = new ArrayList<Pt>();
    Vec m0 = a.getVec("slope");
    Vec m1 = b.getVec("slope");
    if (m0 != null && m1 != null) {
      for (int i = 0; i < 20; i++) {
        double t = (double) i / 20.0;
        double x = a.x * h1(t) + m0.getX() * h2(t) + b.x * h3(t) + m1.getX() * h4(t);
        double y = a.y * h1(t) + m0.getY() * h2(t) + b.y * h3(t) + m1.getY() * h4(t);
        ret.add(new Pt(x, y));
      }
    }
    return ret;
  }

  public void setTightness(final double tightness) {
    if (tightness < 0 || tightness > 1) {
      bug("calculateDirections(" + Debug.num(tightness) + "): input parameter should be in [0..1].");
    }
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        CardinalSpline.this.tightness = tightness;
        CardinalSpline.calculateSlopesCardinal(points, tightness);
        CardinalSpline.interpolateCardinal(interp, points);
        repaint();
      }
    });
  }

  public JPanel makeDrawingPanel() {
    return new JPanel() {

      public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        Components.antialias(g);

        g.setPaint(Color.WHITE);
        g.fill(getBounds());

        g.setPaint(Color.RED);
        for (Pt pt : interp) {
          dot(g, pt);
        }

        g.setPaint(Color.BLACK);
        for (Pt pt : points) {
          dot(g, pt);
        }

        g.drawString("tightness: " + Debug.num(tightness), 20, getHeight() - 12);
      }
    };
  }

  public void dot(Graphics2D g, Pt... pts) {
    double r = 3;
    for (Pt pt : pts) {
      Ellipse2D circle = new Ellipse2D.Double(pt.x - r, pt.y - r, r * 2, r * 2);
      g.fill(circle);
    }
  }

  public static void bug(String what) {
    Debug.out("CatmullRom", what);
  }

}
