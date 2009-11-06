package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;
import org.six11.util.pen.Functions;
import java.util.List;
import java.util.ArrayList;

/**
 *
 **/
public class SquareInterpretation extends PolygonInterpretation {

  public SquareInterpretation(List<LineInterpretation> lines) {
    super("square", lines);
    confidence = 0.5; // TODO, maybe, but probably not.
    //    this.lines = lines;
    for (LineInterpretation lint : lines) {
      relate(lint, this);
    }
  }

  public static boolean isSquare(List<LineInterpretation> lines) {
    //    List<LineInterpretation> lines = pi.getLines();
    List<SketchInterpretation> manyInterps;
    List<AdjacentLineInterpretation> alis = new ArrayList<AdjacentLineInterpretation>();
    boolean ret = false;
    double error = 0d;
    int numCorners = 0;
    double longest = Double.MIN_VALUE;
    double shortest = Double.MAX_VALUE;
    double lineError = Double.MAX_VALUE;
    if (lines.size() == 4) {
      for (LineInterpretation line : lines) {
	double thisLength = line.getLine().getLength();
	longest  = Math.max(thisLength, longest);
	shortest = Math.min(thisLength, shortest);
	manyInterps = line.getChildren("adjacent_line");
	for (SketchInterpretation si : manyInterps) {
	  if (!alis.contains(si)) {
	    alis.add((AdjacentLineInterpretation)si);
	  }
	}
      }
      lineError = shortest / longest;
      if (lineError > 0.8) {
	for(AdjacentLineInterpretation ali : alis) {
	  if (lines.contains(ali.getLineA()) && lines.contains(ali.getLineB())) {
	    // ensure that the adjacent line is one of the polygon's corners.
	    numCorners++;
	    double angle = Functions.getAngleBetween(ali.getLineA().getLine(),
						     ali.getLineB().getLine());
	    angle = Math.toDegrees(Math.abs(angle));
	    angle = Math.abs(90d - angle);
	    error += angle;
	  }
	}
      }
      if (numCorners == 4 && error < 40d) {
	ret = true;
      }
    }

    return ret;
  }

}
