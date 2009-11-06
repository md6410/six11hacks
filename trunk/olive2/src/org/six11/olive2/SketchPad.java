package org.six11.olive2;

import javax.swing.JComponent;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Pt;
import org.six11.util.pen.MouseThing;
import org.six11.util.Debug;

/**
 *  I N K - This is the main I/O component for sketches. It is
 *  responsible for receiving raw input and painting it. There are a
 *  few subclasses of this class that do more fancy things.
 **/
public class SketchPad extends JComponent {

  public final static String MOUSE_MODE_SKETCH = "sketch mode";
  public final static String MOUSE_MODE_NO_SKETCH = "not sketching mode";

  protected Sketch sketch;
  protected double pad = 3.0;

  transient protected Sequence currentSequence;
  transient protected Stroke borderStroke;
  transient protected Stroke drawingStroke;
  transient protected Color bgColor;
  transient protected Color borderColor;
  transient protected Map<String, MouseThing> mouseThings;
  transient protected String currentMouseMode;
  transient protected List<StrokeListener> strokeListeners;

  public SketchPad() {
    mouseThings = new HashMap<String, MouseThing>();
    MouseThing sketchListener = null; //new SketchMouseThing(this);
    MouseThing noSketchListener = null; //new SketchMouseThing(this, false);
    mouseThings.put(MOUSE_MODE_SKETCH, sketchListener);
    mouseThings.put(MOUSE_MODE_NO_SKETCH, noSketchListener);
    enterMouseMode(MOUSE_MODE_SKETCH);
    sketch = new Sketch(); // the main sketch datastructure
    borderStroke = new BasicStroke
      (3f,                        // pen thickness
       BasicStroke.CAP_BUTT,      // cap
       BasicStroke.JOIN_MITER,    // join
       1f,                        // miter limit
       new float[] { 7, 7 },      // dash
       7);                        // dash phase
    drawingStroke = new BasicStroke(3f);
    Color c = randColor();
    bgColor = c.brighter();
    borderColor = c.darker();
  }

  public void clear() {
    sketch.clearSequences();
    repaint();
  }

  public final void enterMouseMode(String what) {
    if (what.equals(currentMouseMode)) { return; }
    MouseThing l = mouseThings.get(currentMouseMode);
    if (l != null) {
      removeMouseListener(l);
      removeMouseMotionListener(l);
    }
    currentMouseMode = what;
    l = mouseThings.get(currentMouseMode);
    if (l == null) {
      bug("I can't enter mode: " + what + " because that doesn't map to a mouse thing.");
      return;
    }
    addMouseListener(l);
    addMouseMotionListener(l);
  }
  
  public void addPoint(Pt pt) {
    if (currentSequence == null) {
      currentSequence = new Sequence();
    }
    currentSequence.add(pt);
    repaint();
  }

  public void endSequence() {
    if (currentSequence != null) {
      currentSequence = Functions.getNormalizedSequence(currentSequence, 4.0);
      sketch.addSequence(currentSequence);
      StrokeEvent ev = new StrokeEvent(this, currentSequence, StrokeEvent.STROKE_END);
      currentSequence = null;
      repaint();
      fireStrokeEvent(ev);
    }
  }

  public void paintComponent(Graphics g1) {
    Graphics2D g = (Graphics2D) g1;
    drawBasicsAndInit(g);
    drawSequences(g);
  }

  protected void drawSequences(Graphics2D g) {
    g.setStroke(drawingStroke);
    g.setColor(Color.BLACK);

    for (Sequence seq : sketch.getSequences()) {
      drawSequence(g, seq);
    }
    if (currentSequence != null) {
      drawSequence(g, currentSequence);
    }

  }

  protected void drawBasicsAndInit(Graphics2D g) {
    g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
    RoundRectangle2D rec = new RoundRectangle2D.Double(pad, pad, getWidth() - 2.0*pad, getHeight() - 2.0*pad, 40, 40);
    g.setColor(bgColor);
    g.fill(rec);
    g.setStroke(borderStroke);
    g.setColor(borderColor);
    g.draw(rec);
  }

  protected void drawSequence(Graphics2D g, Sequence seq) {
    g.draw(seq);
  }

  protected Color randColor() {
    Random rand = new Random(System.currentTimeMillis() + hashCode());
    int r = 225 - rand.nextInt(96);
    int g = 225 - rand.nextInt(96);
    int b = 225 - rand.nextInt(96);
    return new Color(r, g, b);
  }

  /**
   * This gives you direct access to the sketch data that the sketch
   * pad is showing.
   */
  public Sketch getSketch() {
    return sketch;
  }

  public void addStrokeListener(StrokeListener lis) {
    if (strokeListeners == null) {
      strokeListeners = new ArrayList<StrokeListener>();
    }
    if (!strokeListeners.contains(lis)) {
      strokeListeners.add(lis);
    }
  }

  void fireStrokeEvent(StrokeEvent ev) {
    if (strokeListeners == null) { return; }
    for (StrokeListener lis : strokeListeners) {
      lis.handleStrokeAction(ev);
    }
  }

  private void bug(String what) {
    Debug.out("SketchPad", what);
  }

}
