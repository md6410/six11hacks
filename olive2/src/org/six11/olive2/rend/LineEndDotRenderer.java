package org.six11.olive2.rend;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.HashSet;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;
import org.six11.util.gui.shape.Cross;
import org.six11.util.Debug;
import org.six11.olive2.SketchRenderer;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.LineInterpretation;

/**
 * 
 **/
public class LineEndDotRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(2f);
  private Cross cross = new Cross(0d, 0d, 7.5d);
  private Color startColor = new Color(0.0f, 0.7f, 0.0f, 0.5f);
  private Color endColor = new Color(0.7f, 0.0f, 0.0f, 0.5f);

  /**
   * Make a new renderer capable of drawing the dots at the end of "line" types.
   */
  public LineEndDotRenderer() {
    super(false, "line");
  }
  
  public void render(Graphics2D g, SketchInterpretation si) {
    g.setStroke(drawingStroke);
    LineInterpretation lineInterp = (LineInterpretation) si;
    Line line = lineInterp.getLine();
    cross.moveTo(line.getStart().getX(), 22 + line.getStart().getY());
    g.setColor(startColor);
    g.draw(cross);
    cross.moveTo(line.getEnd().getX(), 22 + line.getEnd().getY());
    g.setColor(endColor);
    g.draw(cross);
  }

}

