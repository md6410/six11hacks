package org.six11.twitscan;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.antlr.runtime.RecognitionException;

/**
 * A Twitter scanner, configured by a properties file.
 * 
 * Example properties file:
 * 
 * <tt>
 * username=myCoolUserName 
 * password=ultraSecretPassword 
 * keywords=DARPA,redballoon,redballoons,10balloons,tenballoons
 * fields=text,user/screen_name,user/name,user/id
 * </tt>
 **/
public class TwitScan {
  private static StringBuffer tweetVals = new StringBuffer(1024);
  private static int numTweetsProcessed = 0;
  private static long timerMark = 0;
  private static DecimalFormat df = new DecimalFormat("#0.00");
  public final static String FOLLOW_URL = "http://stream.twitter.com/1/statuses/filter.json";
  private static int periodicUpdate = 50;
  
  public static void main(String[] args) throws IOException, RecognitionException {
	    if (args.length != 1) {
	      System.err.println("Syntax: java -jar twitscan.jar [property-file]");
	      System.err.println("\nConsider piping standard output to a text file. Status information ");
	      System.err.println("is sent to standard error.");
	      System.err.println("\nExample:");
	      System.err.println("java -jar twitscan.jar jello.props 1>>jello.txt");
	      System.exit(-1);
	    }
	    while (true) {
	      Properties props = TwitUtil.loadProperties(args[0]);
	      ArrayList<String> desiredFields = TwitUtil.extractDesiredFields(props);
	      try {
	        HttpURLConnection ht = TwitUtil.makeConnection(FOLLOW_URL, props.getProperty("username"), props
	            .getProperty("password"), props.getProperty("keywords"));
	        TwitUtil.processConnection(ht, desiredFields);
	      } catch (Exception ex) {
	        System.err.println("Exception caught. Reconnecting in a few seconds...");
	        TwitUtil.nap(5000);
	      }
	    }
	  }

}
