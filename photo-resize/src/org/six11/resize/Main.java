package org.six11.resize;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.six11.util.Debug;
import org.six11.util.args.Arguments;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.gui.Components;
import org.six11.util.io.Preferences;

/**
 * 
 **/
public class Main implements DropTargetListener {
  public static void main(String[] args) throws IOException {
    Arguments parsedArgs = new Arguments(args);
    Main resizer = new Main(parsedArgs);
    resizer.showGui();
  }

  private ApplicationFrame af;
  private Preferences prefs;
  private JPanel dropSpot;

  public Main(Arguments args) throws IOException {
    prefs = Preferences.makePrefs("resizer");
    prefs.setPropertyDefault("width", "800");
    prefs.setPropertyDefault("height", "600");
    prefs.setPropertyDefault("dir", System.getProperty("user.home"));
    if (args.getPositionCount() > 0) {
      List<File> resizeUs = new ArrayList<File>();
      for (int i = 0; i < args.getPositionCount(); i++) {
        File f = new File(args.getPosition(i));
        resizeUs.add(f);
      }
      resizeLater(resizeUs);
    }
  }

  private void showGui() {
    af = new ApplicationFrame("Photo Resizer");
    af.setSize(new Dimension(300, 300));
    dropSpot = new JPanel();
    JLabel msg = new JLabel("Drop JPGs Here");
    msg.setHorizontalAlignment(JLabel.CENTER);
    msg.setFont(new Font("Dialog", Font.BOLD, 32));
    dropSpot.setLayout(new BorderLayout());
    dropSpot.add(msg, BorderLayout.CENTER);
    new DropTarget(dropSpot, this);
    JPanel buttonBox = new JPanel();
    buttonBox.setLayout(new GridLayout(1, 0));
    JButton preferencesButton = new JButton("Preferences...");
    preferencesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showPrefs();
      }
    });
    buttonBox.add(preferencesButton);
    af.setLayout(new BorderLayout());
    af.add(buttonBox, BorderLayout.SOUTH);
    af.add(dropSpot, BorderLayout.CENTER);
    af.center();
    af.setVisible(true);
  }

  protected void showPrefs() {
    final JDialog prefsDialog = new JDialog(af, "Photo Resize Preferences");
    prefsDialog.setSize(400, 300);
    Components.centerComponent(prefsDialog);
    JPanel dimBox = new JPanel();
    JLabel maxWFieldLabel = new JLabel("Maximum width:");
    final JTextField maxWField = new JTextField(prefs.getProperty("width"));
    JLabel maxHFieldLabel = new JLabel("Maximum height:");
    final JTextField maxHField = new JTextField(prefs.getProperty("height"));
    JLabel dirLabel = new JLabel("Output Folder");
    JPanel dirFieldBox = new JPanel();
    dirFieldBox.setLayout(new GridLayout(1, 2));
    final JLabel dirValue = new JLabel(prefs.getProperty("dir"));
    final JButton dirButton = new JButton("Choose...");
    dirButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        File initialDir = new File(prefs.getProperty("dir"));
        JFileChooser chooser = new JFileChooser(initialDir);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showSaveDialog(prefsDialog);
        if (result == JFileChooser.APPROVE_OPTION) {
          File outFile = chooser.getSelectedFile();
          setPref("dir", outFile.getAbsolutePath());
          dirValue.setText(outFile.getAbsolutePath());
        }
      }
    });
    dirFieldBox.add(dirValue);
    dirFieldBox.add(dirButton);
    dimBox.setLayout(new GridLayout(0, 2));
    dimBox.add(maxWFieldLabel);
    dimBox.add(maxWField);
    dimBox.add(maxHFieldLabel);
    dimBox.add(maxHField);
    dimBox.add(dirLabel);
    dimBox.add(dirFieldBox);
    JPanel buttonBox = new JPanel();
    JButton doneButton = new JButton("OK");
    ActionListener doneAction = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setPref("width", maxWField.getText());
        setPref("height", maxHField.getText());

        prefsDialog.setVisible(false);
        prefsDialog.dispose();
      }
    };
    doneButton.addActionListener(doneAction);
    buttonBox.add(doneButton);
    prefsDialog.setLayout(new BorderLayout());
    prefsDialog.add(dimBox, BorderLayout.NORTH);
    prefsDialog.add(buttonBox, BorderLayout.SOUTH);
    prefsDialog.setVisible(true);
  }

  protected void setPref(String key, String val) {
    if (key.equals("width") || key.equals("height")) {
      try {
        Integer.parseInt(val);
        setPrefNow(key, val);
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    } else if (key.equals("dir")) {
      File dir = new File(val);
      if (dir.exists() && dir.canWrite()) {
        setPrefNow(key, val);
      }
    } else {
      bug("Unknown key in setPref: " + key + " = " + val);
    }
  }

  private void setPrefNow(String key, String val) {
    try {
      prefs.setProperty(key, val);
      prefs.save();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  protected void resize(File file) {
    boolean ok = true;
    if (!file.exists()) {
      bug("File does not exist: " + file.getAbsolutePath());
      ok = false;
    }
    if (!file.canRead()) {
      bug("File not readable: " + file.getAbsolutePath());
      ok = false;
    }
    File outDir = new File(prefs.getProperty("dir"));
    File outFile = new File(outDir, file.getName());
    if (outFile.exists()) {
      bug("Not overwriting file: " + outFile.getAbsolutePath());
      ok = false;
    }
    if (ok) {
      try {
        BufferedImage srcImg = ImageIO.read(file);
        int w = Integer.parseInt(prefs.getProperty("width"));
        int h = Integer.parseInt(prefs.getProperty("height"));
        double wScale = ((double) w) / ((double) srcImg.getWidth());
        double hScale = ((double) h) / ((double) srcImg.getHeight());
        double scale = Math.min(wScale, hScale);
        int destW = (int) Math.ceil(scale * (double) srcImg.getWidth());
        int destH = (int) Math.ceil(scale * (double) srcImg.getHeight());
        BufferedImage dstImg = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) dstImg.getGraphics();
        Components.antialias(g);
        Components.interpolate(g);
        Components.quality(g);
        g.drawImage(srcImg, 0, 0, destW, destH, null);
        ImageIO.write(dstImg, "jpeg", outFile);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private static void bug(String what) {
    Debug.out("Main", what);
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    dropSpot.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
  }

  public void dragExit(DropTargetEvent dte) {
    dropSpot.setBorder(null);
  }

  public void dragOver(DropTargetDragEvent dtde) {
  }

  public void drop(DropTargetDropEvent dtde) {
    dropSpot.setBorder(null);
    try {
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
        if (flavors[i].isFlavorJavaFileListType()) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          List<File> list = (List<File>) tr.getTransferData(flavors[i]);
          resizeLater(list);
          dtde.dropComplete(true);
          return;
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void resizeLater(final List<File> files) {
    Runnable runner = new Runnable() {
      public void run() {
        for (File file : files) {
          resize(file);
        }
      }
    };
    SwingUtilities.invokeLater(runner);
    try {
      Desktop.getDesktop().open(new File(prefs.getProperty("dir")));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

}
