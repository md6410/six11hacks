package org.six11.turk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.six11.turk.TurkStudy.TurkListener;
import org.six11.turk.TurkStudy.TurkSegment;
import org.six11.util.Debug;
import org.six11.util.args.Arguments;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.pen.Pt;

/**
 * 
 **/
public class Main {
  ApplicationFrame af;
  TurkStudy turk;
  List<File> files;
  int fileIdx;

  public static void main(String[] args) {
    new Main(new Arguments(args)).go();
  }

  public Main(Arguments args) {
    af = new ApplicationFrame("Sketch Segmentation Study");
    turk = new TurkStudy();
    turk.init();
    turk.start();
    turk.addTurkListener(new TurkListener() {
      public void roundFinished(String sketchName, SortedSet<Pt> corners, List<TurkSegment> segments) {
        System.out.println(sketchName);
        System.out.println("  corners: ");
        for (Pt pt : corners) {
          System.out.println("    " + Debug.num(pt) + " seq: " + pt.getString("seqIdx"));
        }
        System.out.println("  segments: ");
        for (TurkSegment seg : segments) {
          System.out.println("    " + seg.type);
        }
        System.out.println();
        nextFile();
      }
    });
    af.add(turk);
    af.setSize(800, 600);
    af.center();
    files = new ArrayList<File>();
    fileIdx = 0;
    for (int i = 0; i < args.getPositionCount(); i++) {
      File where = new File(args.getPosition(i));
      if (where.exists() && where.canRead()) {
        if (where.isDirectory()) {
          for (File f : where.listFiles()) {
            maybeAdd(f.getAbsoluteFile());
          }
        } else {
          maybeAdd(where.getAbsoluteFile());
        }
      }
    }
  }

  private void maybeAdd(File f) {
    if (f.exists() && f.canRead() && f.getName().endsWith(".sketch")) {
      files.add(f);
    }
  }

  public void go() {
    af.setVisible(true);
    nextFile();
  }

  public void nextFile() {
    if (fileIdx < files.size()) {
      try {
        File fileToOpen = files.get(fileIdx++);
        turk.open(fileToOpen.getName(), new FileInputStream(fileToOpen));
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
        System.exit(-1);
      }
    } else {
      System.out.println("Finished.");
      System.exit(0);
    }
  }

}
