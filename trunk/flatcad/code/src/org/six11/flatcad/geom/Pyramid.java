package org.six11.flatcad.geom;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * A pyramid with four triangular faces and a square face on the
 * bottom. This is mostly here as a convenient way of constructing a
 * polyhedron that is not a box, but something that has a little more
 * interesting geometry: The 'top' Vertex of the pyramid connects four
 * faces together.
 *
 * <p>Note: this is just for testing purposes and will eventually go
 * away (either into another package or thrown out entirely).
 **/
public class Pyramid extends Polyhedron  {

  protected Vertex top, n, s, e, w;
  protected Polygon nf, ef, sf, wf, bot;

  /**
   * Make a pyramid of size=1.
   */
  public Pyramid() {
    this(1.0);
  }
  
  /**
   * Make a new pyramid whose square faces are the given length, with
   * the distance from the square to the top being <tt>size/2</tt>.
   */
  public Pyramid(double size) {
    double pos = size / 2d;
    double neg = 0d - pos;
    top = new Vertex(0d, 0d, pos);
    n = new Vertex(0d, pos, 0d);
    s = new Vertex(0d, neg, 0d);
    e = new Vertex(pos, 0d, 0d);
    w = new Vertex(neg, 0d, 0d);
    top.setName("top");
    n.setName("n");
    s.setName("s");
    e.setName("e");
    w.setName("w");
    
    // OpenGL wants the polygons defined in COUNTER-CLOCKWISE order
    nf = new Polygon(e, n, top);
    ef = new Polygon(s, e, top);
    sf = new Polygon(w, s, top);
    wf = new Polygon(n, w, top);
    bot = new Polygon(e, s, w, n);

    setFaces(nf, ef, sf, wf, bot);
  }

}
