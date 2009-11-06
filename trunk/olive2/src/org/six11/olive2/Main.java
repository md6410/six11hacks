package org.six11.olive2;

import java.io.File;
import org.six11.util.Debug;

/**
 * Used to run Olive2.
 **/
public abstract class Main {

  public static void main(final String[] args) {
    OliveUI ui = new OliveUI();
    ui.go();
    
    if (args.length > 0) {
      Debug.out("Main", "Loading file: " + args[0]);
      ui.load(new File(args[0]));
    }
  }

}
