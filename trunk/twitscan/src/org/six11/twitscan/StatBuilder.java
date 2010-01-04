package org.six11.twitscan;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.HashMap;
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
public class StatBuilder {
  public final static String FOLLOW_URL = "http://stream.twitter.com/1/statuses/filter.json";
  
  public static void main(String[] args) throws IOException, RecognitionException {
	    if (args.length != 1) {
	      System.err.println("Syntax: java -jar statbuilder.jar [property-file]");
	      System.err.println("\nConsider piping standard output to a text file. Status information ");
	      System.err.println("is sent to standard error.");
	      System.err.println("\nExample:");
	      System.err.println("java -jar statbuilder.jar >twitterstats.log");
	      System.exit(-1);
	    }
	    while (true) {
	      Properties props = TwitUtil.loadProperties(args[0], new String[] {"username", "password"} );
	      HashMap<String,Integer> stats = new HashMap<String,Integer>(65536);
	      PrintWriter log = new PrintWriter(new FileWriter("test.log"));
	      try {
	        HttpURLConnection ht = TwitUtil.makeConnection(FOLLOW_URL, props.getProperty("username"), props
	            .getProperty("password"), props.getProperty("keywords"));
	        
	        TwitUtil.connectAndCountKeywords(ht, log, stats);
	      } catch (Exception ex) {
	        System.err.println("Exception caught. Reconnecting in a few seconds...");
	        TwitUtil.nap(5000);
	      }
	    }
	  }

}
