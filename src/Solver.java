import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	public Solver(Board initial) {
		solvePuzzle(initial);
	}

	private void solvePuzzle(Board initial) {

		Board initialBoard = initial;
		Board twin = initial.twin();
		// solve twin and original simultaneously.
		solve(initialBoard, twin);
	}

	private void solve(Board puzzle, Board twinPuzzle) {
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		SearchNode initialNode = new SearchNode(null, puzzle);
		pq.insert(initialNode);

		MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
		SearchNode initialTwinNode = new SearchNode(null, puzzle);
		twinPQ.insert(initialTwinNode);

		while (true) {
			if(pq.isEmpty() || twinPQ.isEmpty()) break;
			SearchNode processedNode = pq.delMin();
			SearchNode processedTwinNode = twinPQ.delMin();
			if (processedNode.board.isGoal()) {
				goalNode = processedNode;
				solvable = true;
				break;
			
			} else if (processedTwinNode.board.isGoal()) {
				goalNode = processedNode;
				solvable = true;
				break;
			
			} else {
				processedNode.moves++;
				Iterable<Board> neighbors = processedNode.board
						.neighbors();

				for(Board neighbor: neighbors) {
					boolean match = false;

					for (SearchNode tracker = processedNode; tracker != null; tracker = tracker.root) {
						if (tracker.board.equals(neighbor)) {
							match = true;
							break;
						}
					}
					if (match != true) {
						pq.insert(new SearchNode(processedNode, neighbor));
					}
				}

				processedTwinNode.moves++;
				Iterable<Board> twinNeigbors = processedTwinNode.board
						.neighbors();

				for(Board twinNeighbor: twinNeigbors) {
					boolean twinMatch = false;

					for (SearchNode twinTracker = processedTwinNode; twinTracker != null; twinTracker = twinTracker.root) {
						if (twinTracker.board.equals(twinNeighbor)) {
							twinMatch = true;
							break;
						}
					}
					if (twinMatch != true) {
						twinPQ.insert(new SearchNode(processedTwinNode,
								twinNeighbor));
					}
				}
			}
		}
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
		Solver solver = new Solver(initial);

		// print solution to standard output

		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);

		}
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
		private int manhattan;
		private int priority;
	}
	
	

	private SearchNode goalNode = null;
	private boolean solvable = false;
}