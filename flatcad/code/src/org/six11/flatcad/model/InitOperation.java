package org.six11.flatcad.model;

/**
 * The operation that starts it all -- this is the first operation in
 * every FlatCAD model.
 **/
public class InitOperation extends Operation {
  
  public InitOperation() {
    this.result = new Generation(this, null);
  }
}
