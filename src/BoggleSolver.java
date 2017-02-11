import java.util.HashSet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

public class BoggleSolver {

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		if (dictionary == null)
			throw new NullPointerException();
		dict = new FastPrefixTST<Integer>();
		int i = 0;
		for (String word : dictionary) {
			dict.put(word, i);
			i++;
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an
	// Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		if (board == null)
			throw new NullPointerException();
		this.board = board;
		rows = this.board.rows();
		cols = this.board.cols();
		solutionWords = new HashSet<String>();
		solve();
		return solutionWords;
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through
	// Z.)
	public int scoreOf(String word) {
		if (word == null)
			throw new NullPointerException();
		if (dict.contains(word)) {
			if (word.length() > 2 && word.length() <= 4)
				return 1;
			else if (word.length() == 5)
				return 2;
			else if (word.length() == 6)
				return 3;
			else if (word.length() == 7)
				return 5;
			else if (word.length() >= 8)
				return 11;
		}
		return 0;
	}

	private void solve() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				search(i, j);
			}
		}
	}

	private void search(int sx, int sy) {
		marked = new boolean[rows * cols];
		dfs("", sx, sy);
	}

	private void dfs(String string, int sx, int sy) {
		// System.out.println(string);
		marked[valueOf(sx, sy)] = true;
		if (board.getLetter(sx, sy) == 'Q')
			string += "QU";
		else
			string += board.getLetter(sx, sy);

		if (dict.contains(string) && string.length() > 2) {
			solutionWords.add(string);
		}

		for (int w : adj(sx, sy)) {
			if (!marked[w] && containsPrefix(string))
				dfs(string, w / cols, w % cols);
		}
		string += string.substring(0, string.length() - 1);
		marked[valueOf(sx, sy)] = false;
	}

	private boolean containsPrefix(String str) {
		if (dict.hasPrefix(str))
			return true;
		return false;
	}

	private Iterable<Integer> adj(int row, int col) {
		Bag<Integer> adjacents = new Bag<Integer>();
		if (row == 0) {
			if (col == 0) {
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
			} else if (col == cols - 1) {
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col - 1));
				adjacents.add(valueOf(row, col - 1));
			} else {
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
				adjacents.add(valueOf(row + 1, col - 1));
				adjacents.add(valueOf(row, col - 1));

			}
		} else if (row == rows - 1) {
			if (col == 0) {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
			} else if (col == cols - 1) {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col - 1));
				adjacents.add(valueOf(row, col - 1));

			} else {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
				adjacents.add(valueOf(row - 1, col - 1));
				adjacents.add(valueOf(row, col - 1));

			}
		} else {
			if (col == 0) {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col + 1));
			} else if (col == cols - 1) {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col - 1));
				adjacents.add(valueOf(row, col - 1));
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col - 1));
			} else {
				adjacents.add(valueOf(row - 1, col));
				adjacents.add(valueOf(row - 1, col + 1));
				adjacents.add(valueOf(row, col + 1));
				adjacents.add(valueOf(row - 1, col - 1));
				adjacents.add(valueOf(row, col - 1));
				adjacents.add(valueOf(row + 1, col));
				adjacents.add(valueOf(row + 1, col - 1));
				adjacents.add(valueOf(row + 1, col + 1));

			}
		}
		return adjacents;
	}

	private Integer valueOf(int i, int j) {
		return (i * cols) + j;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		String[] dict = in.readAllLines();
		Stopwatch watch = new Stopwatch();
		BoggleBoard board = new BoggleBoard(args[1]);
		System.out.println(watch.elapsedTime());
		System.out.println(args[0] + " " + args[1]);
		int score = 0;
		BoggleSolver solver = new BoggleSolver(dict);
		for (String s : solver.getAllValidWords(board)) {
			System.out.println("The word is: " + s + "  and its score = "
					+ solver.scoreOf(s));
			score += solver.scoreOf(s);
		}
		System.out.println("Score: " + score);
		System.out.println("Elapsed Time: " + watch.elapsedTime());

	}

	/**
	 * New class to fast check (method hasPrefix()) if such prefix is in the
	 * trie
	 * 
	 * @Date 23.02.14
	 * @Author Princeton professors of Algorithms Java Course & Khokhlushin
	 *         Mikhail
	 * @param <Value>
	 *            value in key/value pair
	 */
	private static class FastPrefixTST<Value> {

		private int N; // size
		private Node root; // root of TST

		private class Node {
			private char c; // character
			private Node left, mid, right; // left, middle, and right subtries
			private Value val; // value associated with string
		}

		// return number of key-value pairs
		public int size() {
			return N;
		}

		/**************************************************************
		 * Is string key in the symbol table?
		 **************************************************************/
		public boolean contains(String key) {
			return get(key) != null;
		}

		public Value get(String key) {
			if (key == null)
				throw new NullPointerException();
			if (key.length() == 0)
				throw new IllegalArgumentException("key must have length >= 1");
			Node x = get(root, key, 0);
			if (x == null)
				return null;
			return x.val;
		}

		// return subtrie corresponding to given key
		private Node get(Node x, String key, int d) {
			if (key == null)
				throw new NullPointerException();
			if (key.length() == 0)
				throw new IllegalArgumentException("key must have length >= 1");
			if (x == null)
				return null;
			char c = key.charAt(d);
			if (c < x.c)
				return get(x.left, key, d);
			else if (c > x.c)
				return get(x.right, key, d);
			else if (d < key.length() - 1)
				return get(x.mid, key, d + 1);
			else
				return x;
		}

		/**************************************************************
		 * Insert string s into the symbol table.
		 **************************************************************/
		public void put(String s, Value val) {
			if (!contains(s))
				N++;
			root = put(root, s, val, 0);
		}

		private Node put(Node x, String s, Value val, int d) {
			char c = s.charAt(d);
			if (x == null) {
				x = new Node();
				x.c = c;
			}
			if (c < x.c)
				x.left = put(x.left, s, val, d);
			else if (c > x.c)
				x.right = put(x.right, s, val, d);
			else if (d < s.length() - 1)
				x.mid = put(x.mid, s, val, d + 1);
			else
				x.val = val;
			return x;
		}

		/**************************************************************
		 * Find and return longest prefix of s in TST
		 **************************************************************/
		public String longestPrefixOf(String s) {
			if (s == null || s.length() == 0)
				return null;
			int length = 0;
			Node x = root;
			int i = 0;
			while (x != null && i < s.length()) {
				char c = s.charAt(i);
				if (c < x.c)
					x = x.left;
				else if (c > x.c)
					x = x.right;
				else {
					i++;
					if (x.val != null)
						length = i;
					x = x.mid;
				}
			}
			return s.substring(0, length);
		}

		// all keys in symbol table
		public Iterable<String> keys() {
			Queue<String> queue = new Queue<String>();
			collect(root, "", queue);
			return queue;
		}

		// all keys starting with given prefix
		public Iterable<String> prefixMatch(String prefix) {
			Queue<String> queue = new Queue<String>();
			Node x = get(root, prefix, 0);
			if (x == null)
				return queue;
			if (x.val != null)
				queue.enqueue(prefix);
			collect(x.mid, prefix, queue);
			return queue;
		}

		// all keys in subtrie rooted at x with given prefix
		private void collect(Node x, String prefix, Queue<String> queue) {
			if (x == null)
				return;
			collect(x.left, prefix, queue);
			if (x.val != null)
				queue.enqueue(prefix + x.c);
			collect(x.mid, prefix + x.c, queue);
			collect(x.right, prefix, queue);
		}

		// return all keys matching given wildcard pattern
		public Iterable<String> wildcardMatch(String pat) {
			Queue<String> queue = new Queue<String>();
			collect(root, "", 0, pat, queue);
			return queue;
		}

		private void collect(Node x, String prefix, int i, String pat,
				Queue<String> q) {
			if (x == null)
				return;
			char c = pat.charAt(i);
			if (c == '.' || c < x.c)
				collect(x.left, prefix, i, pat, q);
			if (c == '.' || c == x.c) {
				if (i == pat.length() - 1 && x.val != null)
					q.enqueue(prefix + x.c);
				if (i < pat.length() - 1)
					collect(x.mid, prefix + x.c, i + 1, pat, q);
			}
			if (c == '.' || c > x.c)
				collect(x.right, prefix, i, pat, q);
		}

		// has TST word with this prefix?
		public boolean hasPrefix(String prefix) {
			Node prefixNode = get(root, prefix, 0);
			if (prefixNode == null)
				return false;
			if (prefixNode.val != null)
				return true;
			if (prefixNode.left == null && prefixNode.mid == null
					&& prefixNode.right == null)
				return false;
			return true;
		}

	}

	private FastPrefixTST<Integer> dict;
	private BoggleBoard board;
	private int rows;
	private int cols;
	private HashSet<String> solutionWords;
	private boolean[] marked;
}
