package org.six11.flatcad.flatlang;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Direction;
import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public abstract class Builtin {

  public static int evalCount = 0; // horrible coding practice...

  public abstract Register eval(List<Register> args);

  public static Map<String, Builtin> getBuiltins(final FlatInterpreter interpreter) {
    Map<String, Builtin> builtins = new HashMap<String, Builtin>();

    // a simple 'print' function
    builtins.put("print", new Builtin() {
	public Register eval(List<Register> args) {
	  StringBuffer buf = new StringBuffer();
	  for (Register r : args) {
	    buf.append(r.toString() + " ");
	  }
	  System.out.println(buf.toString().trim());
	  return Register.getNil();
	}
      });

    // join two or more objects into one list.
    builtins.put("__join", new Builtin() {
	public Register eval(List<Register> args) {
	  return joinRegistersIntoList(args);
	}
      });

    // join two or more objects into one list.
    builtins.put("__split", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  if (args.size() == 3) {
	    ret = splitList(args.get(0), (Register.List) args.get(1), args.get(2));
	  }
	  return ret;
	}
      });

    // join two or more objects into one list.
    builtins.put("__splitAtIndex", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  if (args.size() == 2) {
	    ret = splitListAtIndex(args.get(0), (Register.List) args.get(1));
	  }
	  return ret;
	}
      });

    // a simple 'print' function
    builtins.put("pprint", new Builtin() {
	public Register eval(List<Register> args) {
	  String s = prettyPrint(args);
	  System.out.println(s);
	  return new Register.Str(s);
	}
      });

    builtins.put("__eval", new Builtin() {
	public Register eval(List<Register> args) {
	  evalCount++;
	  for (Register r : args) {
	    try {
	      interpreter.interpret(r.toString(), false, false);
	    } catch (Exception ex) {
	      Debug.out("FlatInterpreter", "failed eval on " + r);	      
	    }
	  }
	  evalCount--;
	  return interpreter.machine.getReturnValue();
	}
      });

    builtins.put("debug", new Builtin() {
	public Register eval(List<Register> args) {
	  for (Register arg : args) {
	    System.out.println(MachineState.stFull(arg));
	  }
	  return Register.getNil();
	}
      });

    builtins.put("pause", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  Register r = args.get(0);
	  try {
	    double val = ((Register.Num) r).v;
	    if (val >= 0d) {
	      long ms = (long) val;
	      Thread.sleep( (long) val);
	    }
	  } catch (Exception ex) {
	    Debug.out("FlatInterpreter", "failed pause() on " + r);
	  }
	  return ret;
	}
      });

    builtins.put("redraw", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  // TODO: are we sure we want to clear here?
	  //	  ret.flagClear = true;
	  ret.flagRedraw = true;
	  return ret;
	};
      });

    builtins.put("clear", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  ret.flagClear = true;
	  return ret;
	};
      });

    builtins.put("animate", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  Register r = args.get(0);
	  try {
	    double val = ((Register.Num) r).v;
	    if (val >= 0d) {
	      long ms = (long) val;
	      ret.flagRedraw = true;
	      ret.flagClear = true;
	      Thread.sleep( (long) val);
	    }
	  } catch (Exception ex) {
	    Debug.out("FlatInterpreter", "failed pause() on " + r);
	  }
	  return ret;
	}
      });

    builtins.put("source", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  Register r = args.get(0);
	  try {
	    if (r instanceof Register.List) {
	      String src = interpreter.unlist((Register.List)r, true);
	      ret = new Register.Str(src);
	    } else if (r instanceof Register.Function) {
	      Register.Function f = (Register.Function) r;
	      Register.List intermediate = interpreter.quoteChildren(f.block, true, null);
	      Register define = new Register.Str("define");
	      Register name = new Register.Str(f.name);
	      List paramList = new ArrayList();
	      paramList.add("(");
	      boolean first = true;
	      for (String s : f.params) {
		if (first) {
		  first = false;
		} else {
		  paramList.add(", ");
		}
		paramList.add(s);
	      }
	      paramList.add(")");
	      Register.List params = interpreter.listFromList(paramList);
	      Register.List funcCode = new Register.List(define);
	      define.next = name;
	      name.next = params;
	      params.next = intermediate;
	      intermediate.next = new Register.Str("done");
	      //	      String src = unlist(funcCode, true);
	      //	      ret = new Register.Str(src);	      
	      ret = funcCode;
	    }
	  } catch (Exception ex) {
	    Debug.out("FlatInterpreter", "failed __source() on " + r);
	  }
	  return ret;
	}
      });


    builtins.put("__flatten", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = flatten(args.get(0));
	  return ret;
	}
      });

    builtins.put("__eq", new Builtin() {
	public Register eval(List<Register> args) {
	  Debug.out("FlatInterpreter", "__eq called");
	  Register ret = new Register.Boolean(false);
	  if (args.size() == 2) {
	    if (args.get(0).equals(args.get(1))) {
	      ret = new Register.Boolean(true);
	    }
	  }
	  Debug.out("FlatInterpreter", "__eq says " + ret);
	  return ret;
	}
      });

    builtins.put("__replaceNode", new Builtin() {
	public Register eval(List<Register> args) {
	  if (args.size() == 3 &&
	      args.get(2) instanceof Register.List) {
	    return modifyList(args.get(0), args.get(1), 
			      (Register.List) args.get(2),
			      false, false);
	  } else {
	    Debug.out("FlatInterpreter", "__replace requires a,b, list");
	    return Register.getNil();
	  }
	}
      });

    builtins.put("__insertNode", new Builtin() {
	public Register eval(List<Register> args) {
	  if (args.size() == 3 &&
	      args.get(2) instanceof Register.List) {
	    return modifyList(args.get(0), args.get(1), 
			      (Register.List) args.get(2),
			      true, false);
	  } else {
	    Debug.out("FlatInterpreter", "__replace requires a,b, list");
	    return Register.getNil();
	  }
	}
      });

    builtins.put("__replaceList", new Builtin() {
	public Register eval(List<Register> args) {
	  if (args.size() == 3 &&
	      args.get(2) instanceof Register.List) {
	    return modifyList(args.get(0), args.get(1), 
			      (Register.List) args.get(2),
			      false, true);
	  } else {
	    Debug.out("FlatInterpreter", "__replace requires a,b, list");
	    return Register.getNil();
	  }
	}
      });

    builtins.put("__insertList", new Builtin() {
	public Register eval(List<Register> args) {
	  if (args.size() == 3 &&
	      args.get(2) instanceof Register.List) {
	    return modifyList(args.get(0), args.get(1), 
			      (Register.List) args.get(2),
			      true, true);
	  } else {
	    Debug.out("FlatInterpreter", "__replace requires a,b, list");
	    return Register.getNil();
	  }
	}
      });

    builtins.put("__cons", new Builtin() {
	public Register eval(List<Register> args) {
// 	  if (args.get(1) instanceof Register.List) {
// 	    return cons(args.get(0), (Register.List) args.get(1));
// 	  } else {
// 	    Debug.out("FlatInterpreter", "__cons: 2nd arg must be a list");
// 	    return Register.getNil();
// 	  }
	  return cons(args.get(0), args.get(1));
	}
      });

    builtins.put("__rest", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  if (args.get(0) instanceof Register.List) {
	    Register.List r = (Register.List) args.get(0);
	    if (r.root == null) {
	      Debug.out("FlatInterpreter", "List already empty.");
	      ret = new Register.List(null);
	    } else {
	      ret = new Register.List(r.root.next);
	    }
	  } else {
	    //Debug.out("FlatInterpreter", "2nd arg must be a list");
	    ret = new Register.List(null);
	  }
	  return ret;
	}
      });

    builtins.put("__first", new Builtin() {
	public Register eval(List<Register> args) {
	  Register ret = Register.getNil();
	  if (args.get(0) instanceof Register.List) {
	    Register.List r = (Register.List) args.get(0);
	    if (r.root == null) {
	      Debug.out("FlatInterpreter", "__first: list empty, I return nil");
	    } else {
	      ret = r.root;
	    }
	  } else {
	    Debug.out("FlatInterpreter", "__first: argument not a list, returning it");
	    return args.get(0);
	  }
	  return ret;
	}
      });

    // Begin a new named part
    builtins.put("__part", new Builtin() {
	public Register eval(List<Register> args) {
	  if (args.size() == 1) {
	    System.out.println("Beginning a new part named " + args.get(0).toString());
	    catchTurtle(Turtle.part(args.get(0).toString()), interpreter);
	  } else {
	    catchTurtle(Turtle.part("part"), interpreter);
	  }
	  return Register.getNil();
	}
      });

    // lift the turtle pen up
    builtins.put("__up", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.up(), interpreter);
	  return Register.getNil();
	}
      });

    // put the turtle pen down
    builtins.put("__down", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.down(), interpreter);
	  return Register.getNil();
	}
      });

    // move the turtle forward
    builtins.put("__forward", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.forward(MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // move the turtle backward
    builtins.put("__backward", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.forward(-1f * MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // turn the turtle left about the z axis
    builtins.put("__left", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.yaw(MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // turn the turtle right about the z axis
    builtins.put("__right", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.yaw(-1f * MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // roll means rotate about the y axis
    builtins.put("__roll", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.roll(MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // pitch means rotate about the x axis
    builtins.put("__pitch", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.pitch(MathUtils.toFloat(args.get(0).toString())),
		      interpreter);
	  return Register.getNil();
	}
      });

    // pitch means rotate about the x axis
    builtins.put("__mark", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.mark(args.get(0).toString()),
		      interpreter);
	  return Register.getNil();
	}
      });

    // square root
    builtins.put("sqrt", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.sqrt(v));
	}
      });    

    // trig
    builtins.put("sin", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.sin(v));
	}
      });    

    // trig
    builtins.put("cos", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.cos(v));
	}
      });    

    // trig
    builtins.put("tan", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.tan(v));
	}
      });    

    // trig
    builtins.put("asin", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.asin(v));
	}
      });    

    // trig
    builtins.put("acos", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.acos(v));
	}
      });    

    // trig
    builtins.put("atan", new Builtin() {
	public Register eval(List<Register> args) {
	  double v = MathUtils.toFloat(args.get(0).toString());
	  return new Register.Num(Math.atan(v));
	}
      });    

    // trig
    builtins.put("pi", new Builtin() {
	public Register eval(List<Register> args) {
	  return new Register.Num(Math.PI);
	}
      });    

    builtins.put("pos", new Builtin() {
	public Register eval(List<Register> args) {
	  return pos(interpreter);
	}
      });

    builtins.put("dir", new Builtin() {
	public Register eval(List<Register> args) {
	  return dir(interpreter);
	}
      });
    
    builtins.put("drawto", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(drawto(interpreter, args),
		      interpreter);
	  return Register.getNil();	  
	}
      });
    
    builtins.put("outputMatrix", new Builtin() {
	public Register eval(List<Register> args) {
	  MathUtils.debugMatrixString("current stack at " + args.get(0), getCurrentTransform(interpreter));
	  return Register.getNil();	  
	}
      });
    
    builtins.put("facedir", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(facedir(interpreter, (Register.Mat) args.get(0)), interpreter);
	  return Register.getNil();	  
	}
      });

    // for debugging
    builtins.put("__showSoup", new Builtin() {
	public Register eval(List<Register> args) {
	  System.out.println(interpreter.soup.bugTurtleLists());
	  return Register.getNil();
	}
      });

    // for debugging
    builtins.put("__showMachine", new Builtin() {
	public Register eval(List<Register> args) {
	  System.out.println(interpreter.machine.toString());
	  return Register.getNil();
	}
      });

    // for debugging
    builtins.put("__showPolygons", new Builtin() {
	public Register eval(List<Register> args) {
	  boolean v = args.size() > 0 && args.get(0).toString().equals("true");
	  interpreter.soup.setShowPolies(v);
	  return Register.getNil();
	}
      });

    // also for debugging
    builtins.put("__jitter", new Builtin() {
	public Register eval(List<Register> args) {
	  interpreter.redrawMe.jitter(MathUtils.toFloat(args.get(0).toString()),
				      MathUtils.toFloat(args.get(1).toString()));
	  return Register.getNil();
	}
      });

    // put turtle at some known location
    builtins.put("__backto", new Builtin() {
	public Register eval(List<Register> args) {
	  doBackto(args.get(0).toString(), interpreter);
	  return Register.getNil();
	}
      });

    // draw a shape (from a shape...done block)
    builtins.put("draw", new Builtin() {
	public Register eval(List<Register> args) {
	  String startPoint = null;
	  if (args.size() > 1) {
	    startPoint = args.get(1).toString();
	  }
	  drawShape(args.get(0).toString(), startPoint, interpreter);
	  return Register.getNil();
	}
      });

    // change the turtle to toggle XOR mode on or off
    builtins.put("turtlexor", new Builtin() {
	public Register eval(List<Register> args) {
	  catchTurtle(Turtle.xor(), interpreter);
	  return Register.getNil();
	}
      });

    // load a file from disk and interpret it
    builtins.put("load", new Builtin() {
	public Register eval(List<Register> args) {
	  interpreter.loadFile(false, args.get(0).toString());
	  return Register.getNil();
	}
      });

    builtins.put("__setPartMode", new Builtin() {
	public Register eval(List<Register> args) {
	  boolean v = ((Register.Boolean)args.get(0)).b;
	  interpreter.soup.setPartMode(v);
	  Debug.out("Builtin", "set part mode to " + v);
	  return args.get(0);
	}
      });
    
    builtins.put("__hpgl", new Builtin() {
	public Register eval(List<Register> args) {
	  try {
	    interpreter.soup.requestHPGL(args.get(0).toString());
	  } catch (Exception ex) {
	    Debug.out("FlatInterpreter", "Some kind of weird exception in hpgl, as follows...");
	    ex.printStackTrace();
	  }
	  return Register.getNil();
	}
      });

    builtins.put("laser", new Builtin() {
	public Register eval(List<Register> args) {
	  interpreter.soup.doLaserMode(args.get(0).toString());
	  Debug.out("Builtin", "did laser mode, saved to " + args.get(0).toString());
	  return Register.getNil();
	}
      });

    builtins.put("sketchup", new Builtin() {
	public Register eval(List<Register> args) {
	  //interpreter.soup.doRubyMode(args.get(0).toString());
	  Debug.out("Builtin", "did sketchup mode, saved to " + args.get(0).toString());
	  return Register.getNil();
	}
      });

    return builtins;
  }

  /**
   * Join all of the objects in the input list into a single return
   * list. This does not modify the input arguments.
   */
  public Register.List joinRegistersIntoList(List<Register> args) {
    Register.List ret = new Register.List(null);
    Register cursor = null;
    for (Register r : args) {
      HeadTail ht = HeadTail.copy(r, true);
      if (ret.root == null) {
	ret.root = ht.head;
      } else {
	cursor.next = ht.head;
      }
      cursor = ht.tail;
    }
    return ret;
  }
  
  /**
   * Given a target object and a list, split the list into two
   * sub-lists and return them in a two-element list. The searched
   * object is the first element in the second list. If the target
   * object is not found, this still returns a tuple, but the second
   * one is empty.
   *
   * splitList("c", ["a", "b", "c", "d"])
   * ==> [["a", "b"], ["c", "d"]
   *
   * splitList("X", ["a", "b", "c", "d"])
   * ==> [["a", "b", "c", "d"], []]
   */
  public Register splitList(Register lookFor, Register.List list,
			    Register throwAway) {
    Register srcCursor = list.root;
    Register destCurs = null;
    Register.List listA = new Register.List(null);
    Register.List listB = new Register.List(null);
    int toss = (int) MathUtils.toFloat(throwAway.toString());

    boolean found = false;
    while (srcCursor != null) {
      if (srcCursor.equals(lookFor) && !found && toss == 0) {
	listB.root = srcCursor.copy();
	destCurs = listB.root;
	found = true;
      } else {
	if (srcCursor.equals(lookFor)) {
	  toss--;
	}
	if (!found && listA.root == null) {
	  listA.root = srcCursor.copy();
	  destCurs = listA.root;
	} else {
	  destCurs.next = srcCursor.copy();
	  destCurs = destCurs.next;
	}
      }
      srcCursor = srcCursor.next;
    }
    listA.next = listB;
    return new Register.List(listA);
  }

  public Register splitListAtIndex(Register idxReg, Register.List list) {
    Register srcCursor = list.root;
    Register destCurs = null;
    Register.List listA = new Register.List(null);
    Register.List listB = new Register.List(null);
    int toss = (int) MathUtils.toFloat(idxReg.toString());      
    while (srcCursor != null && toss > 0) {
      if (listA.root == null) {
	listA.root = srcCursor.copy();
	destCurs = listA.root;
      } else {
	destCurs.next = srcCursor.copy();
	destCurs = destCurs.next;
      }
      toss--;
      srcCursor = srcCursor.next;
    }
    while (srcCursor != null) {
      if (listB.root == null) {
	listB.root = srcCursor.copy();
	destCurs = listB.root;
      } else {
	destCurs.next = srcCursor.copy();
	destCurs = destCurs.next;
      }
      srcCursor = srcCursor.next;
    }
    listA.next = listB;
    return new Register.List(listA);
  }

  public String prettyPrint(List<Register> args) {
    StringBuffer buf = new StringBuffer();
    for (Register r : args) {
      prettyPrint(r, buf, 0, false);
    }
    return buf.toString();
  }
  
  public boolean prettyPrint(Register r, StringBuffer buf, 
			     int level, boolean justFinishedList) {
    boolean ret = false;
    if (buf.length() > 0) {
      buf.append(' ');
    }
    switch (r.type) {
    case (Register.TYPE_FUNCTION):
      //      PrettyPrinter.pprint(buf, ((Register.Function) r).block, 0);
      buf.append(PrettyPrinter.pprint((Register.Function) r));
      break;
    case (Register.TYPE_STRING):
    case (Register.TYPE_NIL):
    case (Register.TYPE_CODE):
      //      buf.append("\n" + FlatDebugger.spaces(level * 4));
      buf.append(r.toString());
      ret = false;
      break;
    case (Register.TYPE_LIST):
      Register.List list = (Register.List) r;
//       if (!onFreshLine(buf)) {
  	buf.append("\n");
//       }
      buf.append(FlatDebugger.spaces(level * 2));
      buf.append("[");
      Register curs = list.root;
      boolean finList = justFinishedList;
      while (curs != null) {
      	finList = prettyPrint(curs, buf, level+1, finList);
      	curs = curs.next;
      }
      if (finList) {
	buf.append("\n" + FlatDebugger.spaces(level * 2));	
	buf.append("]");
      } else {
	buf.append(" ]");
      }
      buf.append(FlatDebugger.spaces(level * 2));
      ret = true;
    }
    return ret;
  }


  public Register flatten(Register in) {
    Register ret = Register.getNil();
    if (in instanceof Register.List) {
      Register.List inList = (Register.List) in;
      Register newCurs = new Register.Str("");
      Register root = newCurs;
      Stack<Register.List> lists = new Stack<Register.List>();
      Register oldCurs = inList.root;
      boolean ok = true;
      while (ok) {
	if (oldCurs == null && lists.size() > 0) {
	  oldCurs = lists.pop().next;
	} else if (oldCurs == null) {
	  ok = false;
	} else if (oldCurs instanceof Register.List) {
	  lists.push((Register.List) oldCurs);
	  oldCurs = ((Register.List) oldCurs).root;
	} else {
	  newCurs.next = oldCurs.copy();
	  newCurs = newCurs.next;
	  oldCurs = oldCurs.next;
	}
      }
      ret = new Register.List(root.next);
    } else {
      ret = in.copy();
    }
    return ret;
  }

  public Register.List modifyList(Register lookFor,
				  Register replaceWith,
				  Register.List list,
				  boolean insertMode,
				  boolean unpackReplaceWith) {
    Register uberNext;
    // special case for first node
    if (lookFor.equals(list.root)) {
      uberNext = insertMode ? list.root : list.root.next;
      HeadTail ht = HeadTail.copy(replaceWith, unpackReplaceWith);
      list.root = ht.head;
      ht.tail.next = uberNext;
    }

    Register curs = list.root;
    Register replacementNode;
    while (curs != null) {
      if (lookFor.equals(curs.next)) {
	HeadTail ht = HeadTail.copy(replaceWith, unpackReplaceWith);
	uberNext = insertMode ? curs.next : curs.next.next;
	curs.next = ht.head;
	ht.tail.next = uberNext;
	curs = ht.tail;
      }
      curs = curs.next;
    }
    
    return list;
  }

  public Register.List cons(Register nubeOrig, Register appendTo) {
    Register nube = nubeOrig.copy();
    Register.List ret = new Register.List(nube);
    if (appendTo instanceof Register.List) {
      nube.next = ((Register.List) appendTo).root;
    } else {
      nube.next = appendTo;
    }
    return ret;
  }

  public void doBackto(String where, FlatInterpreter interpreter) {
    TurtleTree turtles = interpreter.soup.getSourceList(); // get whatever list we're working with atm
    Turtle myrtle = turtles.getCursor().searchBackwards(where);
    if (myrtle != null) {
      turtles.setCursor(myrtle);
    }
  }

  void catchTurtle(Turtle t, FlatInterpreter interpreter) {
    interpreter.soup.addTurtle(t);
  }

  public void drawShape(String shapeName, String optionalStartPoint, FlatInterpreter interpreter) {
    TurtleTree shapeTurtles = interpreter.soup.getShape(shapeName);

    if (optionalStartPoint == null) {
      // throw turtles in order
      Turtle cursor = shapeTurtles.getRoot();
      while (cursor != null) {
	catchTurtle(cursor.copy(), interpreter);
	cursor = cursor.getNextTurtle(0);
      }
    } else {
      Turtle startPoint = shapeTurtles.getRoot().search(optionalStartPoint);
      if (startPoint != null) {
	// throw from startPoint to the end
	Turtle cursor = startPoint;
	while (cursor != null) {
	  catchTurtle(cursor.copy(), interpreter);
	  cursor = cursor.getNextTurtle(0);
	}
	// now throw from the root (inclusive) to startPoint (not inclusive)
	cursor = shapeTurtles.getRoot();
	while (cursor != startPoint) {
	  catchTurtle(cursor.copy(), interpreter);
	  cursor = cursor.getNextTurtle(0);
	}
      }
    }
  }

  static class HeadTail {
    Register head;
    Register tail;
    static HeadTail copy(Register source, boolean unpackIfList) {
      HeadTail ret = new HeadTail();
      if (source instanceof Register.List && unpackIfList) {
	Register.List nube = (Register.List) source.copy();
	ret.head = nube.root;
	ret.tail = null;
	if (ret.head != null) {
	  Register curs = ret.head;
	  while (curs != null) {
	    ret.tail = curs;
	    curs = curs.next;
	  }
	}
      } else {
	ret.head = source.copy();
	ret.tail = ret.head;
      }
      return ret;
    }

  }

  public Register pos(FlatInterpreter interpreter) {
    TurtleTree tree = interpreter.soup.getSourceList();
    tree.calculateGlobalGeometry();
    Turtle turtle = tree.getCursor();
    Vertex where = turtle.getGlobalPoint();
    Register ret = new Register.Vec(where.x(), where.y(), where.z());
    return ret;
  }

  public Register dir(FlatInterpreter interpreter) {
    TurtleTree tree = interpreter.soup.getSourceList();
    tree.calculateGlobalGeometry();
    Turtle turtle = tree.getCursor();
    RealMatrix orientation = turtle.getGlobalTransform();
    Direction u = MathUtils.getU(orientation);
    Direction v = MathUtils.getV(orientation);
    Direction w = MathUtils.getW(orientation);
    Register ret = new Register.Mat(u.x(), u.y(), u.z(),
				    v.x(), v.y(), v.z(),
				    w.x(), w.y(), w.z());
    return ret;    
  }

  public RealMatrix getCurrentTransform(FlatInterpreter interpreter) {
    TurtleTree tree = interpreter.soup.getSourceList();
    tree.calculateGlobalGeometry();
    Turtle turtle = tree.getCursor();
    return turtle.getGlobalTransform();
  }

  public Turtle drawto(FlatInterpreter interpreter, List<Register> args) {
    Register.Vec rv = (Register.Vec) args.get(0);
    double percentDist = 1d;
    if (args.size() == 2) {
      percentDist = ((Register.Num) args.get(1)).v;
    }

    Vertex thereVec = new Vertex(rv.x, rv.y, rv.z);

    RealMatrix there = MathUtils.getTranslationMatrix(thereVec);
    RealMatrix here = getCurrentTransform(interpreter);
    Vertex hereVec = new Vertex(here.getColumn(3));

    Vertex spanningVec = new Vertex(thereVec.x() - hereVec.x(),
				    thereVec.y() - hereVec.y(),
				    thereVec.z() - hereVec.z());

    Vertex desired = new Vertex(hereVec.x() + (spanningVec.x() * percentDist),
				hereVec.y() + (spanningVec.y() * percentDist),
				hereVec.z() + (spanningVec.z() * percentDist));
    // TODO: for the purpose of testing, say we want to only go
    // halfway. how do I construct that matrix?

    // RealMatrix.getData() results in a 2d double array:
    // data[0] = first row: ux, vx, wx, tx
    // ..
    // data[3] = last row: 0, 0, 0, 1

    double[][] thereData = there.getData();
    double[] des = desired.getData();
    thereData[0][3] = des[0];
    thereData[1][3] = des[1];
    thereData[2][3] = des[2];
    thereData[3][3] = des[3];

    there = new RealMatrixImpl(thereData);
    RealMatrix trans = MathUtils.calcUnknownTransformRight(there, here);

    return new Turtle(trans);
  }

  public Turtle facedir(FlatInterpreter interpreter, Register.Mat dir) {
    RealMatrix here = getCurrentTransform(interpreter);
    Vertex loc = new Vertex(here.getColumn(3));
    RealMatrix there = new RealMatrixImpl(dir.getData());

    // so it appears that the answer is to solve for X:
    // there = here * X
    RealMatrix trans = MathUtils.calcUnknownTransformRight(there, here);
    
    // Now, trans has position data in it's last column, but it should
    // be {0,0,0,1} so we don't end up moving.
    double[][] transData = trans.getData();
    transData[0][3] = 0.0;
    transData[1][3] = 0.0;
    transData[2][3] = 0.0;
    transData[3][3] = 1.0;
    trans = new RealMatrixImpl(transData);
    
    //    Debug.out("Builtin", "facedir pushing following transform:\n" + trans);

    return new Turtle(trans);
  }
}
