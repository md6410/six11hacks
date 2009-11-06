package org.six11.modeless;

import org.six11.util.pen.MouseThing;
import org.six11.util.pen.FSM;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Stroke;
import org.six11.util.pen.Functions;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.ArrayList;


/**
 * 
 **/
public class SmartMouseThing extends MouseThing {

  public final static int T_THRESH = 900;
  public final static int BLOT_TIME = 45;
  public final static double M_THRESH = 10.0;
  public final static double NORMALIZATION_DISTANCE = 2.5;
  
  protected transient String debugLastState;

  protected FSM fsm;
  protected Stroke stroke;
  protected Sequence seq;
  protected Pt dragThreshPoint; 
  protected Pt recentPt;
  protected Pt epicenter;
  protected Pt operatePrev;
  protected Timer expandoTimer;
  protected long lastMove;

  protected List<DrawingListener> drawingListeners;

  public SmartMouseThing() {
    
    fsm = new FSM("Drawing");
    //    fsm.setDebugMode(true);
    drawingListeners = new ArrayList<DrawingListener>();
    
    ActionListener expandoHold = new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
	  if (fsm.getState().equals("expand selection")) {
	    fsm.addEvent("hold");
	  } else if (fsm.getState().equals("move") || fsm.getState().equals("smooth")) {
	    if (System.currentTimeMillis() - lastMove > T_THRESH) {
	      fsm.addEvent("hold");
	    } // else { don't do anything--including stopping the timer. }
	  } else {
	    expandoTimer.stop();
	  }
	}
      };
    expandoTimer = new Timer(BLOT_TIME, expandoHold);

    fsm.addChangeListener(new ChangeListener() {
 	public void stateChanged(ChangeEvent ev) {
	  if (!fsm.getState().equals(debugLastState)) {
	    debugLastState = fsm.getState();
	  }
 	}
      });

    fsm.addState("ink");
    fsm.addState("draw");
    fsm.addState("blot init");
    fsm.addState("show widget");
    fsm.addState("expand selection");
    fsm.addState("move");
    fsm.addState("smooth");
    
    FSM.Transition trans;

    trans = new FSM.Transition("down", "ink", "draw") {
	public void doBeforeTransition() {
	  seq = new Sequence();
	  seq.add(recentPt);
	  epicenter = recentPt.copy();
	  stroke = new Stroke(seq);
	  
	  fireProgress();
	  ActionListener ding = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
		if (seq != null && seq.length() < M_THRESH) {
		  fsm.addEvent("hold");
		}
	      }
	    };
	  Timer timer = new Timer(T_THRESH, ding);
	  timer.setRepeats(false);
	  timer.start();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("drag", "draw", "draw") {
	public void doBeforeTransition() {
	  if (!recentPt.equals(seq.getLast())) {
	    seq.add(recentPt);
	    fireProgress();
	  }
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("up", "draw", "ink") {
	public void doBeforeTransition() {
	  Sequence normalized = Functions.getNormalizedSequence(seq, NORMALIZATION_DISTANCE);
	  stroke.setSequence("normalized", normalized);
	  stroke.setDefault("normalized");
	  fireComplete();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("hold", "draw", "blot init") {
	public void doBeforeTransition() {
	  Sequence normalized = Functions.getNormalizedSequence(seq, NORMALIZATION_DISTANCE);
	  stroke.setSequence("normalized", normalized);
	  fireFade();
	}

	public void doAfterTransition() {
	  // TODO: there's some ink in 'seq', and it might just be a
	  // gesture. Ultimately I want to check to see if it is a
	  // gesture, and if it is, I will do an fsm.addEvent("widget
	  // gesture"). But for now, immediately do a
	  // fsm.addEvent("not a widget gesture").
	  fsm.addEvent("not a widget gesture");
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("widget gesture", "blot init", "show widget");
    fsm.addTransition(trans);

    trans = new FSM.Transition("not a widget gesture", "blot init", "expand selection") {
	public void doAfterTransition() {
	  fireSelectionBegin();
	  dragThreshPoint = recentPt.copy();
	  expandoTimer.start();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("done", "show widget", "ink");
    fsm.addTransition(trans);

    trans = new FSM.Transition("up", "expand selection", "ink") {
	public void doBeforeTransition() {
	  fireSelectionRemoved();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("hold", "expand selection", "expand selection") {
	public void doBeforeTransition() {
	  // this represents one 'tick'.
	  fireSelectionExpand();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("drag", "expand selection", "move") {
	public void doBeforeTransition() {
	  fireSelectionComplete();
	  dragThreshPoint = null;
	}

	public void doAfterTransition() {
	  lastMove = System.currentTimeMillis();
	  fireOperateBegin(ScratchPad.OP_MOVE);
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("drag", "move", "move") {
	public void doBeforeTransition() {
	  lastMove = System.currentTimeMillis();
	  fireOperateProgress();
	}
      };
    fsm.addTransition(trans);    

    trans = new FSM.Transition("up", "move", "ink") {
	public void doBeforeTransition() {
	  fireOperateComplete();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("hold", "move", "smooth") {
	public void doBeforeTransition() {
	  fireOperateComplete();
	  dragThreshPoint = recentPt.copy();
	  fireOperateBegin(ScratchPad.OP_SMOOTH);
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("hold", "smooth", "smooth") {
	public void doBeforeTransition() {
	  fireOperateProgress();
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("drag", "smooth", "move") {
	public void doBeforeTransition() {
	  fireOperateComplete();
	  lastMove = System.currentTimeMillis();
	  fireOperateBegin(ScratchPad.OP_MOVE);
	}
      };
    fsm.addTransition(trans);

    trans = new FSM.Transition("up", "smooth", "ink") {
	public void doBeforeTransition() {
	  Sequence normalized = Functions.getNormalizedSequence(seq, NORMALIZATION_DISTANCE);
	  stroke.setSequence("normalized", normalized);
	  stroke.setDefault("normalized");
	  fireComplete();
	  fireOperateComplete();
	}
      };
    fsm.addTransition(trans);

    
  }

  public void addDrawingListener(DrawingListener dl) {
    if(!drawingListeners.contains(dl)) {
      drawingListeners.add(dl);
    }
  }

  protected void fireProgress() {
    for (DrawingListener dl : drawingListeners) {
      dl.strokeInProgress(stroke);
    }
  }

  protected void fireComplete() {
    for (DrawingListener dl : drawingListeners) {
      dl.strokeComplete(stroke);
    }
  }

  protected void fireFade() {
    for (DrawingListener dl : drawingListeners) {
      dl.strokeFade(stroke);
    }    
  }

  protected void fireSelectionBegin() {
    for (DrawingListener dl : drawingListeners) {
      dl.selectionBegan(epicenter);
    }
  }

  protected void fireSelectionComplete() {
    for (DrawingListener dl : drawingListeners) {
      dl.selectionComplete();
    }
  }

  protected void fireSelectionRemoved() {
    for (DrawingListener dl : drawingListeners) {
      dl.selectionRemoved();
    }
  }

  protected void fireSelectionExpand() {
    for (DrawingListener dl : drawingListeners) {
      dl.selectionExpand();
    }
  }

  protected void fireOperateBegin(String operationName) {
    for (DrawingListener dl : drawingListeners) {
      dl.operateBegin(recentPt, operationName);
    }    
  }

  protected void fireOperateProgress() {
    for (DrawingListener dl : drawingListeners) {
      dl.operateProgress(recentPt);
    }    
  }

  protected void fireOperateComplete() {
    for (DrawingListener dl : drawingListeners) {
      dl.operateComplete();
    }    
  }
  
  public void mousePressed(MouseEvent ev) {
    recentPt = new Pt(ev);
    fsm.addEvent("down");
  }

  public void mouseDragged(MouseEvent ev) {
    Pt oldRecent = recentPt;
    recentPt = new Pt(ev);
    if (oldRecent != null) {
      double dx = Math.abs(recentPt.getX() - oldRecent.getX());
      double dy = Math.abs(recentPt.getY() - oldRecent.getY());
      if (dx < 1.0 && dy < 1.0) {
	return;
      }
    }
    if (dragThreshPoint != null && dragThreshPoint.distance(recentPt) < M_THRESH) {
      return;
    } else {
      fsm.addEvent("drag");
    }
  }

  public void mouseReleased(MouseEvent ev) {
    recentPt = new Pt(ev);
    fsm.addEvent("up");
  }
    
}
