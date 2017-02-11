import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class SeamCarver {
	public SeamCarver(Picture picture) {
		// create a seam carver object based on the given picture
		if (picture == null)
			throw new NullPointerException();
		originalPicture = new Picture(picture);
	}

	public Picture picture() {
		// current picture
		return new Picture(originalPicture);
	}

	public int width() {
		// width of current picture
		return originalPicture.width();
	}

	public int height() {
		// height of current picture
		return originalPicture.height();
	}

	public double energy(int x, int y) {
		// energy of pixel at column x and row y
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
			return 1000.0;
		else {
			Color rightColor = originalPicture.get(x + 1, y);
			Color leftColor = originalPicture.get(x - 1, y);
			Color topColor = originalPicture.get(x, y - 1);
			Color bottomColor = originalPicture.get(x, y + 1);

			double deltaX = Math.pow(
					(rightColor.getRed() - leftColor.getRed()), 2)
					+ Math.pow((rightColor.getGreen() - leftColor.getGreen()),
							2)
					+ Math.pow((rightColor.getBlue() - leftColor.getBlue()), 2);
			double deltaY = Math.pow(
					(bottomColor.getRed() - topColor.getRed()), 2)
					+ Math.pow((bottomColor.getGreen() - topColor.getGreen()),
							2)
					+ Math.pow((bottomColor.getBlue() - topColor.getBlue()), 2);

			return Math.sqrt((deltaX + deltaY));
		}
	}

	public int[] findHorizontalSeam() {
		// sequence of indices for horizontal seam
		Picture original = originalPicture;
		Picture transpose = new Picture(original.height(), original.width());

		for (int w = 0; w < transpose.width(); w++) {
			for (int h = 0; h < transpose.height(); h++) {
				transpose.set(w, h, original.get(h, w));
			}
		}

		originalPicture = transpose;

		// call findVerticalSeam
		int[] seam = findVerticalSeam();

		// Transpose back.
		originalPicture = original;

		return seam;
	}

	public int[] findVerticalSeam() {
		edgeTo = new int[width()][height()];
		distTo = new double[width()][height()];

		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				distTo[i][j] = Double.POSITIVE_INFINITY;
			}
		}

		for (int i = 0; i < width(); i++) {
			distTo[i][0] = 195075;
		}

		for (int y = 0; y < height() - 1; y++) {
			for (int x = 0; x < width(); x++) {
				if (x > 0) {
					relax(x, y, x - 1, y + 1);
				}

				relax(x, y, x, y + 1);

				if (x < width() - 1) {
					relax(x, y, x + 1, y + 1);
				}
			}
		}

		// find minimum energy path
		double minEnergy = Double.POSITIVE_INFINITY;
		int minEnergyX = -1;
		for (int w = 0; w < width(); w++) {
			if (distTo[w][height() - 1] < minEnergy) {
				minEnergyX = w;
				minEnergy = distTo[w][height() - 1];
			}
		}

		assert minEnergyX != -1;

		int[] seam = new int[height()];
		seam[height() - 1] = minEnergyX;
		int prevX = edgeTo[minEnergyX][height() - 1];

		for (int h = height() - 2; h >= 0; h--) {
			seam[h] = prevX;
			prevX = edgeTo[prevX][h];
		}

		return seam;
	}

	private void relax(int x1, int y1, int x2, int y2) {
		if (distTo[x2][y2] > distTo[x1][y1] + energy(x2, y2)) {
			distTo[x2][y2] = distTo[x1][y1] + energy(x2, y2);
			edgeTo[x2][y2] = x1;
		}

	}

	public void removeHorizontalSeam(int[] seam) {
		// remove horizontal seam from current picture
		if (seam == null)
			throw new NullPointerException();
		if(seam.length != width() || width() <= 0) {
			throw new IllegalArgumentException();
		}
		for(int i=0;i<seam.length;i++) {
			if(seam[i] < 0 || seam[i] > width() - 1)
				throw new IllegalArgumentException();
		}
		Picture original = originalPicture;
		Picture transpose = new Picture(original.height(), original.width());

		for (int w = 0; w < transpose.width(); w++) {
			for (int h = 0; h < transpose.height(); h++) {
				transpose.set(w, h, original.get(h, w));
			}
		}

		originalPicture = transpose;
		transpose = null;
		original = null;

		// call removeVerticalSeam
		removeVerticalSeam(seam);

		// Transpose back.
		original = originalPicture;
		transpose = new Picture(original.height(), original.width());

		for (int w = 0; w < transpose.width(); w++) {
			for (int h = 0; h < transpose.height(); h++) {
				transpose.set(w, h, original.get(h, w));
			}
		}

		originalPicture = transpose;
		transpose = null;
		original = null;
	}

	public void removeVerticalSeam(int[] seam) {
		// remove vertical seam from current picture
		if (seam == null) {
			throw new NullPointerException();
		}

		if (seam.length != height() || height() <= 0) {
			throw new IllegalArgumentException();
		}
		
		for(int i=0;i<seam.length;i++) {
			if(seam[i] < 0 || seam[i] > height() - 1)
				throw new IllegalArgumentException();
		}

		Picture original = originalPicture;
		Picture carved = new Picture(original.width() - 1, original.height());

		for (int h = 0; h < carved.height(); h++) {
			for (int w = 0; w < seam[h]; w++) {
				carved.set(w, h, original.get(w, h));
			}
			for (int w = seam[h]; w < carved.width(); w++) {
				carved.set(w, h, original.get(w + 1, h));
			}
		}

		originalPicture = carved;
	}

	// UNIT TEST
	public static void main(String[] args) {
		Picture pic = new Picture(args[0]);
		SeamCarver sc = new SeamCarver(pic);
	}

	private Picture originalPicture;
	private int[][] edgeTo;
	private double distTo[][];
}
