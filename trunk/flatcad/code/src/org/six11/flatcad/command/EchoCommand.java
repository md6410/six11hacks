package org.six11.flatcad;

import java.util.List;

/**
 * 
 **/
public class EchoCommand extends Command {
  
  public EchoCommand(Output out, String... names) {
    super(out, names);
  }

  public String getSimpleHelpString() {
    return "Repeats whatever you say";
  }

  public String getFullHelpString() {
    return getSimpleHelpString();
  }

  public void execute(List<String> args) {
    StringBuffer buf = new StringBuffer();
    boolean space = false;
    for (String s : args) {
      if (space) {
	buf.append(" ");
      }
      space = true;
      buf.append(s);
    }
    output.addOutput(buf.toString());
  }
}
