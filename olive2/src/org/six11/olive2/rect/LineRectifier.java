package org.six11.olive2.rect;

import org.six11.olive2.SketchRectifier;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.SketchBook;
import org.six11.olive2.interp.LineInterpretation;
import org.six11.util.Debug;
import org.six11.util.pen.Line;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Vec;
import org.six11.util.data.GaussianHat;
import java.util.List;
import java.util.Map;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * 
 **/
public class LineRectifier extends SketchRectifier {
  Stroke drawingStroke;

  public LineRectifier() {
    drawingStroke = new BasicStroke(4f);
  }

  public void rectifyAll(SketchBook book) {
    List<SketchInterpretation> lines = book.getInterpretations("line");
    Debug.out("LineRectifier", "rectify " + lines.size() + " lines");
  }

  public void rectify(SketchInterpretation interp, Map params) {
    if (interp instanceof LineInterpretation) {
      LineInterpretation lint = (LineInterpretation) interp;
      for (Object key : params.keySet()) {
	// move points some num. of pixels closer to the ideal.
	if ("pixels".equals(key)) {
	  double pixels = (Double) params.get("pixels");
	  rectifyByPixels(lint, pixels);
	}
	
	// move one of the ends of the line closer to the givel point.
	if ("endpoint".equals(key)) {
	  Pt pt = (Pt) params.get("endpoint");
	  rectifyByEndpoint(lint, pt);
	}

	if ("vertical".equals(key)) {
	  // value doesn't matter.
	  Pt start = lint.getLine().getStart();
	  Pt end = lint.getLine().getEnd();
	  Pt desired = new Pt(start.getX(), end.getY());
	  rectifyByEndpoint(lint, desired);
	}
      }
    }
  }

  private void rectifyByPixels(LineInterpretation interp, double pixels) {
    Line ideal = interp.getLine();
    Sequence seq = interp.getPoints();
    
    double d; // i love those
    Pt linePt;
    GaussianHat gauss = new GaussianHat(pixels, pixels/4d);
    for (Pt pt : seq) {
      linePt = Functions.getNearestPointOnLine(pt, ideal);
      if (!Functions.eq(pt, linePt, Functions.EQ_TOL)) {
	d = Functions.getDistanceBetweenPointAndLine(pt, ideal);
	if (d < gauss.getDouble()) {
	  // simply make pt be coincidental with linePt
	  pt.setLocation(linePt);
	} else {
	  Vec dir = new Vec(pt, linePt);
	  dir = dir.getVectorOfMagnitude(gauss.getDouble());
	  double x = pt.getX() + dir.getX();
	  double y = pt.getY() + dir.getY();
	  pt.setLocation(x, y);
	}
      }
    }
  }

  private void rectifyByEndpoint(LineInterpretation interp, Pt endPoint) {
    Debug.out("LineRectifier", "rectifyByEndpoint temporarily or permanently disabled");
//     Line ideal = interp.getLine();
//     Sequence seq = interp.getPoints();
//     Pt moveMe, pivot;
//     if (ideal.getStart().distance(endPoint) < ideal.getEnd().distance(endPoint)) {
//       moveMe = ideal.getStart();
//       pivot = ideal.getEnd();
//     } else {
//       pivot = ideal.getStart();
//       moveMe = ideal.getEnd();
//     }
//     if (!Functions.eq(moveMe, endPoint, Functions.EQ_TOL)) {
//       Vec original = new Vec(pivot, moveMe);
//       Vec desired = new Vec(pivot, endPoint);
//       double scaleFactor = desired.mag() / original.mag();
//       double angle = Functions.getAngleBetween(original, desired);
//       Debug.out("LineRectifier", "Translate by: " + Debug.num(pivot.getX() * -1d) + ", " + Debug.num(pivot.getY() * -1d));
//       Debug.out("LineRectifier", "Rotate by:    " + Debug.num(angle));
//       Debug.out("LineRectifier", "Scale by:     " + Debug.num(scaleFactor));
//       AffineTransform affine = new AffineTransform();
//       affine.translate(pivot.getX() * -1d, pivot.getY() * -1d);
//       affine.rotate(angle);
//       affine.scale(scaleFactor, scaleFactor);
//       affine.translate(pivot.getX(), pivot.getY());
//       Pt dummy = new Pt();
//       for (Pt pt : seq) {
// 	affine.transform(pt, dummy);
// 	Debug.out("LineRectifier", Debug.num(pt) + " --> " + Debug.num(dummy));
//       }
//     }
  }
}
