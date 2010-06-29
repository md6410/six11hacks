package org.six11.understand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.six11.util.Debug;

/**
 * 
 **/
public class Main {
  public static void main(String[] args) throws IOException {
    Map<String, Integer> frequencies = new HashMap<String, Integer>();
    for (String fileName : args) {
      bug("Processing " + fileName);
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      while (in.ready()) {
        String line = in.readLine();
        int lastSpace = line.lastIndexOf("\t");
        // String key = line.substring(0, lastSpace);
        String val = line.substring(lastSpace + 1);
        updateFreq(val, frequencies);
      }
    }
    SortedSet<Word> words = new TreeSet<Word>();
    for (String key : frequencies.keySet()) {
      words.add(new Word(key, frequencies.get(key)));
    }
    for (Word word : words) {
      System.out.println(word.word + "\t" + word.freq);
    }
  }

  private static class Word implements Comparable<Word> {

    String word;
    int freq;

    Word(String word, int freq) {
      this.word = word;
      this.freq = freq;
    }

    public int compareTo(Word o) {
      int ret = 0;
      if (this.freq < o.freq) {
        ret = 1;
      } else if (this.freq > o.freq) {
        ret = -1;
      } else {
        ret = this.word.compareTo(o.word);
      }
      return ret;
    }
  }

  private static void updateFreq(String val, Map<String, Integer> frequencies) {
    if (!frequencies.containsKey(val)) {
      frequencies.put(val, 0);
    }
    frequencies.put(val, frequencies.get(val) + 1);
  }

  private static void bug(String what) {
    Debug.out("Main", what);
  }

}
