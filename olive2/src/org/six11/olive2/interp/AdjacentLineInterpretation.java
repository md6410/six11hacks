package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Line;

/**
 *
 **/
public class AdjacentLineInterpretation extends SketchInterpretation {

  private LineInterpretation interpA, interpB;
  private Pt intersection;
  private double sloppiness;

  public AdjacentLineInterpretation(LineInterpretation interpA, 
				    LineInterpretation interpB) {
    super("adjacent_line");
    confidence = 0.5; // TODO: should be a function of the error

    IntersectionData id = new IntersectionData(interpA.getLine(),
					       interpB.getLine());
    intersection = id.getIntersection();
    if (id.getLineOneParam() < 0.0) {

    }
    this.interpA = interpA;
    this.interpB = interpB;
    relate(interpA, this);
    relate(interpB, this);
  }

  public double getAngleBetweenLines() {
    // I have to make sure I orient the two lines so that the vectors
    // are beginning from the same point.
    Line a = orient(getLineA().getLine());
    Line b = orient(getLineB().getLine());
    return Functions.getAngleBetween(a, b);
  }
  
  private Line orient(Line line) {
    Line ret = null;
    if (line.getStart().distance(intersection) < line.getEnd().distance(intersection)) {
      ret = new Line(line.getStart(), line.getEnd());
    } else {
      ret = new Line(line.getEnd(), line.getStart());
    }
    return ret;
  }

  public Pt getIntersection() {
    return intersection;
  }

  public LineInterpretation getLineA() {
    return interpA;
  }


  public LineInterpretation getLineB() {
    return interpB;
  }


}
