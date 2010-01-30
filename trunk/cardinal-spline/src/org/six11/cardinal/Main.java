package org.six11.cardinal;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.six11.util.gui.ApplicationFrame;
import org.six11.util.gui.MenuButton;

/**
 * 
 **/
public class Main {
  
  private static JPopupMenu menu = null;

  public static void main(String[] args) {
    ApplicationFrame aFrame = new ApplicationFrame("Testing");
    aFrame.setSize(400, 600);
    aFrame.center();
    menu = new JPopupMenu("A Nice Menu");
    menu.add("One thing");
    menu.add("Another thing");
    JMenu anotherMenu = new JMenu("More stuff...");
    anotherMenu.add("Foo");
    anotherMenu.add("Bar");
    menu.add(anotherMenu);
    menu.add("One last thing");
    MenuButton button = new MenuButton("Touch Me");
    button.setMenu(menu);
    JPanel panel = new JPanel();
    panel.add(button);
    aFrame.add(panel);
    aFrame.setVisible(true);
  }

}

//class PopupListener extends MouseAdapter {
//  
//  JPopupMenu popup;
//  
//  PopupListener(JPopupMenu popup) {
//    this.popup = popup;
//  }
//  
//  public void mousePressed(MouseEvent e) {
//    maybeShowPopup(e);
//  }
//
//  public void mouseReleased(MouseEvent e) {
//    // maybeShowPopup(e);
//  }
//
//  private void maybeShowPopup(MouseEvent e) {
////    if (e.isPopupTrigger()) {
//      Component c = e.getComponent();
//      popup.show(c, 0, c.getHeight());
////    }
//  }
//}