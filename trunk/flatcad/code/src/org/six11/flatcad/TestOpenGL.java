/* TestOpenGL.java */

package org.six11.flatcad;

import org.six11.util.Debug;
import java.awt.BorderLayout;
import org.six11.util.gui.ApplicationFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestOpenGL implements ActionListener {
  
  TestOpenGLComponent glComp;
  /**
   * A simple 'Hello World' for the LWJGL stuff, just as a sanity
   * check.
   */
  public static void main(String[] args) throws Exception {
    new TestOpenGL().run();
  }

  public void run() throws Exception {
    ApplicationFrame af = new ApplicationFrame("Hey You!", 500, 400);
    af.setLayout(new BorderLayout());
    JButton f1 = new JButton("Fold Vert");
    f1.addActionListener(this);
    JButton f2 = new JButton("Fold Horiz");
    f2.addActionListener(this);
    af.add(f1, BorderLayout.WEST);
    af.add(f2, BorderLayout.SOUTH);
    glComp = new TestOpenGLComponent();
    af.add(glComp, BorderLayout.CENTER);
    af.setVisible(true);
  }

  public void actionPerformed(ActionEvent ev) {
    Debug.out("TestOpenGL", "Got event: " + ev.getActionCommand());
    if ("Fold Vert".equals(ev.getActionCommand())) {
      glComp.foldVert();
    } else if ("Fold Horiz".equals(ev.getActionCommand())) {
      glComp.foldHoriz();
    }
  }

}
