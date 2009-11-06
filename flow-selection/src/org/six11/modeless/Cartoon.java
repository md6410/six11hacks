package org.six11.modeless;

import java.util.List;
import java.util.ArrayList;
import org.six11.util.pen.Stroke;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;
import org.six11.util.pen.DrawFunction;
import org.six11.util.pen.Functions;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * A Cartoon holds all the drawable strokes in a cartoon.
 **/
public class Cartoon {

  protected int strokeCounter = 0;
  protected List<Stroke> strokes;

  protected BasicStroke defaultStroke;
  protected BasicStroke selectedStroke;
  protected Color selectedColor;
  protected DrawFunction drawFunction;

  public Cartoon() {
    this.strokes = new ArrayList<Stroke>();
    defaultStroke = new BasicStroke(2.0f);
    selectedStroke = new BasicStroke(5.0f);
    selectedColor = Color.RED;
    drawFunction = new DrawFunction() {
      public void draw(Sequence seq, Graphics2D g) {
        Line line = new Line();
        if (seq.size() == 1) {
          Pt second = new Pt(seq.get(0).getX() + 0.1, seq.get(0).getY() + 0.1);
          seq.add(second);
        }
        for (Pt pt : seq) {
          line.push(pt);
          if (line.isValid()) {
            if (pt.hasAttribute("color")) {
              Color color = (Color) pt.getAttribute("color");
              g.setColor(color);
            } else {
              g.setColor(Color.BLACK);
            }
            g.setStroke(defaultStroke);
            g.draw(line);

            // if there is a selection on either endpoint of this
            // line, draw a fat red line, with some amount of alpha
            // as a function of the mean selection strength.
            if (line.getStart().hasAttribute("selection drawn")
                && line.getEnd().hasAttribute("selection drawn")) {
              double strA = line.getStart().hasAttribute("selection strength") ? line.getStart()
                  .getDouble("selection strength") : 0.0;
              double strB = line.getEnd().hasAttribute("selection strength") ? line.getEnd()
                  .getDouble("selection strength") : 0.0;
              double lineStr = ((strA + strB) / 2.0); // mean strength
              if (lineStr > 0.0) {
                g.setStroke(selectedStroke);
                int alpha = (int) (255.0 * lineStr);
                g.setColor(new Color(selectedColor.getRed(), selectedColor.getGreen(),
                    selectedColor.getBlue(), alpha));
                g.draw(line);
              }
            }
          }
        }
      }
    };
  }

  public void add(Stroke stroke) {
    stroke.getDefault().setDrawFunction(drawFunction);
    strokes.add(stroke);
  }

  public void draw(Graphics2D g) {
    for (Stroke stroke : strokes) {
      stroke.draw(g);
    }
  }

  public Stroke getNearestStroke(Pt epicenter) {
    double minDist = Double.MAX_VALUE;
    Stroke nearest = null;
    for (Stroke stroke : strokes) {
      double thisDist = Functions.getMinDistBetweenPointAndSequence(epicenter, stroke.getDefault());
      if (thisDist < minDist) {
        nearest = stroke;
        minDist = thisDist;
      }
    }
    return nearest;
  }

}
