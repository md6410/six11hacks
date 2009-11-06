package org.six11.modeless;

import org.six11.util.Debug;
import org.six11.util.pen.SelectFunction;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;

/**
 *
 */
public class LinearSelect implements SelectFunction {

  protected double strengthFunction(double thisDist, double maxDistance) {
    return Math.max(0.0, 1.0 - (thisDist/maxDistance));
  }

  public void augmentDistance(Sequence seq, Pt pointOnSequence) { 
    bug("I don't need to augment distance, but thanks for asking.");
  }

  public void select(Sequence seq, double maxDistance) { 
    for (Pt pt : seq) {
      double thisDist = pt.getDouble("selection distance");
      double strength = strengthFunction(thisDist, maxDistance);
      pt.setDouble("selection strength", strength);
    }
  }

  public void deselect(Sequence seq) { 
    bug("In linear select function, deselecting.");
    for (Pt pt : seq) {
      pt.setDouble("selection strength", 0.0);
      pt.removeAttribute("selection drawn");
    }
  }

  private void bug(String what) {
    Debug.out("CornerSelect", what);
  }

}
