package org.six11.olive2.rend;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.HashSet;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Line;
import org.six11.util.Debug;
import org.six11.olive2.SketchRenderer;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.NotchInterpretation;

/**
 * 
 **/
public class NotchRenderer extends SketchRenderer {

  private float notchWidth = 6.8f;
  private float notchDepth = notchWidth * 4.0f;

  private Stroke drawingStroke = new BasicStroke(notchWidth);
  private Color notchColor = Color.BLUE.darker();

  /**
   * Make a new renderer capable of drawing "notch" types.
   */
  public NotchRenderer() {
    super(false, "notch");
  }
  
  public void render(Graphics2D g, SketchInterpretation si) {
    g.setStroke(drawingStroke);
    g.setColor(notchColor);
    NotchInterpretation notch = (NotchInterpretation) si;
    
    Vec dir = notch.getDirection().getVectorOfMagnitude((double) notchDepth);
    Pt where = notch.getPoint();
    
    Line line = new Line(where, dir);
    g.draw(line);
  }

}

