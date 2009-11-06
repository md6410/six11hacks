package org.six11.flatcad.geom;

/**
 * A vague complaint that somethign is awry with an intersection
 * operation (such as two things are parallel and don't intersect).
 **/
public class IntersectionException extends RuntimeException {

  /**
   * Simply use the superconstructor.
   */
  public IntersectionException(String msg) {
    super(msg);
  }
}
