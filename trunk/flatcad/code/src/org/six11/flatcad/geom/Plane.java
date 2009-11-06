package org.six11.flatcad.geom;

import org.six11.util.Debug;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.List;

/**
 * A mathematical Plane. This is defined using a point and a
 * direction, which is isomorphic to the standard <tt>Ax + By + Cz + D =
 * 0</tt>.
 **/
public class Plane {
  
  /**
   * A vector that is orthogonal to this plane.
   */
  protected Direction normal;

  /**
   * A point on this plane.
   */
  protected Vertex point;

  /**
   * Makes an undefined plane -- don't use this unless you're krazy
   * with a K.
   */
  public Plane() { /* JUnit requires 0 arg constructor */ }

  /**
   * Make a plane using a point and direction.
   */
  public Plane(Vertex point, Direction normal) {
    this.point = point;
    this.normal = normal;
  }

  /**
   * Return a Direction that is orthogonal to this plane. Note that
   * there are two vectors that are orthogonal to a plane. Just
   * reverse() this to get the other direction.
   */
  public Direction getNormal() {
    return normal;
  }

  /**
   * Return a point that is on the plane.
   */
  public Vertex getPoint() {
    return point;
  }

  /**
   * Return a debugging string including the point and normal.
   */
  public String toString() {
    return "{Plane: P:" + getPoint() + " dir:" + normal + "}";
  }
  
  /**
   * Attempts to intersect this plane and another plane. Assuming the
   * two planes are not parallel, the result will be a line. If the
   * planes are parallel (or coplanar) it will throw an
   * IntersectionException.
   */
  public Line intersect(Plane planeB) {
    Plane planeA = this;

    Vertex   u = planeA.getNormal().cross(planeB.getNormal());
    double  ax = Math.abs(u.x());
    double  ay = Math.abs(u.y());
    double  az = Math.abs(u.z());

    // test if the two planes are parallel
    if ((ax+ay+az) < MathUtils.FLOAT_TOLERANCE) {
      // test if disjoint or coincide
      Vertex  v = planeB.getPoint().getTranslated(planeA.getPoint().getReverse());
      if (Math.abs(planeA.getNormal().dot(v)) < MathUtils.FLOAT_TOLERANCE) {
	throw new IntersectionException("planes are coplanar");
      } else {
	throw new IntersectionException("planes are disjoint");
      }
    }

    // planeA and planeB intersect in a line
    // first determine max abs coordinate of cross product
    int maxc; // max coordinate
    if (ax > ay) {
        if (ax > az)
             maxc = 1;
        else maxc = 3;
    }
    else {
        if (ay > az)
             maxc = 2;
        else maxc = 3;
    }

    // next, to get a point on the intersect line
    // zero the max coord, and solve for the other two
    double ipx = 0d, ipy = 0d, ipz = 0d;      // intersection point x,y, and z
    double d1, d2;           // the constants in the 2 plane equations

    // note: could be pre-stored with plane
    d1 = -1d * planeA.getNormal().dot(planeA.getPoint());
    d2 = -1d * planeB.getNormal().dot(planeB.getPoint());

    switch (maxc) {            // select max coordinate
    case 1:                    // intersect with x=0
      ipx = 0;
      ipy = (d2*planeA.getNormal().z() - d1*planeB.getNormal().z()) / u.x();
      ipz = (d1*planeB.getNormal().y() - d2*planeA.getNormal().y()) / u.x();
      break;
    case 2:                    // intersect with y=0
      ipx = (d1*planeB.getNormal().z() - d2*planeA.getNormal().z()) / u.y();
      ipy = 0;
      ipz = (d2*planeA.getNormal().x() - d1*planeB.getNormal().x()) / u.y();
      break;
    case 3:                    // intersect with z=0
      ipx = (d2*planeA.getNormal().y() - d1*planeB.getNormal().y()) / u.z();
      ipy = (d1*planeB.getNormal().x() - d2*planeA.getNormal().x()) / u.z();
      ipz = 0;
    }
    Vertex ip = new Vertex(ipx, ipy, ipz);
    Line ret = new Line(ip, ip.getTranslated(u));
    return ret;
  }

  /**
   * Returns the absolute value of the distance between the given
   * point and the plane of this polygon. page 211 in my notebook.
   */
  public double getDistanceToPlane(Vertex a) {
    // I need to translate 'a' towards the origin by subtracting one
    // of my vertices from it. Of course, if a is the same as that
    // vertex then a is necessarily on the plane. Otherwise, return
    // the dot product of the translated 'a' and my surface normal. No
    // division is necessary because my normal is kept as a unit
    // vector.
    double ret;
    if (a.isSame(getPoint())) {
      ret = 0.0;
    } else {
      Vertex difference = a.getTranslated(getPoint().getReverse());
      ret = difference.dot(getNormal());
    }
    return ret;
  }

  /**
   * Is the given vertex on the plane of this polygon? This uses
   * MathUtils.FLOAT_TOLERANCE to judge if two doubles are equal.
   */
  public boolean isOnPlane(Vertex a) {
    return Math.abs(getDistanceToPlane(a)) < MathUtils.FLOAT_TOLERANCE;
  }

  /**
   * Returns true if both the start and end points of the provided
   * line is on this plane.
   */
  public boolean isOnPlane(Line line) {
    return isOnPlane(line.getStart()) && isOnPlane(line.getEnd());
  }

  /**
   * Given an arbitrary sized list of vertices, tell us if all of the
   * points are on the same plane. If fewer than three points are
   * given, this returns false. If exactly three points are given, it
   * returns true. If more than three points are given (the most
   * common case), it will make a plane out of the first three, and
   * return true only if every subsequent point is on that plane.
   */
  public static boolean arePointsCoplanar(List<Vertex> points) {
    boolean ret = false;
    if (points.size() < 3) {
      ret = false;
    } else if (points.size() == 3) {
      ret = true;
    } else {
      ret = true;
      Polygon p = new Polygon(points.get(0), points.get(1), points.get(2));
      for (int i=3; i < points.size(); i++) {
	if (!p.isOnPlane(points.get(i))) {
	  ret = false;
	  break;
	}
      }
    }
    return ret;
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testDistanceAndOnPlane() {
    Vertex a = new Vertex(10d, 0d, 5d);
    Vertex b = new Vertex(2d, 6d, 5d);
    Vertex c = new Vertex(49d, 2d, 5d);
    Polygon zIsFive = new Polygon(a,b,c);
    Vertex v = new Vertex(-3d, 3d, 5d);
    Vertex not = v.getScaled(2.0);
    assertEquals(0d, zIsFive.getDistanceToPlane(v));
    assertFalse(0d == zIsFive.getDistanceToPlane(not));
    
    Vertex v2 = new Vertex(8d, 7d, 5d);
    Line yes = new Line(v, v2);
    Line no = new Line(v, not);
    assertTrue(zIsFive.isOnPlane(yes));
    assertFalse(zIsFive.isOnPlane(no));
  }

  @Test public void testIntersection() {
    Vertex pt = new Vertex(10d, 0d, 4d);
    Direction dir = new Direction(1d, 0d, 0d); // the y/z plane
    Plane one = new Plane(pt, dir);

    pt = new Vertex(0d, 2d, 4d);
    dir = new Direction(0d, 1d, 0d); // the x/z plane
    Plane two = new Plane(pt, dir);

    Line intersection = one.intersect(two);
    assertNotNull(intersection);
    dir = intersection.getDirection();
    pt = intersection.getStart();
  }

}
