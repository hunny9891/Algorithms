/*
 * Author: Himanshu Thakur
 * Filename: Percolation.java
 * -------------------------------------------------
 * Creates a Percolation data structure with the specified
 * methods in assignment.
 */

import edu.princeton.cs.algs4.*;

public class Percolation {
	private static final int OPEN = 1;
	

	/*
	 * Constructor: Percolation Usage: Percolation(N)
	 * ---------------------------- Instantiates a Percolation object.
	 */
	public Percolation(int n) {
		if (n < 1)
			throw new IllegalArgumentException();
		count = n;
		id = new int[count * count];
		unionFind = new WeightedQuickUnionUF(count * count + 3);
		uf = new WeightedQuickUnionUF(count * count + 2);
		topSite = count * count + 1;
		bottomSite = count * count + 2;
	}

	/*
	 * Method Name: open Usage: open(i, j) ----------------------- Open a site
	 * specified by i,j and unites the sites adjacent to it if they are open.
	 */

	public void open(int i, int j) throws java.lang.IndexOutOfBoundsException {
		if (i < 1 || j < 1 || i > count || j > count)
			throw new IndexOutOfBoundsException();
		int index = indexOf(i, j);
		if (!isOpen(i, j)) {
			id[index] = OPEN;
		}
		// Check if adjacent is open or not.

		// If it is the top row
		if (i != 1 && isOpen(i - 1, j)) {
			unionFind.union(indexOf(i - 1, j), index);
			uf.union(indexOf(i - 1, j), index);
		} else if (i == 1) {
			// connect to virtual top cell
			unionFind.union(index, topSite);
			uf.union(index, topSite);
		}
		// if not bottom row
		if (i != count && isOpen(i + 1, j)) {
			unionFind.union(indexOf(i + 1, j), index);
			uf.union(indexOf(i + 1, j), index);
		} else if (i == count) {
			unionFind.union(index, bottomSite);
		}
		// if not left border
		if (j != 1 && isOpen(i, j - 1)) {
			unionFind.union(indexOf(i, j - 1), index);
			uf.union(indexOf(i, j - 1), index);
		}
		// if not right border
		if (j != count && isOpen(i, j + 1)) {
			unionFind.union(indexOf(i, j + 1), index);
			uf.union(indexOf(i, j + 1), index);
		}

	}

	/*
	 * Method Name: isOpen Usage: isOpen(i, j) --------------------------
	 * Returns a true if site i.j is open else returns false
	 */
	public boolean isOpen(int i, int j)
			throws java.lang.IndexOutOfBoundsException {
		boolean result = false;
		if (i < 1 || j < 1 || i > count || j > count)
			throw new java.lang.IndexOutOfBoundsException();

		if (id[indexOf(i, j)] == OPEN) {
			result = true;
		}
		return result;
	}

	/*
	 * Method Name: isFull Usage: isFull(i, j) ---------------------- Returns
	 * true if i,j site is connected to any site in top row.
	 */
	public boolean isFull(int i, int j)
			throws java.lang.IndexOutOfBoundsException {
		if (i < 1 || j < 1 || i > count || j > count)
			throw new java.lang.IndexOutOfBoundsException();
		if (unionFind.connected(topSite, indexOf(i, j)) && uf.connected(topSite, indexOf(i, j)))
			return true;
		else
			return false;
	}

	/*
	 * Method Name: percolates Usage: percolates() -----------------------
	 * Returns true if system percolates.
	 */
	public boolean percolates() {
		if (unionFind.connected(topSite, bottomSite))
			return true;
		else
			return false;
	}

	/*
	 * Method Name: indexOf Usage: indexOf(i, j) ------------------------
	 * Calculates the absolute value of the site. For example (1,1) is the first
	 * site and it will return 0, (2,2) is the 4th site and it will return 3 and
	 * so on.
	 * 
	 * Basically converts 2D input to 1D.
	 */
	private int indexOf(int i, int j) {
		if (i < 1 || j < 1 || i > count || j > count)
			return -1;
		return ((i - 1) * (count)) + (j - 1);

	}

	/*
	 * Unit Test
	 */

	public static void main(String args[]) {

		System.out.println("Please enter N");
		System.out.println();
		int N = StdIn.readInt();

		// for(int N = 3; N < 5;N++) {
		Percolation percolation = new Percolation(N);
		Stopwatch sw = new Stopwatch();
		while (!percolation.percolates()) {
			int x = StdRandom.uniform(1, N + 1);
			int y = StdRandom.uniform(1, N + 1);
			System.out.println("---------------------");
			System.out.println("Selected Coordintaes: " + x + " " + y);
			System.out.println("---------------------");
			if (!percolation.isOpen(x, y))
				percolation.open(x, y);
		}
		double timeElapsed = sw.elapsedTime();
		System.out.println("System Percolates!!! Yae");
		System.out.println("Time taken = " + timeElapsed);
		// }

	}

	// Private Instance Variables.
	
	private int id[] = null;
	private int count = -1;
	private WeightedQuickUnionUF unionFind = null;
	private WeightedQuickUnionUF uf = null;
	private int topSite = -1;
	private int bottomSite = -1;
}
