package org.six11.flatcad.geom;

import org.six11.util.Debug;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.glu.GLU.*;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;

import java.util.List;

/**
 * A plain old vertex in 3D space. This is represented as a 4-vector
 * in order to make it work nicely with 4x4 affine matrix
 * transforms. The last component therefore is '1' for all location
 * vertices. Given a vertex that represents direction (see the
 * Direction class) or displacement and is going to be used with
 * matrix math, the last component should be '0'. Note that a common
 * name for the last coordinate is the 'homogenous component'.
 *
 * <p>The semantics of equals() deserves attention. Two Vertex objects
 * are equals only if they are the same object in memory (e.g. v1 ==
 * v2). If you would like to determine if two vertices represent the
 * same point in space, use the isSame() method instead.
 *
 * <p>Because equals() is used by many of the Collections classes and we
 * occasionally really want equals() to mean what 'isSame()' means,
 * there are static methods such as listContainsSame(List<Vertex>
 * Vertex) that tell you if a list contains an isSame() vertex, which
 * is useful.
 *
 * <p>There are a few methods that deal with getting modified versions of
 * a Vertex, and these conform to a naming convention that helps the
 * programmer remember what does what. For example, there are two
 * methods, <tt>getReverse</tt> and <tt>reverse</tt>. All of the
 * methods that start with 'get' (such as <tt>getReverse</tt>) will
 * return something based on a COPY of this vertex. Those that don't
 * (such as <tt>reverse</tt>) will directly modify this vertex and
 * then return it. The reason <tt>reverse</tt> and friends return a
 * reference to this object is so you can chain them together:
 * <tt>myVert.translate(t).reverse().transform(rotationMatrix)</tt>
 * for example.
 **/
public class Vertex implements Namable, Visitable {

  private static int ID_COUNTER = 0;

  /**
   * The origin -- { 0, 0, 0 }. It is inadvisable to modify it. I
   * suggest using copy() if you need to modify it.
   */
  public static Vertex ORIGIN = new Vertex(0d, 0d, 0d);

  /**
   * This vertex's data. data[0] is x, data[1] is y, data[2] is z, and
   * data[3] is the homogenous coordinate, which defaults to 1.
   */
  protected double[] data;

  /**
   * An optional reference to one of the (potentially many) half-edges
   * that leave from this vertex.
   */
  protected HalfEdge edge;

  /**
   * A name, used for debugging purposes. It defaults to something
   * good, like V_423, and uses a static counter to ensure each is
   * unique.
   */
  protected String name;
  
  /**
   * The variable name of this vertex. It defaults to null.
   */
  protected String varName;

  /**
   * The edge that was last visited when sitting on this vertex. Note
   * that it may be null.
   */
  protected HalfEdge lastVisited;
  
  /**
   * Return an empty vertex located at the origin. 
   */
  public Vertex() {
    this(0d, 0d, 0d);
  }

  /**
   * Build a vertex at the location { x, y, z }.
   */
  public Vertex(double x, double y, double z) {
    data = new double[] {x,y,z,1};
    name = "V_" + ID_COUNTER++;
  }

  /**
   * Build a vertex using the provided data. The provided array may be
   * of any length, but only the first four elements will be
   * used. Elements that are not specified (because the 'externalData'
   * array is too short) will default to 0 for the first three
   * components, and 1 for the homogenous component.
   */
  public Vertex(double[] externalData) {
    data = new double[4];
    for (int i=0; i < externalData.length && i < data.length; i++) {
      data[i] = externalData[i];
    }
    if (externalData.length < 4) {
      data[3] = 1d;
    }
  }

  /**
   * Tells you if this vertext has a variable name.
   */
  public boolean hasVarName() {
    return varName != null;
  }

  /**
   * Returns the variable name of this vertex, which may be null.
   *
   * @see setVarName
   */
  public String getVarName() {
    return varName;
  }

  /**
   * Sets this vertex's variable name. This doesn't affect the
   * geometry at all but can make life easier in a GUI.
   */
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
   * Returns this Vertex's HalfEdge, or null if it doesn't have one.
   */
  public HalfEdge getEdge() {
    return edge;
  }

  /**
   * When a Vertex is used in the context of polygons and polyhedra,
   * the data structure that binds everything together is HalfEdge. It
   * is nice (but not strictly required) for a vertex to hold a
   * reference to one of the half-edges that treats it. (It isn't
   * necessary to hold more than one, since you can easily get at all
   * the others from the HalfEdge.)
   */
  public void setEdge(HalfEdge e) {
    edge = e;
  }

  /**
   * Sets the human-readable name for this vertex. By default it has
   * something like V_423, where the number is a comes from a static
   * incrementing int. It's usually good enough, but this is here
   * anyway in case you have a better name for your points.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Make and return a copy of this Vertex. They will be isSame() but
   * not equals().
   */
  public Vertex copy() {
    return new Vertex(data);
  }

  /**
   * Return the x-component as a float.
   */
  public float xf() {
    return (float) data[0];
  }

  /**
   * Return the x-component as a double.
   */
  public double x() {
    return data[0];
  }

  /**
   * Return the y-component as a float.
   */
  public float yf() {
    return (float) data[1];
  }

  /**
   * Return the y-component as a double.
   */
  public double y() {
    return data[1];
  }

  /**
   * Return the z-component as a float.
   */
  public float zf() {
    return (float) data[2];
  }

  /**
   * Return the z-component as a double.
   */
  public double z() {
    return data[2];
  }

  /**
   * Produces a nice string with the name and the three spatial
   * coordinates.
   */
  public String toString() {
    return "{" + name + " " + Debug.num(x()) + ", " + 
      Debug.num(y()) + ", " + Debug.num(z()) + ", h=" + data[3] + "}";
  }

  /**
   * Two vertexes are equals() to one another only if they are the
   * same object in memory. To determine if two points are coincident,
   * use isSame(Vertex).
   */
  public boolean equals(Object other) {
    return other == this;
  }

  /**
   * Tells you if this vertex and the other occupy the same point in
   * space. Due to the inherent inprecision of floating point math,
   * the values may be slightly off. This has a configurable tolerance
   * of MathUtils.FLOAT_TOLERANCE.
   */
  public boolean isSame(Vertex other) {
    return 
      (Math.abs(other.x() - x()) < MathUtils.FLOAT_TOLERANCE) &&
      (Math.abs(other.y() - y()) < MathUtils.FLOAT_TOLERANCE) &&
      (Math.abs(other.z() - z()) < MathUtils.FLOAT_TOLERANCE);
      
  }

  /**
   * Detect if the provided list has a Vertex that isSame() as the one
   * given here. The standard List.contains() method uses the
   * .equals(), which is frequently useful, but sometimes that is not
   * what we want.
   */
  public static boolean listContainsSame(List<Vertex> list, Vertex target) {
    boolean ret = false;
    for (Vertex v : list) {
      if (v.isSame(target)) {
	ret = true;
	break;
      }
    }
    return ret;
  }

  /**
   * Shorthand for isSame(ORIGIN).
   */
  public boolean isZero() {
    return isSame(ORIGIN);
  }

  /**
   * Modifies this vertex in place by multiplying each component by
   * -1, and returning a reference to this.
   */
  public Vertex reverse() {
    data[0] *= -1.0;
    data[1] *= -1.0;
    data[2] *= -1.0;
    return this;
  }

  /**
   * Returns a copy of this vertex that has been <tt>reverse()</tt>d.
   */
  public Vertex getReverse() {
    return copy().reverse();
  }

  /**
   * Return a new Vertex that is just like this one but with the x,y,
   * and z components by the given amount.
   */
  public Vertex getScaled(double amt) {
    return copy().scale(amt);
  }

  /**
   * Scale the x,y, and z components of this Vertex in place and
   * return a reference to 'this'.
   */
  public Vertex scale(double amt) {
    data[0] *= amt;
    data[1] *= amt;
    data[2] *= amt;
    return this;
  }

  /**
   * Create and return a new Vertex that is equivilent to this Vertex,
   * translated by the amount indicated.
   */
  public Vertex getTranslated(Vertex amt) {
    return copy().translate(amt);
  }

  /**
   * Move this Vertex by the given amount and return 'this'.
   */
  public Vertex translate(Vertex amt) {
    data[0] += amt.x();
    data[1] += amt.y();
    data[2] += amt.z();
    return this;
  }

  /**
   * Transform this vertex in place and return a reference to it.
   */
  public Vertex transform(RealMatrix trans) {
    data = trans.operate(data);
    return this;
  }

  /**
   * Create a copy of this vertex, transform it, and return it.
   */
  public Vertex getTransformed(RealMatrix trans) {
    return copy().transform(trans);
  }

  /**
   * Gives you a copy of the double[4] that is used to represent this
   * vertex in memory.
   */
  public double[] getData() {
    double[] ret = new double[4];
    ret[0] = data[0];
    ret[1] = data[1];
    ret[2] = data[2];
    ret[3] = 1;
    return ret;
  }

  /**
   * Returns the magnitude of the x,y,z vector. This is essentially
   * the 3D hypotenuse: <tt>sqrt(x^2 + y^2 + z^2)</tt>.
   */
  public double mag() {
    return Math.sqrt((x()*x()) + (y()*y()) + (z()*z()));
  }

  /**
   * Return a 'normalized' copy of this vertex's double[4] without
   * modifying this vertex. See normalize().
   */
  public double[] getNormalizedData() {
    double[] ret = new double[4];
    double m = mag();
    if (m != 0) {
      ret[0] = data[0] / m;
      ret[1] = data[1] / m;
      ret[2] = data[2] / m;
      ret[3] = data[3];
    } // otherwise leave it as zeros
    return ret;
  }

  /**
   * Normalize this vertex's data, meaning the x,y, and z components
   * are divided by the magnitude. This results in creating a vertex
   * with a magnitude of one, also known as a unit vector.
   */
  public void normalize() {
    this.data = getNormalizedData();
  }

  /**
   * The cross product of this X other.
   */
  public Vertex cross(Vertex other) {
    /*
      Assuming my values are a1, a2, a3, and the other's values are
      b1, b2, b3:
      
     [   0   -a3    a2 ]   [ b1 ]
     [  a3     0   -a1 ] * [ b2 ]
     [ -a2    a1     0 ]   [ b3 ]
      
    */ 
    double valX = -1.0 * z() * other.y() +        y() * other.z();
    double valY =        z() * other.x() + -1.0 * x() * other.z();
    double valZ = -1.0 * y() * other.x() +        x() * other.y();
    return new Vertex(valX, valY, valZ);
  }

  /**
   * I seriously have NO IDEA --HOW-- or --WHY-- this works, but
   * apparently it does. It isn't using the z-component. WTF??!  I am
   * not even aware of any meaning of vector1 det vector2. I don't
   * remember coding this, but I do vaguely remember looking at the
   * web pages that google gives me when I search for 'vector
   * determinant'.
   *
   * THE SOLUTION appears to be here:
   * http://zkwarl.blogspot.com/2006/08/math-tip-find-angle-between-two.html
   */
  public double det(Vertex o) {
    return x()*o.y() - y()*o.x();    
  }

  /**
   * Returns the dot product of this vector dotted with the other.
   */
  public double dot(Vertex o) {
    double ret = 
      x() * o.x() + 
      y() * o.y() + 
      z() * o.z();
    //    Debug.out("Vertex", this + " dot(" + o + ") = " + Debug.num(ret));
    return ret;
  }

  /**
   * Send a command to OpenGL to insert this point. '<tt>glVertex3D(x(),
   * y(), z())</tt>' is what it does.
   */
  public void glVertex() {
    glVertex3d(x(), y(), z());
  }

  /**
   * Rotate this vertex in place using the given rotation matrix and
   * pivot point, returning a reference to this vertex so you can
   * chain calls together.
   */
  public Vertex rotate(RealMatrix rotationMatrix, Vertex pivot) {
    MatrixStack max = new MatrixStack();
    max.push(MathUtils.getTranslationMatrix(pivot.getReverse()));
    max.push(rotationMatrix);
    max.push(MathUtils.getTranslationMatrix(pivot));
    transform(max.getCurrent());
    return this;
  }
  
  /**
   * Return a rotated copy of this vertex. See <tt>rotate</tt>.
   */
  public Vertex getRotated(RealMatrix rotationMatrix, Vertex pivot) {
    return copy().rotate(rotationMatrix, pivot);
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testRotateToXY() {
    Vertex v1 = new Vertex(1.0, 2.0, 3.0);
    Vertex v2 = new Vertex(21.6, 0.5, 3.9);
    Direction u = new Direction(v1, v2);
    // v3 is an arbitrary 3rd point, it is only important that it is
    // not colinear with u.
    Vertex v3 = new Vertex(0d, 0d, 0d);//(1.3, 2.0, 3.9);
    Direction arb = new Direction(v1, v3);
    Direction w = u.cross(arb);
    Direction v = w.cross(u);
    assertEquals(0d, u.dot(w), 0.001);
    assertEquals(0d, u.dot(v), 0.001);
    assertEquals(0d, w.dot(v), 0.001);

    RealMatrix rot = MathUtils.buildMatrixFromVectors(u,v,w);
    Vertex v1_rotated = v1.getTransformed(rot);
    Vertex v2_rotated = v2.getTransformed(rot);
    assertEquals(0d, v1_rotated.z(), 0d);
    assertEquals(0d, v2_rotated.z(), 0d);
    double orig_dist = v1.getTranslated(v2).mag();
    double new_dist  = v1_rotated.getTranslated(v2_rotated).mag();
    assertEquals(orig_dist, new_dist, 0.001d);
  }

  @Test public void testConstructor() {
    Vertex v = new Vertex();
    assertEquals(0d, v.x(), 0.0);
    assertEquals(0d, v.y(), 0.0);
    assertEquals(0d, v.z(), 0.0);

    v = new Vertex(1000000, -1000000, Math.PI);
    assertEquals(1000000d, v.x(), 0.0);
    assertEquals(-1000000d, v.y(), 0.0);
    assertEquals(Math.PI, v.z(), 0.0);
  }

  @Test public void testReverse() {
    Vertex orig = new Vertex(42.0, 0.0, -4.2);
    Vertex inv = orig.getReverse();
    assertEquals(-42d, inv.x(), 0.0);
    assertEquals(0d, inv.y(), 0.0);
    assertEquals(4.2, inv.z(), 0.0);

    Vertex one = new Vertex(-1.0, 0.0, 1.0);
    one.reverse();
    assertEquals(1d, one.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(0d, one.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(-1d, one.z(), MathUtils.FLOAT_TOLERANCE);
  }

  @Test public void testTranslated() {
    Vertex orig = new Vertex(1.0, 4.0, 7.0);
    Vertex amt = new Vertex(9.0, 6.0, 3.0);
    Vertex trn = orig.getTranslated(amt);
    assertEquals(trn.x(), 10.0, 0.0);
    assertEquals(trn.y(), 10.0, 0.0);
    assertEquals(trn.z(), 10.0, 0.0);

    trn = orig.getTranslated(amt.getReverse());
    assertEquals(trn.x(), -8.0, 0.0);
    assertEquals(trn.y(), -2.0, 0.0);
    assertEquals(trn.z(), 4.0, 0.0);
  }

  @Test public void testScale() {
    Vertex tens = new Vertex(10d, 10d, 10d);
    tens.scale(0.5);
    assertEquals(5d, tens.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(5d, tens.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(5d, tens.z(), MathUtils.FLOAT_TOLERANCE);
    
    Vertex misc = new Vertex(10d, 15d, 20d);
    Vertex smaller = misc.getScaled(0.1);
    assertEquals(1d, smaller.x(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(1.5d, smaller.y(), MathUtils.FLOAT_TOLERANCE);
    assertEquals(2.0d, smaller.z(), MathUtils.FLOAT_TOLERANCE);    
  }

  @Test public void testMag() {
    Vertex right = new Vertex(3.0, 0.0, 4.0);
    assertEquals(right.mag(), 5.0, 0.0);
  }

  @Test public void testNormalize() {
    Vertex vert = new Vertex(1d, 1d, 0d);
    vert.normalize();
    double root2inv = 1d / Math.sqrt(2d);
    assertEquals(vert.x(), root2inv, 0d);
    assertEquals(vert.y(), root2inv, 0d);
    assertEquals(vert.z(), 0d, 0d);
  }

  @Test public void testCross() {
    Vertex a = new Vertex(10d, 0d, 0d);
    Vertex b = new Vertex(7d, 6d, 0d);

    Vertex c = a.cross(b);
    assertEquals(c.x(), 0d, 0d);
    assertEquals(c.y(), 0d, 0d);
    assertEquals(c.z(), 60d, 0d);
  }

  

}
