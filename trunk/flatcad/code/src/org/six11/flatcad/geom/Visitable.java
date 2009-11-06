package org.six11.flatcad.geom;

public interface Visitable {

  /**
   * Indicates that the visited thing (such as a polygon or vertex) is
   * just now being visted along the given half-edge.
   */
  public void addVisit(HalfEdge e);

  /**
   * Returns the last HalfEdge that visited this thing.
   */
  public HalfEdge getLastVisitor();
}
