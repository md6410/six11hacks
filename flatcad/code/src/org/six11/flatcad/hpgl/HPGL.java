package org.six11.flatcad.hpgl;

import java.io.FileWriter;
import java.io.IOException;
import org.six11.flatcad.Output;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.model.Turtle;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.SequenceFunction;
import org.six11.util.pen.Pt;
import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

/**
 * 
 **/
public class HPGL {
  
  public static HPGL instance = null;

  public static void initInstance(Output output) {
    instance = new HPGL(output);
  }
  
  public static HPGL getInstance() {
    return instance;
  }

  protected Output output;
  protected boolean firstPoint;
  protected String fileName;
  protected Sequence sequence;
  protected List<Sequence> savedSequences;
  protected List<Turtle> turtles;
  
  protected transient FileWriter writer; // only valid during printing.

  private HPGL(Output output) {
    this.output = output;
    this.savedSequences = new ArrayList<Sequence>();
  }

  public void setFileName(String fn) {
    this.fileName = fn;
  }

  public String getFileName() {
    return fileName;
  }

  public void start() {
    if (fileName != null) {
      try {
	sequence = new Sequence();
	writer = new FileWriter(fileName);
	write("IN\nPA\nSP3\n"); //initialize and go to origin
	firstPoint = true;
	turtles = new ArrayList<Turtle>();
      } catch (IOException ex) {
	output.addOutput("Unable to open '" + fileName + "'");
      }
    }
  }

  private void write(String what) {
    try {
      writer.write(what);
    } catch (IOException ex) {
      output.addOutput("Unable to write to '" + fileName + "'");
    }
  }

  public boolean isPrinting() {
    return sequence != null;
  }

  public void stop() {
    if (sequence.size() > 0) {
      orientSequence();
    }
    sequence = null;

    for (Sequence seq : savedSequences) {
      boolean erst = true;
      for (Pt pt : seq) {
	write((erst ? "PU " : "PD ") + pt.ix() + ", " + pt.iy() + "\n");
	erst = false;
      }
      
      // I offer the following as proof that there is no God.
      if (seq.size() > 0) {
	write("PD " + seq.getLast().ix() + ", " + seq.getLast().iy() + "\n");
      }
    }

    try {
      writer.flush();
      writer.close();
    } catch (IOException ignore) { }
  }

  private void orientSequence() {
    // eventually this should find a place on the (x,y) plane where
    // the current sequence can go without overlapping anything
    // else. For now just translate all the points so that they are
    // positive.
    Rectangle2D r = sequence.getBounds2D();
    int before = sequence.size();
    sequence = sequence.transform(new SequenceFunction.Translate(-1 * r.getX(), -1 * r.getY()));
    int after = sequence.size();
    Debug.out("HPGL", "before/after: " + before + " / " + after);
    savedSequences.add(sequence);
    sequence = new Sequence();
  }

  public void addTurtleAction(Turtle t) {
    if (t.getOpenGLStartVertex() == null) {
      return;
    }
    if (!turtles.contains(t)) {
      // keeping a list of turtles prevents duplicates
      turtles.add(t);
      if (t.move_amt != 0.0) {
	if (firstPoint) {
	  Vertex s = t.getOpenGLStartVertex();
	  firstPoint = false;
	  add3DVertex(s);
	}
	Vertex e = t.getOpenGLEndVertex();

	if (!t.h_down) {
	  Debug.out("HPGL", "It seems you have a pen up statement.");
	  orientSequence();
	}
	add3DVertex(e);
      }
    }
  }

  private void add3DVertex(Vertex v) {
    // eventually I will do plane rectification and place complete
    // cuts on the material intelligently so they don't overlap. Right
    // now this assumes that only the x,y coordinates are used.
    if (sequence == null) {
      output.addOutput("You must be inside a print statement");
    }
    Pt addMe = new Pt((int) (1016d * v.x()), (int) (1016d * v.y()));
    sequence.add(addMe);
  }
  
}
