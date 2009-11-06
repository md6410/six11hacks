package org.six11.olive2.rend;

import org.six11.util.gui.shape.Circle;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.Debug;
import org.six11.olive2.SketchRenderer;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.LineInterpretation;
import org.six11.olive2.interp.AdjacentLineInterpretation;

/**
 * 
 **/
public class AdjacentLineRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(1.6f);

  public AdjacentLineRenderer() {
    super(false, "adjacent_line");
  }
  
  public void render(Graphics2D g, SketchInterpretation interp) {
    // set the stroke + color
    g.setStroke(drawingStroke);
    g.setColor(Color.RED);
    AdjacentLineInterpretation adj = (AdjacentLineInterpretation) interp;
    Pt ix = adj.getIntersection();
    Circle c = new Circle(ix.getX(), ix.getY(), 8.0);
    g.draw(c);
  }
}

