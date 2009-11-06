package org.six11.flatcad.geom;

import org.six11.util.Debug;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * A line in 3D space. While this is implemented using 'start' and
 * 'end' points, this class represents an unbounded line in space that
 * runs (abstractly) to +/- infinity. Of course, the finite math the
 * computer provides precludes that from being true, but you get the
 * point.
 **/
public class Line {

  /**
   * One point on the line. 
   */
  protected Vertex start;

  /**
   * Another point on the line (should be different from and
   * reasonably far away from 'start' in order to make floating point
   * math more solid).
   */
  protected Vertex end;

  /**
   *  Make a line with no start/end points. Only here because JUnit
   *  demands it.
   */
  public Line() {

  }

  /**
   * Make a line with the given start and end points.
   */
  public Line(Vertex start, Vertex end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Gets the point that lies some distance along this line measuring
   * from the start point, with the distance to the end point being
   * 1.0.
   */
  public Vertex getParameterizedVertex(double d) {
    return getStart().getTranslated(getEnd().getTranslated(getStart().getReverse()).scale(d));
  }
  
  /**
   * Returns the 'start' point.
   */
  public Vertex getStart() {
    return start;
  }

  /**
   * Returns the 'end' point.
   */
  public Vertex getEnd() {
    return end;
  }

  /**
   * Returns the direction of this line, which is a unit vector
   * pointing in the direction of start --> end.
   */
  public Direction getDirection() {
    return new Direction(getEnd().getTranslated(getStart().getReverse()));
  }

  /**
   * Is the indicated point on this line?
   */
  public boolean contains(Vertex v) {
    Line other;
    if (v.isSame(getStart())) {
      other = new Line(getParameterizedVertex(0.5), v); 
    } else {
      other = new Line(getStart(), v);
    }
    double dotProduct = getDirection().dot(other.getDirection());
    return Math.abs(1.0 - Math.abs(dotProduct)) < MathUtils.FLOAT_TOLERANCE;
  }

  /**
   * Returns a debugging string in a format similar to <tt>{ {1.0, 2.0,
   * 0.5} to { -0.3, 0.0, 0.7 } }</tt>.
   */
  public String toString() {
    return "{" + start + " to " + end + "}";
  }

  /**
   * Intersects this line with another line, returning a
   * <tt>Line.Intersection</tt> object.
   */
  public Intersection getIntersection(Line other) {
    return new Intersection(this, other);
  }

  /**
   * This class provides information about the results of running an
   * intersection calculation between two lines. It tells you if two
   * 3D lines intersect at a single point, or if not, what the
   * shortest line segment is that can be used to connect the
   * two. Results are stored in the public members of an instance.
   */
  public static class Intersection {

    /**
     * The point (if any) along the constructor's lineA argument that
     * the intersection or intersecting segment begins.
     */
    public Vertex intersectionA;

    /**
     * The point (if any) along the constructor's lineB argument that
     * the intersection or intersecting segment ends.
     */
    public Vertex intersectionB;

    /**
     * The parameter which indicates the location along the
     * constructor's lineA argument that the intersection or
     * intersecting segment begins. If paramA is between 0..1, it
     * means the intersection is between lineA's start/end points.
     */
    public double paramA;

    /**
     * The parameter which indicates the location along the
     * constructor's lineB argument that the intersection or
     * intersecting segment ends. If paramB is between 0..1, it means
     * the intersection is between lineB's start/end points.
     */
    public double paramB;

    /**
     * Returns true if the two lines in 3D space intersect at a single
     * point. This is calculated by looking to see if intersectionA
     * and intersectionB refer to the same point.
     */
    public boolean intersectsAtPoint;
    
    /**
     * Attempt to intersect the two given lines. If the two lines are
     * coincident or parallel, an IntersectionException is thrown.
     */
    public Intersection(Line lineA, Line lineB) throws IntersectionException {
      Vertex p13,p43,p21;

      // d for denominator, {1,2} as start/end lineA, {3,4} lineB
      double d1343, d4321, d1321, d4343, d2121; 

      double numer, denom;
      Vertex p1 = lineA.getStart();
      Vertex p2 = lineA.getEnd();
      Vertex p3 = lineB.getStart();
      Vertex p4 = lineB.getEnd();

      p13 = new Vertex(p1.x() - p3.x(), p1.y() - p3.y(), p1.z() - p3.z());
      p43 = new Vertex(p4.x() - p3.x(), p4.y() - p3.y(), p4.z() - p3.z());

      if (p43.isZero()) {
	throw new IntersectionException("Oh no, p43 is zero.");
      }
      
      p21 = new Vertex(p2.x() - p1.x(), p2.y() - p1.y(), p2.z() - p1.z());
      if (p21.isZero()) {
	throw new IntersectionException("Oh no, p21 is zero.");
      }

      d1343 = p13.x() * p43.x() + p13.y() * p43.y() + p13.z() * p43.z();
      d4321 = p43.x() * p21.x() + p43.y() * p21.y() + p43.z() * p21.z();
      d1321 = p13.x() * p21.x() + p13.y() * p21.y() + p13.z() * p21.z();
      d4343 = p43.x() * p43.x() + p43.y() * p43.y() + p43.z() * p43.z();
      d2121 = p21.x() * p21.x() + p21.y() * p21.y() + p21.z() * p21.z();

      denom = d2121 * d4343 - d4321 * d4321;
      if (Math.abs(denom) < MathUtils.FLOAT_TOLERANCE) {
	throw new IntersectionException("Oh no, denom is zero.");
      }

      numer = d1343 * d4321 - d1321 * d4343;

      double ma = numer / denom;
      double mb = (d1343 + d4321 * ma) / d4343;

      Vertex pa = new Vertex(p1.x() + ma * p21.x(), 
			     p1.y() + ma * p21.y(), 
			     p1.z() + ma * p21.z());
      Vertex pb = new Vertex(p3.x() + mb * p43.x(), 
			     p3.y() + mb * p43.y(), 
			     p3.z() + mb * p43.z());
      
      // collect the values into meaningfully named variables
      intersectionA = pa;
      intersectionB = pb;
      paramA = ma;
      paramB = mb;
      intersectsAtPoint = intersectionA.isSame(intersectionB);
    }    

    /**
     * A convenience method for making a line segment from
     * intersectionA and intersectionB.
     */
    public LineSegment getSegment() {
      return new LineSegment(intersectionA, intersectionB);
    }

    /**
     * Returns a nice debugging string.
     */
    public String toString() {
      return "{Intersection: intersectsAtPoint: " + intersectsAtPoint +
	" paramA: " + paramA + " paramB: " + paramB + " intersectionA: " + intersectionA +
	" intersectionB: " + intersectionB + "}";
    }
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testParameterizedVertex() {
    LineSegment line = new LineSegment(Vertex.ORIGIN, new Vertex(2d, 2d, 2d));
    Vertex ones = line.getParameterizedVertex(0.5);
    assertEquals(1d, ones.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1d, ones.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1d, ones.z(), MathUtils.FLOAT_TOLERANCE);

    Vertex alsoOnes = line.getMidpoint();
    assertEquals(1d, alsoOnes.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1d, alsoOnes.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1d, alsoOnes.z(), MathUtils.FLOAT_TOLERANCE);    
  }

  @Test public void testDirection() {
    Vertex a = new Vertex(0.0, -0.15, 0.02);
    Vertex b = new Vertex(0.0, 0.15, 0.02);
    Line line = new Line(a,b);
    Direction dir = line.getDirection();
    assertEquals(0.0, dir.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1.0, dir.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(0.0, dir.z(), MathUtils.FLOAT_TOLERANCE);
  }

  @Test public void testContains() {
    Vertex a = new Vertex(10d, 0d, 0d);
    Vertex b = new Vertex(14d, 3d, 0d);
    Vertex c = new Vertex(15d, 0d, 0d);
    Vertex d = new Vertex(12d, 1.5, 0d);

    Line primary = new Line(a, b);
    
    assertTrue(primary.contains(d));
    assertFalse(primary.contains(c));
    assertTrue(primary.contains(a));
    assertTrue(primary.contains(b));
  }

  @Test public void testIntersection() {
    Line a = new Line(new Vertex(3d, 4d, 4d), new Vertex(5d, 4d, 4d));
    Line b = new Line(new Vertex(4d, 4d, 3d), new Vertex(4d, 4d, 5d));    
    Intersection ix = new Intersection(a, b);
    assertNotNull(ix.intersectionA);
    assertNotNull(ix.intersectionB);
    assertNotNull(ix.getSegment());
    assertEquals(0.5, ix.paramA, MathUtils.FLOAT_TOLERANCE);
    assertEquals(0.5, ix.paramB, MathUtils.FLOAT_TOLERANCE);
    assertTrue(ix.intersectsAtPoint);
  }

}
