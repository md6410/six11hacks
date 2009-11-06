package org.six11.olive2.rend;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import org.six11.util.pen.Sequence;
import org.six11.olive2.SketchRenderer;

/**
 * 
 **/
public class BasicRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(1.6f);

  public BasicRenderer() {
    super(true);
  }

  public void render(Graphics2D g, Sequence seq) {
    g.setStroke(drawingStroke);
    g.setColor(Color.BLACK);
    g.draw(seq);
  }
}

