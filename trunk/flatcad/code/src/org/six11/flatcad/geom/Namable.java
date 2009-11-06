package org.six11.flatcad.geom;

/**
 * Simply indicates that something can have a variable name assigned
 * to it. This is the 'backwards' way of doing it, but I'm in a hurry.
 */
public interface Namable {
  public String getVarName();
  public void setVarName(String n);
  public boolean hasVarName();
}
