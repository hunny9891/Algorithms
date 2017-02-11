/*
 * Author: Himanshu Thakur
 * Filename: PercolationStats.java
 * -------------------------------------------------
 * This program runs the Monte Carlo Simulation on a 
 * scientific model which has to be percolated.
 * The experiment calculates the sample mean, sample 
 * standard deviation and confidence interval for the
 * system.
 */
import edu.princeton.cs.algs4.*;

public class PercolationStats {
	/*
	 * Constructor: PercolationStats
	 * Usage: PercolationStats(N, T)
	 * -------------------------------
	 * Instantiates a new PercolationStats object.
	 */
	public PercolationStats(int N, int T) {
		if (N < 1 || T < 1)
			throw new IllegalArgumentException();
		this.T = T;
		count = N;
		getThresholdArray();
	}

	/*
	 * Method Name: calculateThreshold 
	 * Usage: caclulateThreshold()
	 * ---------------------------------- 
	 * This method calculates the threshold
	 * probability for the model i.e an N-by-N grid in our case.
	 */
	private double calculateThreshold() {
		Percolation percolation = new Percolation(count);
		int totalOpened = 0;
		while (!percolation.percolates()) {
			int chosenSiteX = StdRandom.uniform(1, count + 1); // Choosen x
																// coordinate at
																// random.
			int chosenSiteY = StdRandom.uniform(1, count + 1); // Choosen y
																// coordinate at
																// random.
			if (!percolation.isOpen(chosenSiteX, chosenSiteY)) {
				percolation.open(chosenSiteX, chosenSiteY);
				totalOpened++;
			}
		}
		return (double) totalOpened / (double) (count * count);
	}

	/*
	 * Method Name: getThresholdArray 
	 * Usage: getThresholdArray()
	 * --------------------------------------------------------- 
	 * This method builds the threshold array for T number of thresholds calculated at each
	 * iteration.
	 */
	private void getThresholdArray() {
		threshold = new double[T];
		for (int i = 0; i < T; i++) {
			threshold[i] = calculateThreshold();
		}
	}

	/*
	 * Method Name: mean Usage: mean() 
	 * -------------------- 
	 * Calculates the mean of the thresholdArray.
	 */
	public double mean() {
		//getThresholdArray();
		/*
		 * Alternate Method
		 * double totalThreshold = 0.0; 
		 * for (int i = 0; i < T; i++) { totalThreshold += threshold[i]; }
		 */
		return StdStats.mean(threshold);
	}

	/*
	 * Method Name: stdev 
	 * Usage: stdev() 
	 * --------------------
	 *  Calculates the Standard Deviation of the thresholdArray.
	 */
	public double stddev() {
		//getThresholdArray();

		/* 
		 * Alternate method
		 * double stDevNum = 0.0; double u = mean(); for (int i = 0; i < T; i++)
		 * { stDevNum += Math.pow((threshold[i] - u), 2); }
		 */
		return StdStats.stddev(threshold);
	}

	/*
	 * Method Name: confidenceLow 
	 * Usage: confidenceLow() 
	 * --------------------
	 * Calculates the low confidence of the given system.
	 */
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(T));
	}

	/*
	 * Method Name: confidenceHigh 
	 * Usage: confidenceHigh() 
	 * --------------------
	 * Calculates the high confidence of the given system.
	 */
	public double confidenceHi() {

		return mean() + ((1.96 * stddev()) / Math.sqrt(T));
	}
	
	/*
	 * Method Name: main
	 * Usage: main
	 * -----------------------
	 * Unit test for this program.
	 */
	public static void main(String[] args) {
		Stopwatch sw = new Stopwatch();
		System.out.println("Testing Percolation Stats");
		System.out.println("----------------------");
		// System.out.println("Enter N");
		int N = Integer.parseInt(args[0]);
		// System.out.println("----------------------");
		int T = Integer.parseInt(args[1]);

		PercolationStats ps = new PercolationStats(N, T);
		System.out.println("Mean threshold is: " + ps.mean());
		System.out.println("Standard Deviation is: " + ps.stddev());
		System.out.println("95% Condidence Interval is: " + ps.confidenceLo() + ", " + ps.confidenceHi());
		System.out.println("------------------------------------------");
		System.out.println("Time Taken: " + sw.elapsedTime());
		
		System.out.println(ps.mean());
		

	}

	// Private Instance Variables.
	private int T = -1;
	private double threshold[] = null;
	private int count = -1;
}
