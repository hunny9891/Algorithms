
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;


public class PointSET {
	public PointSET() {
		// construct an empty set of points
		pointSet = new TreeSet<Point2D>();
	}

	public boolean isEmpty() {
		// is the set empty?
		return pointSet.isEmpty();
	}

	public int size() {
		// number of points in the set
		return pointSet.size();
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null)
			throw new NullPointerException();
		pointSet.add(p);
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null)
			throw new NullPointerException();
		return pointSet.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		for (Point2D p : pointSet) {
			p.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if(rect == null) throw new NullPointerException();
		Queue<Point2D> pointsInRect = new Queue<Point2D>();
		for(Point2D point:pointSet) {
			if(rect.contains(point))
				pointsInRect.enqueue(point);
		}
		return pointsInRect;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if(p == null) throw new NullPointerException();
		if (pointSet.isEmpty())
			return null;
		else {
			Point2D neighbor = null;
			double minDist = Double.POSITIVE_INFINITY;
			double currDist = minDist;
			for(Point2D point:pointSet) {
				currDist = p.distanceSquaredTo(point);
				if(currDist < minDist) {
					minDist = currDist;
					neighbor = point;
				}
				
			}
			return neighbor;
		}
	}

	public static void main(String[] args) {
		/*Point2D point = new Point2D(100, 300);
		Point2D point1 = new Point2D(1000, 3000);
		Point2D point3 = new Point2D(140, 30);
		PointSET ps = new PointSET();
		ps.insert(point);
		ps.insert(point1);
		ps.insert(point3);
		System.out.println(ps.nearest(point1).toString());*/
		
		/*String filename = args[0];
        In in = new In(filename);
        PointSET ps = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            ps.insert(p);
        }
        
        Iterable<Point2D> bag = ps.range(new RectHV(0.6,0.1,0.8,0.6));
        int count = 0;
        for(Point2D p:bag) {
        	count++;
        }
        System.out.println(count);
        ps.draw();
        System.out.println(ps.size());*/

	}

	// Private Instance Variables
	private TreeSet<Point2D> pointSet = null;
}
