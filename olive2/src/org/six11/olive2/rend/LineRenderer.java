package org.six11.olive2.rend;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.HashSet;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.Debug;
import org.six11.olive2.SketchRenderer;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.LineInterpretation;

/**
 * 
 **/
public class LineRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(4.6f);

  /**
   * Make a new renderer capable of drawing "line" types.
   */
  public LineRenderer() {
    super(false, "line");
  }
  
  public void render(Graphics2D g, SketchInterpretation si) {
      g.setStroke(drawingStroke);
      g.setColor(Color.BLUE);
      LineInterpretation lineInterp = (LineInterpretation) si;
      g.draw(lineInterp.getLine());
  }

}

