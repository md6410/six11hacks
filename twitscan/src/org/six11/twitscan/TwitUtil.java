package org.six11.twitscan;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.six11.util.io.StreamUtil;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import sun.misc.Regexp;

/**
 * Twitter Utility functions container
 **/
public class TwitUtil {
  private static StringBuffer tweetVals = new StringBuffer(1024);
  private static int numTweetsProcessed = 0;
  private static long timerMark = 0;
  private static DecimalFormat df = new DecimalFormat("#0.00");
  public final static String FOLLOW_URL = "http://stream.twitter.com/1/statuses/filter.json";
  private static int periodicUpdate = 500;
  private static int dotUpdate = 50;

  public static ArrayList<String> extractDesiredFields(Properties props) {
    ArrayList<String> desiredFields = new ArrayList<String>();
    String fieldsList = props.getProperty("fields");
    StringTokenizer toks = new StringTokenizer(fieldsList, ",");
    while (toks.hasMoreTokens()) {
      String tok = toks.nextToken().trim();
      if (tok.length() > 0) {
        desiredFields.add(tok);
      }
    }
    return desiredFields;
  }

  public static void nap(long sleepyTime) {
    try {
      Thread.sleep(sleepyTime);
    } catch (InterruptedException ex) {
      // ignore
    }
  }

  public static Properties loadProperties(String fileName, String requiredProperties[]) throws FileNotFoundException,
      IOException {
    Properties props = new Properties();
    File propFile = new File(fileName);
    if (!propFile.exists()) {
      System.err.println("File '" + fileName + "' does not exist.");
      System.exit(-1);
    }
    props.load(new FileInputStream(fileName));
    for (int i = 0; i < requiredProperties.length; i++)
    	checkProperty(props, requiredProperties[i]);
    
    String keywords = props.getProperty("keywords");
    System.err.print("Keywords: ");
    if (keywords != null) {
      StringTokenizer keywordTok = new StringTokenizer(keywords, ",");
      while (keywordTok.hasMoreTokens()) {
    	System.err.print(keywordTok.nextToken() + ' ');
      }
    }
    if (props.containsKey("periodicUpdate")) {
      periodicUpdate = Integer.parseInt(props.getProperty("periodicUpdate"));
    }
    return props;
  }

  public static void checkProperty(Properties props, String key) {
    if (!props.containsKey(key)) {
      System.err.println("Please ensure the property file contains an entry for '" + key + "'");
      System.exit(-1);
    }
  }

  public static HttpURLConnection makeConnection(String where, String username, String password,
      String keywords) throws IOException {
    URL url = new URL(where);
    System.err.println("Connecting...");
    HttpURLConnection ht = (HttpURLConnection) url.openConnection();
    ht.setRequestMethod("POST");
    ht.setUseCaches(false);
    ht.setDoOutput(true);
    ht.setDoInput(true);

    String authString = base64Encode(username + ":" + password);
    ht.setRequestProperty("Authorization", "Basic " + authString);
    String words = "track=" + keywords;
    byte[] bytes = words.getBytes();
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    OutputStream out = ht.getOutputStream();
    StreamUtil.writeInputStreamToOutputStream(in, out);
    out.flush();
    out.close();
    return ht;
  }

  public static void processConnection(HttpURLConnection ht, ArrayList<String> desiredFields)
      throws IOException {
    System.err.println("Connected. Processing tweets...");
    BufferedInputStream buf = new BufferedInputStream(ht.getInputStream());
    BufferedReader bufReader = new BufferedReader(new InputStreamReader(buf));
    try {
      while (true) {
        String tweet = bufReader.readLine();
        if (tweet.length() > 0) {
          processTweet(tweet, desiredFields);
        }
        try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
        }
      }
    } catch (Exception ignore) {
    }
  }

  public static void processTweet(String tweet, ArrayList<String> desiredFields) {
    if (numTweetsProcessed == 0) {
      timerMark = System.currentTimeMillis();
    }
    ANTLRStringStream input = new ANTLRStringStream(tweet);
    JSONLexer lexer = new JSONLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    JSONParser parser = new JSONParser(tokens);
    try {
      CommonTree t = (CommonTree) parser.jsonObject().getTree();
      tweetVals.setLength(0);
      for (String field : desiredFields) {
        for (int i = 0; i < t.getChildCount(); i++) {
          String val = processNode(field, t.getChild(i));
          if (val != null) {
            tweetVals.append('>').append(field).append('\t').append(val).append('\n');
            // tweetValues.put(field, val);
          }
        }
      }
      numTweetsProcessed++;
      System.err.print(".");
      if ((numTweetsProcessed % periodicUpdate) == 0) {
        long now = System.currentTimeMillis();
        long duration = now - timerMark;
        double speed = 1000d * ((double) periodicUpdate / ((double) duration));
        timerMark = now;
        System.err
            .print(" (" + numTweetsProcessed + " tweets processed. Last " + periodicUpdate
                + " took " + convertTime(duration) + ", approx. " + df.format(speed)
                + " tweets per second)\n");
      }
      System.out.println(tweetVals);
    } catch (RecognitionException ex) {
      ex.printStackTrace();
    }
  }

  private static String convertTime(long origDuration) {
    long duration = origDuration;
    StringBuilder ret = new StringBuilder();
    long h, m, s;
    long H = 1000 * 60 * 60;
    long M = 1000 * 60;
    long S = 1000;
    h = duration / H;
    duration -= h * H;
    m = duration / M;
    duration -= m * M;
    s = duration / S;
    if (h > 0) {
      ret.append(h + "h ");
    }
    if (h > 0 || m > 0) {
      ret.append(m + "m ");
    }
    ret.append(s + "s");
    return ret.toString();
  }

  private static int avgPeriod = 60 * 10 * 1000; // 10 minute averaging period
  /**
   * con
   */
  public static void connectAndCountKeywords(HttpURLConnection ht, PrintWriter logFile, HashMap<String,Integer> histogram)
  throws IOException {
System.err.println("Connected. Counting keywords in tweets...");
BufferedInputStream buf = new BufferedInputStream(ht.getInputStream());
BufferedReader bufReader = new BufferedReader(new InputStreamReader(buf));
long t0 = System.currentTimeMillis();
long endTime = t0 + avgPeriod;
try {
  while (true) {
	long now = System.currentTimeMillis();
	if (now > endTime) {
		logFile.println(t0 + " ==============================");
		for (Iterator<Entry<String, Integer>> i = histogram.entrySet().iterator(); i.hasNext(); ) {
			Entry<String, Integer> entry = i.next();
			logFile.println(entry.getKey() + ": " + entry.getValue());
		}
		t0 = endTime;
		endTime += avgPeriod;
		histogram.clear();
	}
    String tweet = bufReader.readLine();
    if (tweet.length() > 0) {
      countKeywords(tweet, histogram);
    }
    try {
      Thread.sleep(250);
    } catch (InterruptedException e) {
    }
  }
} catch (Exception ignore) {
}
}

private static void displayTweetStats() {
  numTweetsProcessed++;
  if ((numTweetsProcessed % dotUpdate) != 0)
	  return;
  System.err.print(".");
  if ((numTweetsProcessed % periodicUpdate) != 0)
	  return;
  long now = System.currentTimeMillis();
  long duration = now - timerMark;
  double speed = 1000d * ((double) periodicUpdate / ((double) duration));
  timerMark = now;
  System.err.print(" (" + numTweetsProcessed + " tweets processed. Last " + periodicUpdate
		  + " took " + convertTime(duration) + ", approx. " + df.format(speed)
	      + " tweets per second)\n");
}

//private static Regexp separator = new Regexp("[\\,\\.\\!\\?\\-]+\\s*|\\s+]");

public static void countKeywords(String tweet, HashMap<String,Integer> histogram) {
if (numTweetsProcessed == 0) {
  timerMark = System.currentTimeMillis();
}

ANTLRStringStream input = new ANTLRStringStream(tweet);
JSONLexer lexer = new JSONLexer(input);
CommonTokenStream tokens = new CommonTokenStream(lexer);
JSONParser parser = new JSONParser(tokens);
try {
  CommonTree t = (CommonTree) parser.jsonObject().getTree();
  for (int i = 0; i < t.getChildCount(); i++) {
	  String val = processNode("text", t.getChild(i));
	  if (val != null) {
		  String[] list = val.split("[\\,\\.\\!\\?\\-]+\\s*|\\s+]");
		  for (int j = 0; j < list.length; j++) {
			  histogram.put(list[j], histogram.get(list[j]) + 1);
		  }			  
	  }
  }
} catch (RecognitionException ex) {
  ex.printStackTrace();
}
}
  
  
  private static String processNode(String field, Tree t) {
    String ret = null;
    switch (t.getType()) {
      case JSONLexer.ELEMENT:
        String startOfField = getFirstOfField(field);
        String restOfField = getRestOfField(field);
        if (t.getChild(0).getText().equals("\"" + startOfField + "\"")) {
          ret = processNode(restOfField, t.getChild(1));
        }
        break;
      case JSONLexer.STRING:
      case JSONLexer.INTEGER:
        ret = t.getChild(0).getText();
        break;
      case JSONLexer.OBJECT:
        for (int i = 0; i < t.getChildCount(); i++) {
          String v = processNode(field, t.getChild(i));
          if (v != null) {
            ret = v;
            break;
          }
        }
        break;
      default:
        System.err.println("Unknown node type in processNode: "
            + JSONParser.tokenNames[t.getType()]);
    }
    return ret;
  }

  private static String getFirstOfField(String field) {
    String ret = field;
    if (field.indexOf('/') > 0) {
      ret = field.substring(0, field.indexOf('/'));
    }
    return ret;
  }

  private static String getRestOfField(String field) {
    String ret = null;
    if (field != null && field.indexOf('/') > 0) {
      ret = field.substring(field.indexOf('/') + 1);
    }
    return ret;
  }

  public static String base64Encode(String s) {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    Base64OutputStream out = new Base64OutputStream(bOut);
    try {
      out.write(s.getBytes());
      out.flush();
    } catch (IOException exception) {
    }
    return bOut.toString();
  }

  // debugging member
  public static Map<Integer, String> spaceMap = new HashMap<Integer, String>();

  // debugging method
  public static void processTree(Tree t, int indent) {
    System.out.println(spaces(3 * indent) + bug(t, true));
    for (int i = 0; i < t.getChildCount(); i++) {
      processTree(t.getChild(i), indent + 1);
    }
  }

  // debugging method
  public static String spaces(int n) {
    if (!spaceMap.containsKey(n)) {
      StringBuffer buf = new StringBuffer();
      for (int i = 0; i < n; i++) {
        buf.append(" ");
      }
      spaceMap.put(n, buf.toString());
    }
    return spaceMap.get(n);
  }

  // debugging method
  public static String bug(Tree t, boolean bugChildrenTypes) {
    String childString = "" + t.getChildCount();
    if (bugChildrenTypes) {
      childString = "[";
      for (int i = 0; i < t.getChildCount(); i++) {
        childString += t.getChild(i).getText();
        if (i < (t.getChildCount() - 1)) {
          childString += ", ";
        }
      }
      childString += "]";
    }
    return t.getText() + " line: " + t.getLine() + ", " + "col: " + t.getCharPositionInLine()
        + " type: " + JSONParser.tokenNames[t.getType()] + " children: " + childString;
  }

}
