package org.six11.flatcad.model;

import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.gl.GLDrawable;
import java.util.List;
import java.util.ArrayList;

/**
 * A 2D piece of material. It has its own coordinate system, so by the
 * time drawGL() is called, the matrix stack must have the appropriate
 * transformations on it already if you want it to be drawn correctly.
 **/
public class Piece implements GLDrawable {
  
  protected Polygon geometry;
  protected Piece parent;
  protected List<Piece> children;

  public Piece(Polygon geometry) {
    this.geometry = geometry;
    parent = null;
    children = new ArrayList<Piece>();
  }

  public void drawGL() {
    // TODO: create a Polyhedron that represents this piece and store
    // it for subsequent calls to drawGL.
  }
  
  public void addChild(Piece kid) {
    if (!children.contains(kid)) {
      children.add(kid);
    }
    kid.setParent(this);
  }

  public void setParent(Piece motherDear) {
    parent = motherDear;
    if (!motherDear.children.contains(this)) {
      motherDear.addChild(this);
    }
  }
}
