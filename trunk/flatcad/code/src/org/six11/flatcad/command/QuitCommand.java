package org.six11.flatcad;

import java.util.List;

/**
 * 
 **/
public class QuitCommand extends Command {
  
  public QuitCommand(Output out, String... names) {
    super(out, names);
  }

  public String getSimpleHelpString() {
    return "Exit FlatCAD";
  }

  public String getFullHelpString() {
    return getSimpleHelpString();
  }

  public void execute(List<String> args) {
    System.exit(0);
  }
}
