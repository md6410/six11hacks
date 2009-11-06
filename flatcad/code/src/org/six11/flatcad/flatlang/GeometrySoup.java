package org.six11.flatcad.flatlang;

import java.io.FileWriter;
import java.io.IOException;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.Stack;
import org.apache.commons.math.linear.RealMatrix;
import org.six11.flatcad.geom.MatrixStack;
import org.six11.flatcad.geom.ColorMeister;
import org.six11.flatcad.model.Model;
import org.six11.flatcad.geom.Pyramid;
import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Plane;
import org.six11.flatcad.geom.Polyhedron;
import org.six11.flatcad.geom.PartDescription;
import org.six11.flatcad.geom.PartDescription.Part;
import org.six11.util.Debug;
import org.six11.util.gui.BoundingBox;
import org.six11.util.pen.MultiSequence;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 **/
public class GeometrySoup extends Model {

  //  MatrixStack transformationStack;

  final double MAX_OUTPUT_WIDTH = 12.0;
  final double MAX_OUTPUT_HEIGHT = 12.0;

  TurtleTree turtles;
  List<Vertex> freePoints;
  List<Polygon> polygons;
  
  boolean replacing;
  boolean replacingAll;
  TurtleTree replacementTurtles;

  String replacementStart;
  String replacementEnd;

  boolean shaping;
  TurtleTree shapeTurtles;

  String shapeName;
  Map <String, TurtleTree> shapes;
  
  String hpglFile;
  boolean partMode; // tells the hpgl printer if polies are distinct
		    // parts.
  boolean flagLaser;
  String laserFileName;
  boolean showPolies = false;

  public GeometrySoup() {
    reset();
  }

  public final void reset() {

    // The list of turtle actions (which indicate local geometry) is
    // the source for all other (global) geometry.
    this.turtles = new TurtleTree(Turtle.mark("__program_begin"));
    replacing = false;
    replacementStart = null;
    replacementEnd = null;
    
    shaping = false;
    shapeTurtles = new TurtleTree(Turtle.mark("__shape_turtles"));
    shapeName = null;
    shapes = new HashMap<String, TurtleTree>();

    hpglFile = null;
    partMode = false;
    //    showPolies = false;

    resetGlobal();
  }

  public final void resetGlobal() {
    // Global geometry is entirely derived from turtle geometry.
    //    this.transformationStack = new MatrixStack(true);
    this.freePoints = new ArrayList<Vertex>();
    this.polygons = new ArrayList<Polygon>();
  }

  public void enterShapeDef(String name) {
    this.shapeName = name;
    this.shaping = true;
    this.shapeTurtles = new TurtleTree(Turtle.mark("__shape_turtles"));
  }

  public void exitShapeDef() {
    shapes.put(shapeName, shapeTurtles);
    shaping = false;
  }

  public TurtleTree getShape(String name) {
    return shapes.get(shapeName);
  }

  public TurtleTree getSourceList() {
    return shaping? shapeTurtles : turtles;
  }

  public void enterReplacement(String start, String end, boolean replaceAll) {
    this.replacementStart = start;
    this.replacementEnd = end;
    this.replacementTurtles = new TurtleTree(Turtle.mark("__replacement_turtles"));
    this.replacing = true;
    this.replacingAll = replaceAll;
  }

  private boolean replaceAll(Turtle root) {
    boolean ret = false;
    if (root != null) {
      for (int i=0; i < root.getChildCount(); i++) {
	Turtle startT = root.search(replacementStart, i, false);
	if (startT == null) continue;
	Turtle endT = startT.search(replacementEnd, 0, true);
	int pathIndex = startT.getPathIndex(endT);
	if (pathIndex >= 0) {
	  Turtle startTChild = startT.getNextTurtle(pathIndex);
	  if (replaceSection(startTChild, endT, replacementTurtles.copy()));
	  ret = true;
	}
	if (replaceAll(endT)) {
	  ret = true;
	}
      }
    }
    return ret;
  }

  private boolean replaceAll() {
    TurtleTree sourceList = getSourceList();
    return replaceAll(getSourceList().getRoot());
  }

  private boolean replaceSection(Turtle startTChild, Turtle endT, TurtleTree replacements) {
    boolean ret = false;
    Turtle startT = startTChild.getParent();
    if (endT != null && startT != null) {
      startT.replaceChild(startTChild, replacements.getRoot());
      replacements.add(endT);
      ret = true;
    }
    return ret;
  }

  public void exitReplacement() {

    // look into the 'real' list for the replacement start and end
    // names, and replace all the turtles between (non-inclusive) with
    // those in the replacementTurtles list.

    TurtleTree sourceList = getSourceList();
    boolean atLeastOne = false;

    if (replacingAll) {
      atLeastOne = replaceAll();
    } else {
      // replace only the most recent
      Turtle endT = sourceList.getCursor().searchBackwards(replacementEnd);
      Turtle startT = null;
      Turtle startTChild = null;
      
      if (endT != null && endT.hasParent()) {
	startTChild = endT.searchBackwardsChildWithParent(replacementStart);
	if (startTChild != null) {
	  startT = startTChild.getParent();
	}
      }
      
      atLeastOne = replaceSection(startTChild, endT, replacementTurtles);

    }

    if (atLeastOne) {
      //      replaceGlobalGeometry();
    }

    this.replacementStart = null;
    this.replacementEnd = null;
    this.replacementTurtles = null;
    this.replacing = false;    
    this.replacingAll = false;
  }

  public String bug() {
    String s = "Free Points: " + freePoints.toString();
    return s;
  }

  public String bugTurtleLists() {
    StringBuffer buf = new StringBuffer();
    buf.append("display list:----------------\n" + turtles + "\n");
    buf.append("replacement list:----------------\n" + replacementTurtles + "\n");
    buf.append("shape list:----------------\n" + shapeTurtles + "\n");
    return buf.toString();
  }

  public String bugTurtleList(List<Turtle> manyTurtles) {
    if (manyTurtles == null) return "null.";
    StringBuffer buf = new StringBuffer();
    for (Turtle t : manyTurtles) {
      buf.append(t.toShortString() + "\n");
    }
    return buf.toString().trim();
  }

  public void addTurtle(Turtle t) {
    if (replacing) {
      replacementTurtles.add(t);
    } else if(shaping) {
      shapeTurtles.add(t);
    } else {
      turtles.add(t);
    }
  }

  public void incrementGlobalGeometry(Turtle t) {
    Vertex v = t.getGlobalPoint();
    if (freePoints.isEmpty()) {
      freePoints.add(v);
    }
    if (t.getPermPenState() == Turtle.PEN_UP) {
      freePoints.clear();
    } else {
      if (!freePoints.get(0).isSame(v)) {
	freePoints.add(0, v);
	detectPolygon();
      }
    }
  }

  public void replaceGlobalGeometry() {
    resetGlobal();
    Stack<Turtle> stack = new Stack<Turtle>();
    stack.push(turtles.getRoot());
    Turtle cursor;
    while(stack.size() > 0) {
      cursor = stack.pop();
      incrementGlobalGeometry(cursor);
      for (int i=0; i < cursor.getChildCount(); i++) {
	stack.push(cursor.getNextTurtle(i));
      }
    }

  }

  public void detectPolygon() {
    Vertex top = null;
    int end = -1; // will be > 0 if following block finds suitable mate
    for (Vertex v : freePoints) {
      if (top == null) {
	top = v;
      } else {
	if (top.isSame(v)) {
	  end = freePoints.indexOf(v);
	}
      }
    }

    if (end > 0) {
      List<Vertex> maybePoly = new ArrayList<Vertex>();
      for (int i=1; i <= end; i++) {
	maybePoly.add(freePoints.get(i));
      }
      boolean coplanar = Plane.arePointsCoplanar(maybePoly);
      if (coplanar) {
	Polygon p = new Polygon(maybePoly);
	freePoints.removeAll(maybePoly);
	polygons.add(p);
	stitchPolygons(p);
      }
    }
  }

  public void requestHPGL(String fileName) {
    hpglFile = fileName;
  }

  public void setPartMode(boolean pm) {
    partMode = pm;
  }

  public void doLaserMode(String fileName) {
    flagLaser = true;
    laserFileName = fileName;
  }


  /**
   * Puts HPGL code into the buffer. The buffer is assumed to be
   * blank.
   */
  public void printHPGL(StringBuffer buf) {
    List<Polygon> xyPolygons = new ArrayList<Polygon>();
    Debug.out("GeometrySoup", "HPGLify polygons in " +
	      (partMode ? "part mode" : "distinct polygon mode"));
    // in "part mode", unique polygons are allowed to overlap. This
    // lets us do things like have pieces with holes in them.

    // This contrasts with "distinct polygon mode" in which polygons
    // are assumed to never intersect. 
    Polygon ptrans;

    double xmin = Double.MAX_VALUE;
    double ymin = Double.MAX_VALUE;

    for (Polygon p : polygons) {
      ptrans = p.getXYPolygon(!partMode);
      xyPolygons.add(ptrans);
      for (Vertex v : ptrans.getVertices()) {
	xmin = Math.min(v.x(), xmin);
	ymin = Math.min(v.y(), ymin);
      }
    }

    Vertex transAmt = new Vertex(-xmin, -ymin, 0d);
    for (Polygon p : xyPolygons) {
      p.translate(transAmt);
    }
    double xLimit = 12d;
    double yCursor = 0d;
    double yLineHeight = 0d;
    double xCursor = 0d;
    double fudgeFactor = 0.1;
    
    Rectangle2D bbox;
    boolean erst;
    Vertex startPt = null;
    for (Polygon p : xyPolygons) {
      bbox = p.getBoundingBoxXY();
      if (xCursor + bbox.getWidth() > xLimit) {
	yCursor += yLineHeight + fudgeFactor;
	yLineHeight = 0d;
	xCursor = 0d;
      }
      if (!partMode) {
	p.translate(new Vertex(xCursor, yCursor, 0d));
      }
      erst = true;
      for (Vertex v : p.getVertices()) {
	writeHPGLVert(buf, v, erst);
	if (erst) startPt = v;
	erst = false;
      }
      writeHPGLVert(buf, startPt, false); // complete poly
      xCursor = xCursor + bbox.getWidth() + fudgeFactor;
      yLineHeight = Math.max(yLineHeight, bbox.getHeight());
    }
    // I don't understand why I must do this, but it seems that HPGL
    // will not draw the last line unless the end point is doubled up.
    if (startPt != null) {
      writeHPGLVert(buf, startPt, true);
    }
  }

  private void writeHPGLVert(StringBuffer buf, Vertex v, boolean up) {
    buf.append(up ? "PU " : "PD ");
    buf.append("" + ((int) (1016d * v.x())) + " ");
    buf.append("" + ((int) (1016d * v.y())) + "\n");
  }

  public void stitchPolygons(Polygon stitchMe) {
    for (Polygon p : polygons) {
      Polyhedron.stitch(stitchMe, p);
    }
  }

  public void done() {
    ColorMeister.reset();
    turtles.calculateGlobalGeometry();
    replaceGlobalGeometry();
    if (hpglFile != null) {
      try {
	FileWriter writer = new FileWriter(hpglFile);
	StringBuffer buf = new StringBuffer("IN\nPA\nSP3\n\n");
	printHPGL(buf);
	String str = buf.toString();
	writer.write(str);
	writer.flush();
	writer.close();
	Debug.out("GeometrySoup", "Wrote " + str.length() + " characters to " + hpglFile);
      } catch (Exception ex) {;}
    }
  }

  public void setShowPolies(boolean v) {
    showPolies = v;
  }

  public void draw() {
    glDisable(GL_COLOR_LOGIC_OP);
    glColor3f(1f, 1f, 1f);

    PartDescription pd = null;

    boolean laserWasFlagged = flagLaser;
    if (flagLaser) {
      try {
	pd = new PartDescription();
	flagLaser = false;
      } catch (Exception ex) {
	ex.printStackTrace();
      }
    }
    glPushMatrix();
    turtles.getRoot().draw(pd);
    glPopMatrix();

    // toggle in flatlang with showPolygons(true | false)
    if (showPolies) {
      try {
	for (Polygon p : polygons) {
	  p.drawGL();
	}
      } catch (ConcurrentModificationException ignore) {
	// don't do anything
      }
    }

    if (pd != null && laserWasFlagged) {
      doOutput(pd);

//       try {
// 	// TODO: write points to HPGL after moving all to fit on the
// 	// page.
// 	BoundingBox bb = mseq.getBoundingBox();
// 	mseq.translateAll(-bb.getX(), -bb.getY());
// 	//	Debug.out("GeometrySoup", mseq.toString());
	
// 	// initialize an hpgl file.
// 	FileWriter writer = new FileWriter(laserFileName);
// 	StringBuffer buf = new StringBuffer("IN\nPA\nSP3\n\n");
	
// 	// write pen up/down statements
// 	Pt pt;
// 	for (Sequence seq : mseq.getSequences()) {
// 	  if (seq.size() < 2) continue; // avoid needless movement
// 	  // <PU or PD> <x value> <space> <y value>\n
// 	  for (int i=0; i < seq.size(); i++) {
// 	    pt = seq.get(i);
// 	    if (i == 0) {
// 	      buf.append("PU " + pt.ix() + " " + pt.iy() + "\n");
// 	    }
// 	    buf.append("PD " + pt.ix() + " " + pt.iy() + "\n");	  
// 	    if (i == (seq.size() - 1)) {
// 	      // I must double up the last point for some reason.
// 	      buf.append("PU " + pt.ix() + " " + pt.iy() + "\n");
// 	    }
// 	  }
// 	}
// 	writer.write(buf.toString());
// 	Debug.out("GeometrySoup", "Wrote " + buf.toString().length() + " bytes to " + laserFileName);
	
// 	// close the hpgl file.
// 	writer.flush();
// 	writer.close();
//       } catch (IOException ex) {
// 	ex.printStackTrace();
//       }
    }


// The following commented-out block is for the ruby output.    
//     if (mseq != null) {
//       try {
// 	BoundingBox bb = mseq.getBoundingBox();
// 	mseq.translateAll(-bb.getX(), -bb.getY());

// 	FileWriter writer = new FileWriter(rubyFileName);
// 	StringBuffer buf = new StringBuffer();
// 	buf.append("require 'sketchup.rb'\n\n");
// 	buf.append("def drawStuff\n");
// 	buf.append("\tmodel = entities\n");
// 	buf.append("\tents=model.entities\n");
	
// 	// write pen up/down statements
// 	Pt pt1 = null;
// 	Pt pt2 = null;
// 	for (Sequence seq : mseq.getSequences()) {
// 	  if (seq.size() < 2) continue; // avoid needless movement
// 	  // <PU or PD> <x value> <space> <y value>\n
// 	  for (int i=0; i < seq.size(); i++) {
// 	    pt1 = seq.get(i);
// 	    if (pt2 != null) {
// // 	      buf.append("\tents.add_line([" + pt2.ix() + ", " + pt2.iy() + ", " + pt2.iz() + "], ");
// // 	      buf.append("[" + pt2.ix() + ", " + pt2.iy() + ", " + pt2.iz() + "])\n");
// 	    }
// 	    pt2 = pt1;
// 	  }
// 	}
// 	buf.append("end\n\n");
// 	buf.append("if( not file_loaded?(\"gabe.rb\") )\n");
// 	buf.append("\tadd_separator_to_menu(\"Plugins\")\n");
// 	buf.append("\tplugins_menu = UI.menu(\"Plugins\")\n");
// 	buf.append("\tplugins_menu.add_item(\"Gabe\") { drawStuff }\n");
// 	buf.append("end\n\n");
// 	buf.append("\tfile_loaded(\"gabe.rb\")\n");

// 	writer.write(buf.toString());
// 	Debug.out("GeometrySoup", "Wrote " + buf.toString().length() + " bytes to " + laserFileName);
	
// 	// close the hpgl file.
// 	writer.flush();
// 	writer.close();

//       } catch (IOException ex) {
// 	ex.printStackTrace();
//       }
//     }

  }

  private void doOutput(PartDescription pd) {
    pd.fix();
    pd.layout(MAX_OUTPUT_WIDTH, MAX_OUTPUT_HEIGHT);
    pd.hpgl("a.hpgl");
    System.out.println(" * Note that your output is in `a.hpgl'. Earlier output regarding your file name lies!");
  }

}
