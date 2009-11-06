package org.six11.flatcad.geom;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Math.*;
/**
 * 
 **/
public class BoundingBox {
  public double min_x = Double.MAX_VALUE;
  public double max_x = -Double.MAX_VALUE;
  public double min_y = Double.MAX_VALUE;
  public double max_y = -Double.MAX_VALUE;
  public boolean has_data = false;
  
  public void add(double x, double y) {
    has_data = true;
    min_x = min(x, min_x);
    max_x = max(x, max_x);
    min_y = min(y, min_y);
    max_y = max(y, max_y);
  }

  public void addSequence(List<Vertex> verts) {
    for (Vertex v : verts) {
      add(v.x(), v.y());
    }
  }

  public void addManySequences(List<List<Vertex>> manyVerts) {
    for (List<Vertex> verts : manyVerts) {
      addSequence(verts);
    }
  }

  public double width() {
    return max_x - min_x;
  }

  public double height() {
    return max_y - min_y;
  }

  public void fatten(double amt_x, double amt_y) {
    double dx = amt_x / 2.0;
    double dy = amt_y / 2.0;
    min_x = min_x - dx;
    max_x = max_x + dx;
    min_y = min_y - dy;
    max_y = max_y + dy;
  }
  
  public String toString() {
    return 
      "Bounding Box (" + width() + " by " + height() + "). " +
      " min_x = " + min_x + " " +
      " max_x = " + max_x + " " +
      " min_y = " + min_y + " " +
      " max_y = " + max_y + ".";
  }
  
}
