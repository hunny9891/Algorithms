/*
 * Author : Himanshu Thakur
 * Filename: Subset.java
 * --------------------------
 * Program is a client program to
 * test randomized queue or a deque.
 */
import edu.princeton.cs.algs4.*;

public class Subset {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		System.out.println("Enter N");
		int N = StdIn.readInt();
		if (N <= 0 || k <= 0)
			throw new IllegalArgumentException();
		if (0 > k || k > N)
			throw new IllegalArgumentException();
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		System.out.println("Enter Strings");
		for (int i = 0; i < N; i++) {
			queue.enqueue(StdIn.readString());
		}

		for (int j = 0; j < k; j++) {
			System.out.println(queue.dequeue());
		}
	}
}
