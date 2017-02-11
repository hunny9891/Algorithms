import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new NullPointerException();
		graph = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		checkBounds(v, w);
		Node results = simultaneousBFS(v, w);
		return results.getAncestorDistance();

	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		checkBounds(v, w);
		Node results = simultaneousBFS(v, w);
		return results.getAncestor();

	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		checkNull(v, w);
		ArrayList<Integer> lenList = new ArrayList<Integer>();
		Iterator itrV = v.iterator();
		Iterator itrW = w.iterator();

		if (!itrV.hasNext() && itrW.hasNext()) {
			return -1;
		} else if (itrV.hasNext() && !itrW.hasNext()) {
			return -1;
		} else if (!itrV.hasNext() && !itrW.hasNext()) {
			return -1;
		} else {
			for (int vertex1 : v) {
				for (int vertex2 : w) {
					if (length(vertex1, vertex2) == -1)
						lenList.add(Integer.MAX_VALUE);
					lenList.add(length(vertex1, vertex2));
				}
			}
			return Collections.min(lenList);
		}

	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		checkNull(v, w);
		ArrayList<Integer> ancestorList = new ArrayList<Integer>();
		ArrayList<Integer> lenList = new ArrayList<Integer>();
		Iterator itrV = v.iterator();
		Iterator itrW = w.iterator();

		if (!itrV.hasNext() && itrW.hasNext()) {
			return -1;
		} else if (itrV.hasNext() && !itrW.hasNext()) {
			return -1;
		} else if (!itrV.hasNext() && !itrW.hasNext()) {
			return -1;
		} else {
			for (int vertex1 : v) {
				for (int vertex2 : w) {
					if (ancestor(vertex1, vertex2) == -1) {
						ancestorList.add(Integer.MAX_VALUE);
						lenList.add(Integer.MAX_VALUE);
					}
					lenList.add(length(vertex1, vertex2));
					ancestorList.add(ancestor(vertex1, vertex2));

				}
			}
			int minLen = Collections.min(lenList);
			int minIndex = lenList.indexOf(minLen);

			return ancestorList.get(minIndex);
		}

	}

	private Node simultaneousBFS(int v, int w) {
		Queue<Integer> qForV = new Queue<Integer>();
		Queue<Integer> qForW = new Queue<Integer>();
		int V = graph.V();

		int[] distFromV = new int[V];
		int[] distFromW = new int[V];

		boolean[] markedForV = new boolean[V];
		boolean[] markedForW = new boolean[V];

		markedForV[v] = true;
		markedForW[w] = true;

		qForV.enqueue(v);
		qForW.enqueue(w);

		while (!qForV.isEmpty()) {
			int s = qForV.dequeue();
			for (int d : graph.adj(s)) {
				if (!markedForV[d]) {
					distFromV[d] = distFromV[s] + 1;
					markedForV[d] = true;
					qForV.enqueue(d);
				}
			}
		}

		while (!qForW.isEmpty()) {
			int s = qForW.dequeue();
			for (int d : graph.adj(s)) {
				if (!markedForW[d]) {
					distFromW[d] = distFromW[s] + 1;
					markedForW[d] = true;
					qForW.enqueue(d);
				}
			}
		}

		int ancestor = -1;
		int ancestorDistance = 0;
		for (int i = 0; i < V; i++) {
			if (markedForV[i] && markedForW[i]) {
				if (ancestor == -1) {
					ancestor = i;
				}
				if (distFromV[ancestor] + distFromW[ancestor] > distFromV[i]
						+ distFromW[i]) {
					ancestor = i;

				}
			}

		}

		if (ancestor != -1)
			ancestorDistance = distFromV[ancestor] + distFromW[ancestor];
		else
			ancestorDistance = -1;

		Node node = new Node(ancestor, ancestorDistance);
		return node;

	}

	private void checkNull(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new NullPointerException();

	}

	private void checkBounds(int v, int w) {
		if (v < 0 || v > graph.V() - 1 || w < 0 || w > graph.V() - 1) {
			throw new IndexOutOfBoundsException();
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

	private class Node {
		private int ancestor;
		private int ancestorDistance;

		public Node(int ancestor, int ancestorDistance) {
			this.ancestor = ancestor;
			this.ancestorDistance = ancestorDistance;
		}

		public int getAncestor() {
			return ancestor;
		}

		public int getAncestorDistance() {
			return ancestorDistance;
		}
	}

	private Digraph graph;

}
