import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class IDAStarSolver {

	public IDAStarSolver(Board initial) {
		SearchNode initialNode = new SearchNode(null, initial);
		IDAStar(initialNode);
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if (!isSolvable())
			return -1;
		else
			return this.goalNode.moves;
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (!isSolvable())
			return null;
		else {
			Stack<Board> goalPath = new Stack<Board>();
			for (SearchNode curr = goalNode; curr != null; curr = curr.root) {
				goalPath.push(curr.board);
			}
			return goalPath;
		}
	}

	private void IDAStar(SearchNode initial) {
		double limit = initial.board.manhattan();
		ResultNode result = null;
		while (true) {
			result = contour(initial, limit);
			if (result.getLimit() == Double.POSITIVE_INFINITY)
				break;
			if (result.getNode() != null) {
				if (result.getNode().board.isGoal()) {
					solvable = true;
					goalNode = result.getNode();
					break;
				}
			}
		}
	}

	private ResultNode contour(SearchNode currNode, double limit) {
		double cost = currNode.priority;
		if (limit < cost)
			return new ResultNode(null, limit);

		if (currNode.board.isGoal()) {
			solvable = true;
			return new ResultNode(currNode, cost);
		}

		double newLimit = Double.POSITIVE_INFINITY;
		currNode.moves++;
		Iterable<Board> neighbors = currNode.board.neighbors();
		ResultNode result = null;

		for (Board neighbor : neighbors) {
			while (true) {
				result = contour(new SearchNode(currNode, neighbor), limit);
				if (result.node != null && result.node.board.isGoal() )
					return result;
				newLimit = min(newLimit, cost);
			}
		}

		return new ResultNode(null, newLimit);
	}

	private double min(double newLimit, double cost) {
		if (newLimit <= cost)
			return newLimit;
		else
			return cost;
	}

	private class SearchNode implements Comparable<SearchNode> {
		public SearchNode(SearchNode root, Board board) {
			if (root == null) {
				this.moves = 0;

			} else {
				this.moves = root.moves;
			}
			this.root = root;
			this.board = board;
			this.manhattan = this.board.manhattan();
			this.priority = this.manhattan + this.moves;
		}

		@Override
		public int compareTo(SearchNode that) {
			if (this.priority < that.priority)
				return -1;
			else if (this.priority > that.priority)
				return 1;
			else
				return 0;
		}

		// Private Instance Variables
		public Board board;
		public int moves;
		public SearchNode root;
		public int manhattan;
		public int priority;
	}

	private class ResultNode {

		public ResultNode(SearchNode node, double limit) {
			this.limit = limit;
			this.node = node;
		}

		public double getLimit() {
			return limit;
		}

		public SearchNode getNode() {
			return node;
		}

		private double limit;
		private SearchNode node;
	}

	private boolean solvable = false;
	private SearchNode goalNode;

	/****************************************************************************************************************
	 * unit Test
	 * 
	 * @param args
	 ****************************************************************************************************************/
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();

		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		IDAStarSolver solver = new IDAStarSolver(initial);

		// print solution to standard output

		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);

		}

	}

}
