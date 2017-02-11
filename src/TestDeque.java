// CSE 373 Homework 3 (The Amazing Deque)
// instructor-provided file

import java.util.Iterator;
import edu.princeton.cs.algs4.*;
/**
 * This is a basic testing program for your ArrayDeque class.
 * It will help you to test each of your methods to make sure they work properly.
 * Please feel free to modify it to add your own testing code.
 */
public class TestDeque {
    /** Runs the testing program. */
	public static void main(String[] args) {
		Stopwatch sw = new Stopwatch();
		Deque<String> deque = new Deque<String>();
		testAddLast(deque);
		testRemoveLast(deque);
		testIsEmptySize(deque);
		//testClear();
		
		deque = new Deque<String>();
		testAddFirst(deque);
		testRemoveFirst(deque);
		testAddRemove(deque);
		
		testIteratorIterable();
		testResize();
		System.out.println("--------------");
		System.out.println("Elapsed Time is: " + sw.elapsedTime());
	}
	
	// Tests the addLast method.
	private static void testAddLast(Deque<String> deque) {
		System.out.println("addLast, toString:");
		deque.addLast("seven");
		System.out.println(deque);
		deque.addLast("years");
		System.out.println(deque);
		deque.addLast("ago");
		System.out.println(deque);
	}
		
	// Tests the removeLast method.
	private static void testRemoveLast(Deque<String> deque) {
		System.out.println();
		System.out.println("removeLast:");
		System.out.println(deque.removeLast());
		System.out.println(deque);
		System.out.println(deque.removeLast());
		System.out.println(deque);
		System.out.println(deque.removeLast());
		System.out.println(deque);
	}
	
	// Tests the isEmpty and size methods.
	private static void testIsEmptySize(Deque<String> deque) {
		System.out.println();
		System.out.println("isEmpty / size:");
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		deque.addLast("seven");
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		deque.addLast("years");
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		deque.addLast("ago");
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		while (!deque.isEmpty()) {
			System.out.println(deque.removeLast());
			System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		}
	}
	
	// Tests the clear method.
	private static void testClear() {
		System.out.println();
		System.out.println("clear:");
		Deque<String> deque = new Deque<String>();
		deque.addLast("four");
		deque.addLast("score");
		deque.addLast("and");
		deque.addLast("seven");
		deque.addLast("years");
		deque.addLast("ago");
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
		//deque.clear();
		System.out.println(deque + ", size " + deque.size() + ", empty? " + deque.isEmpty());
	}
	
	// Tests the addFirst method.
	private static void testAddFirst(Deque<String> deque) {
		System.out.println();
		System.out.println("addFirst:");
		deque.addFirst("and");
		System.out.println(deque);
		deque.addFirst("score");
		System.out.println(deque);
		deque.addFirst("four");
		System.out.println(deque);
		
		deque.addLast("seven");
		System.out.println(deque);
		deque.addLast("years");
		System.out.println(deque);
		deque.addLast("ago");
		System.out.println(deque);
	}
	
	// Tests the removeFirst method.
	private static void testRemoveFirst(Deque<String> deque) {
		System.out.println();
		System.out.println("removeFirst:");
		while (!deque.isEmpty()) {
			System.out.println(deque.removeFirst());
			System.out.println(deque);
		}
	}
	
	// Tests the addFirst, addLast, removeFirst, and removeLast methods together.
	private static void testAddRemove(Deque<String> deque) {
		System.out.println();
		System.out.println("add/remove-First/Last together:");
		deque.addFirst("and");
		deque.addLast("seven");
		deque.addFirst("score");
		deque.addLast("years");
		deque.addFirst("four");
		deque.addLast("ago");
		System.out.println(deque);
		while (!deque.isEmpty()) {
			System.out.println(deque.removeLast());
			System.out.println(deque);
			System.out.println(deque.removeFirst());
			System.out.println(deque);
		}
	}
	
	// Tests the iterator method and the for-each loop (Iterable).
	private static void testIteratorIterable() {
		System.out.println();
		System.out.println("iterator:");
		Deque<String> deque = new Deque<String>();
		deque.addFirst("and");
		deque.addLast("seven");
		deque.addFirst("score");
		deque.addLast("years");
		deque.addFirst("four");
		deque.addLast("ago");
		Iterator<String> itr = deque.iterator();
		while (itr.hasNext()) {
			System.out.println("iterator next() = " + itr.next());
		}
		
		System.out.println();
		System.out.println("Iterable:");
		for (String s : deque) {
			System.out.println("foreach loop value = " + s);
		}
		
	}
	
	// A large test that checks whether the internal array can resize properly after many adds.
	private static void testResize() {
		System.out.println();
		System.out.println("many elements (resize):");
		Deque<String> deque = new Deque<String>();
		
		// String[] words = GETTYSBURG.split(" ");
		for (int i = 0; i < 50; i++) {
			if (i % 3 == 0) {
				deque.addFirst("f" + i);
				System.out.println("after adding word #f" + i + " at front: " + deque
						+ ", size " + deque.size());
			} else {
				deque.addLast("b" + i);
				System.out.println("after adding word #b" + i + " at back : " + deque
						+ ", size " + deque.size());
			}
		}
		
		for (int i = 0; !deque.isEmpty(); i++) {
			if (i % 2 == 0) {
				System.out.println(deque.removeLast());
			} else {
				System.out.println(deque.removeFirst());
			}
			System.out.println(deque);
		}
	}
}
