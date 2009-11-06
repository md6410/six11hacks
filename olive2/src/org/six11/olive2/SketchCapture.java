package org.six11.olive2;

import org.six11.util.gui.Colors;
import java.awt.geom.RoundRectangle2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.six11.util.Debug;

/**
 * 
 **/
public class SketchCapture extends JComponent {

  private SketchBook sketchBook;
  private Color bgColor;
  private Color borderColor;
  private Stroke borderStroke;
  private double borderPad = 3.0;

  public SketchCapture() {
    sketchBook = new SketchBook();
    sketchBook.addChangeListener(new ChangeListener() {
	public void stateChanged(ChangeEvent ev) {
	  repaint();
	}
      });
    
    initGUI();
  }

  SketchBook getSketchBook() {
    return sketchBook;
  }

  private final void initGUI() {
    bgColor = Colors.getDefault().get(Colors.BACKGROUND);
    borderColor = Colors.getDefault().get(Colors.ACCENT);
    borderStroke = new BasicStroke
      (3f,                        // pen thickness
       BasicStroke.CAP_BUTT,      // cap
       BasicStroke.JOIN_MITER,    // join
       1f,                        // miter limit
       new float[] { 7, 7 },      // dash
       7);                        // dash phase
    SketchMouseThing mouseThing = new SketchMouseThing(sketchBook);
    addMouseListener(mouseThing);
    addMouseMotionListener(mouseThing);
  }

  public void paintComponent(Graphics g1) {
    Graphics2D g = (Graphics2D) g1;
    drawBorderAndBackground(g);
    sketchBook.draw(g);
  }

  protected void drawBorderAndBackground(Graphics2D g) {
    g.setRenderingHints(new RenderingHints
			(RenderingHints.KEY_ANTIALIASING,
			 RenderingHints.VALUE_ANTIALIAS_ON));
    RoundRectangle2D rec = new RoundRectangle2D.Double
      (borderPad, borderPad, getWidth() - 2.0*borderPad, getHeight() - 2.0*borderPad, 40, 40);
    g.setColor(bgColor);
    g.fill(rec);
    g.setStroke(borderStroke);
    g.setColor(borderColor);
    g.draw(rec);
  }
  

}
