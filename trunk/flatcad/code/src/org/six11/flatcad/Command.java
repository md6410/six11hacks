package org.six11.flatcad;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public abstract class Command {
  
  protected Output output;
  protected List<String> names;

  public Command(Output output, String... names) {
    this.output = output;
    this.names = new ArrayList<String>();
    for (String name : names) {
      this.names.add(name);
    }
    if (this.output == null) {
      throw new RuntimeException("Commands require an Output object.");
    }
    if (this.names.size() == 0) {
      throw new RuntimeException("Commands need a name, buddy");
    }
  }

  public List<String> getNames() {
    return names;
  }

  /**
   * Gives you a prefix for help text. This will look something like
   * the following:
   *
   * <pre>
   * "make (create, build): "
   * "cut (trim): "
   * "fold: "
   * </pre>
   */
  public final String getHelpPrefix() {
    StringBuffer ret = new StringBuffer(names.get(0));
    if (names.size() > 1) {
      ret.append("(");
      boolean comma = false;
      for (int i=1; i < names.size(); i++) {
	if (comma) {
	  ret.append(", ");
	}
	comma = true;
	ret.append(names.get(i));
      }
      ret.append(")");
    }
    ret.append(": ");
    return ret.toString();
  }

  public void getSimpleHelp() {
    output.addOutput(getHelpPrefix() + getSimpleHelpString());
  }

  public abstract String getSimpleHelpString();

  public void getFullHelp() {
    output.addOutput(getHelpPrefix() + getFullHelpString());
  }

  public abstract String getFullHelpString();

  /**
   * When your subclass overrides this, it receives all the arguments
   * already chopped up. Note that the first argument is NOT the
   * command name. So if I type "put that there", my args list will
   * be [that, there].
   */
  public abstract void execute(List<String> args);

  public String getMainName() {
    return names.get(0);
  }

}
