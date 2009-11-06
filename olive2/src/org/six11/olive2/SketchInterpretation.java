package org.six11.olive2;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.data.Lists;
import org.six11.util.Debug;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This represents a single interpretation of PART OF the user's
 * sketch. Say the user has drawn a desert island scene. One
 * interpretation may be concerned with the circle (which is the sun),
 * another interpretation with the repetitive wavy structure (which is
 * the top of the water). A third (and default) interpretation
 * involves all the ink the user has applied and does no
 * recognition. Using these three interpretations, there is some
 * overlap: the first and third interpretations share the ink for the
 * sun, and the second and third interpretations share ink for the
 * water. The remaining ink is only accounted for by the third
 * interpretation.
 *
 * A fourth interpretation may be derived from these first three
 * interpretations: it may defer to the specialized interpretations
 * for the ink that is "double-booked"; it will use the generic
 * interpretation for the rest.
 *
 * Essentially, this defines some kind of structured response to the
 * user's drawing such as "this sequence is a straight line", or
 * "these two straight lines are parallel".
 *
 * For example, say we have a LineSketchAnalyzer that looks for
 * straight line segments. If we are given some raw input sequence, we
 * will look to see if the entire thing can plausibly be interpreted
 * as a straight line. If so, our interpretation encodes "straight
 * line" and refers to the input that was used in making that
 * decision, as well as some confidence value between zero and one.
 *
 * Now, say that LineSketchAnalyzer has identified two unique straight
 * lines. Say we have a second analyzer called ParallelLineAnalizer
 * that accepts straight line interpretations as input and reports if
 * the two lines can plausibly be interpreted as parallel, and if so,
 * how confident we are.
 **/
public class SketchInterpretation {

  static Map<Class, Integer> instanceCounts = new HashMap<Class, Integer>();
  
  final void assignDebuggingName() {
    if (!instanceCounts.containsKey(this.getClass())) {
      instanceCounts.put(this.getClass(), 1);
    }
    int count = instanceCounts.get(this.getClass());
    instanceCounts.put(this.getClass(), count + 1);
    debuggingName = name + "-" + count;
  }

  /**
   * exactly one of these should be null. For direct geometric
   * interpretations of the user data, use primitiveData. For
   * interpretations that derive from other interpretations, use
   * parents.
   */
  protected Sequence primitiveData = null;

  /**
   * If this interpretation is integral--meaning that it is based on
   * other interpretations--the 'parents' list points to those
   * interpretations that were used in making this
   * interpretation. Also see doc for primitiveData.
   *
   * This can be managed with the static 'relate' function.
   */
  protected List<SketchInterpretation> parents = null;

  /**
   * Like 'parents', the 'children' list helps maintain relations
   * between various relations. This refers to all the interpretations
   * that are based on this interpretation.
   *
   * This can be managed with the static 'relate' function.
   */
  protected List<SketchInterpretation> children = null;

  /**
   * The kind of interpretation, such as "line", "parallel lines", or
   * "uninterpreted ink".
   */
  protected String name = null;

  /**
   * This will be the 'name' followed by a number. The number begins
   * at 1 and is incremented every time a SketchInterpretation of a
   * given class is instantiated.
   */
  protected String debuggingName = null;

  /**
   * This is how sure this interpreter is of it's own input. This is
   * completely devoid of any other contextual information.
   */
  protected double confidence;

  protected Map<Pt, Double> pointConfidence;

  protected Map<String, List<Sequence>> rectifiedGeometry;

  /**
   * Make a new SketchInterpretation with an empty list of integral
   * interpretations.
   */
  protected SketchInterpretation(String name) {
    this.parents = new ArrayList<SketchInterpretation>();
    this.children = new ArrayList<SketchInterpretation>();
    this.name = name;
    pointConfidence = new HashMap<Pt, Double>();
    rectifiedGeometry = new HashMap<String, List<Sequence>>();
    assignDebuggingName();
  }
  
  /**
   * Create a new primitive interpretation named "uninterpreted ink".
   */
  public SketchInterpretation(Sequence rawData) {
    this(rawData, "uninterpreted ink");
  }

  /**
   * Create a new primitive interpretation with the given name.
   */
  public SketchInterpretation(Sequence rawData, String name) {
    this.primitiveData = rawData;
    this.name = name;
    assignDebuggingName();
  }

  /**
   * Gives you the name of this interpretation.
   */
  public String getName() {
    return name;
  }

  public List<Sequence> getRectifiedGeometry(String rectType) {
    if (!rectifiedGeometry.containsKey(rectType)) {
      rectifiedGeometry.put(rectType, new ArrayList<Sequence>());
    }
    return rectifiedGeometry.get(rectType);
  }

  /**
   * Returns the name of the interpretation along with a number that
   * uniquely identifies it within the set of like interpretations.
   */
  public String getDebuggingName() {
    return debuggingName;
  }

  /**
   * A nice string for use in debugging.
   */
  public String toString() {
    return getDebuggingName();
  }

  /**
   * Tells you if this interpretation is based solely on the direct
   * user input and not some prerequisite interpretations. If this
   * returns true, then the getPrimitiveData() will return something
   * useful.
   */
  public boolean isPrimitive() {
    return primitiveData != null;
  }

  /**
   * Gives you the primitive data for this interpretation (make sure
   * to check with isPrimitive() first).
   */
  public Sequence getPrimitiveData() {
    return primitiveData;
  }

  /**
   * Tells you if this interpretation is based on other
   * interpretatations and NOT directly on the user's raw data. For
   * example, the interpretation "line" may be primitive, but
   * "parallel lines" is integral because it requires two line
   * interpretations. If this interpretation is "parallel lines" then
   * the integral data will be two or more "line" interpretations.
   *
   * If this returns true then getIntegralData() will return something
   * useful.
   */
  public boolean isIntegral() {
    return parents != null;
  }

  /**
   * Gives you the integral data for this interpretation (check with
   * isIntegral() first).
   */
  public List<SketchInterpretation> getIntegralData() {
    return parents;
  }

  public List<SketchInterpretation> getChildren() {
    return children;
  }

  public List<SketchInterpretation> getChildren(String... kindsArray) {
    List<String> kinds = new ArrayList<String>();
    for (String s : kindsArray) {
      kinds.add(s);
    }
    List<SketchInterpretation> ret = new ArrayList<SketchInterpretation>();
    for (SketchInterpretation si : children) {
      if (kinds.contains(si.getName()) && !ret.contains(si)) {
	ret.add(si);
      }
    }
    return ret;
  }

  /**
   * Gives you the confidence for this particular interpretation.
   */
  public double getConfidence() {
    return confidence;
  }

  public static List<SketchInterpretation> getInterpList(Pt pt) {
    if (!pt.hasAttribute("interpretation")) {
      pt.setList("interpretation", new ArrayList<SketchInterpretation>());
    }
    List<SketchInterpretation> ret = SketchBook.getAllInterpretations(pt);//List("interpretation");
    return ret;
  }

  public static SketchInterpretation getInterp(Pt pt, String name) {
    SketchInterpretation ret = null;
    List<SketchInterpretation> all = SketchInterpretation.getInterpList(pt);
    for (SketchInterpretation interp : all) {
      if (interp.getName().equals(name)) {
	ret = interp;
	break;
      }
    }
    return ret;
  }

  public static void relate(SketchInterpretation parent,
			    SketchInterpretation child) {
    Debug.stacktraceIf("SquareInterpretation", "found relate statement", 6);
    parent.children.add(child);
    child.parents.add(parent);
  }

  protected void addInterpretation(Pt pt) {
    if (!pt.hasAttribute("interpretation")) {
      pt.setList("interpretation", new ArrayList<SketchInterpretation>());
    }
    pt.getList("interpretation").add(this);
  }

  protected void setPointConfidence(Pt pt, double c) {
    pointConfidence.put(pt, c);
  }

  public double getPointConfidence(Pt pt) {
    double ret = 0.0;
    if (pointConfidence.containsKey(pt)) {
      ret = pointConfidence.get(pt);
    }
    return ret;
  }

  public void debugParents() {
    debugList(parents, "parents");
  }

  public void debugList(List<SketchInterpretation> list, String name) {
    StringBuffer buffer = new StringBuffer();
    for (SketchInterpretation p : list) {
      buffer.append(p);
      if (!Lists.isLast(parents, p)) {
	buffer.append(", ");
      }
    }
    Debug.out("SketchInterpretation", this + " " + name + " = { " + buffer + " }");
  }

  public void debugChildren() {
    debugList(children, "children");
  }

}
