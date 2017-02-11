import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class KdTree {
	private static final boolean VERTICAL = true;
	private static final boolean HORIZONTAL = false;

	public KdTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return 1 + size(x.lb) + size(x.rt);
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		root = insert(root, p, VERTICAL, 0, 0, 1, 1);
	}

	private Node insert(Node x, Point2D p, boolean orientation, double xmin,
			double ymin, double xmax, double ymax) {
		if (p == null)
			throw new NullPointerException();
		if (x == null) {
			x = new Node(p);
			x.rect = new RectHV(xmin, ymin, xmax, ymax);
			x.lb = null;
			x.rt = null;
			x.orientation = orientation;
			return x;
		} else {
			double cmp;
			if (x.orientation == VERTICAL) {
				cmp = p.x() - x.p.x();
				if (cmp < 0)
					x.lb = insert(x.lb, p, HORIZONTAL, x.rect.xmin(),
							x.rect.ymin(), x.p.x(), x.rect.ymax());
				else
					x.rt = insert(x.rt, p, HORIZONTAL, x.p.x(), x.rect.ymin(),
							x.rect.xmax(), x.rect.ymax());
			} else {
				cmp = p.y() - x.p.y();
				if (cmp < 0) {
					x.lb = insert(x.lb, p, VERTICAL, x.rect.xmin(),
							x.rect.ymin(), x.rect.xmax(), x.p.y());
				} else {
					x.rt = insert(x.rt, p, VERTICAL, x.rect.xmin(), x.p.y(),
							x.rect.xmax(), x.rect.ymax());
				}
			}

			return x;
		}

	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		return contains(root, p);
	}

	private boolean contains(Node x, Point2D p) {
		if (x == null)
			return false;
		if (x.p.equals(p)) {
			return true;
		}

		double comp;

		if (x.orientation == VERTICAL) {
			comp = p.x() - x.p.x();
		} else
			comp = p.y() - x.p.y();

		if (comp < 0)
			return contains(x.lb, p);
		else
			return contains(x.rt, p);
	}

	public void draw() {
		draw(root);

	}

	private void draw(Node x) {
		if (x == null) {
			return;
		} else {
			if (x.orientation == VERTICAL) {
				StdDraw.setPenColor(Color.RED);
				StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
			} else {
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
			}
		}
		draw(x.lb);
		draw(x.rt);

	}

	public Point2D nearest(Point2D p) {
		return nearest(root, p, Double.POSITIVE_INFINITY);
	}

	private Point2D nearest(Node x, Point2D p, Double distance) {
		if (x == null)
			return null;
		Point2D nearestPoint = null;
		double calculateDistance = p.distanceSquaredTo(x.p);

		if (calculateDistance < distance) {
			nearestPoint = x.p;
			distance = calculateDistance;
		}

		Node leftNode = x.lb;
		Node rightNode = x.rt;

		if (leftNode != null && rightNode != null) {
			if (leftNode.rect.distanceSquaredTo(p) > rightNode.rect
					.distanceSquaredTo(p)) {
				leftNode = x.rt;
				rightNode = x.lb;
			}
		}

		Point2D currNearestPoint = nearest(leftNode, p, distance);
		if (currNearestPoint != null) {
			calculateDistance = p.distanceSquaredTo(currNearestPoint);
			if (calculateDistance < distance) {
				nearestPoint = currNearestPoint;
				distance = calculateDistance;
			}
		}

		Point2D currSecNearestPoint = nearest(rightNode, p, distance);
		if (currSecNearestPoint != null) {
			calculateDistance = p.distanceSquaredTo(currSecNearestPoint);
			if (calculateDistance < distance) {
				nearestPoint = currSecNearestPoint;
				distance = calculateDistance;
			}
		}

		return nearestPoint;
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		Stack<Point2D> points = new Stack<Point2D>();

		return range(rect, root, points);
	}

	private Iterable<Point2D> range(RectHV rect, Node x, Stack<Point2D> points) {
		if (x != null) {
			if (rect.intersects(x.rect)) {
				if (rect.contains(x.p))
					points.push(x.p);
			}

			range(rect, x.lb, points);
			range(rect, x.rt, points);
		}

		return points;

	}

	public static void main(String[] args) {
		/*
		 * KdTree kdtree = new KdTree(); assert kdtree.size() == 0;
		 * kdtree.insert(new Point2D(.7, .2)); assert kdtree.size() == 1;
		 * kdtree.insert(new Point2D(.5, .4)); kdtree.insert(new Point2D(.2,
		 * .3)); kdtree.insert(new Point2D(.4, .7)); kdtree.insert(new
		 * Point2D(.9, .6)); assert kdtree.size() == 5; kdtree.draw();//
		 * StdOut.println(kdtree);
		 */
		/*
		 * kdtree = new KdTree(); kdtree.insert(new Point2D(0.206107,
		 * 0.095492)); kdtree.insert(new Point2D(0.975528, 0.654508));
		 * kdtree.insert(new Point2D(0.024472, 0.345492)); kdtree.insert(new
		 * Point2D(0.793893, 0.095492)); kdtree.insert(new Point2D(0.793893,
		 * 0.904508)); kdtree.insert(new Point2D(0.975528, 0.345492)); assert
		 * kdtree.size() == 6; kdtree.insert(new Point2D(0.206107, 0.904508));
		 * StdOut.println(kdtree.size()); assert kdtree.size() == 7;
		 * 
		 * StdOut.println("size: " + kdtree.size());
		 * 
		 * KdTree myTree = new KdTree(); double var = 0.01; for(int
		 * i=0;i<40;i++) { myTree.insert(new Point2D(var,var)); var++; }
		 * 
		 * System.out.println(myTree.size());
		 */

		String filename = args[0];
		In in = new In(filename);
		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}

		/*
		 * Iterable<Point2D> bag = kdtree.range(new RectHV(0.6, 0.1, 0.8, 0.6));
		 * int count = 0; for (Point2D p : bag) { count++; }
		 * System.out.println(count); kdtree.draw();
		 * System.out.println(kdtree.size());
		 */

		kdtree.printLevelOrder();
	}

	private void printLevelOrder() {
		printLevelOrder(root);

	}

	private void printLevelOrder(Node x) {
		if (x == null)
			return;
		System.out.println(x.p.toString());
		printLevelOrder(x.lb);
		printLevelOrder(x.rt);

	}

	// Private Class
	private static class Node {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private boolean orientation;

		public Node(Point2D p) {
			this.p = p;
		}
	}

	// Private Instance Variables
	private Node root;

}
