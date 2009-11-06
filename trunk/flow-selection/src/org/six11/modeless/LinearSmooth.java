package org.six11.modeless;

import org.six11.util.Debug;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Line;
import org.six11.util.pen.OperateFunction;

/**
 * 
 **/
public class LinearSmooth implements OperateFunction {

  public final static double DAMPER = 40.0;
  protected Pt penDown;
  protected Line line;

  public void beginOperation(Pt penLocation) {
    penDown = penLocation;
  }

  public void operate(Sequence seq, Pt penLocation) {
    double str, dx, dy;
    if (line == null) {
      Pt lineStart = null;
      Pt lineEnd = null;
      for (Pt pt : seq) {
	if (pt.hasAttribute("selection strength")) {
	  str = pt.getDouble("selection strength");
	  if (lineStart == null && str > 0.0) {
	    lineStart = pt;
	  } else if (lineStart != null && str >= 0.0) {
	    lineEnd = pt;
	    break;
	  }
	}
      }
      if (lineStart == null || lineEnd == null) {
	Debug.out("LinearSmooth", "Couldn't determine endpoints of line to tween to. I have start/end as " + lineStart + "/" + lineEnd);
	return;
      }
      line = new Line(lineStart, lineEnd);
    }
    for (Pt pt : seq) {
      if (pt.hasAttribute("selection strength")) {
	str = pt.getDouble("selection strength");
	if (str > 0.0) {
	  line.ptLineDist(pt);	  
	  Pt near = Functions.getNearestPointOnLine(pt, line);
	  dx = (near.getX() - pt.getX()) / DAMPER;
	  dy = (near.getY() - pt.getY()) / DAMPER;
	  pt.setLocation(pt.getX() + (str * dx),
			 pt.getY() + (str * dy));
	}
      }
    }
  }

  public void endOperation() {
    line = null;
    penDown = null;
  }
}
