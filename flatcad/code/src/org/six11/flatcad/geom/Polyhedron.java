package org.six11.flatcad.geom;

import org.apache.commons.math.linear.RealMatrix;

import org.six11.util.Debug;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.six11.flatcad.gl.GLDrawable;
import org.six11.flatcad.gl.SelectionModel;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * This is a simple implementation of a Polyhedron that contains a
 * bunch of polygons for faces. 
 *
 * <p>Be aware that when you make a new polyhedron with polygons, it
 * will 'stitch' them together. This partly involves removing
 * redundant vertices. After you create a polygon, it is guaranteed
 * that some of your original vertices will no longer be in the
 * Polyhedron or any of its constituent Polygons.
 *
 * <p>You can make transformations to all the points in the polyhedron
 * using the <tt>transform</tt> method.
 **/
public class Polyhedron implements GLDrawable {
  
  /**
   * A reference to each of he Polygons that make up this Polyhedron.
   */
  protected List<Polygon> faces;

  /**
   * The cursor of a polyhedron represents one (and only one) face
   * that is in some way special. This is mostly a cop-out because I
   * needed a way to indicate which is the 'raw' face of a polyhedron
   * after a cut operation. If Java were a civilized programming
   * language it would support tuples and multiple return values, and
   * I wouldn't have this problem at all.
   */
  Polygon cursor;

  /**
   * Make a blank Polyhedron -- don't use unless you are JUnit.
   */
  public Polyhedron() {
    super();
  }

  /**
   * Make a new Polyhedron with a list of polygons. Order does not
   * matter.
   */
  public Polyhedron(List<Polygon> polies) {
    super();
    setFaces(polies);
  }

  /**
   * Make a new Polyhedron with a bunch of polygons. Order does not
   * matter.
   */
  public Polyhedron(Polygon... polies) {
    super();
    setFaces(polies);
  }

  /**
   * Sets the list of faces for this Polyhedron, tossing out any data
   * from before. This just packages your arguments together and calls
   * <tt>setFaces(List)</tt>.
   */
  public final void setFaces(Polygon... polies) {
    List<Polygon> list = new ArrayList<Polygon>();
    for (Polygon p : polies) {
      list.add(p);
    }
    setFaces(list);
  }

  /**
   * Sets the list of faces for this Polyhedron, tossing out any data
   * from before.
   */
  public final void setFaces(List<Polygon> polies) {
    faces = new ArrayList<Polygon>();
    for (Polygon p : polies) {
      faces.add(p);
      for (Polygon other : polies) {
	stitch(other, p);
      }
    }
    unifyVertices();
  }  

  /**
   * Cuts this polyhedron into two parts, returning the parts without
   * modifying this polyhedron. This is somewhat complicated.
   *
   * <p>The two vertices are used to determine the cut plane. First,
   * this method finds a polygon that is coplanar to a cut line formed
   * from a and b. This polygon (called the 'cut polygon') has a
   * surface normal. The cut plane is simply the plane whose normal is
   * orthogonal to the cut line and cut polygon and passes through a
   * point on the cut line.
   *
   * <p>Next, it uses the cutPlane and intersects it with the various
   * polygons in the polyhedron to determine which polygons are
   * eventually going to be split up. Each polygon is put into one of
   * two piles, one for the left, and one for the right. (left and
   * right are arbitrary names for the two return polyhedra).
   *
   * <p>Then all the polygons that were not split up are put into the
   * appropriate pile. The last step is to put a final polygon into
   * both piles that is where the cut plane passed through the
   * original polyhedron. It makes a new Polyhedron from the two piles
   * of polygons.
   */
  public Polyhedron[] cut(Vertex a, Vertex b) {
    Polyhedron[] ret = new Polyhedron[2];
    Line cutLine = new Line(a, b);

    //
    // 1. Identify which polygon the line is on, if any.
    //
    Polygon cutFace = searchForPolygon(cutLine);
    if (cutFace == null) {
      throw new IllegalGeometryException
	("There is no face that can claim coplanarity with this line.");
    }

    //
    // 2. Create a plane that passes through the cutLine and is
    // orthogonal to both cutLine and cutFace.
    //
    Plane cutPlane = new Plane
      (cutLine.getStart(), 
       new Direction(cutLine.getDirection().cross(cutFace.getNormal())));

    //
    // 3. Find the ordered vertices of a cutPolygon.
    //
    // 3a. Find one face that has intersection points.
    List<Vertex> ixPoints = new ArrayList<Vertex>();
    List<Vertex> points = new ArrayList<Vertex>();
    List<Polygon> leftPolies = new ArrayList<Polygon>();
    List<Polygon> rightPolies = new ArrayList<Polygon>();
    Polygon poly = null;
    HalfEdge he = null;
    Vertex v = null;
    for (Polygon p : faces) {
      points = p.getIntersectionPoints(cutPlane);
      if (points.size() == 2) {
	ixPoints.add(points.get(0));
	ixPoints.add(points.get(1));
	he = p.search(points.get(1));
	break;
      }
    }
    // 3b. Assuming one of the faces has intersection points, flip
    // through the adjacent faces that have the intersection points.
    int counter = 0;
    while (he != null) {
      if (counter++ > 1000) {
	Debug.out("Polyhedron", "It is likely you are in an infinite loop in cut(). Breaking.");
	break;
      }
      poly = he.getPair().getFace();
      points = poly.getIntersectionPoints(cutPlane);

      if (points.size() == 2) {
	v = null;
	if (!Vertex.listContainsSame(ixPoints, points.get(0))) {
	  v = points.get(0);
	} else if (!Vertex.listContainsSame(ixPoints, points.get(1))) {
	  v = points.get(1);
	} else {
	  he = null; // done!
	}

	if (v != null) {
	  ixPoints.add(v);
	  he = poly.search(v);
	}
      }
    }

    // 3c. If all went well, 'ixPoints' now has enough for a
    // 'cutPolygon' polygon. Each edge of 'cutPolygon' should be on
    // the plane of exactly one other polygon in this
    // polyhedron. Working in vertex order of 'cutPolygon', find the
    // face on this polyhedron that hosts each line, and split
    // it. Record the faces that have been completed in the
    // 'splitFaces' list. Splitting each face produces a 'left' and a
    // 'right' polygon. Record those in separate lists.

    Polygon cutPolygon = new Polygon(ixPoints);
    List<Polygon> splitFaces = new ArrayList<Polygon>();
    Polygon cutMe, cutMeOrig;
    Polygon[] leftAndRight;
    List<HalfEdge> cycle = cutPolygon.getEdges();
    int j;
    for (int i=0; i < cycle.size(); i++) {
      j = (i+1) % cycle.size();
      Line cutMeLine = new Line(cycle.get(i).getPoint(), 
				cycle.get(j).getPoint());
      cutMeOrig = searchForPolygon(cutMeLine);
      cutMe = cutMeOrig.copy();
      cutMe.insert(cutMeLine.getStart());
      cutMe.insert(cutMeLine.getEnd());
      
      leftAndRight = cutMe.split(cutMeLine.getStart(), cutMeLine.getEnd());

      leftPolies.add(leftAndRight[0]);
      rightPolies.add(leftAndRight[1]);
      splitFaces.add(cutMeOrig);
    }
    
    // We can use determinants to calculate which side of the cut
    // plane the rest of the faces of my polyhedron should be on. Just
    // pick a point from each of the remaining faces (e.g. those not
    // in the splitFaces list), and form a new direction from a point
    // on the cutPolygon to it, and find the determinant. Where the
    // determinant is positive, the polygon goes on the 'left'
    // side. For negative determinants, they're on the left side.
    Vertex vA = cutPolygon.getEdge().getPoint();
    Vertex vB = cutPolygon.getEdge().getNext().getPoint();
    Direction polyDir = new Direction(vA, vB);
    Vertex vC;
    Direction otherDir;
    for (Polygon p : faces) {
      if (!splitFaces.contains(p)) {
	vC = p.getPoint();
	otherDir = new Direction(vA, vC);
	if (polyDir.det(otherDir) > 0.0) {
	  leftPolies.add(p.copy());
	} else {
	  rightPolies.add(p.copy());
	}
      }
    }
    
    rightPolies.add(cutPolygon);
    Polygon rev = cutPolygon.getReverse();
    Polygon revCopy = rev.copy();
    leftPolies.add(cutPolygon.getReverse().copy());

    ret[0] = new Polyhedron(leftPolies);
    ret[0].setCursor(leftPolies.get(leftPolies.size() - 1)); // remember the 'raw' side
    ret[1] = new Polyhedron(rightPolies);
    ret[1].setCursor(rightPolies.get(rightPolies.size() - 1)); // remember the 'raw' side
    ret[0].checkIntegrity();
    ret[1].checkIntegrity();

    return ret;
  }


  /**
   * A sanity check to see if the number of vertices, faces, and edges
   * fits the equation <tt>V + F - E = 2</tt>.  If the relation holds,
   * the polygon is OK, at least by Leonard Euler's standards.

   */
  public boolean checkEuler() {
    Set<Vertex> eulerV = getAllPoints();
    Set<Polygon> eulerF = new HashSet<Polygon>();
    Set<HalfEdge> euler2E = new HashSet<HalfEdge>();

    List<HalfEdge> halfEdges;
    for (Polygon p : faces) {
      for (HalfEdge ed : p.getEdges()) {
	euler2E.add(ed);
      }
      eulerF.add(p);
    }

    int V = eulerV.size();
    int F = eulerF.size();
    int E = euler2E.size() / 2;
    int result = V + F - E;
    if (result != 2) {
      Debug.out("Polyhedron", "checkEuler: V:" + V + " F: " + F + " E:" + E + " V + F - E = " + result);
      Debug.out("Polyhedron", "Vertices are as follows:\n" + Debug.num(eulerV, "\n"));
    }
    return result == 2;
  }

  /**
   * This is a housekeeping method that throws out duplicate vertices
   * in a responsible, red-blooded American way. Mom and Uncle Same
   * would approve of this method, and serve you apple pie and freedom
   * fries for executing it.
   *
   * This is needed because two adjacent polygons each with N points
   * will meet at a minimum of two points (call this value M). After
   * joining these two polygons there are now 2N points in our
   * aggregated thingy. But there should be only 2N - M. I tried to
   * account for this at 'stitch' time, when the polyhedron combines
   * various polygons together, but this doesn't work because it only
   * compares two polygons at any given time, but there may be an
   * arbitrarily large number of faces that share a vertex.
   *
   * After unifying the vertices, the Euler formula should work out.
   */
  final void unifyVertices() {

    // Make a Map that stores the relation:
    // Vertex <--> List of HalfEdges whose .getPoint().isSame() as key
    Map<Vertex, List<HalfEdge>> uncleanVerts = new HashMap<Vertex, List<HalfEdge>>();
    List<HalfEdge> edges;
    boolean found;
    Vertex v;
    for (Polygon p : faces) {
      edges = p.getEdges();
      for (HalfEdge ed : edges) {
	v = ed.getPoint();
	found = false;
	for (Vertex unclean : uncleanVerts.keySet()) {
	  if (v.isSame(unclean)) {
	    found = true;
	    uncleanVerts.get(unclean).add(ed);
	    break;
	  }
	}
	if (!found) {
	  uncleanVerts.put(v, new ArrayList<HalfEdge>());
	}
      }
    }

    for (Vertex voo : uncleanVerts.keySet()) {
      for (HalfEdge ed : uncleanVerts.get(voo)) {
	ed.setPoint(voo);
      }
    }
    
  }

  /**
   * For each face, you can form a vector that is the face's nomal
   * scaled by the face's area. If you sum that value for each face in
   * the polyhedron, they should sum to zero. This throws an
   * IllegalGeometryException if this is not the case.
   */
  void checkIntegrity() {
    Vertex sum = new Vertex(0d, 0d, 0d);
    Vertex addMe;
    for (Polygon p : faces) {
      addMe = p.getNormal().scale(p.area());
      sum.translate(addMe);
    }
    if (!sum.isZero()) {
      throw new IllegalGeometryException("Polyhedron is either missing face(s) or some of them are oriented the wrong way: sum is " + sum);
    }
  }

  /**
   * Joins a and b if they share a common edge (using isSame() vertex
   * semantics).
   */
  public static void stitch(Polygon a, Polygon b) {
    if (a != b) {
      for (HalfEdge edgeA : a.getEdges()) {
	for (HalfEdge edgeB : b.getEdges()) {
	  if (edgeA.getStartPoint().isSame(edgeB.getPoint()) &&
	      edgeB.getStartPoint().isSame(edgeA.getPoint())) {
	    edgeA.setPair(edgeB);
	  }
	}
      }
    }
  }

  /**
   * If this polyhedron has a face that contains an edge between the
   * start and end vertices, return the half-edge representing that
   * connection, null otherwise.
   */
  public HalfEdge search(Vertex start, Vertex end) {
    HalfEdge ret = null;
    for (Polygon p : getFaces()) {
      ret = p.search(start, end);
      if (ret != null) {
	break;
      }
    }
    return ret;
  }

  /**
   * Returns a Polygon that has the given line on its plane.
   */
  public Polygon searchForPolygon(Line line) {
    Polygon ret = null;
    for (Polygon p : faces) {
      if (p.isOnPlane(line)) {
	ret = p;
	break;
      }
    }
    return ret;
  }

  /**
   * Returns this Polyhedron's list of faces (this is not a copy, so
   * be careful).
   */
  public List<Polygon> getFaces() {
    return faces;
  }

  /**
   * Returns the cursor for the polygon.
   *
   * @see cursor
   */
  public Polygon getCursor() {
    return cursor;
  }
  
  /**
   * Sets the cursor for this polygon.
   *
   * @see cursor
   */
  public void setCursor(Polygon c) {
    cursor = c;
  }

  /**
   * Returns a nice debugging string that includes the number of faces
   * and a debugging line for each face.
   */
  public String toString() {
    StringBuffer buf = new StringBuffer("Polyhedron (" + faces.size() + " faces)\n");
    buf.append(Debug.num(faces, "\n"));
    buf.append("-------");
    return buf.toString();
  }

  /**
   * Transforms each vertex in this polyhedron by the provided matrix
   * and returns a reference to this polyhedron (useful for chaining).
   */
  public Polyhedron transform(RealMatrix trans) {
    Set<Vertex> vertices = getAllPoints();
    for (Vertex v : vertices) {
      v.transform(trans);
    }
    return this;
  }

  /**
   * Returns a set of all the points contained in the polyhedron.
   */
  public Set<Vertex> getAllPoints() {
    Set<Vertex> vertices = new HashSet<Vertex>();
    List<Vertex> oneFaceVerts;
    for (Polygon p : faces) {
      oneFaceVerts = p.getVertices();
      vertices.addAll(oneFaceVerts);
    }
    return vertices;
  }

  /**
   * Return a face that has the largest surface area. When there is a
   * tie, it picks the first one in the faces list.
   */
  public Polygon findLargestFace() {
    Polygon ret = null;
    double area = 0;
    double tmpArea;
    for (Polygon p : faces) {
      tmpArea = p.area();
      if (tmpArea > area) {
	ret = p;
	area = tmpArea;
      }
    }
    return ret;
  }


  public void drawGL() {
    drawGL(null);
  }

  /**
   * Draw this polyhedron to the OpenGL pipeline by successively
   * drawing each face.
   */
  public void drawGL(SelectionModel sm) {
    for (Polygon drawMe : faces) {
      drawMe.drawGL(sm);
    }
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testFindLargestFace() {
    Pyramid pyramid = new Pyramid();
    Polygon fat = pyramid.findLargestFace();
    assertTrue(fat == pyramid.bot);
  }

  @Test public void testCut() {
    Box otherBox = new Box(1.0, 0.8, 0.05);
    HalfEdge edge1 = otherBox.search(otherBox.wnt, otherBox.ent);
    Vertex point1 = new LineSegment(otherBox.wnt, otherBox.ent).getMidpoint();
    HalfEdge edge2 = otherBox.search(otherBox.wst, otherBox.est);
    Vertex point2 = new LineSegment(otherBox.wst, otherBox.est).getMidpoint();
    Polyhedron[] two = otherBox.cut(point1, point2);
    assertNotNull(two);
    assertTrue(two.length == 2);
    assertNotNull(two[0]);
    assertNotNull(two[1]);
  }

  @Test public void testStitch() {
    Pyramid source = new Pyramid();
    Vertex a = source.top.copy();
    Vertex b = source.n.copy();
    Vertex c = source.w.copy();

    Vertex d = source.top.copy();
    Vertex e = source.e.copy();
    Vertex f = source.n.copy();

    Vertex g = source.top.copy();
    Vertex h = source.s.copy();
    Vertex i = source.e.copy();

    Vertex j = source.top.copy();
    Vertex k = source.w.copy();
    Vertex l = source.s.copy();

    Vertex m = source.s.copy();
    Vertex n = source.w.copy();
    Vertex o = source.n.copy();
    Vertex p = source.e.copy();


    Polygon one = new Polygon(a,b,c);
    Polygon two = new Polygon(d,e,f);
    Polygon tre = new Polygon(g,h,i);
    Polygon fur = new Polygon(j,k,l);
    Polygon fiv = new Polygon(m,n,o,p);

    Polyhedron body = new Polyhedron(one,two,tre,fur,fiv);
    assertTrue(body.checkEuler());
  }



}

// (inefficient + correct) > (efficient + incorrect)
