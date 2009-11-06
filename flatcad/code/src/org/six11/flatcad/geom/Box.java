package org.six11.flatcad.geom;

import org.six11.util.Debug;

/**
 * Create a 3D Box, which is a six-sided, rectangularly-faced
 * polyhedron. This is a good way to create flat sheets of
 * material. 
 **/
public class Box extends Polyhedron {
  
  /**
   * Vertices of the box, using a predictable naming convention. w and
   * e mean west and east, n and s mean north and south, and t and b
   * mean top and bottom. 
   */
  Vertex wnt, ent, est, wst, wnb, enb, esb, wsb;

  /**
   * The top, bottom, north, south, east, and west faces.
   */
  Polygon top, bot, n, s, e, w;

  /**
   * Make a box of default size.
   */
  public Box() {
    this(1d, 1d, 0.1);
  }
  
  /**
   * Make a box with the given dimensions in x, y, and z that is
   * centered at the origin.
   */
  public Box(double xdim, double ydim, double zdim) {
    double px = xdim / 2d;
    double nx = 0d - px;
    double py = ydim / 2d;
    double ny = 0d - py;
    double pz = zdim / 2d;
    double nz = 0d - pz;

    wnt = new Vertex(nx, py, pz); // top vertices
    ent = new Vertex(px, py, pz);
    est = new Vertex(px, ny, pz);
    wst = new Vertex(nx, ny, pz);

    wnb = new Vertex(nx, py, nz); // bottom vertices
    enb = new Vertex(px, py, nz);
    esb = new Vertex(px, ny, nz);
    wsb = new Vertex(nx, ny, nz);

    // OpenGL considers counter-clockwise vertex order as 'front'.
    top = new Polygon(wnt, wst, est, ent);
    top.setName("top");

    bot = new Polygon(enb, esb, wsb, wnb);
    bot.setName("bot");

    n   = new Polygon(enb, wnb, wnt, ent);
    n.setName("north");

    s   = new Polygon(est, wst, wsb, esb);
    s.setName("south");

    e   = new Polygon(est, esb, enb, ent);
    e.setName("east");

    w   = new Polygon(wnt, wnb, wsb, wst);
    w.setName("west");
    
    setFaces(top, bot, n, s, e, w);
  }

}
