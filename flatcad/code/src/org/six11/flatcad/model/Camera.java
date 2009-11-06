package org.six11.flatcad.model;

import static org.lwjgl.opengl.GL11.*;

public class Camera {

  protected double rotX; 
  protected double rotY;
  protected double radius;

  public Camera() {
    this.rotX = 0f;
    this.rotY = 0f;
    this.radius = 10.0;
  }
  
  public void extendRadius(double dr) {
    radius += dr;
  }

  public void rotate(double dx, double dy) {
    rotX += dx;
    rotY += dy;
  }

  public void glSetupCamera() {
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslated(0d, 0d, -1.0 * radius);
    glRotatef((float)rotY, 1f, 0f, 0f);
    glRotatef((float)rotX, 0f, 1f, 0f);    
  }

}
