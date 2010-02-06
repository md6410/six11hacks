package org.six11.turk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.six11.util.Debug;
import org.six11.util.gui.ApplicationFrame;

/**
 * 
 **/
public class Main {
  static TurkStudy turk;

  public static void main(String[] args) throws FileNotFoundException {
    ApplicationFrame af = new ApplicationFrame("Sketch Segmentation Study");
    turk = new TurkStudy();
    turk.init();
    turk.start();
    turk.addChangeListener(makeChangeListener());
    af.add(turk);
    af.setSize(800, 600);
    af.center();
    af.setVisible(true);
    turk.open(new FileInputStream(args[0]));
  }

  private static ChangeListener makeChangeListener() {
    return new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        bug("That's all for this sketch. Maybe open a new file?");
      }
    };
  }
  
  private static void bug(String what) {
    Debug.out("Main", what);
  }
}
