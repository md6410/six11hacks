package org.six11.httpecho;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple servlet that reports as much useful information about the request as possible. This is
 * useful for debugging web clients, and for helping to understand what all the various Servlet
 * methods do.
 * 
 * Also, I am aware that the HTML strewn around in there is ugly as hell. I wanted to do this
 * without depending on an external JSP file or similar.
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>
 */
public class Echo extends HttpServlet {

  /**
   * Defers to doPost.
   */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    doPost(req, resp);
  }

  /**
   * Output all the information we can about the request.
   */
  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    resp.setContentType("text/html");
    write(resp, "<html><head><title>HTTP Echo</title></head><body>");
    write(resp, "<table border=\"1\">");
    outTableRow(resp, "getAuthType:", req.getAuthType());
    outTableRow(resp, "getContextPath:", req.getContextPath());
    if (req.getCookies() == null) {
      outTableRow(resp, "getCookies:", "<i>null</i>");
    } else {
      write(resp, "<tr><td>getCookies: " + req.getCookies().length + " total...</td><td>");
      write(resp, "\n<table border=\"1\">");
      for (int i = 0; i < req.getCookies().length; i++) {
        outTableRow(resp, "  Cookie " + i + ":", req.getCookies()[i].getName() + " = "
            + req.getCookies()[i].getValue());
      }
      outCloseSubTable(resp);
    }
    if (req.getHeaderNames() == null) {
      outTableRow(resp, "getHeaderName:", "<i>null</i>");
    } else {
      Enumeration headers = req.getHeaderNames();
      int headerCount = 0;
      write(resp, "<tr><td>getHeader:</td><td>");
      write(resp, "\n<table border=\"1\">");
      while (headers.hasMoreElements()) {
        String key = (String) headers.nextElement();
        outTableRow(resp, "  Header key " + headerCount++ + ":", key + " = " + req.getHeader(key));
      }
      write(resp, "\n</table></td></tr>");
    }
    outTableRow(resp, "getMethod:", req.getMethod());
    outTableRow(resp, "getPathInfo:", req.getPathInfo());
    outTableRow(resp, "getPathTranslated:", req.getPathTranslated());
    outTableRow(resp, "getQueryString:", req.getQueryString());
    outTableRow(resp, "getRemoteUser:", req.getRemoteUser());
    outTableRow(resp, "getRequestedSessionId:", req.getRequestedSessionId());
    outTableRow(resp, "getRequestURI:", req.getRequestURI());
    outTableRow(resp, "getRequestURL:", req.getRequestURL().toString());
    outTableRow(resp, "getServletPath:", req.getServletPath());
    outTableRow(resp, "getSession:", "" + req.getSession());
    if (req.getAttributeNames() == null) {
      outTableRow(resp, "getAttributeNames:", "<i>null</i>");
    } else {
      Enumeration attribs = req.getAttributeNames();
      int attribCount = 0;
      write(resp, "<tr><td>getAttributeNames:</td><td>");
      write(resp, "\n<table border=\"1\">");
      while (attribs.hasMoreElements()) {
        String key = (String) attribs.nextElement();
        outTableRow(resp, "  Attrib key " + attribCount++ + ": " + key, "" + req.getAttribute(key));
      }
      write(resp, "\n</table></td></tr>");
    }
    outTableRow(resp, "getCharacterEncoding:", req.getCharacterEncoding());
    outTableRow(resp, "getContentLength:", "" + req.getContentLength());
    outTableRow(resp, "getContentType:", req.getContentType());
    Map params = req.getParameterMap();
    write(resp, "<tr><td>Parameters:" + params.size() + " total</td><td>");
    write(resp, "\n<table border=\"1\">");
    StringBuilder buf = new StringBuilder();
    for (Object key : params.keySet()) {
      buf.setLength(0);
      String skey = (String) key;
      String[] vals = (String[]) params.get(key);
      for (String val : vals) {
        outTableRow(resp, skey + ": ", val);
      }
    }
    outCloseSubTable(resp);
    outTableRow(resp, "getProtocol:", req.getProtocol());
    outTableRow(resp, "getRemoteAddr:", req.getRemoteAddr());
    outTableRow(resp, "getRemoteHost:", req.getRemoteHost());
    outTableRow(resp, "getScheme:", req.getScheme());
    outTableRow(resp, "getServerName:", req.getServerName());
    outTableRow(resp, "getServerPort:", "" + req.getServerPort());
    outTableRow(resp, "isSecure:", "" + req.isSecure());
    write(resp, "</table>");
    write(resp, "Input stream, in its entirety (starting next line)");
    writeInputStreamToOutputStream(req.getInputStream(), resp.getOutputStream());
    write(resp, "</body></html>");
    resp.getOutputStream().close();
  }

  private void outCloseSubTable(HttpServletResponse resp) throws IOException {
    write(resp, "</table></td></tr>");
  }

  private void outTableRow(HttpServletResponse resp, String label, String msg) throws IOException {
    writeStringToOutputStream("<tr><td>" + label + "</td>" + "\n<td>" + msg + "</td></tr>\n", resp
        .getOutputStream());
  }

  private void write(HttpServletResponse resp, String what) throws IOException {
    writeStringToOutputStream(what, resp.getOutputStream());
  }

  /**
   * Taken from org.six11.util.io.StreamUtil.
   */
  public static void writeInputStreamToOutputStream(InputStream in, OutputStream out)
      throws IOException {
    byte[] buffer = new byte[256];
    int byteCount = 0;
    while ((byteCount = in.read(buffer)) >= 0) {
      out.write(buffer, 0, byteCount);
    }
  }

  /**
   * Taken from org.six11.util.io.StreamUtil.
   */
  public static void writeStringToOutputStream(String s, OutputStream out) throws IOException {
    ByteArrayInputStream bs = new ByteArrayInputStream(s.getBytes());
    writeInputStreamToOutputStream(bs, out);
  }

}
