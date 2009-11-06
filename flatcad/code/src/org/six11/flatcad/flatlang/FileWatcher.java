package org.six11.flatcad.flatlang;

import java.io.File;
import org.six11.util.io.FileUtil;
import org.six11.util.gui.ApplicationFrame;
import org.six11.flatcad.gl.OpenGLDisplay;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;

/**
 * 
 **/
public class FileWatcher {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.print("watch [--graphics] filename\n");
      System.exit(0);
    }

    boolean graphics = false;
    OpenGLDisplay display = null;
    List<String> rewrittenArgs = new ArrayList<String>();
    for (String arg : args) {
      if (arg.equals("--graphics")) {
	graphics = true;
      } else {
	rewrittenArgs.add(arg);
      }
    }

    args = new String[rewrittenArgs.size()];
    for (int i=0; i < rewrittenArgs.size(); i++) {
      args[i] = rewrittenArgs.get(i);
    }
    
    FlatInterpreter interp = new FlatInterpreter();
    spitTime();
    String fileName = interp.useArguments(args);
    if (graphics) {
      display = new OpenGLDisplay();
      display.setPreferredSize(new Dimension(250, 250));
      display.setModel(interp.getSoup());
      interp.setComponent(display);
      ApplicationFrame af = new ApplicationFrame("Watching " + fileName);
      af.add(display);
      af.setVisible(true);
    }
    
    int errorCount = 0;
    long modTime = 0L;
    while (true) {
      try {
	File file = new File(fileName);
	if (modTime == 0) {
	  modTime = file.lastModified();
	}
	if (modTime < file.lastModified()) {
	  modTime = file.lastModified();
	  spitTime();
	  String program = FileUtil.loadStringFromFile(file);
	  interp.handleUserInput(program, false);
	  display.repaint();
	}
	try {
	  Thread.sleep(200);
	} catch (InterruptedException ex) {
	  
	}
	errorCount = 0;
      } catch (Exception ex) {
	errorCount++;
	if (errorCount > 10) {
	  System.out.println("Maximum error count reached.");
	  System.exit(0);
	}
      }
    }
  }

  static void spitTime() {
    System.out.println("------------[" + new Date() + "]");
  }
}
