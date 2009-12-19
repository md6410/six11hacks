package org.six11.httpecho;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.six11.util.Debug;
import org.six11.util.io.StreamUtil;

/**
 * 
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class Echo extends HttpServlet {

  public Echo() {
    super();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    doPost(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

  }

  protected void forwardTo(String servletPath, HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher(servletPath).forward(req, resp);
  }

}
