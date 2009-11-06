package org.six11.chinablog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

/**
 * 
 *
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class Post {
  
  public static List<Post> getPosts(DatabaseThing dbThing) throws ServletException {
    List<Post> posts = new ArrayList<Post>();
    String sql = "select id from post";
    List<Integer> ids = dbThing.getIDs(sql, new String[] { });
    for (int id : ids) {
      posts.add(new Post(dbThing, "" + id));
    }
    return posts;
  }
  
  public Post(DatabaseThing dbThing, String id) throws ServletException {
    String sql = "select author_id, content, date, title from post where id = ?";
    Map<String, String> vals = dbThing.getRow(sql, id);
    this.id = id;
    this.authorId = unescape(vals.get("author_id"));
    this.content = unescape(vals.get("content"));
    this.date = unescape(vals.get("date"));
    this.title = unescape(vals.get("title"));
    Author a = new Author(dbThing, authorId);
    this.authorName = unescape(a.getName());
  }

  private String unescape(String in) {
    return in.replace("\\'", "'").replace("\\\"", "\"");
  }
    
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }
  

  private String id, authorId, content, date, title, authorName;

}
