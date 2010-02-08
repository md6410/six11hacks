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

import org.six11.turk.TurkStudy.TurkSegment;
import org.six11.turk.TurkStudy.Type;
//import org.six11.util.Debug;
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

  SortedSet<Pt> corners; // stores locations of user-specified corners
  List<TurkSegment> segments; // stores user-specified segment types
  String sketchName; // used to identify which sketch 'corners' and 'segments' goes with.

  List<Sequence> sequences;
  List<GeneralPath> paths;
  Stroke basicStroke;
  Stroke highlightStroke;
  TurkSegment segmentCursor;
  int segmentCursorIdx;

  Pt cursor; // actual Pt object reference that is nearest the mouse location.
  Mode mode;

  public TurkSurface() {
    sequences = new ArrayList<Sequence>();
    paths = new ArrayList<GeneralPath>();
    corners = new TreeSet<Pt>();
    setCornerFinding();

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

      public void mouseExited(MouseEvent ev) {
        whackCursor(null);
      }
    };
    addMouseMotionListener(mouseThing);
    addMouseListener(mouseThing);

  }

  public SortedSet<Pt> getCorners() {
    return corners;
  }

  public List<TurkSegment> getSegments() {
    return segments;
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
    if (mouse != null) {
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
    } else {
      cursor = null;
    }
    repaint();
  }

  public void paintComponent(Graphics g1) {
    super.paintComponent(g1);
    Graphics2D g = (Graphics2D) g1.create();
    Components.antialias(g);
    g.setPaint(Color.WHITE);
    g.fill(getVisibleRect());
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
        for (TurkSegment s : segments) {
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

  public void addSequence(int seqIdx, Sequence seq) {
    for (Pt pt : seq) {
      pt.setString("seqIdx", "" + seqIdx);
    }
    sequences.add(seq);
    corners.add(seq.getFirst());
    corners.add(seq.getLast());
    GeneralPath gp = TurkStudy.makePath(seq, 0, seq.size() - 1);
    paths.add(gp);
  }

  public final void setCornerFinding() {
    mode = Mode.cornerFinding;
    repaint();
  }

  public void setLabeling() {
    mode = Mode.labeling;
    segmentCursor = null;
    List<Pt> cornerList = new ArrayList<Pt>(corners);
    segments = new ArrayList<TurkSegment>();
    for (int i = 0; i < cornerList.size() - 1; i++) {
      TurkSegment tseg = makeSegment(cornerList.get(i), cornerList.get(i + 1));
      if (tseg != null) {
        segments.add(tseg);
      }
    }
    incrementSegmentCursor();
    repaint();
  }

//   private static void bug(String what) {
//   Debug.out("TurkSurface", what);
//   }

  public void setDone() {
    mode = Mode.done;
  }

  private void incrementSegmentCursor() {
    if (segmentCursor == null) {
      segmentCursorIdx = 0;
    } else {
      segmentCursorIdx++;
    }
    if (segmentCursorIdx < segments.size()) {
      segmentCursor = segments.get(segmentCursorIdx);
      repaint();
    }
  }

  private TurkSegment makeSegment(Pt a, Pt b) {
    TurkSegment ret = null;
    outer: {
      for (Sequence seq : sequences) {
        int idxA = -1;
        int idxB = -1;
        for (int i = 0; i < seq.size(); i++) {
          Pt pt = seq.get(i);
          if (a == pt) {
            idxA = seq.indexOf(a);
          } else if (b == pt) {
            idxB = seq.indexOf(b);
          }
          if (idxA >= 0 && idxB >= 0) {
            ret = new TurkSegment(seq.indexOf(a), seq.indexOf(b), seq, TurkStudy.Type.unknown);
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

  public boolean setSegmentType(Type t) {
    if (segmentCursor != null) {
      segmentCursor.type = t;
      incrementSegmentCursor();
      repaint();
    }
    return (segmentCursorIdx >= segments.size());
  }

  public void setSketchName(String sketchName) {
    this.sketchName = sketchName;
  }
}
