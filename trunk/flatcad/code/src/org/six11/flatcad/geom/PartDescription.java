package org.six11.flatcad.geom;

import org.apache.commons.math.linear.RealMatrix;
import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import static java.lang.Math.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 **/
public class PartDescription {
  
  private List<Part> parts;

  public PartDescription() {
    parts = new ArrayList<Part>();
    newPart("default part");
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(parts.size() + " part(s):\n");
    for (Part p : parts) {
      buf.append(p.toString());
    }
    buf.append("---");
    return buf.toString();
  }

  public void newPart(String name) {
    Part p = new Part(name);
    parts.add(p);
    System.out.println("Begun making part: " + name);
  }

  public Part current() {
    return parts.get(parts.size() - 1);
  }

  public List<Part> getParts() {
    return parts;
  }

  /**
   * Translate everything to the origin, and remove degenerate parts.
   */
  public void fix() {
    List<Part> fixedParts = new ArrayList<Part>();
    for (Part p : parts) {
      if (p.isViable()) {
	BoundingBox bb1 = p.getBoundingBox();
	p.translateToOrigin();
	fixedParts.add(p);
	BoundingBox bb2 = p.getBoundingBox();
      }
    }
    parts = fixedParts;
  }

  public void layout(double max_x, double max_y) {
    BoundingBox bb;
    double cursor_x = 0d;
    double cursor_y = 0d;
    double line_height = 0d;
    double fluff = 0.1;
    
    for (Part p : parts) {
      
      if (p.isViable()) {
	bb = p.getBoundingBox();
	if (bb.width() + cursor_x > max_x) {
	  cursor_x = 0d;
	  cursor_y += line_height + fluff;
	  line_height = 0d;
	}
	p.translate(cursor_x, cursor_y);
	cursor_x += bb.width() + fluff;
	line_height = max(line_height, bb.height());
      }
    }
  }

  public void hpgl(String fileName) {
    try {
      FileWriter out = new FileWriter(fileName);
      out.write("IN\nPA\nSP3\n\n");

      for (Part p : parts) {
	out.write("PU\n");
	p.hpgl(out);
      }

      out.close();
    } catch (IOException ex) {
      Debug.out("PartDescription", "Writing HPGL failed due to IO Exception. Stacktrace follows.");
      ex.printStackTrace();
    }
  }

  private static void writeHPGLVert(FileWriter out, Vertex v, boolean up) throws IOException {
    out.write(up ? "PU " : "PD ");
    out.write("" + ((int) (1016d * v.x())) + " ");
    out.write("" + ((int) (1016d * v.y())) + "\n");
  }

  public static class Part {
    
    private String name;
    private int id = 0;
    private List<List<Vertex>> sequences;

    public Part(String name) {
      this.name = name;
      sequences = new ArrayList<List<Vertex>>();
      startSequence();
    }

    public void hpgl(FileWriter out) throws IOException {
      boolean first;
      out.write("CO \"part name: " + name + "\"\n");
      for (List<Vertex> verts : sequences) {
	first = true;
	for (Vertex vert : verts) {
	  if (first) {
	    out.write("PU\n");	    
	  }
	  writeHPGLVert(out, vert, first);
	  if (first) {
	    first = false;
	    writeHPGLVert(out, vert, first);
	  }
	}
	if (verts.size() > 0) {
	  // re-write the last vertex, I don't know why this is necessary
	  writeHPGLVert(out, verts.get(verts.size() - 1), first);
	}
      }
    }

    public boolean isViable() {
      int points = 0;
      for (List<Vertex> seq : sequences) {
	points += seq.size();
      }

      return points > 0;
    }

    public BoundingBox getBoundingBox() {
      BoundingBox bb = new BoundingBox();
      bb.addManySequences(sequences);
      return bb;
    }

    public void translate(double dx, double dy) {
      Vertex trans = new Vertex(dx, dy, 0d);
      for (List<Vertex> verts : sequences) {
	for (Vertex vert : verts) {
	  vert.translate(trans);
	}
      }
    }

    /**
     * This returns points that have been rotated to the XY plane. It
     * assumes that all the lines comprising a part are coplanar.
     *
     * To do this, it simply needs to find three points that do not
     * lie on the same line--they are a triangle. This gives us the
     * surface normal and the surface direction. From there we can
     * easily take the cross product to get a third orientation
     * axis. Using this uvw basis we make a rotation matrix, and
     * transform each point in the original part to bring it to the XY
     * plane.
     */
    public List<List<Vertex>> getRotatedToXYPlane() {
      List<List<Vertex>> ret = new ArrayList<List<Vertex>>();

      boolean debug = name.equals("gear collar");

      // Get three non colinear points. The rest should be co-planar.
      List<Vertex> triangle = new ArrayList<Vertex>();
      Direction d1 = null;
      Direction d2 = null;
      boolean foundTriangle = false;
      boolean goNext;
      for (List<Vertex> verts : sequences) {
	if (foundTriangle) {
	  break;
	}
	for (Vertex vert : verts) {
	  goNext = false;
	  if (foundTriangle) {
	    break;
	  }
	  // ensure that vert isn't the same as another in the list.
	  for (Vertex existing : triangle) {
	    if (existing.isSame(vert)) {
	      goNext = true;
	      break;
	    }
	  }
	  if (goNext) {
	    continue;
	  }
	
	  triangle.add(vert);
	  if (triangle.size() == 3) {
	    d1 = new Direction(triangle.get(0), triangle.get(1));
	    d2 = new Direction(triangle.get(0), triangle.get(2));
	    if (d1.dot(d2) == 1d) {
	      triangle.remove(2);
	    } else {
	      foundTriangle = true;
	    }
	  }
	}
      }

      if (triangle.size() == 3) {
	Direction w = d1.cross(d2);
	Direction u = d1;
	Direction v = w.cross(u);

	RealMatrix rot = MathUtils.buildMatrixFromVectors(u,v,w);
	
	for (List<Vertex> verts : sequences) {
	  List<Vertex> transVerts = new ArrayList<Vertex>();
	  ret.add(transVerts);
	  for (Vertex vert : verts) {
	    transVerts.add(vert.getTransformed(rot));
	  }
	}
      }

      return ret;
    }

    public BoundingBox translateToOrigin() {

      boolean debug = name.equals("gear collar");

      List<List<Vertex>> munged = getRotatedToXYPlane();
      
      BoundingBox bb = new BoundingBox();
      bb.addManySequences(munged);

      Vertex transAmt = new Vertex(-bb.min_x, -bb.min_y, 0.0);
      for (List<Vertex> verts : munged) {
      	for (Vertex vert : verts) {
      	  vert.translate(transAmt);
      	}
      }
      bb = new BoundingBox();
      bb.addManySequences(munged);
      
      sequences = munged;
      
      return bb;
    }

    private String getDebugPoints(List<List<Vertex>> points) {
      StringBuffer buf = new StringBuffer();
      for (List<Vertex> seq : points) {
	for (Vertex v : seq) {
	  buf.append(v + " ");
	}
	buf.append("\n");
      }
      return buf.toString();
    }

    public void startSequence() {
      List<Vertex> currentSequence = new ArrayList<Vertex>();
      sequences.add(currentSequence);
    }

    private List<Vertex> current() {
      return sequences.get(sequences.size() - 1);
    }

    private Vertex lastPoint() {
      return current().get(current().size() - 1);
    }

    public void addSegment(Vertex a, Vertex b) {
      if (!(current().size() > 0 && lastPoint().isSame(a))) {
	current().add(a);
      }
      current().add(b);
    }

    public String getName() {
      return name;
    }

    public void addPoint(Vertex v) {
      current().add(v);
    }

    public int numSequences() {
      return sequences.size();
    }

    public List<Vertex> getSequence(int idx) {
      return sequences.get(idx);
    }
    
    public String toString() {
      StringBuffer buf = new StringBuffer(name + " (" + sequences.size() + " sequences)\n");
      for (List<Vertex> list : sequences) {
	buf.append("  [ ");
	for (Vertex v : list) {
	  buf.append(v + " ");
	}
	buf.append(" ]\n");
      }
      return buf.toString();
    }
  }

}
