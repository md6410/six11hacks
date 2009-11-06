package org.six11.flatcad.geom;

/**
 * A catch-all exception class for any kind of problem with the basic
 * geometry of some polytope.
 **/
public class IllegalGeometryException extends RuntimeException {

  /**
   * Simply use the superconstructor.
   */
  public IllegalGeometryException(String msg) {
    super(msg);
  }
}
