package org.six11.olive2.rend;

import org.six11.util.gui.shape.Circle;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.olive2.SketchRenderer;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.DenseInterpretation;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * 
 **/
public class DenseRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(1.6f);
  private Stroke thinDrawingStroke = new BasicStroke(0.8f);

  public DenseRenderer() {
    super(true, "dense");
  }

  public void render(Graphics2D g, SketchInterpretation si) {
    DenseInterpretation vi = (DenseInterpretation) si;
    g.setStroke(drawingStroke);
    g.setColor(Color.GREEN);
    g.draw(vi.getHullAsSequence());
    g.setStroke(thinDrawingStroke);
    g.setColor(Color.RED.darker().darker());
    g.draw(vi.getRectAsSequence());
    Pt cen = vi.getCentroid();
    Circle c = new Circle(cen.getX(), cen.getY(), 8.0);
    g.draw(c);
  }
}

