package org.six11.flatcad;

import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

/**
 * 
 **/
public class TextEntryOld extends JPanel implements Output {
  
  JTextArea textArea;
  int boundary = 0;
  int currentCarret = 0;
  String currentPrompt;

  List<UserInputListener> userInputListeners;

  public final static int COLOR_SCHEME_HACKER = 1;

  public TextEntryOld() {
    userInputListeners = new ArrayList<UserInputListener>();

    textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.addKeyListener(new KeyAdapter() {
	public void keyTyped(KeyEvent ev) {
	  if (!isCarretInEditableRegion()) {
	    Debug.out("TextEntryOld", "yeah you are not in the right area. fixing?");
	    sendCaretToEnd();
	  }
	  if (!isCarretInEditableRegion()) {
	    Debug.out("TextEntryOld", "still not in the right area!");
	  }
	}
      });

    InputMap textAreaInputMap = textArea.getInputMap();
    ActionMap textAreaActionMap = textArea.getActionMap();
    Object issueCommandKey = "issue a command";
    Action issueCommandAction = new TextEntryOldIssueCommandAction();
    textAreaActionMap.put(issueCommandKey, issueCommandAction);
    textAreaInputMap.put(KeyStroke.getKeyStroke("ENTER"), issueCommandKey);

    CaretListener caretListener = new TextEntryOldCaretListener();
    textArea.addCaretListener(caretListener);

    JScrollPane scrollPane = new JScrollPane
      (textArea, 
       ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
       ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
  }
  
  public void setPrompt(String prompt) {
    currentPrompt = prompt;
    addOutput(currentPrompt);
  }

  public String getPrompt() {
    return currentPrompt;
  }

  public void addOutput(String what) {
    if (what != null) {
      Document doc = textArea.getDocument();
      try {
	doc.insertString(doc.getLength(), what, null);
	sendCaretToEnd();
      } catch (BadLocationException ex) {
	Debug.out("TextEntryOld", "Bad Location");
      }
    }
  }

  public void sendCaretToEnd() {
    Document doc = textArea.getDocument();
    boundary = doc.getLength();
    textArea.setCaretPosition(boundary);
  }

  public interface UserInputListener {
    public void handleUserInput(String input);
  }

  class TextEntryOldIssueCommandAction extends AbstractAction {
    public void actionPerformed(ActionEvent ev) {
      if (isCarretInEditableRegion()) {
	handleInput();
      }
    }
  }

  public void handleInput() {
    Document doc = textArea.getDocument();
    try {
      int lengthBefore = doc.getLength();
      int len = lengthBefore- boundary;
      if (len > 0) {
	String userInput = doc.getText(boundary, len);
	addOutput("\n");
	fireUserInput(userInput);
	sanitizeEnding();
      } else {
	addOutput("\n"); // let the user hit 'enter' to put space
			 // between last command and next
      }
      addOutput(currentPrompt);
    } catch (BadLocationException ex) {
      Debug.out("TextEntryOld", "Bad Location");
    }
  }

  /**
   * This just removes all but one newline at the end of the document.
   */
  private void sanitizeEnding() {
    Document doc = textArea.getDocument();
    int newlineCount = 0;
    int end = doc.getLength();
    int cursor = end - 1;
    try {
      while (doc.getText(cursor, newlineCount + 1).startsWith("\n")) {
	newlineCount++;
	cursor--;
      }
    } catch (BadLocationException ex) {
      ex.printStackTrace();
    }
    if (newlineCount == 0) {
      addOutput("\n");
    } else {
      try {
	doc.remove(end - newlineCount, newlineCount - 1);
      } catch (BadLocationException ex) {
	ex.printStackTrace();
      }
    }
  }

  /**
   * Programmatically add text to the bottom of the terminal as though
   * the user had typed it.
   */
  public void insertCommandTextAtBottom(String what) {
    Document doc = textArea.getDocument();
    try {
      textArea.getCaret().setDot(doc.getLength());
      doc.insertString(doc.getLength(), what, null);
    } catch (BadLocationException ex) {
      Debug.out("TextEntryOld", "Bad Location");
    }
  }

  class TextEntryOldCaretListener implements CaretListener {
    public void caretUpdate(CaretEvent ev) {
      currentCarret = ev.getDot();
      textArea.setEditable(isCarretInEditableRegion());
    }
  }

  protected boolean isCarretInEditableRegion() {
    return (currentCarret >= boundary);
  }

  protected void addUserInputListener(UserInputListener userListener) {
    if (!userInputListeners.contains(userListener)) {
      userInputListeners.add(userListener);
    }
  }

  protected void fireUserInput(String userInput) {
    for (UserInputListener lis : userInputListeners) {
      lis.handleUserInput(userInput);
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
    }
  }

}
