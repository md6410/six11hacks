package org.six11.flatcad.geom;

import org.six11.util.Debug;

/**
 * A Direction is a special type of Vertext that is normalized -- it
 * specifies direction but it's length is always one. The homogenous
 * coordinate for all Direction objects is zero.
 **/
public class Direction extends Vertex {

  public final static Direction X = new Direction(1d, 0d, 0d);
  public final static Direction Y = new Direction(0d, 1d, 0d);
  public final static Direction Z = new Direction(0d, 0d, 1d);

  /**
   * Make a new direction with the given x,y,z components. The input
   * values may have any value. The final values that are stored are
   * always normalized. So you may use something like <tt>new
   * Direction(100, 0, 0)</tt>, but you'll get back a direction of
   * length one that points along the positive x-axis.
   */
  public Direction(double x, double y, double z) {
    super(x,y,z);
    data[3] = 0d;
    normalize();
  }

  /**
   * Make a new Direction with the provided data (ext[0]=x, 1=y, 2=z,
   * the rest is ignored).
   */
  public Direction(double[] ext) {
    super(ext);
    normalize();
    data[3] = 0d;
  }

  /**
   * Create a new Direction that has the same direction as the given
   * source vertex, but is normalized.
   */
  public Direction(Vertex source) {
    super(source.data);
    normalize();
    data[3] = 0d;
  }

  /**
   * Create a Direction that points from a to b. The order is
   * important.
   */
  public Direction(Vertex a, Vertex b) {
    this(b.getTranslated(a.getReverse()));
  }

  public Direction copy() {
    return new Direction(super.copy());
  }
  
  public Direction cross(Vertex a) {
    return new Direction(super.cross(a));
  }
  
}
