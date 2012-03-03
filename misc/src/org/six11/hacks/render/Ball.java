package org.six11.hacks.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static org.six11.hacks.render.RenderTest.rand;
import static org.six11.hacks.render.RenderTest.randFloatBetween;

public class Ball {

  private float x, y;
  private float dx, dy;
  private float r;
  private Color fillColor, borderColor;
  private Ellipse2D circ;
  private BufferedImage im;

  public Ball() {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    this.r = 50f;
    this.x = randFloatBetween(r, screen.width - r);
    this.y = randFloatBetween(r, screen.height - r);
    this.dx = randFloatBetween(0.5f, 2f);
    this.dy = randFloatBetween(0.5f, 2f);
    this.fillColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
        rand.nextFloat());
    this.borderColor = fillColor.darker().darker();
    this.circ = new Ellipse2D.Float(x - r, y - r, 2 * r, 2 * r);
  }

  public void move(Dimension bounds) {
    x = x + dx;
    y = y + dy;
    if (x + r > bounds.width && dx > 0) {
      dx = -dx;
    }
    if (x - r < 0 && dx < 0) {
      dx = -dx;
    }
    if (y + r > bounds.height && dy > 0) {
      dy = -dy;
    }
    if (y - r < 0 && dy < 0) {
      dy = -dy;
    }
    circ.setFrame(x - r, y - r, 2 * r, 2 * r);
  }

  public void render(Graphics2D g) {
    g.setColor(fillColor);
    g.fill(circ);
    g.setColor(borderColor);
    g.draw(circ);
  }

  public BufferedImage renderImage() {
    if (im == null) {
      im = new BufferedImage((int) circ.getWidth() + 1, (int) circ.getHeight() + 1,
          BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = im.createGraphics();
      Point2D loc = getLocation();
      g.translate(-loc.getX(), -loc.getY());
      render(g);
    }
    return im;
  }

  public Shape getClip() {
    return circ.getFrame();
  }

  public Point2D getLocation() {
    return new Point2D.Float((float) circ.getMinX(), (float) circ.getMinY());
  }

}
