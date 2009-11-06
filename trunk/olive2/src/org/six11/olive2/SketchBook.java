package org.six11.olive2;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.jdom.Document;
import org.jdom.Element;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics2D;
import org.six11.olive2.analyzers.BasicAnalyzer;
import org.six11.olive2.analyzers.LineAnalyzer;
import org.six11.olive2.analyzers.AdjacentLineAnalyzer;
import org.six11.olive2.analyzers.PolygonAnalyzer;
import org.six11.olive2.analyzers.DenseAnalyzer;
import org.six11.olive2.analyzers.NotchAnalyzer;
import org.six11.olive2.rend.BasicRenderer;
import org.six11.olive2.rend.DotRenderer;
import org.six11.olive2.rend.LineRenderer;
import org.six11.olive2.rend.LineEndDotRenderer;
import org.six11.olive2.rend.AdjacentLineRenderer;
import org.six11.olive2.rend.PolygonRenderer;
import org.six11.olive2.rend.DenseRenderer;
import org.six11.olive2.rend.NotchRenderer;
import org.six11.olive2.rect.LineRectifier;
import org.six11.olive2.rect.PolygonRectifier;

/**
 * All the user's sketches go here. This is the clearing house for
 * storing the user's input and choosing among various
 * interpretations.
 **/
public class SketchBook implements StrokeListener {

  private List<Sequence> rawData;
  private List<ChangeListener> changeListeners;
  private List<SketchAnalyzer> analyzers;
  private Map<String, SketchRenderer> renderers;
  private Map<String, SketchRectifier> rectifiers;
  private List<String> rendererOrder;
  private boolean shouldRecache = false;

  // interpretations are built by looking at rawData. this is only
  // valid for short periods of time.
  private Set<SketchInterpretation> interps;

  public SketchBook() {
    initDataStructures();
    changeListeners = new ArrayList<ChangeListener>();
    initAnalyzers();
    initRenderers();
    initRectifiers();
  }

  private final void initDataStructures() {
    rawData = new ArrayList<Sequence>();
    interps = new HashSet<SketchInterpretation>();
  }
  
  /**
   * Instantiate one or more analyzers and add them to the list of
   * current analyzers.
   */
  private final void initAnalyzers() {
    analyzers = new ArrayList<SketchAnalyzer>();
    analyzers.add(new BasicAnalyzer(this));
    analyzers.add(new LineAnalyzer(this));
    analyzers.add(new AdjacentLineAnalyzer(this));
    analyzers.add(new PolygonAnalyzer(this));
    analyzers.add(new DenseAnalyzer(this));
    analyzers.add(new NotchAnalyzer(this));
  }

  private final void initRenderers() {
    renderers = new HashMap<String, SketchRenderer>();
    rendererOrder = new ArrayList<String>();
    renderers.put("basic", new BasicRenderer());
    renderers.put("dots", new DotRenderer());
    renderers.put("lines", new LineRenderer());
    renderers.put("line enddots", new LineEndDotRenderer());
    renderers.put("adjacent_lines", new AdjacentLineRenderer());
    renderers.put("polygons", new PolygonRenderer());
    renderers.put("notches", new NotchRenderer());
    renderers.put("dense", new DenseRenderer());
    renderers.put("squares", renderers.get("polygons"));
    rendererOrder.add("notches");
    rendererOrder.add("polygons");
    rendererOrder.add("adjacent_lines");
    rendererOrder.add("lines");
    rendererOrder.add("dots");
    rendererOrder.add("basic");
  }

  private final void initRectifiers() {
    rectifiers = new HashMap<String, SketchRectifier>();
    rectifiers.put("lineRect", new LineRectifier());
    rectifiers.put("lineRectVertical", new LineRectifier());    
    rectifiers.put("polygonRect", new PolygonRectifier());    
    //    rectifiers.put("denseRect", new DenseRectifier());
  }

  /**
   * Create a sketchBook element that represents the current sketch.
   */
  public Element toXML() {
    Element root = new Element("sketchBook");
    Element stroke, point;
    for (Sequence seq : rawData) {
      stroke = new Element("stroke");
      for (Pt pt : seq) {
	point = new Element("pt");
	point.setAttribute("x", "" + pt.ix());
	point.setAttribute("y", "" + pt.iy());
	point.setAttribute("t", "" + pt.getTime());
	stroke.addContent(point);
      }
      root.addContent(stroke);
    }
    return root;
  }

  public void fromXML(Element root) {
    initDataStructures();
    List kids = root.getChildren("stroke");
    //    Debug.out("SketchBook", kids.size() + " strokes");
    Element stroke, point;
    String stX, stY, stT;
    Pt pt;
    Sequence seq;
    for (int i=0; i < kids.size(); i++) {
      seq = new Sequence();
      stroke = (Element) kids.get(i);
      List points = stroke.getChildren("pt");
      //      Debug.out("SketchBook", "stroke_" + i + ": " + points.size());
      for (int j=0; j < points.size(); j++) {
	point = (Element) points.get(j);
	stX = point.getAttributeValue("x");
	stY = point.getAttributeValue("y");
	stT = point.getAttributeValue("t");
	pt = new Pt(Double.parseDouble(stX), Double.parseDouble(stY), Long.parseLong(stT));
	seq.add(pt);
	if (seq.size() == 1) {
	  fire(new StrokeEvent(this, seq, System.currentTimeMillis(),
			       StrokeEvent.STROKE_BEGIN));
	} else {
	  fire(new StrokeEvent(this, seq, System.currentTimeMillis(),
			       StrokeEvent.STROKE_PROGRESS));
	}
	//	Debug.out("SketchBook", "\tpt_" + j + ": " + stX + ", " + stY + ", t=" + stT);
      }
      fire(new StrokeEvent(this, seq, System.currentTimeMillis(),
			   StrokeEvent.STROKE_END));
      
    }
    fireDataChange();
  }

  public List<SketchInterpretation> getInterpretations(String name) {
    List<SketchInterpretation> ret = new ArrayList<SketchInterpretation>();
    for (SketchInterpretation si : interps) {
      if (si.getName().equals(name)) {
	ret.add(si);
      }
    }
    return ret;
  }
  
  public void setRendererEnabled(String name, boolean b) {
    if (renderers.get(name) != null) {
      renderers.get(name).setEnabled(b);
      rendererOrder.remove(name);
      rendererOrder.add(name);
    }
  }

  public SketchRenderer getRenderer(String name) {
    return renderers.get(name);
  }

  public SketchRectifier getRectifier(String name) {
    return rectifiers.get(name);
  }

  void fire(StrokeEvent ev) {
    handleStrokeAction(ev);
    for (StrokeListener lis : analyzers) {
      lis.handleStrokeAction(ev);
    }

    
    if (ev.getType() == StrokeEvent.STROKE_END) {
      // this is ghetto. I need a more sophisticated model of how the
      // various interpretations related to one another. Example: an
      // 'adjacent line' interpreter requires 'line'
      // interpretations. I have to ensure that 'adjacent line' waits
      // until 'line' is done. Right now I do this by putting
      // 'adjacent line' in the list of interpreters after 'line'.
      for (SketchAnalyzer anal : analyzers) {
	if (anal.isIntegral()) {
	  if (shouldRecache) {
	    recacheInterpretations();
	  }
	  anal.handleIntegral();
	}
      }
      if (shouldRecache) {
	recacheInterpretations();
      }
    }
  }

  public void flagRecache() {
    shouldRecache = true;
  }

  public void handleStrokeAction(StrokeEvent ev) {
    if (ev.isBegin()) {
      addSequence(ev.getSequence());
    }
    fireDataChange();
  }
  
  private void addSequence(Sequence s) {
    if (!rawData.contains(s)) { 
      rawData.add(s);
    }
  }

  public void addChangeListener(ChangeListener lis) {
    if (!changeListeners.contains(lis)) {
      changeListeners.add(lis);
    }
  }

  public void removeChangeListener(ChangeListener lis) {
    changeListeners.remove(lis);
  }

  private void fireDataChange() {
    ChangeEvent ev = new ChangeEvent(this);
    for (ChangeListener lis : changeListeners) {
      lis.stateChanged(ev);
    }
  }

  public void draw(Graphics2D g) {
    SketchRenderer r;
    for (String name : rendererOrder) {
      r = renderers.get(name);
      r.renderSequences(g, rawData);
    }

    for (String name : rendererOrder) {
      r = renderers.get(name);
      r.renderInterpretations(g, interps);
    }

  }

  private void recacheInterpretations() {
    interps.clear();
    
    for (Sequence seq : rawData) {
      for (Pt pt : seq) {
	if (pt.getList("interpretation") != null) {
	  interps.addAll(traverse(pt.getList("interpretation")));
	}
      }
    }
    Set<String> uniqueInterpNames = new HashSet<String>();
    for (SketchInterpretation si : interps) {
      uniqueInterpNames.add(si.getName());
    }
    Debug.out("SketchBook", "Interps updated " + Debug.now() + ". Unique: " + uniqueInterpNames);
    shouldRecache = false;
  }

  /**
   * Unconditionally clear all ink and interpretations.
   */
  public void clear() {
    rawData.clear();//setSize(0);
    recacheInterpretations();
    Debug.out("SketchBook", "Cleared");
  }

  void debugRendering() {
    recacheInterpretations();
    if (rawData.size() > 0) {
      StringBuffer which = new StringBuffer();
      StringBuffer same = new StringBuffer();
      int stroke = 0;
      List<SketchInterpretation> previousPtInterps = 
	new ArrayList<SketchInterpretation>();
      for (Sequence seq : rawData) {
	Debug.out("SketchBook", "Stroke " + stroke++ + ": " + seq.size() + " points");
	for (Pt pt : seq) {
	  List<SketchInterpretation> interps = traverse(pt.getList("interpretation"));
	  if (Lists.areListsSame(previousPtInterps, interps)) {
	    same.append(pt.getID() + " ");
	  } else {
	    if (same.length() > 0) {
	      Debug.out("SketchBook", "    (same with points " + same.toString().trim() + ")");
	      same.setLength(0);
	    } 
	    for (SketchInterpretation si : interps) {
	      which.append(si.getDebuggingName() + " ");
	    }
	    Debug.out("SketchBook", "  pt " + pt.getID() + " is involved in interpretations: " + which);
	    which.setLength(0);
	  }
	  previousPtInterps = interps;
	}
      }
    } else {
      Debug.out("SketchBook", "No strokes yet.");
    }
  }

  public static List<SketchInterpretation> getAllInterpretations(Pt pt) {
    List<SketchInterpretation> ret = traverse(pt.getList("interpretation"));
    return ret;
  }

  /**
   * Do a pre-order walk of the following interpretations and return a
   * list that contains no duplicates.
   */
  private static List<SketchInterpretation> traverse(List<SketchInterpretation> in) {
    List<SketchInterpretation> ret = new ArrayList<SketchInterpretation>();
    for (SketchInterpretation p : in) {
      traverse(ret, p);
    }
    return ret;
  }

  private static void traverse(List<SketchInterpretation> ret, SketchInterpretation parent) {
    if (!ret.contains(parent)) {
      ret.add(parent);
      if (parent.children != null) {
	for (SketchInterpretation child : parent.children) {
	  traverse(ret, child);
	}
      }
    }
  }
}
