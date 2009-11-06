package org.six11.olive2.rend;

import org.six11.util.gui.shape.Square;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.olive2.SketchRenderer;

/**
 * 
 **/
public class DotRenderer extends SketchRenderer {

  private Stroke drawingStroke = new BasicStroke(0.6f);
  private double side = 2.7;
  private double sideHalf = 0d;//side * 0.5;
  //  private Square s = new Square(0d, 0d, side);

  public DotRenderer() {
    super(false);
  }

  public void render(Graphics2D g, Sequence seq) {
    g.setStroke(drawingStroke);
    for (Pt pt : seq) {
      Square s = new Square(pt.getX(), pt.getY() + 22, side);
      //      s.moveTo(pt.getX() - sideHalf, pt.getY() - sideHalf);
      g.setColor(Color.WHITE);
      g.fill(s);
      g.setColor(Color.BLACK);
      g.draw(s);
    }
  }
}

