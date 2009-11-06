package org.six11.flatcad.geom;

import org.six11.util.Debug;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.math.linear.RealMatrix;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.glu.GLU.*;

import org.six11.flatcad.gl.GLDrawable;
import org.six11.flatcad.gl.SelectionModel;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * A Polygon consisting of an ordered list of Vertex objects. It is
 * held together using HalfEdge data structures.
 *
 * <p>Instance methods give you information about this polygon, such
 * as the list of vertices, half-edges, line segments, the surface
 * normal, and so on. It includes a bunch of methods that aren't
 * strictly necessary but make life easier, such as questions 'is this
 * point on the plane of this polygon?'  (<tt>isOnPlane(Vertex)</tt>),
 * or 'what is the longest line segment connecting two nonadjacent
 * edges?'  (<tt>findLongestMidline()</tt>). (Note that
 * <tt>isOnPlane</tt> is actually in the superclass, Plane, so look
 * there for more.)
 *
 * <p>The most important thing to keep in mind when using this polygon
 * is that the vertices must be supplied in <i>counter-clockwise
 * order</i>, assuming you are looking at the top of the polygon. The
 * surface normal will then be pointing up towards you. When this is
 * drawn in OpenGL, it is likely that the polygon will be invisible if
 * you are looking at the back of the polygon.
 **/
public class Polygon extends Plane implements GLDrawable, Namable, Visitable {

  private static int ID_COUNTER = 0;
  private static Random rand = new Random(System.currentTimeMillis());

  /**
   * A reference to one of the half-edges in this polygon. All others
   * can be accessed from here.
   */
  protected HalfEdge edge;

  /**
   * Color values in the range [0..1] for use in drawing.
   */
  private double red, green, blue;
//   Color myColor;

  /**
   * The name, used for debugging purposes.
   */
  protected String name = "Polygon_" + ID_COUNTER++;

  protected String varName;

  /**
   * The edge that was last visited when sitting on this vertex. Note
   * that it may be null.
   */
  protected HalfEdge lastVisited;

  /**
   * Make a blank polygon. You probably should not use this, since it
   * is only here because JUnit wants it.
   */
  public Polygon() {
    super();
    // JUnit demands
  }

  /**
   * Make a polygon with the given list of vertices. The vertices
   * should be given in counter-clockwise direction as you are looking
   * at the 'front' of the polygon (with the surface normal poking you
   * in the eye).
   */
  public Polygon(Vertex... points) {
    List<Vertex> args = new ArrayList<Vertex>();
    for (Vertex v : points) {
      args.add(v);
    }
    usePoints(args);

  }

  /**
   * Like the vararg constructor.
   */
  public Polygon(List<Vertex> points) {
    usePoints(points);
  }

  /**
   * Creates a copy of this polygon.
   */
  public Polygon copy() {
    List<Vertex> altPoints = new ArrayList<Vertex>();
    List<Vertex> points = getVertices();
    for (Vertex v : points) {
      altPoints.add(v.copy());
    }
    return new Polygon(altPoints);
  }

  /**
   * This provides a 2D bounding box that only looks at the x and y
   * values.
   */
  public Rectangle2D getBoundingBoxXY() {
    
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;

    for (Vertex v : getVertices()) {
      minX = Math.min(minX, v.x());
      minY = Math.min(minY, v.y());
      maxX = Math.max(maxX, v.x());
      maxY = Math.max(maxY, v.y());
    }

    return new Rectangle2D.Double(minX, minY, maxX-minX, maxY-minY);
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
   * Indicates that the visited thing (such as a polygon or vertex) is
   * just now being visted along the given half-edge.
   */
  public void addVisit(HalfEdge e) {
    lastVisited = e;
  }

  /**
   * Returns the last HalfEdge that visited this thing.
   */
  public HalfEdge getLastVisitor() {
    return lastVisited;
  }


  /**
   * Establishes all the points used in this polygon (throwing out the
   * old ones, if any). This is essentially the init method.
   */
  public final void usePoints(List<Vertex> points) {
    edge = null;
    HalfEdge previous = null;
    for (Vertex v : points) {
      HalfEdge he = new HalfEdge(v, this);
      if (edge == null) {
	edge = he;
      }
      if (previous != null) {
	previous.setNext(he);
	v.setEdge(previous);
      }
      previous = he;
    }
    if (previous != null && edge != null) {
      previous.setNext(edge); // complete ring
    }

    // Set up random color
    Color c = ColorMeister.nextColor();
//     red = rand.nextDouble();
//     green = rand.nextDouble();
//     blue = rand.nextDouble();
    setColor(c);
    //    setColor(new Color(224, 68, 3));

//     Debug.out("Polygon", "Initialized a polygon with points: " + Debug.num(points, " ") + ".");
//     Debug.out("Polygon", "My edge is " + edge + ".");
//     Debug.out("Polygon", "My surface normal is: " + getNormal() + ".");
//     Debug.out("Polygon", "My internal list of points is: " + getVertices() + ".");
  }

  private void setColor(Color c) {
    //    Debug.out("Polygon", "changing color to " + c + " in polygon " + hashCode());
    red = ((double)c.getRed())/255.0;
    green = ((double)c.getGreen())/255.0;
    blue = ((double)c.getBlue())/255.0;
    //    Debug.out("Polygon", "colormeister gave me " + c + " r: " + red + " g: " + green + " b: " + blue);
  }

  /**
   * Returns one of the half-edges used in this polygon. You can get
   * the others by following the edge around.
   */
  public HalfEdge getEdge() {
    return edge;
  }

  /**
   * Sets a half-edge contained in this polygon. Be careful that you
   * set this to a half-edge for a vertex that is actually in the
   * polygon, or you'll have problems.
   */
  protected void setEdge(HalfEdge edge) {
    this.edge = edge;
  }

  /**
   * Returns the ordered list of line segments. It is undefined
   * regarding which particular line segment will be first, but it is
   * guaranteed that each successive line segment follows the previous
   * in counter-clockwise order.
   */
  public List<LineSegment> getSegments() {
    List<LineSegment> ret = new ArrayList<LineSegment>();
    HalfEdge cursor = edge;
    do {
      ret.add(new LineSegment(cursor.getPoint(), cursor.getNext().getPoint()));
      cursor = cursor.getNext();
    } while (cursor != edge);
    return ret;
  }

  /**
   * Gets the in-order list of half-edges.
   */
  public List<HalfEdge> getEdges() {
    List<HalfEdge> ret = new ArrayList<HalfEdge>();
    HalfEdge cursor = edge;
    do {
      ret.add(cursor);
      cursor = cursor.getNext();
    } while (cursor != edge);
    return ret;
  }

  /**
   * Returns an in-order list of vertices contained in this polygon
   * (counter-clockwise looking a the 'top' of the polygon).
   */
  public List<Vertex> getVertices() {
    List<Vertex> ret = new ArrayList<Vertex>();
    HalfEdge cursor = edge;
    do {
//       Debug.out("Polygon", "edge: " + edge + ", cursor: " + cursor);
      ret.add(cursor.getPoint());
      cursor = cursor.getNext();
    } while (cursor != edge);
    return ret;
  }

  /**
   * Finds the half-edge that represents a line segment that contains
   * the indicated Vertex, or null if none are found.
   */
  public HalfEdge search(Vertex containsMe) {
    HalfEdge ret = null;
    HalfEdge start = getEdge();
    HalfEdge cursor = start;
    Vertex prev = null;
    Vertex succ = null;
    LineSegment seg;
    do {
      prev = cursor.getPoint();
      succ = cursor.getNext().getPoint();
      seg = new LineSegment(prev, succ);
      if (seg.contains(containsMe)) {
	ret = cursor.getNext(); // signals the end of the loop
      }
      cursor = cursor.getNext();
    } while (cursor != start && ret == null);
    return ret;
  }
  
  /**
   * If this polygon contains an edge between the start and end
   * vertices, return the half-edge representing that connection, null
   * otherwise. TODO: this implementation is pretty bone-headed and
   * should be redone sometime, but it works so I'll leave it as-is
   * for now.
   */
  public HalfEdge search(Vertex start, Vertex end) {
    HalfEdge ret = null;

    HalfEdge st = getEdge();
    HalfEdge cursor = st;
    int counter = 0;
    Vertex prev = null;
    Vertex succ = null;
    Vertex first = null;

    do {
      counter++;
      prev = succ;
      succ = cursor.getPoint();
      if (first == null) {
	first = succ;
      }
      if (start.equals(prev) && end.equals(succ)) {
	ret = cursor;
	break;
      }
      cursor = cursor.getNext();
    } while (cursor != st);
    if (start.equals(succ) && end.equals(first)) {
      ret = edge;
    }

    return ret;
  }

  /**
   * Returns two polygons that result from splitting this polygon
   * along a line formed between the two vertices. For best results,
   * make sure these vertices are actually on this polygon, and ensure
   * that they are not adjacent. 
   *
   * <p>Both a and b MUST be .equals() to one of the vertices in this
   * polygon's vertex list. You might consider looping through
   * getVertices() in order to get an exact reference.
   */
  public Polygon[] split(Vertex a, Vertex b) {
    Polygon[] ret = new Polygon[2];
    HalfEdge heA = null;
    HalfEdge heB = null;
    // look for half-edges that end in a or b
    for (HalfEdge he : getEdges()) {
      if (he.getPoint().equals(a)) {
	heA = he;
      } 
      if (he.getPoint().equals(b)) {      
	heB = he;
      }
    }
    if (heA == null || heB == null) {
      Debug.out("Polygon", "Can't find half-edge for a or b: " + heA + " or " + heB);
    } else {
      
      // Formulate the ordered vertices of the two new polygons.
      List faceAPolies = new ArrayList<Vertex>();
      HalfEdge cursor = heB;
      do {
	faceAPolies.add(cursor.getPoint().copy());
	cursor = cursor.getNext();
      } while (cursor != heA);
      faceAPolies.add(cursor.getPoint().copy());
      List faceBPolies = new ArrayList<Vertex>();
      do {
	faceBPolies.add(cursor.getPoint().copy());
	cursor = cursor.getNext();
      } while (cursor != heB);
      faceBPolies.add(cursor.getPoint().copy());

      ret[0] = new Polygon(faceAPolies);
      ret[1] = new Polygon(faceBPolies);
    }
    return ret;
  }

  /**
   * Sets the name of this polygon, used for debugging, but could also
   * be made to do other tricks.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the debugging name for this Polygon.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the Vertex at the end of this Polygon's half-edge. Note
   * that this overrides Plane.getPoint() because Plane stores a
   * vertex that is provided to it at construction time, but our our
   * edge may change due to insertions and such.
   */
  public Vertex getPoint() {
    return edge.getPoint();
  }

  /**
   * Insert a Vertex into this polygon. In order for this to work, the
   * given 'addMe' vertex MUST be along one of the edges. Otherwise it
   * will throw an IllegalGeometryException.
   */
  public void insert(Vertex addMe) {
    HalfEdge he = search(addMe);
    if (he == null) {
      throw new IllegalGeometryException("There is no edge that can claim this point.");
    }
    he.insert(addMe);
  }

  /**
   * Calculate the area of this polygon, assuming that each triangle
   * involving the first point does not cross any other triangle. To
   * be totally safe, your polygon should be convex, but that is not
   * strictly required for this to work.
   */
  public double area() {
    double sum = 0d;
    List<Vertex> points = getVertices();
    int n = points.size() - 1;
    for (int i=1; i < n; i++) {
      sum += MathUtils.calculateArea(points.get(0), points.get(i), points.get(i+1));
    }
    return sum;
  }

  /**
   * Create a polygon that has been rotated to the XY plane and
   * translated such that its bounding box's bottom left sits on the
   * origin.
   */
  public Polygon getXYPolygon(boolean translate) {
    
    // Using the normal and the surface direction, form a uvw
    // orthonormal basis, and display what that basis is.
    Direction w = getNormal().copy();
    Direction u = getSurfaceDirection().copy();
    Direction v = w.cross(u);
    
    RealMatrix rot = MathUtils.buildMatrixFromVectors(u,v,w);
    RealMatrix trn = 
      translate 
      ? MathUtils.getTranslationMatrix(edge.getPoint().getReverse())
      : MathUtils.getTranslationMatrix(new Vertex(0d, 0d, 0d));
    RealMatrix rm  = rot.multiply(trn);
    
    List<Vertex> hacked = new ArrayList<Vertex>();
//     Debug.out("Polygon", "Applying transformation " +
// 	      (translate ? "(with translation)" : "(without translation)"));
    Vertex transVer;
    for (Vertex ver : getVertices()) {
      transVer = ver.getTransformed(rm);
      hacked.add(transVer);
      //      Debug.out("Polygon", "transformed " + ver + " --> " + transVer);
    }
    Polygon hackedPoly = new Polygon(hacked);

//     Rectangle2D bbox = hackedPoly.getBoundingBoxXY();
//     Vertex addMe = new Vertex(bbox.getX(), bbox.getY(), 0d);
//     addMe.reverse();
//     hackedPoly.translate(addMe);
    return hackedPoly;
  }

  /**
   * Returns an arbitrary vector that is parallel to the surface of
   * this polygon.
   */
  public Direction getSurfaceDirection() {
    // Specifically, return the normalized vector from the first point
    // to the second. That's as good as any.
    return new Direction(getEdge().getPoint(), getEdge().getNext().getPoint());
  }

  public Direction getNormal() {
    // Form two vectors, each of which is from the first point to
    // another point on the polygon. The surface normal is simply the
    // cross product. It matters which order you cross them. Assume
    // points:

    // O = the origin, the first point
    // F = the point right after O
    // L = the point right after F

    // Then OF x OL will be positive if the surface normal is going
    // into the screen. OpenGL assumes that positive z goes into the
    // screen. OL x OF will give you the correct magnitude but the
    // opposite direction.
    
    if (edge == null) {
      Debug.out("Polygon", "'edge' is null in Polygon.getNormal()");
    }
    Vertex reverseRoot = edge.getPoint().getReverse();
    Vertex F = edge.getNext().getPoint().getTranslated(reverseRoot);
    Vertex L = edge.getNext().getNext().getPoint().getTranslated(reverseRoot);
    
    //    return Direction.cross(F, L);
    return new Direction(F.cross(L));
  }

  /**
   * Intersect the plane with this polygon and return the list of
   * points where they intersect. In the case of a non-convex polygon
   * it is possible for more than two points to be returned. This uses
   * (and is different from) Plane.intersect() because while the
   * plane/polygon *planes* may intersect, they do not necessarily
   * intersect within the bounds of the polygon.
   */
  public List<Vertex> getIntersectionPoints(Plane plane) {
    List<Vertex> ret = new ArrayList<Vertex>();
    try {
      Line intersectionLine = intersect(plane);

      // does the given line 'intersectionLine' pass through any of the
      // line segments that define our polygon's boundary?
      LineSegment ls;
      Line.Intersection intersection;
      Vertex ixPoint;

      List<Vertex> vz = getVertices();
      for (int i=0; i < vz.size(); i++) {
	int j = (i + 1) % vz.size();
	ls = new LineSegment(vz.get(i), vz.get(j));
	try {
	  intersection = ls.getIntersection(intersectionLine);
	  if (intersection.intersectsAtPoint && 
	      intersection.paramA < (1.0 + MathUtils.FLOAT_TOLERANCE) &&
	      intersection.paramA > (0d - MathUtils.FLOAT_TOLERANCE)) {
	    if (!Vertex.listContainsSame
		(ret, intersection.intersectionA)) {
	      ret.add(intersection.intersectionA);
	    }
	  }
	} catch (IntersectionException ignore) {
	  // don't worry. parallel happens.
	}
      }
    } catch(Exception ignore) {

    }
    return ret;
  }

  /**
   * Create a new polygon that has the same points as this one but in
   * the opposite order.
   */
  public Polygon getReverse() {
    List<Vertex> points = getVertices();
    Collections.reverse(points);
    return new Polygon(points);
  }

  /**
   * Returns a reasonable debugging string for this Polygon that
   * includes the name and a space-delimited list of vertices.
   */
  public String toString() {
    StringBuffer buf = new StringBuffer("{" + name + " ");
    for (Vertex v : getVertices()) {
      buf.append(v + " ");
    }
    buf.append("}");
    return buf.toString().trim();
  }

  /**
   * Translate all my vertices by the given amount. Note: you might
   * want to use the polyhedron transform method if this polygon is
   * part of a polyhedron, because it will (1) affect other vertices,
   * and (2) since polygons share vertices you might inadvertently
   * translate the same vertex more than one time.
   */
  public void translate(Vertex amount) {
    for (Vertex v : getVertices()) {
      v.translate(amount);
    }
  }

  /**
   * Find the longest line that joins the midpoints two nonadjacent
   * edges. This works for Polygons that have 4 or more edges. (It
   * will return null for a triangle.)
   */
  public LineSegment findLongestMidline() {
    LineSegment ret = null;
    double longest = 0;
    List<LineSegment> segs = getSegments();
    LineSegment candidate;
    int k;
    for (int i=0; i < segs.size(); i++) {
      for (int j=i+2; j < i + (segs.size() - 1); j++) {
	k = j % segs.size();
	candidate = new LineSegment(segs.get(i).getMidpoint(),
				    segs.get(k).getMidpoint());
	if (candidate.length() > longest) {
	  longest = candidate.length();
	  ret = candidate;
	}
      }
    }
    return ret;
  }

  /**
   * Find the shortest line that joins the midpoints two nonadjacent
   * edges. See findLongestMidline().
   */
  public LineSegment findShortestMidline() {
    LineSegment ret = null;
    double shortest = Double.MAX_VALUE;
    List<LineSegment> segs = getSegments();
    LineSegment candidate;
    int k;
    for (int i=0; i < segs.size(); i++) {
      for (int j=i+2; j < i + (segs.size() - 1); j++) {
	k = j % segs.size();
	candidate = new LineSegment(segs.get(i).getMidpoint(),
				    segs.get(k).getMidpoint());
	if (candidate.length() < shortest) {
	  shortest = candidate.length();
	  ret = candidate;
	}
      }
    }
    return ret;
  }


  public void drawGL() {
    drawGL(null);
  }

  private double r(int n) {
    return ((double) n) / 255.0;
  }

  /**
   * Writes commands to the OpenGL pipeline that draws this polygon. 
   */
  public void drawGL(SelectionModel sm) {

    boolean sel = false;
    if (sm != null && sm.hasSelection()) {
      sel = (this == sm.getSelection().getFace());
    }

    if (sel) {
      glColor3d(1d, 1d, 1d);
    } else {
      //           Debug.out("Polygon", "using color r: " + red + " g: " + green + " b: " + blue);
           glColor3d(red, green, blue);
//       Debug.out("Polygon", "what " + myColor);
//       glColor3d(r(myColor.getRed()),
// 		r(myColor.getGreen()),
// 		r(myColor.getBlue()));
    }
    glBegin(GL_POLYGON); {
      for (Vertex v : getVertices()) {
	v.glVertex();
      }
    } glEnd();

    if (sel) {
      // draw the selected point as a big blob
      glPointSize(10f);
      glBegin(GL_POINTS); {
	glColor3d(0d, 1d, 0d);
	sm.getSelection().getPoint().glVertex();
      } glEnd();

      // now draw the 'selected edge', which is actually the selected
      // half-edge's next edge. It is confusing, but if you think
      // about how half-edges work and keep in mind that the
      // half-edge's end point is the one it keeps track of, this
      // makes sense.
      glLineWidth(3.5f);
      glColor3d(1d, 0d, 0d);
      HalfEdge he = sm.getSelection();
      glBegin(GL_LINES); {
	he.getPoint().glVertex();
	he.getNext().getPoint().glVertex();
      } glEnd();
    }

  }

  /* ------------------------------   Debug functions. ------- */
  void debugHalfEdges() {
    Debug.out("Polygon", "Half edges for " + name + ":");
    HalfEdge cursor = edge;
    int counter = 0;
    String pairString;
    String nextString;
    String faceString;
    do {
      counter++;
      pairString = cursor.getPair() == null ? "NO PAIR" : "" + cursor.getPair().getID();
      nextString = cursor.getNext() == null ? "NO NEXT" : "" + cursor.getNext().getID();
      faceString = cursor.getFace() == this ? "OK": "Wrong Face!: " + cursor.getFace();
      Debug.out("Polygon", "\t" + cursor.getID() + " " + cursor.getPoint() + " p: " + pairString +
		" n: " + nextString + " f:" + faceString);
      cursor = cursor.getNext();
      if (counter > 12) {
	Debug.out("Polygon", "\tIt is likely that you're in an infinite loop. Breaking.");
	break;
      }
    } while (cursor != edge);
  }

  /* ------------------------------     Test functions. ------- */
  @Test public void testSplit() {
    Vertex a, b, c, d, e, f;
    a = new Vertex(1d, 1d, 0d);
    a.setName("a");
    b = new Vertex(1d, 3d, 0d);
    b.setName("b");
    c = new Vertex(3d, 5d, 0d);
    c.setName("c");
    d = new Vertex(6d, 4d, 0d);
    d.setName("d");
    e = new Vertex(5d, 2d, 0d);
    e.setName("e");
    f = new Vertex(3d, 1d, 0d);
    f.setName("f");
    
    Polygon big = new Polygon(a,b,c,d,e,f);

    Polygon[] small = big.split(b,e);
    assertNotNull(small);
    assertTrue(small.length == 2);
    assertTrue(small[0].getVertices().size() == 4);
    assertTrue(small[1].getVertices().size() == 4);
    // it is important that the vertices in the two return sets are
    // NOT EQUAL to each other or the original.
    
    for (Vertex bigVert : big.getVertices()) {
      for (Vertex zeroVert : small[0].getVertices()) {
	assertFalse(bigVert.equals(zeroVert));
      }
      for (Vertex oneVert : small[1].getVertices()) {
	assertFalse(bigVert.equals(oneVert));
      }
    }

    for (Vertex zeroVert :  small[0].getVertices()) {
      for (Vertex oneVert : small[1].getVertices()) {
	assertFalse(zeroVert.equals(oneVert));
      }
    }
  }

  @Test public void testArea() {
    Vertex a,b,c,d;
    a = new Vertex(0d, 0d, 0d);
    b = new Vertex(0d, 1d, 0d);
    c = new Vertex(1d, 1d, 0d);
    d = new Vertex(1d, 0d, 0d);
    Polygon poly = new Polygon(a,b,c,d);
    assertEquals(1d, poly.area());
  }

  @Test public void testNormal() {
    Vertex p1 = new Vertex(2d, 1d, 0d);
    Vertex p2 = new Vertex(5d, 1d, 0d);
    Vertex p3 = new Vertex(3d, 4d, 0d);

    Polygon poly = new Polygon(p1, p2, p3);
    Direction norm = poly.getNormal();
    assertEquals(0d, norm.x(), 0d);
    assertEquals(0d, norm.y(), 0d);
    assertEquals(1d, norm.z(), 0d);

    // if the polygon is built in the opposite order, the normal
    // should be negative one.
    poly = new Polygon(p1, p3, p2);
    norm = poly.getNormal();
    assertEquals(0d, norm.x(), 0d);
    assertEquals(0d, norm.y(), 0d);
    assertEquals(-1d, norm.z(), 0d);    
  }

  @Test public void testIntersectionPoints() {
    Polygon triangle = new Polygon(new Vertex(0d, 1d, 0d),
				   new Vertex(1d, 0d, 0d),
				   new Vertex(0d, 0d, 0d));

    Vertex a = new Vertex(0d, 0.25, 0d);
    Vertex b = new Vertex(2d, 0.25, 0d);
    Vertex c = new Vertex(1d, 1d, 0d);
    Vertex d = new Vertex(0d, -1d, 0d);
    Vertex e = new Vertex(1d, -1d, 0d);
    Vertex f = new Vertex(1d, 0d, 0d);
    Vertex g = new Vertex(0d, 0d, 0d);
    
    // use to translate a point one in the z direction
    Vertex z = new Vertex(0d, 0d, 1d);

    Plane parallelInside     = new Polygon(a, b, b.getTranslated(z));
    Plane parallelOutside    = new Polygon(d, e, e.getTranslated(z));
    Plane notParallelInside  = new Polygon(g, c, c.getTranslated(z));
    Plane notParallelOutside = new Polygon(e, b, b.getTranslated(z));
    Plane coincident         = new Polygon(g, f, f.getTranslated(z));
    Plane glancing           = new Polygon(e, f, f.getTranslated(z));

    assertTrue(triangle.getIntersectionPoints(parallelInside).size() == 2);
    assertTrue(triangle.getIntersectionPoints(parallelOutside).size() == 0);
    assertTrue(triangle.getIntersectionPoints(notParallelInside).size() == 2);
    assertTrue(triangle.getIntersectionPoints(notParallelOutside).size() == 0);
    assertTrue(triangle.getIntersectionPoints(coincident).size() == 2);
    assertTrue(triangle.getIntersectionPoints(glancing).size() == 1);
  }

  @Test public void testFindWhateverMidline() {
    // first test on a square
    Vertex a,b,c,d,e,f;
    a = new Vertex(4, 4, 4);
    b = new Vertex(4, 0, 4);
    c = new Vertex(0, 0, 4);
    d = new Vertex(0, 4, 4);
    Polygon p = new Polygon(a,b,c,d);
    LineSegment longest = p.findLongestMidline();
    LineSegment shortest = p.findShortestMidline();
    assertEquals(4, longest.length(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(4, shortest.length(), MathUtils.FLOAT_TOLERANCE);

    // elongate the x=4 side and bring them closer together in y
    a.translate(new Vertex(3,-1,0));
    b.translate(new Vertex(3, 1,0));
    //    p = new Polygon(a,b,c,d);
    longest = p.findLongestMidline();
    shortest = p.findShortestMidline();
    assertEquals(7, longest.length(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(3, shortest.length(), MathUtils.FLOAT_TOLERANCE);

    // try with a six sided thing
    a = new Vertex(1,0,0);
    b = new Vertex(0,1,0);
    c = new Vertex(0,2,0);
    d = new Vertex(1,3,0);
    e = new Vertex(5,3,0);
    f = new Vertex(5,0,0);
    Polygon bestBuyTag = new Polygon(a,b,c,d,e,f);
    longest = bestBuyTag.findLongestMidline();
    shortest = bestBuyTag.findShortestMidline();
    assertEquals(5, longest.length(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(2, shortest.length(), MathUtils.FLOAT_TOLERANCE);
  }




}
