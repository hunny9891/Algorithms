import java.util.Comparator;

import edu.princeton.cs.algs4.*;

public class Point implements Comparable<Point> {

	public Point(int x, int y) {
		// construct the point (x, y)
		this.x = x;
		this.y = y;
	}

	public void draw() {
		// draw this point
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		// draw the line segment from this point to that point
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public String toString() {
		// string representation
		return "(" + x + ", " + y + ")";
	}

	public int compareTo(Point that) {
		// is this point lexicographically smaller than that point?
		if (that == null)
			throw new NullPointerException();
		if (this.y < that.y || (this.y == that.y && this.x < that.x))
			return -1;
		else if (this.y > that.y || (this.y == that.y && this.x > that.x))
			return 1;
		else
			return 0;
	}

	public double slopeTo(Point that) {
		// the slope between this point and that point
		if (this.x == that.x && this.y == that.y)
			return Double.NEGATIVE_INFINITY;
		else if (that.x == this.x && this.y != that.y)
			return Double.POSITIVE_INFINITY;
		else if (that.y == this.y && this.x != that.x)
			return 0.0;
		else {
			double slope = (double) (that.y - this.y) / (that.x - this.x);
			return slope;
		}

	}

	/**
	 * Compares two points by the slope they make with this point. The slope is
	 * defined as in the slopeTo() method.
	 *
	 * @return the Comparator that defines this ordering on points
	 */
	public Comparator<Point> slopeOrder() {
		return new SlopeOrderComparator();
	}

	public static void main(String[] args) {
		/*
		 * StdDraw.setXscale(0, 32768); StdDraw.setYscale(0, 32768);
		 * StdDraw.show(0); StdDraw.setPenRadius(0.01); // make the points a bit
		 * larger
		 */
		Point point1 = new Point(3000, 4000);
		Point point2 = new Point(3000, 4000);
		System.out.println(point1.slopeTo(point2));
		/*
		 * point1.draw(); point2.draw(); point1.drawTo(point2);
		 * 
		 * StdDraw.show();
		 */
	}

	private class SlopeOrderComparator implements Comparator<Point> {

		@Override
		public int compare(Point thisPoint, Point thatPoint) {
			// TODO Auto-generated method stub
			double slope1 = slopeTo(thisPoint);
			double slope2 = slopeTo(thatPoint);
			if (slope1 < slope2)
				return -1;
			else if (slope1 > slope2)
				return 1;
			else
				return 0;
		}

	}

	private final int x;
	private final int y;
}