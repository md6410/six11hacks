package org.six11.flatcad.geom;

import org.six11.util.Debug;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class ColorMeister {

  static int cursor = 0;
  static List<Color> colors = new ArrayList<Color>();
  static {
    colors.add(Color.LIGHT_GRAY);
    colors.add(Color.ORANGE);
    colors.add(Color.YELLOW);
    colors.add(Color.GREEN);
    colors.add(Color.CYAN);
    colors.add(Color.RED);
    colors.add(Color.MAGENTA);
    colors.add(Color.BLUE);
    colors.add(Color.GRAY);
    colors.add(Color.PINK);
  }

  public static void reset() {
    cursor = 0;
  }

  public static Color nextColor() {
    Color ret = colors.get(cursor);
    cursor = (cursor + 1) % colors.size();
//     Debug.out("ColorMeister", "cursor is at " + cursor);
    //    new RuntimeException("calling nextColor").printStackTrace();
    return ret;
  }
}
