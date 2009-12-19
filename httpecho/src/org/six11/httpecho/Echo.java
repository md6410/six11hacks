package org.six11.httpecho;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    out(resp, "getAuthType: " + req.getAuthType());
    out(resp, "getContextPath: " + req.getContextPath());
    if (req.getCookies() == null) {
      out(resp, "getCookies: <null>");
    } else {
      out(resp, "getCookies: " + req.getCookies().length + " total...");
      for (int i = 0; i < req.getCookies().length; i++) {
        out(resp, "  Cookie " + i + ": " + req.getCookies()[i].getName() + " = "
            + req.getCookies()[i].getValue());
      }
    }
    if (req.getHeaderNames() == null) {
      out(resp, "getHeaderName: <null>");
    } else {
      Enumeration headers = req.getHeaderNames();
      int headerCount = 0;
      out(resp, "getHeader:");
      while (headers.hasMoreElements()) {
        String key = (String) headers.nextElement();
        out(resp, "  Header key " + headerCount++ + ": " + key + " = " + req.getHeader(key));
      }
    }
    out(resp, "getMethod: " + req.getMethod());
    out(resp, "getPathInfo: " + req.getPathInfo());
    out(resp, "getPathTranslated: " + req.getPathTranslated());
    out(resp, "getQueryString: " + req.getQueryString());
    out(resp, "getRemoteUser: " + req.getRemoteUser());
    out(resp, "getRequestedSessionId: " + req.getRequestedSessionId());
    out(resp, "getRequestURI: " + req.getRequestURI());
    out(resp, "getRequestURL: " + req.getRequestURL());
    out(resp, "getServletPath: " + req.getServletPath());
    out(resp, "getSession: " + req.getSession());
    if (req.getAttributeNames() == null) {
      out(resp, "getAttributeNames: <null>");
    } else {
      Enumeration attribs = req.getAttributeNames();
      int attribCount = 0;
      out(resp, "getAttribute:");
      while (attribs.hasMoreElements()) {
        String key = (String) attribs.nextElement();
        out(resp, "  Attrib key " + attribCount++ + ": " + key + " = " + req.getAttribute(key));
      }
    }
    out(resp, "getCharacterEncoding: " + req.getCharacterEncoding());
    out(resp, "getContentLength: " + req.getContentLength());
    out(resp, "getContentType: " + req.getContentType());
    Map params = req.getParameterMap();
    out(resp, "Parameters: " + params.size()
        + " total... (values are on newlines followed by two spaces)");
    StringBuilder buf = new StringBuilder();
    for (Object key : params.keySet()) {
      buf.setLength(0);
      String skey = (String) key;
      String[] vals = (String[]) params.get(key);
      for (String val : vals) {
        buf.append("\n  " + val);
      }
      out(resp, "Parameter: " + skey + " = " + buf.toString());
    }
    out(resp, "getProtocol: " + req.getProtocol());
    out(resp, "getRemoteAddr: " + req.getRemoteAddr());
    out(resp, "getRemoteHost: " + req.getRemoteHost());
    out(resp, "getScheme: " + req.getScheme());
    out(resp, "getServerName: " + req.getServerName());
    out(resp, "getServerPort: " + req.getServerPort());
    out(resp, "isSecure: " + req.isSecure());
    resp.getOutputStream().close();
  }

  private void out(HttpServletResponse resp, String msg) throws IOException {
    StreamUtil.writeStringToOutputStream(msg + "\n", resp.getOutputStream());
  }

  protected void forwardTo(String servletPath, HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher(servletPath).forward(req, resp);
  }

}
