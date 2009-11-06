package org.six11.chinablog;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.six11.util2.Debug;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class DatabaseThing {

  private DataSource datasource;

  public static String STRING = "String";
  public static String INT = "Integer";

  public DatabaseThing(DataSource source) {
    datasource = source;
  }

  /**
   * Load up a prepared statement with some parameter values. If the number of slots in the
   * statement is more than the number of provided params, the last param value
   * (params[params.length-1]) will be used. For example, if the statement is
   * "select id from t where a=? and b=? and c=?", but 'params' only contains a single element 'x',
   * the resulting query will be "select id from t where a='x' and b='x' and c='x'".
   * 
   * @param stmt
   *          A PreparedStatement. @see SqlStrings.
   * @param params
   *          A list of parameters in string format, or null if there are no params.
   * @throws SQLException
   *           If the world comes to an untimely end.
   */
  private void setParameters(PreparedStatement stmt, String... params) throws SQLException {
    if (params == null)
      return;
    ParameterMetaData meta = stmt.getParameterMetaData();
    int p_i = 1;
    for (String p : params) {
      if (isNumber(p)) {
        stmt.setInt(p_i, Integer.parseInt(p));
      } else {
        stmt.setString(p_i, p);
      }
      p_i = p_i + 1;
    }
    while (p_i <= meta.getParameterCount()) {
      stmt.setString(p_i, params[params.length - 1]);
      p_i = p_i + 1;
    }
  }

  private boolean isNumber(String p) {
    boolean ret = false;
    try {
      Integer.parseInt(p);
      ret = true;
    } catch (NumberFormatException ex) {
    }
    return ret;
  }

  /**
   * Attempts to insert a new row into the database using the canned query with the given
   * parameters.
   * 
   * @param query
   *          A canned query (please see SqlStrings.java).
   * @param params
   *          The values for the parameterizable bits in the query (the question marks).
   * @throws ServletException
   *           If the world ends, or if the DB has a cough.
   */
  public void insertRow(String query, String... params) throws ServletException {
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private Map<String, String> getResultMap(ResultSet rs, ResultSetMetaData meta)
      throws SQLException {
    Map<String, String> ret = new HashMap<String, String>();
    for (int i = 0; i < meta.getColumnCount(); i++) {
      String v = rs.getString(i + 1);
      if (!rs.wasNull()) {
        String k = meta.getColumnName(i + 1);
        ret.put(k, v);
      }
    }
    return ret;
  }

  private void bug(String what) {
    Debug.out("DatabaseThing", what);
  }

  /**
   * Gets several rows at once. Be careful when using aliases for column names because they don't
   * appear to work correctly with the Connector/J MySQL driver.
   * 
   * @param query
   * @param params
   * @return
   * @throws ServletException
   */
  public List<Map<String, String>> getRows(String query, String... params) throws ServletException {
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      ResultSet rs = stmt.executeQuery();
      ResultSetMetaData meta = rs.getMetaData();
      while (rs.next()) {
        ret.add(getResultMap(rs, meta));
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }

  public Map<String, String> getRow(String query, String... params) throws ServletException {
    Connection conn = null;
    Map<String, String> ret = new HashMap<String, String>();
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      ResultSet rs = stmt.executeQuery();
      ResultSetMetaData meta = rs.getMetaData();
      if (rs.next()) {
        ret = getResultMap(rs, meta);
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }

  public int getInt(String query, String... params) throws ServletException {
    Connection conn = null;
    int ret = 0;
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      ResultSet rs = stmt.executeQuery();
      ResultSetMetaData meta = rs.getMetaData();
      if (rs.next()) {
        ret = rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }

  /**
   * Gives you results where each value is in a specific indexed location. This is sometimes needed
   * if you don't know the name of something (or if several things have the same name).
   */
  public List<String> getRowAsList(String query, String... params) throws ServletException {
    Connection conn = null;
    List<String> ret = new ArrayList<String>();
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      ResultSet rs = stmt.executeQuery();
      ResultSetMetaData meta = rs.getMetaData();
      if (rs.next()) {
        for (int i = 0; i < meta.getColumnCount(); i++) {
          String v = rs.getString(i + 1);
          //if (!rs.wasNull()) {
            ret.add(v);
          //}
        }
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }

  /**
   * Gets a list of ints. The name of the method reflects its intended use (to get a bunch of IDs in
   * the form of integers), but this could be used for any SQL statement that yields a list of ints.
   */
  public List<Integer> getIDs(String query, String... params) throws ServletException {
    Connection conn = null;
    List<Integer> ret = new ArrayList<Integer>();
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      setParameters(stmt, params);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        ret.add(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }

  public String idListToCommaString(List<Integer> ints) {
    StringBuffer buf = new StringBuffer();
    int lastOne = ints.size() - 1;
    for (int i = 0; i < ints.size(); i++) {
      buf.append("" + ints.get(i));
      if (i != lastOne) {
        buf.append(", ");
      }
    }
    return buf.toString();
  }

  /**
   * Sets a parameter directly in an SQL string, rather than using the prepared statement's
   * setString/setInt methods. This is necessary because performing a SELECT statement with a
   * comma-separated list of integers in an IN clause won't work with the Java SQL classes.
   * 
   * Example: sql string is "SELECT person_id from group where group_name=? and group_id in (?)" We
   * want to set the second parameter to "1,3,4". We use this method thusly: String modifiedQuery =
   * db.setParameterDirectly(sql, "1,3,4", 2); Now the modifiedQuery can still be used as a
   * PreparedStatement, but it only has one param: modifiedQuery is
   * "SELECT person_id from group where group_name=? and group_id in (1,3,4)"
   * 
   * @param sql
   * @param insertMe
   * @param paramIndex
   *          indexes start at 1
   * @return
   */
  public String setParameterDirectly(String sql, String insertMe, int paramIndex) {
    int idx = -1;
    for (int i = 0; i < paramIndex; i++) {
      idx = sql.indexOf('?', idx + 1);
    }
    String ret = sql.substring(0, idx).concat(insertMe).concat(sql.substring(idx + 1));
    return ret;
  }

  public void update(String sql, String... params) throws ServletException {
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      setParameters(stmt, params);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new ServletException(e);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
