package org.six11.modeless;

import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import org.six11.util.pen.Stroke;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;
import org.six11.util.pen.SelectFunction;
import org.six11.util.pen.OperateFunction;
import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * A ScratchPad is a simple JComponent that can draw Strokes.
 **/
public class ScratchPad extends JComponent {
  
  public static final String OP_MOVE = "operation: move";
  public static final String OP_SMOOTH = "operation: smooth";
  public static final String OP_HINGE = "operation: hinge";

  public static final int FADE_TICK = 70;
  public static final int FADE_INCREMENT = 6;
  public static final double SELECT_INCREMENT= 4.0;

  Stroke inProg;
  Stroke selectedStroke;
  double selectionDist;

  List<Stroke> fading;
  Timer fadeTimer;

  Map<String, SelectFunction> selectFunctions;
  String currentSelectFunction;

  Map<String, OperateFunction> operateFunctions;
  String currentOperateFunction;

  // Pt prevOperatePoint;

   Cartoon complete;

  public ScratchPad() {
    SmartMouseThing smt = new SmartMouseThing();
    selectFunctions = new HashMap<String, SelectFunction>();
    operateFunctions = new HashMap<String, OperateFunction>();

    addMouseMotionListener(smt);
    addMouseListener(smt);
    complete = new Cartoon();
    fading = new ArrayList<Stroke>();
    ActionListener fadeTick = new ActionListener() {
	public void actionPerformed(ActionEvent ev) {
	  fade(); // fade will turn off the timer if it's not
		  // necessary any more.
	}
      };
    fadeTimer = new Timer(FADE_TICK, fadeTick);

    smt.addDrawingListener(new DrawingListener() {
	public void strokeInProgress(Stroke progress) {
	  inProg = progress;
	  repaint();
	}

	public void strokeFade(Stroke fadeMe) {
	  inProg = null;
	  fading.add(fadeMe);
	  fade();
	}

	public void strokeComplete(Stroke done) {
	  inProg = null;
	  complete.add(done);
	  repaint();
	}

	public void selectionBegan(Pt epicenter) {
	  selectedStroke = complete.getNearestStroke(epicenter);
	  selectionDist = 0.0;
	  if (selectedStroke != null) {
	    Functions.setDistancesOnSequence("selection distance", selectedStroke.getDefault(), 
					     epicenter);
	    Pt centerOnSequence = Functions.getNearestPointOnSequence(epicenter, selectedStroke.getDefault());
	    getSelectFunction(currentSelectFunction).augmentDistance
	      (selectedStroke.getDefault(), centerOnSequence);
	    for (Pt pt : selectedStroke.getDefault()) {
	      pt.setAttribute("selection drawn", "sure");
	    }
	  }
	}
	
	public void selectionExpand() {
	  if (selectedStroke == null) {
	    return;
	  }
	  SelectFunction selFunction = getSelectFunction(currentSelectFunction);
	  selectionDist += SELECT_INCREMENT;
	  selFunction.select(selectedStroke.getDefault(), selectionDist);
	  repaint();
	}

	public void selectionComplete() {
	  // indicates the selection should be removed
	  selectionDist = 0.0;
	  for (Pt pt : selectedStroke.getDefault()) {
	    pt.removeAttribute("selection drawn");
	  }
	  repaint();
	}

	public void selectionRemoved() {
	  SelectFunction selFunction = getSelectFunction(currentSelectFunction);
	  if (selectedStroke != null) {
	    selFunction.deselect(selectedStroke.getDefault());
	  }
	  selectedStroke = null;
	  repaint();
	}

	public void operateBegin(Pt epicenter, String operateFunction) {
	  currentOperateFunction = operateFunction;
	  OperateFunction opFunction = getOperateFunction(currentOperateFunction);
	  if (opFunction != null) {
	    if (operateFunction.equals(ScratchPad.OP_SMOOTH)) {
	    }
	    opFunction.beginOperation(epicenter);
	  } else {
	    bug("I don't have operation: " + operateFunction);
	  }
	}

	public void operateProgress(Pt newEpicenter) {
	  OperateFunction opFunction = getOperateFunction(currentOperateFunction);
	  if (opFunction != null && selectedStroke != null) {
	    opFunction.operate(selectedStroke.getDefault(), newEpicenter);
	  }
	  repaint();
	}

	public void operateComplete() {
	  if (currentOperateFunction != null) {
	    OperateFunction opFunction = getOperateFunction(currentOperateFunction);
	    if (opFunction != null) {
	      opFunction.endOperation();
	    }
	  }
	  currentOperateFunction = null;
	}
      });
  }

  public void setSelectFunction(String name) {
    currentSelectFunction = name;
  }

  public void addSelectFunction(String name, SelectFunction sf) {
    selectFunctions.put(name, sf);
  }

  public SelectFunction getSelectFunction(String functionName) {
    return selectFunctions.get(functionName);
  }

  public void addOperateFunction(String name, OperateFunction op) {
    operateFunctions.put(name, op);
  }

  public OperateFunction getOperateFunction(String functionName) {
    return operateFunctions.get(functionName);
  }

  private void fade() {
    // turn the timer on/off as necessary
    if (!fadeTimer.isRunning() && fading.size() > 0) {
      fadeTimer.start();
    } if (fadeTimer.isRunning() && fading.size() == 0) {
      fadeTimer.stop();
    }

    // if there's something to fade, do it and repaint.
    if (fading.size() > 0) {
      List<Stroke> done = new ArrayList<Stroke>();
      for (Stroke fadeMe : fading) {
	Sequence s = fadeMe.getDefault();
	Color myColor;
	if (s.get(0).hasAttribute("color")) {
	  Pt pt;
	  Color prevColor;
	  for (int i=s.size()-1; i > 0; i--) { // from last to next-to-first
	    pt = s.get(i);
	    myColor = (Color) pt.getAttribute("color");
	    prevColor = (Color) s.get(i-1).getAttribute("color");
	    Color newColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(),
				       prevColor.getAlpha());
	    pt.setAttribute("color", newColor);
	  }
	  myColor = (Color) s.get(0).getAttribute("color");
	  Color newColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(),
				     Math.max(0, myColor.getAlpha() - FADE_INCREMENT));
	  s.get(0).setAttribute("color", newColor);
	} else {
	  for (Pt pt : s) {
	    pt.setAttribute("color", new Color(0, 0, 0, 255));
	  }
	}
	if (((Color)s.getLast().getAttribute("color")).getAlpha() <= 0) {
	  done.add(fadeMe);
	}
      }
      for (Stroke doneFading : done) {
	fading.remove(doneFading);
      }
      repaint();
    }
  }

  public void paintComponent(Graphics g1) {
    Graphics2D g = (Graphics2D) g1;
    g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
					   RenderingHints.VALUE_ANTIALIAS_ON));
    Rectangle2D border = new Rectangle2D.Double(0, 0, getWidth()-1, getHeight()-1);
    g.setStroke(new BasicStroke(1.5F));
    g.setColor(Color.WHITE);
    g.fill(border);
    g.setColor(Color.BLACK);
    g.draw(border);
   
    complete.draw(g);

    g.setStroke(new BasicStroke(3.0F));
    for (Stroke s : fading) {
      s.draw(g);
    }
    if (inProg != null) {
      inProg.draw(g);
    }
 
  }

  private void bug(String what) {
    Debug.out("ScratchPad" , what);
  }
}
