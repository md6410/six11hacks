package org.six11.olive2.analyzers;

import org.six11.util.Debug;
import org.six11.util.adt.Graph;
import org.six11.util.data.Statistics;
import org.six11.util.data.Lists;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Functions;
import org.six11.olive2.SketchAnalyzer;
import org.six11.olive2.StrokeEvent;
import org.six11.olive2.SketchBook;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.LineInterpretation;
import org.six11.olive2.interp.AdjacentLineInterpretation;
import org.six11.olive2.interp.PolygonInterpretation;
import org.six11.olive2.interp.SquareInterpretation;
import java.util.Collections;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 **/
public class PolygonAnalyzer extends SketchAnalyzer {

  public PolygonAnalyzer(SketchBook book) {
    super(book, false, false, true, true);
  }
  
  public void handleBegin(StrokeEvent ev) {
  }

  public void handleProgress(StrokeEvent ev) {
  }

  public void handleEnd(StrokeEvent ev) {
  }

  private void extractPolygon(Graph g, Graph.Edge ed) {
    List<LineInterpretation> linesA = new ArrayList<LineInterpretation>();
    List<LineInterpretation> linesB = new ArrayList<LineInterpretation>();
    Graph.Node cursor = ed.a;
    linesA.add((LineInterpretation)cursor.data);
    while (cursor.getPredecessor() != null) {
      cursor = cursor.getPredecessor();
      linesA.add((LineInterpretation)cursor.data);
    }
    cursor = ed.b;
    linesB.add((LineInterpretation)cursor.data);
    while (cursor.getPredecessor() != null) {
      cursor = cursor.getPredecessor();
      linesB.add((LineInterpretation)cursor.data);
    }
    Collections.reverse(linesB);
    linesB.remove(0);
    linesA.addAll(linesB);

    boolean redundant = false;
    List<LineInterpretation> otherLines;
    for (SketchInterpretation si : book.getInterpretations("polygon")) {
      otherLines = ((PolygonInterpretation) si).getLines();
      if (Lists.areListsSame(otherLines, linesA)) {
	redundant = true;
	break;
      }
    }
    if(!redundant) {
      PolygonInterpretation pi = new PolygonInterpretation(linesA);
      pi.informSketchBook();
      book.flagRecache();
    }
  }

  public void handleIntegral() {
    Debug.out("PolygonAnalyzer", "I'm going");
    List adjacentLines = book.getInterpretations("adjacent_line");
    AdjacentLineInterpretation adj;
    final Graph g = new Graph();
    Graph.EdgeCallback edgeProgress = new Graph.EdgeCallback() {
	public void run(Graph.Edge ed) {
	  extractPolygon(g, ed);
	}
      };
    g.setDirected(false);
    Graph.Node na, nb;
    Graph.Node bfsStart = null;
    for (int i=0; i < adjacentLines.size(); i++) {
      adj = (AdjacentLineInterpretation) adjacentLines.get(i);
      na = buildNode(g, adj.getLineA());
      if (!g.containsNode(na)) {
	g.addNode(na);
	if (bfsStart == null) {
	  bfsStart = na;
	}
      } else {
      }
      nb = buildNode(g, adj.getLineB());
      if (!g.containsNode(nb)) {
	g.addNode(nb);
      }
      g.addEdge(buildEdge(na, nb, adj, edgeProgress));

    }
    if (bfsStart != null) {
      Queue st = new LinkedList<Graph.Node>();
      st.offer(bfsStart);
      g.bfs(st);
    }
  }

  private String debugNode(Graph.Node n) {
    return "[ " + n.toString() + "#" + n.hashCode() + " (" + n.getStateStr() + "/parent=" + n.getPredecessor() + "#" + (n.getPredecessor() == null ? "" : n.getPredecessor().hashCode()) + ") ]";
  }

  private Graph.Node buildNode(Graph g, LineInterpretation line) {
    Graph.Node ret = null;
    List<Graph.Node> existing = g.getNodes(line);
    if (existing.size() == 0) {
      ret = new Graph.Node(line);
    } else if (existing.size() == 1) {
      ret = existing.get(0);
    }
    return ret;
  }

  private Graph.Edge buildEdge(Graph.Node na, Graph.Node nb, AdjacentLineInterpretation adj,
			       Graph.EdgeCallback edgeProgress) {
    Graph.Edge ret = new Graph.Edge(na, nb, adj);
    ret.setAction(Graph.BACK, edgeProgress);
    return ret;
  }


}
