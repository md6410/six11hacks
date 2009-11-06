package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.data.Statistics;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Functions;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.awt.Color;

/**
 *
 **/
public class NotchInterpretation extends SketchInterpretation {

  protected Pt where;
  protected Vec dir;

  public NotchInterpretation(PolygonInterpretation polygon,
			     DenseInterpretation dense) {
    super("notch");

    // find the line on the polygon that is nearest the dense region
    Pt polyCentroid = polygon.getHull().getConvexCentroid();
    Pt denseCentroid = dense.getHull().getConvexCentroid();
    Line line;
    double d;
    Line closestLine = null;
    double closestDist = Double.MAX_VALUE;
    for (LineInterpretation lineInterp : polygon.getLines()) {
      line = lineInterp.getLine();
      d = Functions.getDistanceBetweenPointAndLine(denseCentroid, line);
      if (d < closestDist) {
	closestLine = line;
	closestDist = d;
      }
    }

    // draw a line between the two centroids, and find where that line
    // intersects the polygon's line. Bam! there's your notch point.
    Line radialLine = new Line(polyCentroid, denseCentroid);
    IntersectionData id = new IntersectionData(radialLine, closestLine);
    where = id.getIntersection();
    
    // Now figure out which direction it should go. Make it normal to
    // the polygon's boundary facing inwards.
    Vec polyNormalA = Functions.getVectorNormal(new Vec(closestLine));
    Vec polyNormalB = polyNormalA.getFlip();
    Vec radialVec = new Vec(radialLine).getFlip();
    double dotA = Functions.getDotProduct(radialVec, polyNormalA);
    double dotB = Functions.getDotProduct(radialVec, polyNormalB);
    if (dotA < dotB) {
      dir = polyNormalA;
    } else {
      dir = polyNormalB;
    }

    relate(polygon, this);
    relate(dense, this);

    Debug.out("NotchInterpretation", "notch location: " +
	      Debug.num(where) + ", dir: " + Debug.num(dir));
  }

  public Vec getDirection() {
    return dir;
  }

  public Pt getPoint() {
    return where;
  }
}
