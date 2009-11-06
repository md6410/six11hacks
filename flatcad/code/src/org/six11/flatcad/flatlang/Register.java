package org.six11.flatcad.flatlang;

import org.six11.flatcad.geom.MathUtils;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.six11.util.Debug;
import org.antlr.runtime.tree.*;

/**
 * A register is a place for a value to go. If it is part of a list,
 * the 'nextRegister' will point to something. (Lists end when the
 * last element points to Nil or null.
 **/
public abstract class Register {
  
  public final static int TYPE_STRING = 0;
  public final static int TYPE_FUNCTION = 2;
  public final static int TYPE_CODE = 3;
  public final static int TYPE_LIST = 4;
  public final static int TYPE_NIL = 5;
  public final static int TYPE_BOOLEAN = 6;
  public final static int TYPE_NUMBER = 7;
  private final static int TYPE_OBJECT = 8; // private because nobody outside of this file should see
  public final static int TYPE_OBJECT_REF = 9;
  public final static int TYPE_VEC = 10;
  public final static int TYPE_MATRIX = 11;
  
  private static final Nil NIL = new Nil();

  public final static Register.Nil getNil() { 
    return NIL.copy();
  }

  public boolean flagRedraw;
  public boolean flagClear;

  public final int type;
  public Register next;
  private Map <Object, Register> memberMap;
  private String referentID;
  private Register referentParent;

  public Register(int type) {
    this.type = type;
  }

  public Map<Object, Register> getMemberMap() {
    return memberMap;
  }

  public String getReferentID() {
    return referentID;
  }

  public Register getReferentParent() {
    return referentParent;
  }

  public void setReferent(String id, Register parent) {
    referentID = id;
    referentParent = parent;
  }

  public void clearReferent() {
    setReferent(null, null);
  }

  public boolean isNil() {
    return type == TYPE_NIL;
  }
  
  public Register getMember(Object name) {
    Register ret = Register.NIL;
    if (memberMap != null && memberMap.containsKey(name)) {
      ret = memberMap.get(name);
    }
    //    System.out.println("Value of " + this + "." + name + " = " + ret);
    return ret;
  }

  public void setMember(Object name, Register reg) {
    if (memberMap == null) {
      memberMap = new HashMap<Object, Register>();
    }
    memberMap.put(name, reg);
  }

  public static String debugFully(Register r, int level) {
    String spaces = FlatDebugger.spaces(level * 2);
    StringBuffer buf = new StringBuffer(spaces);
    if (r instanceof ObjRef) {
      buf.append("<obj ref> ");
      // r = ((ObjRef)r).obj;
    }
    buf.append(debug(r));
    if (r.memberMap == null) {
      buf.append(" (no member map) ");
    } else {
      for (Map.Entry<Object, Register> en : r.memberMap.entrySet()) {
	buf.append("\n" + spaces + " -- " + en.getKey() + " = " + en.getValue() + "\n");
      }
    }

    buf.append(" referentID = " + r.referentID + " ");
    buf.append(" referentParent = " + r.referentParent + " ");

    return buf.toString();
  }

  public static String debugMembers(Register r) {
    StringBuffer buf = new StringBuffer();
    if (r instanceof ObjRef) {
      r = ((ObjRef)r).obj;
    }
    if (r.memberMap != null) {
      for (Map.Entry<Object, Register> en : r.memberMap.entrySet()) {
	buf.append("{" + en.getKey() + " = " + en.getValue() + "} ");
      }
    }
    return buf.toString();
  }

  public static String debug(Register r) {
    StringBuffer buf = new StringBuffer();
    Register curs = r;
    int count = 0;
    while (curs != null) {
      count++;
      if (count > 1) buf.append("\n");
      buf.append("{" + curs + "} (" + typeStr(curs) + ")");
      curs = curs.next;
    }
    if (count > 1) buf.append(count + " items total.\n");
    return buf.toString();
  }

  public static String typeStr(Register r) {
    String ret = "Unknown";
    switch (r.type) {
    case TYPE_STRING: ret = "STR"; break;
    case TYPE_FUNCTION: ret = "FUN"; break;
    case TYPE_CODE: ret = "CDE"; break; 
    case TYPE_LIST: ret = "LST"; break;
    case TYPE_NIL: ret = "NIL"; break;
    case TYPE_BOOLEAN: ret = "BOOLEAN"; break;
    case TYPE_NUMBER: ret = "NUMBER"; break;
    case TYPE_OBJECT: ret = "OBJ"; break;
    case TYPE_OBJECT_REF: ret = "REF"; break;
    case TYPE_VEC: ret = "VEC"; break;
    case TYPE_MATRIX: ret = "MAT"; break;
    default: 
      Debug.out("Register", "Warning: unknown type in typeStr");
    }
    return ret;
  }

  public abstract Register copy();

  public Register copyMembers(Register in) {
    if (memberMap != null) {
      in.memberMap = (HashMap) ((HashMap)memberMap).clone();
    }
    return in;
  }

  public boolean equals(Object other) {
    boolean ret = (other instanceof Register &&
		   toString().equals(other.toString()));
    return ret;
  }

  public String toString() {
    return "";
  }

  public String getMemberMapString() {
     if (memberMap == null) {
       return "";
     }
     StringBuffer buf = new StringBuffer();
     for (Map.Entry en : memberMap.entrySet()) {
       buf.append(en.getKey() + " = " + en.getValue() + "\n");
     }
     return buf.toString();
  }

  public void setNext(Register n) {
    if (next != null) {
      Debug.out("Register", "Warning: clobbering next list item pointer. This is probably a bug.");
      (new RuntimeException("Here I am")).printStackTrace();
    }
    next = n;
  }

  public void reset() {
    next = null;
    memberMap = null;
    referentID = null;
    referentParent = null;
  }

  public static class Nil extends Register {
    private Nil() { super(TYPE_NIL); }
    public String toString() { return "nil"; }
    public Nil copy() { return new Nil(); }
  }

  public static class Str extends Register {
    String v;
    public Str(String v) { super(TYPE_STRING); this.v = v; }
    public String toString() { return super.toString() + v; }
    public Str copy() { return (Str) copyMembers(new Str(v)); }
  }

  public static class Boolean extends Register {
    boolean b;
    public Boolean(boolean bIn) { super(TYPE_BOOLEAN); b = bIn; }
    public String toString() { return super.toString() + b; }
    public Boolean copy() { return (Boolean) copyMembers(new Boolean(b)); }
  }

  public static class Num extends Register {
    double v;
    public Num(double vIn) { super(TYPE_NUMBER); v = vIn; }
    public Num(String asStr) { 
      super(TYPE_NUMBER); 
      v = MathUtils.toFloat(asStr);
    }
    public String toString() { return super.toString() + v; }
    public Num copy() { return (Num) copyMembers(new Num(v)); }
  }

  public static class List extends Register {
    Register root;
    public List(Register root) { super(TYPE_LIST); this.root = root; }

    /**
     * Overridden to take care of special values such as 'n', the
     * length of the list.
     */
    public Register getMember(Object name) {
      Register ret;
      if (name.equals("n")) {
	ret = calculateLength();
      } else {
	ret = super.getMember(name);
      }
      return ret;
    }

    public Register calculateLength() {
      int count = 0;
      Register curs = root;
      while(curs != null) {
	count++;
	curs = curs.next;
      }
      return new Register.Num(count);
    }

    public List copy() {
      // make a copy of this List. Retain a reference to the top, but
      // operate on a cursor that jumps to 'next' until you run out of
      // road.
      List ret = null;
      
      // The special case is the empty list.
      if (root == null) {
	ret = new List(null);
      } else {
	// root is not null.
	ret = new List(root.copy());
	Register srcCurs = root;
	Register destCurs = ret.root;
	while(srcCurs.next != null) {
	  destCurs.next = srcCurs.next.copy();
	  destCurs = destCurs.next;
	  srcCurs = srcCurs.next;
	}
      }
      return (List) copyMembers(ret);
    }

    public String toDebugString() {
      return toString(true);
    }

    public String toString() {
      return toString(false);
    }

    public String toString(boolean complete) {
      StringBuffer buf = new StringBuffer("[");
      Register cursor = root;
      boolean first = true;
      while(cursor != null) {
	if (first) {
	  if (complete) {
	    buf.append(MachineState.st(cursor));
	  } else {
	    buf.append(cursor);
	  }
	  first = false;
	} else {
	  if (complete) {
	    buf.append(", " + MachineState.st(cursor));
	  } else {
	    buf.append(", " + cursor);
	  }
	}
	cursor = cursor.next;
      }
      buf.append("]");      
      return super.toString() + buf.toString();
    }

    public String trace() {
      StringBuffer buf = new StringBuffer();
      Register curs = root;
      if (curs != null) {
	buf.append(curs.hashCode() + " -> ");
	while (curs.next != null) { 
	  curs = curs.next;
	  buf.append(curs.hashCode() + " -> ");
	}
      }
      buf.append("EOL");
      return buf.toString();
    }
  }

  public static class Function extends Register {
    
    public String name;
    public java.util.List<String> params;
    public Tree block;
    public boolean isSafe;

    private Function(String n, java.util.List<String> p, Tree b, boolean isSafe) {
      super(TYPE_FUNCTION);
      this.name = n;
      this.params = p;
      this.block = b;
      this.isSafe = isSafe;
    }
    
    public Function(Tree t) {
      super(TYPE_FUNCTION);

      // child 0: an ID, the name of the function
      // child 1: a param list
      // child 2: (optional) a block of code
      // child 2 or 3: (optional) the 'safe' literal modifier
      
      name = t.getChild(0).getText();
      
      params = new ArrayList<String>();
      for (int i=0; i < t.getChild(1).getChildCount(); i++) {
	params.add(t.getChild(1).getChild(i).getText());
      }
      
      if (t.getChildCount() > 2) {	
	block = t.getChild(2);
      }

      if (t.getChildCount() > 3) {
	isSafe = true;
      }
    }

    public String toString() {
      StringBuffer pList = new StringBuffer();
      boolean first = true;
      for (String s : params) {
	if (first) {
	  first = false;
	  pList.append(s);
	} else {
	  pList.append(", " + s);
	}
      }
      return name + "(" + pList + ")";
    }
    
    public Function copy() {
      return (Function) copyMembers(new Function(name, params, block, isSafe));
    }
  }

  public static class ObjRef extends Register {
    public Obj obj;

    public ObjRef() {
      super(TYPE_OBJECT_REF);
      obj = new Obj();
    }

    private ObjRef(Obj obj) {
      super(TYPE_OBJECT_REF);
      this.obj = obj;
    }

    public String toString() {
      return obj.toString();
    }

    public ObjRef copy() {
      return new ObjRef(obj);
    }

    public Register getMember(Object name) {
      return obj.getMember(name);
    }

    public void setMember(Object name, Register reg) {
      obj.setMember(name, reg);
    }

    public Map<Object, Register> getMemberMap() {
      return obj.getMemberMap();
    }

  }

  public static class Obj extends Register {
    public Obj() { 
      super(TYPE_OBJECT); 
    }
    public String toString() { return getMember("__str__").toString(); }
    public Obj copy() { 
      return this; // reference only
    }
  }

  public static class Vec extends Register {
    double x, y, z;

    public Vec(double x, double y, double z) {
      super(TYPE_VEC);
      this.x = x;
      this.y = y;
      this.z = z;
      setMember("x", new Register.Num(x));
      setMember("y", new Register.Num(y));
      setMember("z", new Register.Num(z));
    }

    public String toString() { 
      return "{" + 
	x + ", " +
	y + ", " +
	z + "}";
    }

    public double[] getData() {
      return new double[] { x, y, z, 0 };
    }

    public Vec add(Vec other) {
      return new Vec(x + other.x,
		     y + other.y,
		     z + other.z);
    }

    public Vec flip() {
      return new Vec(-x, -y, -z);
    }

    public Vec mult(double amt) {
      return new Vec(x * amt,
		     y * amt,
		     z * amt);
    }

    public Vec copy() {
      return new Vec(x,y,z);
    }
  }
  
  public static class Mat extends Register {
    Vec u, v, w, t;
    public Mat(double ux, double vx, double wx,
	       double uy, double vy, double wy,
	       double uz, double vz, double wz) {
      super(TYPE_MATRIX); 
      this.u = new Vec(ux, uy, uz);
      this.v = new Vec(vx, vy, vz);
      this.w = new Vec(wx, wy, wz);
      this.t = new Vec(0, 0, 0);
    }

    public String toString() {
      return 
	Debug.num(u.x) + " " + Debug.num(v.x) + " " + Debug.num(w.x) + " " + Debug.num(0) + "\n" +
	Debug.num(u.y) + " " + Debug.num(v.y) + " " + Debug.num(w.y) + " " + Debug.num(0) + "\n" +
	Debug.num(u.z) + " " + Debug.num(v.z) + " " + Debug.num(w.z) + " " + Debug.num(0) + "\n" +
	Debug.num(0)   + " " + Debug.num(0)   + " " + Debug.num(0)   + " " + Debug.num(1);
    }

    public Mat copy() {
      return new Mat(u.x, u.y, u.z,
		     v.x, v.y, v.z,
		     w.x, w.y, w.z);
    }

    public double[][] getData() {
      double [][] matdata = new double[][] {
	u.getData(),
	v.getData(),
	w.getData(),
	{ 0, 0, 0, 1}
      };
      return matdata;
    }
  }
}
