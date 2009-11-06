package org.six11.flatcad.gl;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Mouse implements MouseMotionListener, MouseListener {
  
  protected OpenGLDisplay comp;
  protected int lastX;
  protected int lastY;

  public Mouse(OpenGLDisplay comp) {
    this.comp = comp;
    reset();
  }

  public final void reset() {
    this.lastX = -1;
    this.lastY = -1;
  }

  public void mouseClicked(MouseEvent ev) { }

  public void mouseEntered(MouseEvent ev) { }

  public void mouseExited(MouseEvent ev) { }

  public void mousePressed(MouseEvent ev) { }

  public void mouseReleased(MouseEvent ev) { 
    reset();
  }

  public void mouseMoved(MouseEvent ev) { }

  public void mouseDragged(MouseEvent ev) {
    if (lastX >= 0 && lastY >= 0) {
      mouseDragged(ev.getPoint().x - lastX,
		   ev.getPoint().y - lastY,
		   ev.isShiftDown());
    }
    lastX = ev.getPoint().x;
    lastY = ev.getPoint().y;
  }


  public void mouseDragged(int dx, int dy, boolean changeRadius) {
    if (changeRadius) {
      //comp.camRadius += ((float) dy) / 100f;
      //      comp.camera.extendRadius(((double) dy) / 100d);
    } else {
      comp.getCamera().rotate((double) dx, (double) dy);
    }
    
    comp.repaint();
  }

}
