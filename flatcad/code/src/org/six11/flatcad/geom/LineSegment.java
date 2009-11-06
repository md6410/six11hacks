package org.six11.flatcad.geom;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * A bounded 3D line segment.
 **/
public class LineSegment extends Line {

  /**
   * Make a line segment with no particular start/end point -- don't
   * use unless you are JUnit.
   */
  public LineSegment() { 
    super(); // JUnit demands 0arg
  }

  /**
   * Make a new bounded line segment with the provided start/end
   * points.
   */
  public LineSegment(Vertex start, Vertex end) {
    super(start, end);
  }
  
  /**
   * Is the indicated point on this line between the start and and
   * points (inclusive)? 
   */
  public boolean contains(Vertex v) {
    boolean ret = false;
    if (super.contains(v)) { 
      // it's on the line, but it is between start and end? An easy
      // way to tell if 'v' is between the start and end points is to
      // see if the following is true in all dimensions:
      // start <= v <= end  -OR-  start >= v >= end
      ret =
	((start.x() <= v.x() && v.x() <= end.x()) || (start.x() >= v.x() && v.x() >= end.x()))
	&&
	((start.y() <= v.y() && v.y() <= end.y()) || (start.y() >= v.y() && v.y() >= end.y()))
	&&
	((start.z() <= v.z() && v.z() <= end.z()) || (start.z() >= v.z() && v.z() >= end.z()));
      
      // Floating point error may be confounding our efforts, so look
      // to see if 'v' is the same as one of the end points.
      ret = ret || start.isSame(v) || end.isSame(v);
    }
    return ret;
  }

  /**
   * What is the absolute value of the distance between start and end?
   */
  public double length() {
    return start.getTranslated(end.getReverse()).mag();
  }

  /**
   * Returns the midpoint of this line -- shorthand for
   * getParameterizedVertex(0.5).
   */
  public Vertex getMidpoint() {
    return getParameterizedVertex(0.5);
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testLength() {
    LineSegment ls = new LineSegment(new Vertex(4, 2, 4), new Vertex(0,2,4));
    assertEquals(4, ls.length(), 0.0001);
  }

  @Test public void testContains() {
    LineSegment seg = new LineSegment(new Vertex(2d, 2d, 2d),
				      new Vertex(8d, 8d, 8d));
    assertTrue(seg.contains(new Vertex(2d, 2d, 2d)));
    assertTrue(seg.contains(new Vertex(3d, 3d, 3d)));
    assertTrue(seg.contains(new Vertex(7d, 7d, 7d)));
    assertTrue(seg.contains(new Vertex(8d, 8d, 8d)));


    assertFalse(seg.contains(new Vertex(-7d, -7d, -7d)));
    assertFalse(seg.contains(new Vertex(9d, 9d, 9d)));
    assertFalse(seg.contains(new Vertex(0.234, -0.848, 5.342)));

    seg = new LineSegment(new Vertex(-0.5, -0.4, 0.02),
			  new Vertex(0.5, -0.4, 0.02));
    assertTrue(seg.contains(new Vertex(0.0, -0.4, 0.02)));
  }
}
