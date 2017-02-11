import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		if (isRepeated(points))
			throw new java.lang.IllegalArgumentException();
		int N = points.length;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					for (int l = k + 1; l < N; l++) {
						if (isCollinear(points[i], points[j], points[k],
								points[l])) {
							Point[] colPoints = {points[i], points[j], points[k], points[l]};
							Arrays.sort(colPoints);
							segments.add(new LineSegment(colPoints[0], colPoints[colPoints.length - 1]));
						}
					}
				}
			}

		}
	}
	
	public int numberOfSegments() {
		return segments.size();
	}
	
	public LineSegment[] segments() {
		int n = numberOfSegments();
		LineSegment[] segmentArr = new LineSegment[n];
		for (int i = 0; i < n; i++) {
			segmentArr[i] = segments.get(i);
		}
		return segmentArr;
	}
	
	private boolean isRepeated(Point[] points) {
		boolean flag = false;
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0 ) {
					flag = true;
				}

			}
			if (flag)
				break;
		}
		return flag;
	}


	private static boolean isCollinear(Point p1, Point p2, Point p3, Point p4) {
		if(p1 == null || p2 == null || p3 == null || p4 == null) throw new java.lang.NullPointerException();
		if (p1.slopeTo(p2) == p2.slopeTo(p3)
				&& p2.slopeTo(p3) == p3.slopeTo(p4))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
	    int N = in.readInt();
	    Point[] points = new Point[N];
	    for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.show(0);
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    Point p1 = new Point(0,0);
	    Point p2 = new Point(0,0);
	    Point[] poin = {p1, p2};
	    BruteCollinearPoints collinear = new BruteCollinearPoints(poin);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}

	
	
	private java.util.ArrayList<LineSegment> segments = new java.util.ArrayList<LineSegment>();

}
