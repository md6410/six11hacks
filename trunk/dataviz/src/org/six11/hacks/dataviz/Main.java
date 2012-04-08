package org.six11.hacks.dataviz;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.six11.util.gui.Components;
import org.six11.util.pen.DrawingBuffer;
import org.six11.util.pen.DrawingBufferRoutines;
import org.six11.util.pen.Pt;

public class Main {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    File inFile = new File(args[0]);
    BufferedReader in = new BufferedReader(new FileReader(inFile));
    File outFile = new File(args[1]);
    List<String> questions = new ArrayList<String>();
    List<String> lower = new ArrayList<String>();
    List<String> mean = new ArrayList<String>();
    List<String> upper = new ArrayList<String>();
    List<String> width = new ArrayList<String>();
    List<List<String>> lists = new ArrayList<List<String>>();
    lists.add(questions);
    lists.add(lower);
    lists.add(mean);
    lists.add(upper);
    lists.add(width);
    while (in.ready()) {
      List<String> thisList = lists.remove(0);
      String line = in.readLine();
      StringTokenizer tok = new StringTokenizer(line, ",");
      while (tok.hasMoreTokens()) {
        thisList.add(tok.nextToken());
      }
    }
    DrawingBuffer buf = new DrawingBuffer();
    Font boringFont = new Font("Times New Roman", Font.PLAIN, 24);
    double xMult = 4;
    double yPad = 4;
    double yCursor = yPad;
    Graphics2D forFont = Components.getHeadlessGraphics();
    FontMetrics fm = forFont.getFontMetrics(boringFont);
    double yIncrement = yPad + fm.getHeight();
    double yTextOffset = fm.getDescent();
    Color errorBarColor = Color.GRAY;
    Color dotColor = Color.red.darker().darker();
    Color gridColor = Color.GRAY;
    Color textColor = Color.black;
    double errorBarThickness = 3.5;
    double gridThickness = 1.5;
    double dotRadius = errorBarThickness * 0.8;
    double textX = 120 * xMult;
    for (int i=0; i < questions.size(); i++) {
      double a = xMult * Double.parseDouble(lower.get(i));
      double b = xMult * Double.parseDouble(upper.get(i));
      double m = xMult * Double.parseDouble(mean.get(i));
      DrawingBufferRoutines.line(buf, new Pt(a, yCursor), new Pt(b, yCursor), errorBarColor, errorBarThickness);
      DrawingBufferRoutines.dot(buf, new Pt(m, yCursor), dotRadius, 1, dotColor, dotColor);
      DrawingBufferRoutines.text(buf, new Pt(textX, yCursor + yTextOffset), questions.get(i), textColor, boringFont);
      yCursor = yCursor + yIncrement;
    }
    double[] gridX = new double[] { 0, xMult * 25, xMult * 50, xMult * 75, xMult * 100 };
    for (double xVal : gridX) {
      DrawingBufferRoutines.line(buf, new Pt(xVal, 0), new Pt(xVal, yCursor), gridColor, gridThickness);
    }
    BufferedImage image = buf.getImage();
    ImageIO.write(image, "PNG", outFile);
  }

}
