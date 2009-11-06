package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.data.Statistics;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;
import org.six11.util.pen.ConvexHull;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.awt.Color;

/**
 *
 **/
public class PolygonInterpretation extends SketchInterpretation {

  public static double SIDE_LENGTH_SLOP = 13d;

  private List<Pt> points;
  private List<LineInterpretation> lines;
  private PolygonData polygonData;
  private double sloppiness;
  private Color color;
  private ConvexHull hull;

  public PolygonInterpretation(String subtype, List<LineInterpretation> lines) {
    super(subtype);   
    color = null;
    LineInterpretation prev = null;
    IntersectionData id;
    this.lines = lines;
    points = new ArrayList<Pt>();
    for (LineInterpretation child : lines) {
      if (prev != null) {
	id = new IntersectionData(prev.getLine(), child.getLine());
	points.add(id.getIntersection());
      }
      prev = child;
    }
    id = new IntersectionData(prev.getLine(), lines.get(0).getLine());
    points.add(id.getIntersection());
    polygonData = new PolygonData(lines);
    hull = new ConvexHull(points);
  }

  public void setColor(Color c) {
    color = c;
  }

  public Color getColor() {
    return color;
  }

  public ConvexHull getHull() {
    return hull;
  }

  public Sequence getVertices() {
    return polygonData.getVertices();
  }

  public double getLengthMean() {
    return polygonData.getLength();
  }

  public double getAngleMean() {
    return polygonData.getAngle();
  }

  public boolean isEquiAngular() {
    return polygonData.getAngleFactor() > 10d;
  }

  public boolean isEquiLateral() {
    return polygonData.getLengthFactor() > 10d;
  }

  public int getNumSides() {
    return polygonData.getNumSides();
  }

  public Pt getCentroid() {
    return hull.getConvexCentroid();
  }

  public PolygonInterpretation(List<LineInterpretation> lines) {
    this("polygon", lines);
  }

  public void informSketchBook() {
    for (LineInterpretation child : lines) {
      relate(child, this);
    }
  }
  
  public List<Pt> getPoints() {
    return points;
  }

  public List<LineInterpretation> getLines() {
    return lines;
  }

  /**
   * This has data about a polygon: the number of sides, how 'regular'
   * it is in terms of side lengths and internal angles.
   */
  private static class PolygonData {
    int numSides;
    double lengthMean;
    double lengthStdDev;
    double angleMean;
    double angleStdDev;
    Sequence vertices;
    
    public PolygonData(List<LineInterpretation> interps) {
      numSides = interps.size();
      vertices = new Sequence();
      // get data about the line lengths.
      Statistics lineData = new Statistics();
      for (LineInterpretation interp : interps) {
	lineData.addData(interp.getLine().getLength());
      }
      lengthMean = lineData.getMean();
      lengthStdDev = lineData.getStdDev();

      // get data about the internal angles.
      Statistics angleData = new Statistics();
      Set<AdjacentLineInterpretation> alis = new HashSet<AdjacentLineInterpretation>();
      List<SketchInterpretation> manyInterps;
      for (LineInterpretation interp : interps) {
	manyInterps = interp.getChildren("adjacent_line");
	for (SketchInterpretation si : manyInterps) {
	  alis.add((AdjacentLineInterpretation)si); // store each adjacent line uniquely
	}
      }
      for (AdjacentLineInterpretation ali : alis) {
	if (interps.contains(ali.getLineA()) && interps.contains(ali.getLineB())) {
	  double angle = ali.getAngleBetweenLines();
	  angle = Math.toDegrees(Math.abs(angle));
	  angleData.addData(angle);
	  vertices.add(ali.getIntersection());
	} else {
	  Debug.out("PolygonInterpretation", "How did THAT get in there?");
	}
      }
      angleMean = angleData.getMean();
      angleStdDev = angleData.getStdDev();
    }

    public Sequence getVertices() {
      return vertices;
    }
    
    public double getLength() {
      return lengthMean;
    }

    public double getLengthFactor() {
      return lengthMean / lengthStdDev;
    }

    public double getAngle() {
      return angleMean;
    }

    public double getAngleFactor() {
      return angleMean / angleStdDev;
    }

    public int getNumSides() {
      return numSides;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[");
      buf.append("numSides: " + numSides + " ");
      buf.append("lengthMean: " + Debug.num(lengthMean) + " ");
      buf.append("lengthStdDev: " + Debug.num(lengthStdDev) + " ");
      buf.append("length factor: " + Debug.num(lengthMean / lengthStdDev) + " ");
      buf.append("angleMean: " + Debug.num(angleMean) + " ");
      buf.append("angleStdDev: " + Debug.num(angleStdDev) + " ");
      buf.append("angle factor: " + Debug.num(angleMean / angleStdDev));
      buf.append("]");
      return buf.toString();
    }
  }

}
