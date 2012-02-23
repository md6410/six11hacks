package org.six11.hacks.kidbrother;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;
import static org.six11.util.Debug.bug;

import javax.swing.JPanel;

import org.six11.util.Debug;
import org.six11.util.args.Arguments;
import org.six11.util.gui.ApplicationFrame;
import org.six11.util.io.FileUtil;
import org.six11.util.io.FileWatcher;
import org.six11.util.io.FileWatcher.WatchedFile;

public class KidBrother extends JPanel {

  public static void main(String[] in) {
    Arguments args = new Arguments(in);
    if (args.hasFlag("useColor")) {
      Debug.useColor = Boolean.parseBoolean(args.getValue("useColor"));
    }
    ApplicationFrame af = new ApplicationFrame("KidBrother");
    af.setSize(300, 200);
    af.add(new KidBrother());
    af.center();
    af.setVisible(true);
  }

  private List<FileWatcher.WatchedFile> files;
  private FileWatcher.Handler handler;
  private Map<String, File> cachedVersion;
  private Preferences prefs;

  public KidBrother() {
    setBackground(Color.WHITE);
    cachedVersion = new HashMap<String, File>();
    handler = new FileWatcher.Handler() {
      public void init(WatchedFile wf) {
        try {
          File srcFile = new File(wf.fileName);
          File tmpFile = File.createTempFile("kidbrother", "cache");
          FileUtil.copy(srcFile, tmpFile);
          cachedVersion.put(wf.fileName, tmpFile);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      public void noticeChange(WatchedFile wf) {
        File srcFile = new File(wf.fileName);
        File tmpFile = cachedVersion.get(wf.fileName);
        String result = compare(tmpFile, srcFile, wf.modTime);
        System.out.println(result);
        FileUtil.copy(srcFile, tmpFile);
      }
    };

    prefs = Preferences.userNodeForPackage(getClass());
    String fullPath = prefs.get("path", "");
    StringTokenizer pathItems = new StringTokenizer(fullPath, ":");
    files = new ArrayList<FileWatcher.WatchedFile>();
    while (pathItems.hasMoreTokens()) {
      String fileName = pathItems.nextToken();
      bug("Watched file: " + fileName);
      maybeAddPath(fileName);
//      files.add(new WatchedFile(fileName));
    }
        final FileWatcher fw = new FileWatcher();
    Thread watchThread = new Thread(new Runnable() {
      public void run() {
        fw.watch(handler, files);
      }
    });
    watchThread.start();
    DropTargetListener dropListener = new DropTargetListener() {
      public void dragEnter(DropTargetDragEvent ev) {
      }

      public void dragExit(DropTargetEvent ev) {
      }

      public void dragOver(DropTargetDragEvent ev) {
      }

      public void drop(DropTargetDropEvent ev) {
        Transferable droppedStuff = ev.getTransferable();
        DataFlavor fileFlavor = null;
        for (DataFlavor flavor : droppedStuff.getTransferDataFlavors()) {
          if (flavor.isFlavorJavaFileListType()) {
            fileFlavor = flavor;
            break;
          }
        }
        if (fileFlavor != null) {
          try {
            ev.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> droppedFiles = (List<File>) droppedStuff.getTransferData(fileFlavor);
            for (File f : droppedFiles) {
              maybeAddPath(f.getAbsolutePath());
            }
            ev.dropComplete(true);
          } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        } else {
          bug("No file flavor found.");
        }
      }

      public void dropActionChanged(DropTargetDragEvent ev) {

      }
    };
    new DropTarget(this, dropListener);
  }

  protected String compare(File tmpFile, File srcFile, long time) {
    StringBuilder result = new StringBuilder();
    int numLines = 0;
    ProcessBuilder pb = new ProcessBuilder("diff", tmpFile.getAbsolutePath(),
        srcFile.getAbsolutePath());
    try {
      Process proc = pb.start();
      BufferedReader resultReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      proc.waitFor();
      while (resultReader.ready()) {
        numLines = numLines + 1;
        result.append(resultReader.readLine() + "\n");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    result.insert(0, "Change: " + srcFile.getName() + " lines: " + numLines + " time: " + time + "\n");
    return result.toString();
  }

  protected void maybeAddPath(String absPath) {
    bug("Acquiring lock on 'files'...");
    synchronized (files) {
      bug("Got it.");
      boolean found = false;
      for (WatchedFile wf : files) {
        if (wf.fileName.equals(absPath)) {
          found = true;
          break;
        }
      }
      if (!found) {
        String fullPath = prefs.get("path", "");
        String newPath = fullPath + ":" + absPath;
        bug("Made new path: " + newPath);
        prefs.put("path", newPath);
        WatchedFile wf = new WatchedFile(absPath);
        handler.init(wf);
        bug("Adding watch file: " + wf.fileName);
        files.add(wf);
      }
    }
  }
}
