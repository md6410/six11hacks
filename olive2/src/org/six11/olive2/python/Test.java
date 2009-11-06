package org.six11.olive2.python;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.Debug;
import org.python.util.PythonInterpreter; 
import org.python.core.*; 

public class Test { 

  JTextArea text;
  PythonInterpreter interp;

  public static void main(String []args) throws PyException { 
    new Test().run();
  }

  public void interpret() {
    try {
      Document doc = text.getDocument();
      String input = doc.getText(0, doc.getLength());
      interp.exec(input);
      Debug.out(": : : : ", "");
    } 
    catch (BadLocationException ignore) { }
    catch (PyException ex) {
      Debug.out("? : : : ", "      (execution had an error of type " + ex.getClass() + ")");
      PyTraceback tb = ex.traceback;
      interp.set("tb", tb);
      Debug.out("? : : : ", "traceback is now in the variable 'tb' for you to inspect.");
    }
  }

  public void run() {
    interp = new PythonInterpreter();
    ApplicationFrame aFrame = new ApplicationFrame("Jython Interpreter", 400, 400);
    text = new JTextArea();
    text.addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent ev) {
	  // refer to FlatCAD's TextEntry.java for hints
	  if (ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_ENTER) {
	    interpret();
	  }
	}
      });

    JScrollPane scroller = new JScrollPane(text);
    aFrame.add(scroller);
    aFrame.setVisible(true);

      
//     System.out.println("Hello, brave new world");
//     interp.exec("import sys");
//     interp.exec("print sys");
    
//     interp.set("a", new PyInteger(42));
//     interp.exec("print a");
//     interp.exec("x = 2+2");
//     PyObject x = interp.get("x");
    
//     System.out.println("x: "+x);
//     System.out.println("Goodbye, cruel world");
  }
}
