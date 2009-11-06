package org.six11.flatcad.gl;

/**
 * A simple interface that declares something to be capable of drawing
 * itself to the OpenGL pipe.
 **/
public interface GLDrawable {

  /**
   * Use various gl commands to put down pretty pixels. It is the
   * responsibility of each implementing class to make sure the GL
   * state is the same on return as it was on entry. So if you push
   * something onto the stack or change a drawing attribute, make sure
   * you pop the stack and change the attribs back to what they were.
   */
  public void drawGL();

}
