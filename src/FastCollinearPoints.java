import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

public class FastCollinearPoints {

	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		if (isRepeated(points))
			throw new java.lang.IllegalArgumentException();

		int N = points.length;
		Arrays.sort(points);
		Point[] aux = new Point[N];

		for (int i = 0; i < N; i++) {
			aux[i] = points[i];
		}

		ArrayList<Point> collinearPoints = new ArrayList<Point>();
		for (int i = 0; i < N; i++) {
			Point p = points[i];
			Arrays.sort(aux, p.slopeOrder());
			for (int j = 0; j < N - 1; j++) {
				if (p.compareTo(aux[j]) != 0) {
					if (p.slopeTo(aux[j]) == p.slopeTo(aux[j + 1])) {
						collinearPoints.add(aux[j]);
					}
					
					if(collinearPoints.size() > 1 && j == N - 2 && p.slopeTo(aux[j]) == p.slopeTo(aux[j + 1])) {
						collinearPoints.add(aux[j + 1]);
					}

					else if (p.slopeTo(aux[j]) != p.slopeTo(aux[j + 1])
							&& collinearPoints.size() > 1) {
						collinearPoints.add(aux[j]);
						break;
					}
					
				}

			}
			Collections.sort(collinearPoints);
			if (collinearPoints.size() > 2
					&& p.compareTo(collinearPoints.get(0)) == -1) {
				LineSegment segment = new LineSegment(p,
						collinearPoints.get(collinearPoints.size() - 1));
				segments.add(segment);
			}
			collinearPoints.clear();
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
				if (points[i].compareTo(points[j]) == 0) {
					flag = true;
				}

			}
			if (flag)
				break;
		}
		return flag;
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}

	}

	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
}