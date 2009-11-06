package org.six11.flatcad.geom;

import org.six11.util.Debug;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * A Half-Edge datastructure for storing data about the relationship
 * between points, polygons, which ends up describing polyhedra. For
 * more see the following on the Winged-Edge datastructure:
 *
 * <p><a href="http://www.cs.mtu.edu/~shene/COURSES/cs3621/NOTES/model/winged-e.html">http://www.cs.mtu.edu/~shene/COURSES/cs3621/NOTES/model/winged-e.html</a>
 *
 * <p>Also this article at flipCode on the half edge structure:
 *
 * <p><a href="http://www.flipcode.com/articles/article_halfedge.shtml">http://www.flipcode.com/articles/article_halfedge.shtml</a>
 *
 * <p>Also, Peter Shirley's Computer Graphics book (2nd ed.) has a
 * brief discussion, and is the place that gave me the original
 * pointer.
 *
 * <p>HalfEdges may be used for any Polygon, including polygons that
 * are not part of a Polyhedron. But they are designed to work with
 * polygons that form together in polyhedra.
 **/
public class HalfEdge implements Namable {

  private static int ID_COUNTER = 0;
  
  /**
   * End point.
   */
  protected Vertex endPoint;   // remember that pair.endPoint is my start point.

  /**
   * The 'pair' of this edge, which is the other edge on the adjoined
   * polygon that traverses the same two vertices, but in the opposite
   * direction.
   */
  protected HalfEdge pairEdge; // pair.endPoint == this

  /**
   * The polygon that this edge is attached to.
   */
  protected Polygon face;      // face on my 'right'. the face on my 'left' is pair.face.

  /**
   * The next half-edge for this polygon, in counter-clockwise order
   * as you look at the top of the polygon.
   */
  protected HalfEdge nextEdge; // the next half edge in the sequence

  /**
   * A unique ID, assigned incrementally to each newly created
   * HalfEdge.
   */
  protected int id;

  protected String varName;

  /**
   * Make a new HalfEdge with no knowledge of it's face, pair,
   * endpoint, or successor.
   */
  public HalfEdge() {
    // all start out null !
    id = ID_COUNTER++;
  }

  /**
   * Make a new HalfEdge with the given endPoint and Polygon
   * association.
   */
  public HalfEdge(Vertex endPoint, Polygon face) {
    this.endPoint = endPoint;
    this.face = face;
    // pairEdge and nextEdge are null !
    id = ID_COUNTER++;
  }

  public boolean hasVarName() {
    return varName != null;
  }

  public String getVarName() {
    return varName;
  }

  public void setVarName(String n) {
    varName = n;
  }



  /**
   * Returns true if this half edge and the other half edge share the
   * same Vertex (using Vertex.equals()) and same Polygon
   * (using Polygon.equals()).
   */
  public boolean equals(Object other) {
    boolean ret = false;
    if (other instanceof HalfEdge) {
      HalfEdge o = (HalfEdge) other;
      ret = (getPoint().equals(o.getPoint()) && getFace().equals(o.getFace()));
    }
    return ret;
  }

  /**
   * Sets the end point of this half-edge to the given Vertex. NOTE:
   * you can only change to a different Vertex if
   * v.isSame(getPoint()). If the isSame() test fails, it will throw a
   * new IllegalGeometryException.
   */
  public void setPoint(Vertex v) {
    if (v.isSame(endPoint)) {
      endPoint = v;
    } else {
      throw new IllegalGeometryException
	("Trying to change the point of a half-edge to something " +
	 "with different coordinates: " + v + " != " + endPoint);
    }
  }

  /**
   * Sets the pair of this half-edge, as well as informing
   * <tt>pairEdge</tt> to point to this half-edge. In other words, to
   * establish a pairing between half-edges a and b, you only need to
   * do a.setPair(b), which takes care of b.setPair(a) for you.
   */
  public void setPair(HalfEdge pairEdge) {
    this.pairEdge = pairEdge;
    if (pairEdge.getPair() != this) {
      pairEdge.setPair(this);
    }
  }

  /**
   * Returns the pair of this half-edge. The pair traverses the same
   * two points, but on another polygon and pointing in the opposite
   * direction.
   */
  public HalfEdge getPair() {
    return pairEdge;
  }

  /**
   * Returns the successor half-edge. If your polygon is well-formed,
   * you should be able to call getNext() on each successive half-edge
   * and eventually get back to where you started.
   */
  public HalfEdge getNext() {
    return nextEdge;
  }

  /**
   * Sets the successor half-edge.
   */
  public void setNext(HalfEdge nextEdge) {
    this.nextEdge = nextEdge;
  }

  /**
   * Returns the end point of this half-edge. This is a direct
   * operation and requires no computation, unlike getStartPoint().
   */
  public Vertex getPoint() {
    return endPoint;
  }

  /**
   * Returns the start point of this half-edge. This is not a direct
   * operation, since the half-edge doesn't directly store it's
   * starting point. It traverses each successive half-edge until it
   * finds ao particular edge that points to this one. The end point
   * of that particular edge is this edge's start point. Use
   * sparingly.
   */
  public Vertex getStartPoint() {
    Vertex ret = null;
    HalfEdge he = getPreviousEdge();
    if (he != null) {
      ret = he.getPoint();
    }
    return ret;
  }

  /**
   * Sets the Polygon for which this half-edge is related.
   */
  public void setFace(Polygon f) {
    face = f;
  }

  /**
   * Returns the Polygon this half-edge is related to.
   */
  public Polygon getFace() {
    return face;
  }

  /**
   * Inserts a vertex between the start and end points of this
   * half-edge and updates all the neighboring HalfEdge objects with
   * the change. Given that this operation renders this half-edge and
   * it's pair useless, you should not use this or it's pair any
   * longer. This returns the half-edge that is adjacent to this
   * edge's polygon and points to the inserted Vertex <tt>z</tt>.
   */
  public HalfEdge insert(Vertex z) {
    HalfEdge myPreviousEdge = getPreviousEdge();
    HalfEdge myInsertedA = new HalfEdge(z, face);
    HalfEdge myInsertedB = new HalfEdge(getPoint(), face);
    myPreviousEdge.setNext(myInsertedA);
    myInsertedA.setNext(myInsertedB);
    myInsertedB.setNext(getNext());

    // in case my polygon is pointing to me, make it point to one of
    // the now-legit Half-Edges, because 'this' is no longer part of
    // the circuit.
    face.setEdge(myInsertedA);

    // if this half-edge is part of a polyhedron, then it will have a
    // pair. This is not strictly required, though, since half-edges
    // can be used for disembodied polygons.
    if (getPair() != null) {
      HalfEdge pairPreviousEdge = getPair().getPreviousEdge();
      HalfEdge pairInsertedA = new HalfEdge(z, getPair().getFace());
      HalfEdge pairInsertedB = new HalfEdge(getPair().getPoint(), getPair().getFace());
      pairPreviousEdge.setNext(pairInsertedA);
      pairInsertedA.setNext(pairInsertedB);
      pairInsertedB.setNext(getPair().getNext());
      
      getPair().getFace().setEdge(pairInsertedA);

      myInsertedA.setPair(pairInsertedB);
      myInsertedB.setPair(pairInsertedA);
    }
    return myInsertedA;
  }

  /**
   * Returns the half-edge that points to this half-edge. This suffers
   * from the same inefficiency as <tt>getStartPoint()</tt>.
   */
  public HalfEdge getPreviousEdge() {
    HalfEdge cursor = getNext();
    while (cursor != null && cursor.getNext() != this) {
      cursor = cursor.getNext();
    }
    return cursor;
  }

  /**
   * Return the unique ID for this half-edge.
   */
  public int getID() {
    return id;
  }

  /**
   * Return a debugging string in the format "(id) (point) p: ("NO
   * PAIR" | "(pair's id") n: ("NO NEXT" | "(next's id"), for example:
   *
   * <p><tt>42 { 10, 40, 2 } p: 89 n: NO NEXT</tt>
   */
  public String toString() {
      String pairString = getPair() == null ? "NO PAIR" : "" + getPair().getID();
      String nextString = getNext() == null ? "NO NEXT" : "" + getNext().getID();
      String ret = getID() + " " + getPoint() + " p: " + pairString + " n: " + nextString;
      return ret;
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testPolygon() {
    Vertex a = new Vertex(1d, 1d, 1d);
    Vertex b = new Vertex(1d, 0d, 0d);
    Vertex c = new Vertex(0d, 0d, 1d);
    Vertex weirdo = new Vertex(1d, 0d, 1d);

    Polygon poly = new Polygon(a,b,c);

    HalfEdge start = poly.getEdge();
    // it is not defined which half-edge the polygon should use as the
    // start edge, so we have to try each possible combination.
    if (start.getPoint() == a) {
      assertEquals(c, start.getStartPoint());
    } else if (start.getPoint() == b) {
      assertEquals(a, start.getStartPoint());
    } else if (start.getPoint() == c) {
      assertEquals(b, start.getStartPoint());
    } else {
      fail("Did poly's edge doesn't point to a, b, or c. Odd.");
    }
    HalfEdge cursor = start;
    int counter = 0;
    do {
      counter++;
      cursor = cursor.getNext();
    } while (cursor != start);
    assertTrue(counter == 3);
    
    HalfEdge otherAB = poly.search(a, b);
    assertNotNull(otherAB);
    assertTrue(otherAB.getPoint().equals(b));
    HalfEdge otherBC = poly.search(b, c);
    assertNotNull(otherBC);
    assertTrue(otherBC.getPoint().equals(c));
    HalfEdge otherCA = poly.search(c, a);
    assertNotNull(otherCA);
    assertTrue(otherCA.getPoint().equals(a));
    
    HalfEdge weirdoEdge = poly.search(a, weirdo);
    assertNull(weirdoEdge);

    List<HalfEdge> edges = poly.getEdges();
    assertTrue(edges.size() == 3);
  }

  @Test public void testPair() {
    Vertex a = new Vertex(2d, 1d, 4d);
    Vertex b = new Vertex(9d, 2d, 7d);
    Vertex c = new Vertex(0d, 8d, 4d);
    Vertex d = new Vertex(9d, 7d, 2d);

    Polygon poly1 = new Polygon (a,b,c);
    Polygon poly2 = new Polygon (d,c,b);
    HalfEdge edge1 = new HalfEdge(b, poly1);
    HalfEdge edge2 = new HalfEdge(c, poly2);
    
    edge1.setPair(edge2);
    assertTrue(edge1.getPair() == edge2);
    assertTrue(edge2.getPair() == edge1);
  }

  @Test public void testPolyhedron() {

    Pyramid pyramid = new Pyramid(1d);

    List<Polygon> faces = pyramid.getFaces();
    assertNotNull(faces);
    assertTrue(faces.contains(pyramid.nf));
    assertTrue(faces.contains(pyramid.sf));
    assertTrue(faces.contains(pyramid.ef));
    assertTrue(faces.contains(pyramid.wf));
    assertTrue(faces.size() == 5);
  }

  @Test public void testGetPreviousEdge() {
    Pyramid pyramid = new Pyramid();
    HalfEdge eastToSouth = null;
    for (HalfEdge he : pyramid.bot.getEdges()) {
      if (he.getPoint() == pyramid.s) {
	eastToSouth = he;
	break;
      }
    }
    assertNotNull(eastToSouth);
    HalfEdge maybeNtoE = eastToSouth.getPreviousEdge();
    assertNotNull(maybeNtoE);
    assertEquals(pyramid.n, maybeNtoE.getStartPoint());
    assertEquals(pyramid.e, maybeNtoE.getPoint());
  }
  
  @Test public void testInsert() {
    Pyramid pyramid = new Pyramid();
    HalfEdge eastToSouth = null;
    for (HalfEdge he : pyramid.bot.getEdges()) {
      if (he.getPoint() == pyramid.s) {
	eastToSouth = he;
	break;
      }
    }
    assertNotNull(eastToSouth);
    LineSegment southeastLine = new LineSegment(pyramid.s, pyramid.e);
    Vertex addMe = southeastLine.getMidpoint();
    assertNotNull(eastToSouth.getFace());
    assertNotNull(eastToSouth.getPair());
    assertNotNull(eastToSouth.getPair().getFace());
    assertEquals(pyramid.bot, eastToSouth.getFace());
    assertEquals(pyramid.ef, eastToSouth.getPair().getFace());
   
    eastToSouth.insert(addMe);
    
    HalfEdge insertedToSouth = null;
    for (HalfEdge he : pyramid.bot.getEdges()) {
      if (he.getPoint() == pyramid.s) {
	insertedToSouth = he;
	break;
      }
    }
    assertNotNull(insertedToSouth);
    assertNotNull(insertedToSouth.getFace());
    assertNotNull(insertedToSouth.getPair());
    assertNotNull(insertedToSouth.getPair().getFace());
    assertEquals(pyramid.s, insertedToSouth.getPoint());
    assertEquals(addMe, insertedToSouth.getPair().getPoint());
    assertEquals(pyramid.e, insertedToSouth.getPair().getNext().getPoint());
    assertEquals(addMe, insertedToSouth.getPair().getNext().getPair().getPoint());
    assertEquals(pyramid.bot, insertedToSouth.getFace());
    assertEquals(pyramid.ef, insertedToSouth.getPair().getFace());
  }

}
