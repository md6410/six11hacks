package org.six11.turk;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;

import org.six11.turk.TurkStudy.Type;
import org.six11.util.Debug;
import org.six11.util.gui.Components;
import org.six11.util.gui.shape.Circle;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Sequence;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class TurkSurface extends JPanel {

  List<Sequence> sequences;
  List<GeneralPath> paths;
  SortedSet<Pt> corners;
  List<Segment> segments;
  Stroke basicStroke;
  Stroke highlightStroke;
  Segment segmentCursor;
  int segmentCursorIdx;

  Pt cursor; // actual Pt object reference that is nearest the mouse location.
  Mode mode;

  public TurkSurface() {
    sequences = new ArrayList<Sequence>();
    paths = new ArrayList<GeneralPath>();
    corners = new TreeSet<Pt>();
    basicStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    Stroke outline = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    highlightStroke = new CompositeStroke(basicStroke, outline);
    MouseAdapter mouseThing = new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent ev) {
        whackCursor(new Pt(ev));
      }

      public void mouseClicked(MouseEvent ev) {
        addCorner();
      }
    };
    addMouseMotionListener(mouseThing);
    addMouseListener(mouseThing);
    setCornerFinding();
  }

  /**
   * Attempts to add a corner at the current cursor location.
   */
  private void addCorner() {
    if (cursor != null) {
      boolean tooClose = false;
      for (Pt corner : corners) {
        if (corner.distance(cursor) < 15.0) {
          tooClose = true;
        }
      }
      if (!tooClose) {
        corners.add(cursor);
        repaint();
      }
    }
  }

  /**
   * Sets the cursor location to the closest sequence point to the given mouse location.
   */
  protected void whackCursor(Pt mouse) {
    Pt best = null;
    double bestDist = Double.MAX_VALUE;
    for (Sequence seq : sequences) {
      for (Pt pt : seq) {
        double d = pt.distance(mouse);
        if (d < bestDist) {
          bestDist = d;
          best = pt;
        }
      }
    }
    cursor = best;
    repaint();
  }

  public void paintComponent(Graphics g1) {
    Graphics2D g = (Graphics2D) g1;
    Components.antialias(g);
    g.setPaint(Color.WHITE);
    g.fill(getBounds());
    Circle circ = new Circle(0, 0, 10);

    if (mode == Mode.cornerFinding) {
      g.setPaint(Color.BLACK);
      g.setStroke(basicStroke);
      for (Shape s : paths) {
        g.draw(s);
      }
      if (cursor != null) {
        circ.moveTo(cursor.x, cursor.y);
        g.setPaint(Color.RED);
        g.fill(circ);
      }
      g.setColor(Color.BLUE);
      for (Pt corner : corners) {
        circ.moveTo(corner.x, corner.y);
        g.fill(circ);
      }
    }

    if (mode == Mode.labeling) {
      if (segments != null) {
        g.setStroke(basicStroke);
        for (Segment s : segments) {
          switch (s.type) {
            case line:
            case arc:
            case curve:
              g.setPaint(Color.LIGHT_GRAY);
              break;
            case unknown:
              g.setPaint(Color.BLACK);
              break;
          }
          g.draw(s.path);
        }
      }
      if (segmentCursor != null) {
        g.setStroke(highlightStroke);
        g.setColor(Color.RED);
        g.draw(segmentCursor.path);
      }
    }

  }

  public void clearDrawing() {
    sequences.clear();
    paths.clear();
    corners.clear();
    cursor = null;
    segmentCursor = null;
    repaint();
  }

  public void addSequence(Sequence seq) {
    sequences.add(seq);
    corners.add(seq.getFirst());
    corners.add(seq.getLast());
    GeneralPath gp = makePath(seq, 0, seq.size() - 1);
    paths.add(gp);
  }

  GeneralPath makePath(Sequence seq, int start, int end) {
    GeneralPath gp = new GeneralPath();
    for (int i = start; i <= end; i++) {
      Pt pt = seq.get(i);
      if (i == start) {
        gp.moveTo(pt.x, pt.y);
      } else {
        gp.lineTo(pt.x, pt.y);
      }
    }
    return gp;
  }

  public final void setCornerFinding() {
    mode = Mode.cornerFinding;
    repaint();
  }

  public void setLabeling() {
    bug("Now labeling.");
    mode = Mode.labeling;
    segmentCursor = null;
    List<Pt> cornerList = new ArrayList<Pt>(corners);
    segments = new ArrayList<Segment>();
    for (int i = 0; i < cornerList.size() - 1; i++) {
      segments.add(makeSegment(cornerList.get(i), cornerList.get(i + 1)));
    }
    incrementSegmentCursor();
    repaint();
  }
  
  private static void bug(String what) {
    Debug.out("TurkSurface", what);
  }

  public void setDone() {
    mode = Mode.done;
  }

  private void incrementSegmentCursor() {
    if (segmentCursor == null) {
      bug("segment cursor was null, so now the index is zero.");
      segmentCursorIdx = 0;
    } else {
      bug("segment cursor was " + segmentCursorIdx);
      segmentCursorIdx++;
    }
    if (segmentCursorIdx < segments.size()) {
      segmentCursor = segments.get(segmentCursorIdx);
      repaint();
    }
  }

  private Segment makeSegment(Pt a, Pt b) {
    Segment ret = null;
    outer: {
      for (Sequence seq : sequences) {
        for (Pt pt : seq) {
          if (a == pt) {
            ret = new Segment(seq.indexOf(a), seq.indexOf(b), seq, TurkStudy.Type.unknown);
            break outer;
          }
        }
      }
    }
    return ret;
  }

  private static enum Mode {
    cornerFinding, labeling, done
  }

  private class CompositeStroke implements Stroke {
    private Stroke s1, s2;

    public CompositeStroke(Stroke stroke1, Stroke stroke2) {
      this.s1 = stroke1;
      this.s2 = stroke2;
    }

    public Shape createStrokedShape(Shape shape) {
      return s2.createStrokedShape(s1.createStrokedShape(shape));
    }
  }

  private class Segment {
    TurkStudy.Type type;
    GeneralPath path;

    public Segment(int start, int end, Sequence seq, TurkStudy.Type type) {
      this.type = type;
      this.path = makePath(seq, start, end);
    }
  }

  public boolean setSegmentType(Type t) {
    if (segmentCursor != null) {
      segmentCursor.type = t;
      incrementSegmentCursor();
      repaint();
    }
    return (segmentCursorIdx >= segments.size());
  }
}
