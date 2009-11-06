package org.six11.olive2.analyzers;

import org.six11.util.Debug;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Functions;
import org.six11.olive2.SketchAnalyzer;
import org.six11.olive2.StrokeEvent;
import org.six11.olive2.SketchBook;
import org.six11.olive2.interp.LineInterpretation;
import org.six11.olive2.interp.PolygonInterpretation;
import org.six11.olive2.interp.DenseInterpretation;
import org.six11.olive2.interp.NotchInterpretation;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 *
 **/
public class NotchAnalyzer extends SketchAnalyzer {


  protected Map<PolygonInterpretation, List<DenseInterpretation>> avoid;

  public NotchAnalyzer(SketchBook book) {
    super(book, 
	  false, // observeBegin 
	  false, // observeProgress
	  true,  // observeEnd
	  true); // observeIntegral
    avoid = new HashMap<PolygonInterpretation, List<DenseInterpretation>>();
  }
  
  private void setAvoid(PolygonInterpretation pi, DenseInterpretation di) {
    if (!avoid.containsKey(pi)) {
      avoid.put(pi, new ArrayList<DenseInterpretation>());
    }
    List<DenseInterpretation> many = avoid.get(pi);
    if (!many.contains(di)) {
      many.add(di);
    }
  }

  private boolean shouldAvoid(PolygonInterpretation pi, DenseInterpretation di) 
  {
    boolean ret = false;
    if (avoid.containsKey(pi)) {
      List<DenseInterpretation> many = avoid.get(pi);
      ret = many.contains(di);
    }
    return ret;
  }

  public void handleEnd(StrokeEvent ev) {
  }

  public void handleIntegral() {
    List polygons = book.getInterpretations("polygon");
    List denseRegions = book.getInterpretations("dense");
    Debug.out("NotchAnalyzer", "what up, yo? " + 
	      polygons.size() + " polygons and " +
	      denseRegions.size() + " dense regions.");
    PolygonInterpretation polygon;
    DenseInterpretation dense;
    for (int i=0; i < polygons.size(); i++) {
      polygon = (PolygonInterpretation) polygons.get(i);
      for (int j=0; j < denseRegions.size(); j++) {
	dense = (DenseInterpretation) denseRegions.get(j);
	if (!shouldAvoid(polygon, dense)) {
	  maybeMakeNotch(polygon, dense);
	  setAvoid(polygon, dense);
	}
      }
    }
  }

  private void maybeMakeNotch(PolygonInterpretation polygon,
			      DenseInterpretation dense) {
    // polygon area should be >> dense region area
    double polyArea = polygon.getHull().getConvexArea();
    double denseArea = dense.getHull().getConvexArea();
    Debug.out("NotchAnalyzer", "Area ratio is " + 
	      Debug.num(polyArea) + " / " + Debug.num(denseArea) + " = " +
	      Debug.num(polyArea/denseArea));

    /**** Potentially bail ****/
    if (polyArea / denseArea < 10.0) {
      Debug.out("NotchAnalyzer", "area ratio incorrect, bailing");
      return;
    }
    
    // the dense area should be close to the polygon boundary, but
    // still inside it.
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

    /**** Potentially bail ****/
    if (closestLine == null) {
      Debug.out("NotchAnalyzer", "Couldn't find closest line. bailing");
      return;
    }

    double polyRadius = Functions.getDistanceBetweenPointAndLine
      (polyCentroid, closestLine);
    Debug.out("NotchAnalyzer", "Distance ratio is " + 
	      Debug.num(closestDist) + " / " + Debug.num(polyRadius) + " = " +
	      Debug.num(closestDist / polyRadius));
    if ((closestDist / polyRadius) > 0.3) {
      Debug.out("NotchAnalyzer", "distance ratio wrong. bailing");
      return;
    }
    
    new NotchInterpretation(polygon, dense);
    book.flagRecache();
    Debug.out("NotchAnalyzer", " * I have a winner *");
  }

}
