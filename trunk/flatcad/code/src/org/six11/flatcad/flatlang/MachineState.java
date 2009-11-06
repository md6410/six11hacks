package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import org.six11.flatcad.geom.Vertex;
import org.apache.commons.math.linear.RealMatrix;

/**
 * 
 **/
public class MachineState {
  protected static boolean debugging = false;
  
  protected int quoteCount;
  protected boolean safe;
  protected Stack<Map<Object, Register>> variableStack;
  protected Stack<Register> memberContext;
  protected Map<Object, Register> builtinVars;

  public MachineState() {
    reset();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer("\n");
    int count = 1;
    for (Map<Object, Register> m : variableStack) {
      buf.append("Context " + count + ": " + m.size() + " mappings.\n");
      for (Map.Entry<Object, Register> entry : m.entrySet()) {
	buf.append("   " + entry.getKey() + " = " + entry.getValue() + "\n");
      }
      count++;
    }
    return buf.toString();
  }
  
  Stack<Map<Object, Register>> getVariableStack() {
    return variableStack;
  }

  public final void reset() {
    quoteCount = 0;
    safe = false;
    variableStack = new Stack<Map<Object, Register>>();
    memberContext = new Stack<Register>();
    if (builtinVars != null) {
      variableStack.push(builtinVars);
    }
    pushContext("Resetting");
  }

  public void rememberBuiltinVars() {
    if (builtinVars == null) {
      builtinVars = variableStack.peek();
    }
  }

  public boolean popQuote() {
    quoteCount = Math.max(quoteCount-1, 0);
    return isQuoting();
  }

  public void pushQuote() {
    quoteCount++;
  }

  public boolean isQuoting() {
    return quoteCount > 0;
  }

  public void pushContext(String reason) {
    Map context = new HashMap<Object, Register>();
    variableStack.push(context);
    if (debugging) {
      Debug.out("MachineState-stack", "pushContext: " + reason + ", now in level " + variableStack.size());
    }
    
  }

  public void popContext() {
    variableStack.pop();
    if (debugging) {
      Debug.out("MachineState-stack", "popContext, now in level " + variableStack.size());
    }
  }

  public void setReturnValue(Register value) {
    setValue("__return__", value);
  }

  public Register getReturnValue() {
    return getValue("__return__");
  }

  public Register getValue(Object name) {
    bug("Looking for " + st(name));
    Register ret = Register.getNil();
    boolean found = false;
    if (memberContext.size() > 0) {
      bug("\tcase A");
      Register parentMember = memberContext.peek();
      ret = parentMember.getMember(name);
      found = !ret.isNil();
    } 
    
    if (!found && isQuoting()) {
      bug("\tcase B");
      ret = new Register.Str(name.toString()); // makes me nervous
    } else if (!found) {
      bug("\tcase C");
      Map<Object, Register> m = findNewestMapWithName(name);
      if (m != null) {
	ret = m.get(name);
      }
    }
    bug("getValue(" + st(name) + ") ==> " + stFull(ret));
    return ret;
  }

  private Map<Object, Register> findNewestMapWithName(Object name) {
    Map<Object, Register> ret = null;

    for (int i=variableStack.size() - 1; i >= 0; i--) {
      bug("\tdepth: " + i + " has objects: " + Debug.num(variableStack.get(i).keySet(), " "));
      if (variableStack.get(i).containsKey(name)) {
	ret = variableStack.get(i);
	bug("\t!! Found object '" + name + "' at stack level " + i);
	if (safe && 
	    ret.get(name).type != Register.TYPE_FUNCTION && 
	    (i < variableStack.size() - 1)) {
	  bug("\trestricting access to non-function definition for '" + name + "'.");
	  ret = null;
	}
	break;
      } else {
	bug("\tDid not find object '" + name + "' at stack level " + i);
      }
    }
    return ret;
  }

  private void showMemberContext(String how) {
    StringBuffer buf = new StringBuffer();
    for (Register r : memberContext) {
      buf.append(Register.debug(r) + " -- ");
    }
    bug(how + ": (level " + memberContext.size() + "): " + buf.toString());
  }

  public void pushMemberContext(Register r) {
    memberContext.push(r);
    showMemberContext("pushMemberContext");
  }

  public void popMemberContext() {
    memberContext.pop();
    showMemberContext("popMemberContext");
  }

  public void addParamToContext(Object name) {
    variableStack.peek().put(name, Register.getNil());
  }

  public void setValue(Register parent, Object name, Register value) {
    parent.setMember(name, value);
    bug("setValue(parent=" + st(parent) + ", " + st(name) + ", " + stFull(value) + ")");
  }

  /**
   * 'safe mode' means the machine is restricted to use only locally
   * defined variables, though globally defined functions are still
   * ok.
   */
  public void setSafeMode(boolean s) {
    bug("setting safe mode: " + s);
    safe = s;
  }

  public void setValue(Object name, Register value) {

    if (value.type == Register.TYPE_FUNCTION) {
      // functions always go in the top level. This is a hack to make
      // it so that functions that are defined inside an 'eval' call
      // end up visible. Functions *should* be put into the map that
      // is associated with the calling context.
      variableStack.get(0).put(name, value);
    } else if (memberContext.size() > 0) {
      memberContext.peek().setMember(name, value);
      bug("setValue(" + st(name) + ", " + st(value) + ") in member " +
	  Register.debug(memberContext.peek()) + ", AKA " + st(memberContext.peek()));
    } else {
      Map m = findNewestMapWithName(name);
      if (m == null) {
	// just use current context
	variableStack.peek().put(name, value);
 	bug("setValue(" + st(name) + ", " + st(value) + ") new variable");
      } else {
	m.put(name, value);
 	bug("setValue(" + st(name) + ", " + st(value) + ") old variable");
      }
    }
  }

  public void bug(String what) {
    if (debugging) {
      Debug.out("MachineState", what);
    }
  }

  public static String st(Object o) {
    String n = o.getClass().getName();
    int ending = Math.max(0, n.lastIndexOf(".")) + 1;
    String ret = null;
    if (o instanceof Register.List) {
      ret = ((Register.List) o).toDebugString() + "(" + o.hashCode() + "/" + n.substring(ending) + ")"; 
    } else {
      ret = o.toString() + "(" + o.hashCode() + "/" + n.substring(ending) + ")"; 
    }
    return ret;
  }

  public static String stFull(Object o) {
    String ret = st(o);
    if (o instanceof Register) {
      Register ro = (Register) o;
      ret += "_members={" + Register.debugMembers(ro) + "}";
      if (ro.getReferentID() != null) {
	ret += "_ref={id:" + ro.getReferentID() + ",parent:" + st(ro.getReferentParent()) + "}";
      }
      if (ro.next != null) {
	ret += "--->" + st(ro.next);
      }
    }
    return ret;
  }

  /**
   * I don't know how to get ANLTR to not include the quote chars, so
   * I will just strip them off using this little function.
   */
  public static String stripQuotes(String orig) {
    String ret = orig;
    if (ret.equals("\"\"")) {
      ret = "";
    } else {
      if (ret.length() > 1 && ret.startsWith("\"")) {
	ret = ret.substring(1, ret.length());
      }
      if (ret.length() > 1 && ret.endsWith("\"")) {
	ret = ret.substring(0, ret.length() - 1);
      }
    }
      return ret;
  }

}
