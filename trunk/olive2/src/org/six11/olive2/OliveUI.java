package org.six11.olive2;

import org.six11.util.gui.ApplicationFrame;
import org.six11.util.layout.FrontEnd;
import org.six11.util.lev.NamedAction;
import org.six11.util.Debug;
import org.six11.util.io.FileUtil;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import java.awt.GridLayout;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JFileChooser;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.six11.olive2.rend.PolygonRenderer;

/**
 * 
 **/
public class OliveUI {
  
  private ApplicationFrame frame;
  private Map<String, NamedAction> actions = new HashMap<String, NamedAction>();
  private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
  private SketchCapture sketchCapture;
  private File flatLangFile = new File("/Users/johnsogg/Projects/FlatCAD/code/fl/aa.fl");
  private File sketchBookFile;
  
  public OliveUI() {
    frame = new ApplicationFrame("Olive2");
    sketchCapture = new SketchCapture();
    FrontEnd fe = new FrontEnd();
    JPanel controlPanel = makeControlPanel();
    final String capture = "capture";
    final String panel = "panel";
    fe.add(sketchCapture, capture);
    fe.add(controlPanel, panel);
    
    fe.addRule(FrontEnd.ROOT, FrontEnd.N, capture, FrontEnd.N);
    fe.addRule(FrontEnd.ROOT, FrontEnd.S, capture, FrontEnd.S);
    fe.addRule(FrontEnd.ROOT, FrontEnd.W, capture, FrontEnd.W);

    fe.addRule(FrontEnd.ROOT, FrontEnd.N, panel, FrontEnd.N);
    fe.addRule(FrontEnd.ROOT, FrontEnd.S, panel, FrontEnd.S);
    fe.addRule(FrontEnd.ROOT, FrontEnd.E, panel, FrontEnd.E);
    fe.addRule(panel, FrontEnd.W, capture, FrontEnd.E);
    
    frame.add(fe);
  }
  
  public void go() {
    frame.setSize(600, 500);
    frame.center();
    frame.setVisible(true);
  }

  private JPanel makeControlPanel() {
    JPanel ret = new JPanel();
    ret.setLayout(new GridLayout(0,1));

    actions.put("save", new SaveAction());
    buttons.put("save", new JButton(actions.get("save")));
    ret.add(buttons.get("save"));

    actions.put("load", new LoadAction());
    buttons.put("load", new JButton(actions.get("load")));
    ret.add(buttons.get("load"));

    actions.put("basic", new ShowRendererAction("Show Ink", "basic"));
    buttons.put("basic", new JToggleButton(actions.get("basic")));
    buttons.get("basic").setSelected(true);
    ret.add(buttons.get("basic"));

    actions.put("dots", new ShowRendererAction("Show Dots", "dots"));
    buttons.put("dots", new JToggleButton(actions.get("dots")));
    ret.add(buttons.get("dots"));

    actions.put("lines", new ShowRendererAction("Show Lines", "lines"));
    buttons.put("lines", new JToggleButton(actions.get("lines")));
    ret.add(buttons.get("lines"));

    actions.put("line enddots", new ShowRendererAction("Show Line Enddots", "line enddots"));
    buttons.put("line enddots", new JToggleButton(actions.get("line enddots")));
    ret.add(buttons.get("line enddots"));

    actions.put("adjacent_lines", new ShowRendererAction("Show Adjacent Lines", "adjacent_lines"));
    buttons.put("adjacent_lines", new JToggleButton(actions.get("adjacent_lines")));
    ret.add(buttons.get("adjacent_lines"));

    actions.put("polygons", new ShowRendererAction("Show Polygons", "polygons", "squares")); 
    buttons.put("polygons", new JToggleButton(actions.get("polygons")));
    ret.add(buttons.get("polygons"));

    actions.put("notches", new ShowRendererAction("Show Notches", "notches")); 
    buttons.put("notches", new JToggleButton(actions.get("notches")));
    ret.add(buttons.get("notches"));

    actions.put("lineRect", new RectifyAction("Rectify Lines", "lineRect", "line"));
    buttons.put("lineRect", new JButton(actions.get("lineRect")));
    ret.add(buttons.get("lineRect"));

    actions.put("lineRectVertical", new RectifyAction("Vertical Lines", "lineRectVertical", "line"));
    buttons.put("lineRectVertical", new JButton(actions.get("lineRectVertical")));
    ret.add(buttons.get("lineRectVertical"));

    actions.put("polygonRect", new RectifyAction("Rectify Polygons", "polygonRect", "polygon"));
    buttons.put("polygonRect", new JButton(actions.get("polygonRect")));
    ret.add(buttons.get("polygonRect"));

    actions.put("denseRect", new ShowRendererAction("Show Dense", "denseRect", "dense"));
    buttons.put("denseRect", new JToggleButton(actions.get("denseRect")));
    ret.add(buttons.get("denseRect"));

    actions.put("flatlang", new GenerateFlatLangAction());
    buttons.put("flatlang", new JButton(actions.get("flatlang")));
    ret.add(buttons.get("flatlang"));

    actions.put("debug", new DebugAction());
    buttons.put("debug", new JButton(actions.get("debug")));
    ret.add(buttons.get("debug"));

    actions.put("clear", new ClearAction());
    buttons.put("clear", new JButton(actions.get("clear")));
    ret.add(buttons.get("clear"));

    return ret;
  }

  private void whackRenderer(String who, boolean b) {
    sketchCapture.getSketchBook().setRendererEnabled(who, b);
    sketchCapture.repaint();
  }

  private JFileChooser makeSketchFileChooser() {
    return FileUtil.makeFileChooser(sketchBookFile, "sketch", "Olive2 Sketch Files");
  }

  private void save() {
    if (sketchBookFile == null) {
      JFileChooser chooser = makeSketchFileChooser();
      int retVal = chooser.showSaveDialog(frame);
      if (retVal == JFileChooser.APPROVE_OPTION) {
	sketchBookFile = chooser.getSelectedFile();
      }
    }
    autosave();
  }

  private void autosave() {
    if (sketchBookFile != null) {
      // TODO: write sketchbook
      Debug.out("OliveUI", "Save sketchBook to file: " + sketchBookFile);
      try {
	SketchBook sketchBook = sketchCapture.getSketchBook();
	Element xml = sketchBook.toXML();
	Document xmlDoc = new Document(xml);
	XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	FileWriter writer = new FileWriter(sketchBookFile);
	xmlOut.output(xmlDoc, writer);
	writer.close();
	Debug.out("OliveUI", "Saved!");
      } catch (IOException ex) {
	Debug.out("OliveUI", "Error saving to file.");
	ex.printStackTrace();
      }
    }
  }

  private void load() {
    JFileChooser chooser = makeSketchFileChooser();
    int retVal = chooser.showOpenDialog(frame);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      load(chooser.getSelectedFile());
    }
  }

  public void load(File file) {
    sketchBookFile = file;
    if (sketchBookFile != null) {
      try {
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(sketchBookFile);
	SketchBook sketchBook = sketchCapture.getSketchBook();
	sketchBook.fromXML(doc.getRootElement());
	Debug.out("OliveUI", "Load sketchBook from file: " + sketchBookFile);
      } catch (Exception ex) {
	Debug.out("OliveUI", "Error loading from file.");
	ex.printStackTrace();
      }
    }
  }

  private void generateFlatLang() {
    if (flatLangFile == null) {
      JFileChooser chooser = new JFileChooser("/Users/johnsogg/Projects/FlatCAD/code/fl");
      int retVal = chooser.showSaveDialog(frame);
      if (retVal == JFileChooser.APPROVE_OPTION) {
	flatLangFile = chooser.getSelectedFile();
      }
    }
    if (flatLangFile != null) {
      try {
	Debug.out("OliveUI", "Write FlatLang to " + flatLangFile);
	SketchBook sketchBook = sketchCapture.getSketchBook();
	FileWriter writer = new FileWriter(flatLangFile);
	PolygonRenderer polyRen = (PolygonRenderer) sketchBook.getRenderer("polygons");
	polyRen.renderFlatLang(writer, sketchBook.getInterpretations("polygon"));
      } catch (IOException ex) {
	Debug.out("OliveUI", "Encountered some sort of file IO problem:");
	ex.printStackTrace();
      }
    }
  }

  class RectifyAction extends NamedAction {
    
    HashMap params;
    String rectName;
    String interpName;

    RectifyAction(String buttonLabel, String rectName, String interpName) {
      super(buttonLabel);
      params = new HashMap();
      if ("lineRect".equals(rectName)) {
	params.put("pixels", 3d);
      } else if ("lineRectVertical".equals(rectName)) {
	params.put("vertical", "");
      }
      this.rectName = rectName;
      this.interpName = interpName;
    }

    public void activate() {
      SketchRectifier rect = sketchCapture.getSketchBook().getRectifier(rectName);
      Debug.out("OliveUI", "activating rectifier named " + rectName);
      if (rect != null) {
	List<SketchInterpretation> interps = sketchCapture.getSketchBook().getInterpretations(interpName);
	Debug.out("OliveUI", "applying " + rectName + " on " + interps.size() + " interpretations named " + interpName);
	for (SketchInterpretation interp : interps) {
	  rect.rectify(interp, params);
	}
      }
      sketchCapture.repaint();
    }
  }

  class ShowRendererAction extends NamedAction {

    String internalName; // just the first in the list.
    List<String> internalNameList;

    ShowRendererAction(String buttonLabel, String... internalNames) {
      super(buttonLabel);
      internalNameList = new ArrayList<String>();
      for (String s : internalNames) {
	internalNameList.add(s);
      }
      internalName = internalNameList.get(0);
    }

    public void activate() {
      boolean enabledState = buttons.get(internalName).isSelected();
      Debug.out("OliveUI", getName() + " to " + enabledState);
      for (String s : internalNameList) {
	whackRenderer(s, enabledState);
      }
    }
  }

  class SaveAction extends NamedAction {
    SaveAction() {
      super("Save SketchBook");
    }

    public void activate() {
      save();
    }
  }

  class LoadAction extends NamedAction {
    LoadAction() {
      super("Load SketchBook...");
    }

    public void activate() {
      load();
    }
  }

  class GenerateFlatLangAction extends NamedAction {
    GenerateFlatLangAction() {
      super("Generate FlatLang");
    }

    public void activate() {
      generateFlatLang();
    }
  }

  class DebugAction extends NamedAction {
    DebugAction() {
      super("Debug (console)");
    }

    public void activate() {
      sketchCapture.getSketchBook().debugRendering();
    }
  }

  class ClearAction extends NamedAction {
    ClearAction() {
      super("Clear");
    }

    public void activate() {
      sketchCapture.getSketchBook().clear();
      sketchCapture.repaint();
    }
  }


}
