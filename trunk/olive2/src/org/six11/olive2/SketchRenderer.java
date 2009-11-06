package org.six11.olive2;

import java.awt.Graphics2D;
import org.six11.util.pen.Sequence;
import org.six11.util.Debug;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;

/**
 * Sketch renderers are responsible for turning an unambiguous sketch
 * model into some external form such as HPGL, STL, or a sequence of
 * drawing statements in Java.
 **/
public abstract class SketchRenderer {

  private boolean enabled = true;
  private Set<String> validInterpretations = new HashSet<String>();

  public SketchRenderer(boolean defaultEnabled, String... validNames) {
    enabled = defaultEnabled;
    for (String s : validNames) {
      validInterpretations.add(s);
    }
  }
  
  public void setEnabled(boolean b) {
    enabled = b;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public final void renderSequences(Graphics2D g, List<Sequence> manySequences) {
    if (enabled) {
      for (Sequence seq : manySequences) {
	render(g, seq);
      }
    }
  }

  public void render(Graphics2D g, Sequence seq) {
    // subclasses must implement if they are interested in rendering
    // sequence data.
  }

  public void render(Graphics2D g, SketchInterpretation si) {
    // subclasses must implement this if they are interested in
    // rendering SketchInterpretation objects. MAKE SURE that the
    // constructor calls super(..) with a list of acceptable
    // interpretations. This is used by the SketchRenderer superclass
    // when handing the job over to its subclasses. SketchRenderer
    // will only give subclasses a rendering task if the subclass
    // claims that it can actually perform the task.
  }

  public final void renderInterpretations(Graphics2D g, Collection<SketchInterpretation> manyInterps) {
    if (enabled) {
      for (SketchInterpretation si : manyInterps) {
	if (validInterpretations.contains(si.getName())) {
	  render(g, si);
	}
      }
    }
  }

  public void renderFlatLang(FileWriter writer, Collection<SketchInterpretation> manyInterps) {
    // Subclasses that can render to FlatLang
  }


}
