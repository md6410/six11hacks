package org.six11.turk;

import java.awt.BorderLayout;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JOptionPane;

import org.six11.util.io.HttpUtil;
import org.six11.util.lev.NamedAction;
import org.six11.util.pen.OliveDrawingSurface;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.SequenceIO;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class DrawingStudy extends JApplet {

  Map<String, NamedAction> actions;
  OliveDrawingSurface surface;
  String amazonID;

  public void init() {
    amazonID="unknown";
    initActions();
    JPanel buttonBar = new JPanel();
    buttonBar.add(new JButton(actions.get("Done")));
    buttonBar.add(new JButton(actions.get("Clear")));
    surface = new OliveDrawingSurface();
    setLayout(new BorderLayout());
    add(buttonBar, BorderLayout.NORTH);
    add(surface, BorderLayout.CENTER);
  }
  
  public void passID(String id) {
    amazonID = id;
  }

  private void initActions() {
    actions = new HashMap<String, NamedAction>();
    actions.put("Done", new NamedAction("Done") {
      public void activate() {
        done();
      }

    });
    actions.put("Clear", new NamedAction("Clear") {
      public void activate() {
        clear();
      }
    });
  }

  protected void clear() {
    surface.getSoup().clearBuffers();
    surface.getSoup().getSequences().clear();
  }

  private void done() {
    try {
      List<Sequence> sequences = surface.getSoup().getSequences();
      StringWriter writer = new StringWriter();
      SequenceIO.writeAll(sequences, writer);
      System.out.println("Showing contents of writer: ");
      System.out.println(writer.toString());
      System.out.println("Done.");
      StringBuilder params = new StringBuilder();

      if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "You are about to save the diagram and quit.", "Are you sure?", JOptionPane.YES_NO_OPTION)){
    	  
      }

      HttpUtil ht = new HttpUtil();
      ht.setParam("sketchData", writer.toString(), params);
      ht.setParam("amazonID", amazonID, params);
      // set other params as necessary, like the user ID string
      // change the following filename/php script/whatever to your favorite thing.
      String myUrl = getCodeBase().toExternalForm() + "/upload.php";
      ht.post(myUrl, params);
    } catch (Exception ignore) {
      ignore.printStackTrace();
    }
    clear();  
  }

  @Override
  public void start() {
    super.start();
  }

  @Override
  public void stop() {
    super.stop();
  }

}
