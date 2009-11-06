
package org.six11.modeless;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.OperateFunction;
import org.six11.util.pen.Pt;

public class MoveOperation implements OperateFunction {
  
  Pt lastPt;

  public MoveOperation() {
    lastPt = null;
  }

  public void beginOperation(Pt penLocation) { 
    lastPt = penLocation;
  }

  public void endOperation() { 
    lastPt = null;
  } 

  public void operate(Sequence seq, Pt pen) { 
    double str;
    if (lastPt == null) return;
    double dx = pen.getX() - lastPt.getX();
    double dy = pen.getY() - lastPt.getY();
    for (Pt pt : seq) {
      if (pt.hasAttribute("selection strength")) {
	str = pt.getDouble("selection strength");
	pt.setLocation(pt.getX() + (str * dx),
		       pt.getY() + (str * dy));
      }
    }
    lastPt = pen;
  }

}
