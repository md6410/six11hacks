package org.six11.chinablog;

import java.util.Map;

import javax.servlet.ServletException;

/**
 * 
 *
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class Author {
  private String id, name;
  
  public Author(DatabaseThing dbThing, String id) throws ServletException {
    String sql = "select name from author where id = ?";
    Map<String, String> vals = dbThing.getRow(sql, id);
    this.id = id;
    this.name = vals.get("name");
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
