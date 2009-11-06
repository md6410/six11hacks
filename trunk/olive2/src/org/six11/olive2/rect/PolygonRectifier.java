package org.six11.olive2.rect;

import org.six11.olive2.SketchRectifier;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.PolygonInterpretation;
import org.six11.util.Debug;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.gui.shape.Triangle;
import org.six11.util.gui.shape.Square;
import org.six11.util.gui.shape.MovableShape;
import org.six11.util.math.Function;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;
import java.util.List;

// TODO: I don't have enough intuition about how this works, I'll
// start with a Line rectifier.

/**
 * 
 **/
public class PolygonRectifier extends SketchRectifier {
  Stroke drawingStroke;
  public static Color COLOR_TRIANGLE = Color.RED;
  public static Color COLOR_SQUARE = Color.BLUE;
  public static Color COLOR_PENTAGON = Color.GREEN;
  public static Color COLOR_HEXAGON = Color.YELLOW;

  public PolygonRectifier() {
    drawingStroke = new BasicStroke(4f);
  }
  
  public void rectify(SketchInterpretation si, Map params) {
    PolygonInterpretation pi = (PolygonInterpretation) si;
    List<Sequence> geom = pi.getRectifiedGeometry("polygon");
    geom.clear();
    int n = pi.getNumSides();
    Pt centroid = pi.getCentroid();
    MovableShape s = null;
    if (pi.isEquiAngular() && pi.isEquiLateral()) {
      switch (n) {
      case 3:
	Debug.out("PolygonRectifier", "Equilateral Triangle");
	s = new Triangle(centroid.getX(), centroid.getY(), pi.getLengthMean());
	pi.setColor(COLOR_TRIANGLE);
	break;
      case 4:
	Debug.out("PolygonRectifier", "Equilateral Square");
	s = new Square(centroid.getX(), centroid.getY(), pi.getLengthMean());
	pi.setColor(COLOR_SQUARE);
	break;
      case 5:
	Debug.out("PolygonRectifier", "Equilateral Pentagon (not implemented yet)");
	break;
      case 6:
	Debug.out("PolygonRectifier", "Equilateral Hexagon (not implemented yet)");
	break;
      }
      if (s != null) {
	final MovableShape pure = s;
	final Sequence have = pi.getVertices();
	Function errorFunction = new Function() {
	    public double eval(double x) {
	      pure.rotateTo(x);
	      return GeometryFixer.measureError(GeometryFixer.join(pure.getGeometry()), have);
	    }
	  };
	double x = GeometryFixer.findMinimalError(errorFunction, 0d, 4d * Math.PI, 0.1);
	pure.rotateTo(x);
	geom.addAll(s.getGeometry());
      }
    } else {
      if (!pi.isEquiAngular()) {
	Debug.out("PolygonRectifier", n + " sided polygon is not equiangular");
      }
      if (!pi.isEquiLateral()) {
	Debug.out("PolygonRectifier", n + " sided polygon is not equilateral");
      }
    }
  }
}
