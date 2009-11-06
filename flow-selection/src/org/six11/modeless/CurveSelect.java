package org.six11.modeless;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;

/**
 *
 */
public class CurveSelect extends LinearSelect {

  private double speedbump = 10.0; // multiply with curvature to get
				   // distance increase. should be > 1

  public void augmentDistance(Sequence seq, Pt pointOnSequence) { 
    double curveSum = 0.0;
    if(seq.size() < 2) return;
    if (!seq.get(0).hasAttribute("curvature")) {
      Functions.calculateCurvature(seq);
    }
    double curve, dist;
    for (Pt pt : seq) {
      curve = Math.abs(pt.getDouble("curvature")) * speedbump;
      dist = pt.getDouble("selection distance");
      curveSum += curve;
      dist += curveSum;
      pt.setDouble("selection distance", dist);
    }
  }

}
