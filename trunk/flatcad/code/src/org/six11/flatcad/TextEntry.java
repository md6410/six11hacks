package org.six11.flatcad;

import org.six11.flatcad.geom.ColorMeister;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.six11.util.Debug;
import org.six11.util.io.FileUtil;
import org.six11.util.gui.FileChooserFilter;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 
 **/
public class TextEntry extends JPanel {
  
  JTextArea textArea;
  JScrollBar vertScrollBar;
  String currentPrompt;
  File diskFile;
  File oldDiskFile;
  List<PropertyChangeListener> fileListeners;

  List<UserInputListener> userInputListeners;

  public final static int COLOR_SCHEME_HACKER = 1;
  public final static int COLOR_SCHEME_DEMO = 2;

  public TextEntry() {
    userInputListeners = new ArrayList<UserInputListener>();
    fileListeners = new ArrayList<PropertyChangeListener>();

    textArea = new JTextArea();

    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent ev) {
	  if (ev.isControlDown() && ev.isShiftDown() && ev.getKeyCode() == KeyEvent.VK_ENTER) {
	    interpretSelection();
	  } else if (ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_ENTER) {
	    interpret();
	  }

	  if (ev.isControlDown() && ev.isShiftDown() && ev.getKeyCode() == KeyEvent.VK_S) {
	    saveAs();
	  }

	  if (ev.isControlDown() && !ev.isShiftDown() && ev.getKeyCode() == KeyEvent.VK_S) {
	    if (diskFile == null) {
	      saveAs();
	    } else {
	      save();
	    }
	  }

	  if (ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_O) {
	    open();
	  }

	  if (ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_N) {
	    newFile();
	  }
	}
      });

    JScrollPane scrollPane = new JScrollPane
      (textArea, 
       ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
       ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    vertScrollBar = scrollPane.getVerticalScrollBar();
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
  }

  public interface UserInputListener {
    public void handleUserInput(String input, boolean limited);
  }

  public int getLineNumber() {
    int ret = 0;
    try {
      int pos = textArea.getCaretPosition();
      // JTextArea reports a 0-base number, but for any application
      // I've ever seen, line numbers are reported where 1 is the
      // first line.
      ret = 1 + textArea.getLineOfOffset(pos);
    } catch (BadLocationException ex) {;}
    return ret;
  }

  public void loadFile(String fileName) {
    loadFile(fileName, false);
  }
  
  public void loadFile(String fileName, boolean anon) {
    try {
      String allText = FileUtil.loadStringFromFile(fileName);
      textArea.setText(allText);
      textArea.setCaretPosition(0);
      vertScrollBar.setValue(0);
      if (!anon) {
	diskFile = new File(fileName);
	System.out.println("Opening " + diskFile.getName());
      }
      fireFileNameChange(fileName);
      interpret();
    } catch (FileNotFoundException ex) {
      Debug.out("Main", "Can't find file: " + fileName);
    } catch (IOException ex) {
      Debug.out("Main", "Some kind of IOException. check the console.");
      ex.printStackTrace();
    }
  }

  private void fireFileNameChange(String fileName) {
    PropertyChangeEvent ev = new PropertyChangeEvent(this, "fileName", "", fileName);
    for (PropertyChangeListener lis : fileListeners) {
      lis.propertyChange(ev);
    }
  }

  public void addFileNameListener(PropertyChangeListener lis) {
    if (!fileListeners.contains(lis)) {
      fileListeners.add(lis);
    }
  }

  public void newFile() {
    if (diskFile != null) { 
      oldDiskFile = diskFile;
    }
    diskFile = null;
    textArea.setText("");
    fireFileNameChange(" (new file) ");
    interpret();
    System.out.println("New file");
  }

  public File getPwd() {
    File ret;
    if (diskFile != null) {
      ret = diskFile.getParentFile();
    } else if (oldDiskFile != null) {
      ret = oldDiskFile.getParentFile();
    } else {
      ret = new File(".");
    }
    return ret;
  }

  public void open() {
    try {
      File pwd = getPwd();
      JFileChooser chooser = FileChooserFilter.getChooser(pwd.getCanonicalPath(), "FlatLang Files", ".fl");
      int what = chooser.showOpenDialog(this);
      if (what == JFileChooser.APPROVE_OPTION) {
	String diskFileName = chooser.getSelectedFile().getAbsolutePath();
	loadFile(diskFileName);
      }
    } catch (Exception ex) {
      Debug.out("TextEntry", "aaaah freaking out!");
      ex.printStackTrace();
    }
  }

  public void saveAs() {
    try {
      File pwd = getPwd();
      JFileChooser chooser = FileChooserFilter.getChooser(pwd.getCanonicalPath(), "FlatLang Files", ".fl");
      int what = chooser.showSaveDialog(this);
      if (what == JFileChooser.APPROVE_OPTION) {
	diskFile = chooser.getSelectedFile();
	fireFileNameChange(diskFile.getName());
	save();
      }
    } catch (Exception ex) {
      Debug.out("TextEntry", "aaaah freaking out!");
      ex.printStackTrace();
    }
  }

  public void save() {
    if (diskFile != null) {
      try {
	BufferedWriter writer = new BufferedWriter(new FileWriter(diskFile));
	writer.write(textArea.getText());
	writer.flush();
	writer.close();
	System.out.println("Saved " + diskFile.getName());
      } catch (IOException ex) {
	Debug.out("TextEntry", "failed to save file for some reason");
	ex.printStackTrace();
      }
    } else {
      saveAs();
    }
  }

  public void interpretSelection() {
    Debug.out("TextEntry", "Interpreting selected region at " + new Date());
    String input = textArea.getSelectedText();
    fireUserInput(input, true);
  }

  public void interpret() {
    try {
      Debug.out("TextEntry", "Interpreting at " + new Date());
      Document doc = textArea.getDocument();
      String input = doc.getText(0, doc.getLength());
      fireUserInput(input, false);
    } catch (BadLocationException ignore) { }

  }

  public String getText() {
    String ret = "";
    try {
      Document doc = textArea.getDocument();
      ret = doc.getText(0, doc.getLength());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  protected void addUserInputListener(UserInputListener userListener) {
    if (!userInputListeners.contains(userListener)) {
      userInputListeners.add(userListener);
    }
  }

  protected void fireUserInput(String userInput, boolean limited) {
    ColorMeister.reset();
    for (UserInputListener lis : userInputListeners) {
      lis.handleUserInput(userInput, limited);
    }
  }

  /**
   * This exists because all command-line terminals should be green on
   * black with a red cursor... misguided people may create their own
   * inferior color schemes.
   */
  public void setColorScheme(int scheme) {
    switch (scheme) {
    case COLOR_SCHEME_HACKER:
      textArea.setForeground(Color.GREEN);
      textArea.setBackground(Color.BLACK);
      textArea.setCaretColor(Color.RED);
      textArea.setSelectedTextColor(Color.GREEN);
      textArea.setSelectionColor(Color.RED);
    case COLOR_SCHEME_DEMO:
      textArea.setForeground(Color.BLACK);
      textArea.setBackground(Color.WHITE);
      textArea.setCaretColor(Color.RED);
      textArea.setSelectedTextColor(Color.BLUE);
      textArea.setSelectionColor(Color.RED);
      textArea.setFont(new Font("sansserif", Font.BOLD, 12));
    }
  }

}
