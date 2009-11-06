package org.six11.modeless;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.OperateFunction;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;
import org.six11.util.Debug;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

/**
 * This is a smooth operator, no doubt about it. This works much like
 * the hold-to-select process.
 *
 * First, after the user has moved a selection and then stopped for
 * some amount of time, the operation switches over to SmoothOperation
 * (this one). Immediately after switching to this operation, the
 * controller will call beginOperation(Pt), indicating the spot where
 * the mouse has stopped. Then, SmoothOperation starts a timer that
 * triggers a smoothing every so many milliseconds.
 *
 * A smoothing involves taking the original sequence and turning it
 * into a cubic spline. 
 */
public class SmoothOperation implements OperateFunction {
  
  private final static double ERROR_TOLERANCE = 0.5;
  private final static double C = 20.0;

  protected double toofar = 6.0;
  protected int tick = 70;
  protected Timer timer;
  protected Component comp;

  transient protected Pt penDown;
  transient protected Sequence sequence;
  transient protected int numSplinePoints;
  transient protected Sequence spline;
  transient protected double error; // sum of distance from sequence to spline

  public SmoothOperation(Component comp) {
    this.comp = comp;
    ActionListener ding = new ActionListener() {
	public void actionPerformed(ActionEvent ev) {
	  try {
	    smooth();
	    SmoothOperation.this.comp.repaint();
	  } catch (Throwable th) {
	    bug("Caught exception, printing stack trace.");
	    th.printStackTrace();
	    if (th.getCause() != null) {
	      th.getCause().printStackTrace();
	    } else {
	      bug("No cause for this exception.");
	    }
	  }
	}
      };
    timer = new Timer(tick, ding);
  }

  /**
   * This is one 'tick' in a smoothing operation. Of the transient
   * variables in this object, 'penDown', 'sequence', and
   * 'numSplinePoints' are guaranteed to have valid values. 'spline'
   * may be null, and if it is, 'error' is also not valid.
   *
   */
  protected void smooth() {
    if (!timer.isRunning() || sequence == null) {
      return;
    }
    if (spline == null) {
      spline = Functions.getSpline(sequence, numSplinePoints, 4);
      error = Double.MAX_VALUE;
      establishPairings();
    } else if (error < ERROR_TOLERANCE) {
      if (numSplinePoints <= 4) {
	spline = null;
	error = Double.MAX_VALUE;
	timer.stop();
	return;
      }
      
      numSplinePoints = Math.min(numSplinePoints - 4, 4);
      error = Double.MAX_VALUE;

      spline = Functions.getSpline(sequence, numSplinePoints, 4);
      establishPairings();
    }

    error = 0.0;
    int ptCnt = 0;
    for (Pt pt : sequence) {
      Pt destination = (Pt) pt.getAttribute("tween");

      if (pt.distance(destination) < 1.0) {
	pt.setLocation(destination.getX(), destination.getY());
      } else {
	double dx = (destination.getX() - pt.getX()) / C;
	double dy = (destination.getY() - pt.getY()) / C;
	pt.setLocation(pt.getX() + dx , pt.getY() + dy);
	error += Math.hypot(dx, dy);
      }
      ptCnt++;
    }
    
  }

  private void establishPairings() {
    // HAVE: spline, sequence

    // NEED: for each point pt in sequence, find a point s along
    // spline (interpolating) that is same % distance along spline as
    // pt is along sequence. Then call pt.setAttribute("tween", s);

    // 'spline' is now a spline that we should tween to.
    int splineIdx = 0;
    double splineDist = 0.0;
    double seqDist = 0.0;
    Pt pt;
    Pt prevSeq = sequence.get(0);
    Pt prevSpline;
    for (int seqIdx=0; seqIdx < sequence.size(); seqIdx++) {

      // part 1: get pt and seqDist set up correctly
      pt = sequence.get(seqIdx);
      seqDist += prevSeq.distance(pt);
      prevSeq = pt;

      // part 2: find index of spline point that is just past where we
      // want to be, then find the interpolated point between our
      // known point and that spline point. Set the "tween" attribute
      // of the sequence point.
      double additionalDist = 0.0;
      prevSpline = spline.get(splineIdx);
      Pt spt;
      for (int sidx = splineIdx; sidx < spline.size(); sidx++) {
	spt = spline.get(sidx);
	additionalDist = spt.distance(prevSpline);
	if (additionalDist + splineDist >= seqDist) {
	  // the target point is between splineIdx and sidx
	  Pt target = Functions.getPointAtDistance(spline, splineIdx, splineDist, 1, seqDist);
	  pt.setAttribute("tween", target);
	  break;
	} else if (sidx >= (spline.size() -1)) {
	  pt.setAttribute("tween", spline.getLast());
	}
	prevSpline = spt;
	splineIdx = sidx;
	splineDist += additionalDist;
      }
    }
  }

  public void beginOperation(Pt penLocation) { 
    penDown = penLocation;
  }

  public void endOperation() { 
    penDown = null;
    spline = null;
    error = Double.MAX_VALUE;
    timer.stop();
  } 

  public void operate(Sequence seq, Pt penLocation) {
    if (sequence == null) {
      numSplinePoints = seq.size();
    }
    sequence = seq;
    if (penDown != null && penLocation.distance(penDown) > toofar) {
      endOperation();
    } else if (!timer.isRunning()) {
      timer.start();
    }
  }

  private void bug(String what) {
    Debug.out("SmoothOperator", what);
  }
}
