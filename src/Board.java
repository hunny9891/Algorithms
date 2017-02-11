import edu.princeton.cs.algs4.*;

public class Board {
	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		N = blocks[0].length;
		board = constructBoard(blocks);
	}

	private int[][] constructBoard(int[][] blocks) {
		int[][] tempBoard = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tempBoard[i][j] = blocks[i][j];
			}
		}

		return tempBoard;

	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		// board dimension N
		return N;
	}

	public int hamming() {
		// number of blocks out of place
		int numBlocksOutOfPlace = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N - 1 && j == N - 1)
					break;
				if (board[i][j] != valueOf(i, j))
					numBlocksOutOfPlace++;
			}
		}
		return numBlocksOutOfPlace;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int totalManhattan = 0;
		int difference = 0;
		int row = -1, col = -1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] != valueOf(i, j)) {
					if (board[i][j] == 0) {
						continue;
					} else {
						row = (board[i][j] - 1) / N;
						col = (board[i][j] - 1) % N;
						difference = Math.abs(((row - i)))
								+ Math.abs((col - j));
					}
					totalManhattan += difference;
					difference = 0;
				}
			}
		}
		return totalManhattan;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	private int valueOf(int i, int j) {
		return (N * i) + (j + 1);
	}

	public Board twin() {
		int[][] twinBlocks = new int[N][N];
		twinBlocks = constructBoard(board);
		while (true) {
			int randomRow = StdRandom.uniform(N);
			while (true) {
				int columnIndexOne = StdRandom.uniform(N);
				int columnIndexTwo = -1;
				if (columnIndexOne == 0) {
					columnIndexTwo = columnIndexOne + 1;
				} else if (columnIndexOne == N - 1) {
					columnIndexTwo = columnIndexOne - 1;
				} else {
					columnIndexTwo = StdRandom.uniform(columnIndexOne - 1,
							columnIndexOne + 2);
				}
				if (columnIndexOne != columnIndexTwo
						&& !(twinBlocks[randomRow][columnIndexOne] == 0 || twinBlocks[randomRow][columnIndexTwo] == 0)) {
					exchAdjacent(twinBlocks, randomRow, columnIndexOne,
							columnIndexTwo);
					break;
				}
				if (randomRow + 1 > N - 1) {
					randomRow--;
				} else
					randomRow++;

			}

			break;
		}
		Board twinBoard = new Board(twinBlocks);
		return twinBoard;
	} // a board that is obtained by exchanging two adjacent blocks in the same

	private void exchAdjacent(int[][] twinBlocks, int randomRow,
			int columnIndexOne, int columnIndexTwo) {
		int temp = twinBlocks[randomRow][columnIndexOne];
		twinBlocks[randomRow][columnIndexOne] = twinBlocks[randomRow][columnIndexTwo];
		twinBlocks[randomRow][columnIndexTwo] = temp;

	}

	// row

	public boolean equals(Object y) {
		// does this board equal y?
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		boolean result = true;
		Board that = (Board) y;
		if (that.dimension() != this.dimension())
			return false;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				if (this.board[i][j] != that.board[i][j]) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		Queue<Board> neighborQueue = new Queue<Board>();
		int blankRow = -1, blankCol = -1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 0) {
					blankRow = i;
					blankCol = j;
					break;
				}
			}
		}
		int[][] tempBlocks = null;
		if (blankRow == 0) {
			tempBlocks = exch(blankRow, blankCol, blankRow + 1, blankCol);
			neighborQueue.enqueue(new Board(tempBlocks));
			if (blankCol == 0) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else if (blankCol == dimension() - 1) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			}
		} else if (blankRow == dimension() - 1) {
			tempBlocks = exch(blankRow, blankCol, blankRow - 1, blankCol);
			neighborQueue.enqueue(new Board(tempBlocks));
			if (blankCol == 0) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else if (blankCol == dimension() - 1) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			}
		} else {
			tempBlocks = exch(blankRow, blankCol, blankRow + 1, blankCol);
			neighborQueue.enqueue(new Board(tempBlocks));
			tempBlocks = exch(blankRow, blankCol, blankRow - 1, blankCol);
			neighborQueue.enqueue(new Board(tempBlocks));
			if (blankCol == 0) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else if (blankCol == dimension() - 1) {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			} else {
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol + 1);
				neighborQueue.enqueue(new Board(tempBlocks));
				tempBlocks = exch(blankRow, blankCol, blankRow, blankCol - 1);
				neighborQueue.enqueue(new Board(tempBlocks));
			}
		}
		return neighborQueue;
	}

	private int[][] exch(int blankRow, int blankCol, int i, int j) {
		int[][] neighbor = constructBoard(board);
		int temp = neighbor[blankRow][blankCol];
		neighbor[blankRow][blankCol] = neighbor[i][j];
		neighbor[i][j] = temp;
		return neighbor;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		System.out.println(initial.manhattan());

		System.out.println(initial.twin().toString());

	}

	// Private Instance Variables.
	private int[][] board = null;
	private int N = -1;

}
