package org.six11.turk;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.six11.util.Debug;
import org.six11.util.lev.NamedAction;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.SequenceIO;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class TurkStudy extends JApplet {

  private static final String K_CORNER_MODE = "Corner Mode";
  private static final String K_LABEL_MODE = "Label Mode";

  TurkSurface surface;
  JPanel buttonBar;
  Map<String, NamedAction> actions;
  List<ChangeListener> changeListeners;

  public void init() {
    changeListeners = new ArrayList<ChangeListener>();
    surface = new TurkSurface();
    buttonBar = new JPanel();
    actions = new HashMap<String, NamedAction>();
    initActions();
    JPanel cornerFindingMode = new JPanel();
    cornerFindingMode.add(new JButton(actions.get("Done")));
    JPanel labelingMode = new JPanel();
    labelingMode.add(new JButton(actions.get("Mistake")));
    labelingMode.add(new JButton(actions.get("Line")));
    labelingMode.add(new JButton(actions.get("Arc")));
    labelingMode.add(new JButton(actions.get("Other Curve")));
    buttonBar.setLayout(new CardLayout());
    buttonBar.add(cornerFindingMode, K_CORNER_MODE);
    buttonBar.add(labelingMode, K_LABEL_MODE);
    setLayout(new BorderLayout());
    add(buttonBar, BorderLayout.NORTH);
    add(surface, BorderLayout.CENTER);

    for (NamedAction action : actions.values()) {
      KeyStroke s = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
      if (s != null) {
        getRootPane().getInputMap().put(s, action.getName());
        getRootPane().getActionMap().put(action.getName(), action);
      }
    }

  }

  public void start() {
    super.start();
  }

  private final void initActions() {
    actions.put("Done", new NamedAction("Done", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0)) {
      public void activate() {
        findCorners();
      }
    });
    actions.put("Mistake", new NamedAction("I made a mistake, let me go back", KeyStroke
        .getKeyStroke(KeyEvent.VK_DELETE, 0)) {
      public void activate() {
        mistake();
      }
    });
    actions.put("Line", new NamedAction("Line (1)", KeyStroke.getKeyStroke(KeyEvent.VK_1, 0)) {
      public void activate() {
        label(Type.line);
      }
    });
    actions.put("Arc", new NamedAction("Arc (2)", KeyStroke.getKeyStroke(KeyEvent.VK_2, 0)) {
      public void activate() {
        label(Type.arc);
      }
    });
    actions.put("Other Curve", new NamedAction("Other Curve (3)", KeyStroke.getKeyStroke(
        KeyEvent.VK_3, 0)) {
      public void activate() {
        label(Type.curve);
      }
    });

  }

  protected void label(Type t) {
    boolean result = surface.setSegmentType(t);
    if (result) {
      surface.setDone();
      fireFinished();
    }
  }

  public void addChangeListener(ChangeListener lis) {
    changeListeners.add(lis);
  }
  
  private void fireFinished() {
    ChangeEvent ev = new ChangeEvent(this);
    for (ChangeListener cl : changeListeners) {
      cl.stateChanged(ev);
    }
  }

  protected void mistake() {
    CardLayout cards = (CardLayout) buttonBar.getLayout();
    cards.show(buttonBar, K_CORNER_MODE);
    surface.setCornerFinding();
  }

  protected void findCorners() {
    CardLayout cards = (CardLayout) buttonBar.getLayout();
    cards.show(buttonBar, K_LABEL_MODE);
    surface.setLabeling();
  }

  void open(InputStream inStream) {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
      List<Sequence> sequences = SequenceIO.readAll(in);
      bug("Read " + sequences.size() + " sequences from input stream.");
      surface.clearDrawing();
      for (Sequence seq : sequences) {
        surface.addSequence(seq);
      }
      bug("Reset drawing surface with new data.");
    } catch (IOException ex) {
      bug("Can't read from input stream:");
      ex.printStackTrace();
    }
  }

  public static enum Type {
    line, arc, curve, unknown;
  }

  private void bug(String what) {
    Debug.out("TurkStudy", what);
  }
}
