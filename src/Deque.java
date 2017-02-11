/*
 * Author: Himanshu Thakur
 * Filename: Deque.java
 * ------------------------------
 * The program implements the Deque API where the client
 * can insert and delete elements from both ends of a list.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	/*
	 * Constructor: Deque
	 * Usage: Deque()
	 * -------------------
	 * Instantiates a deque object.
	 */
	public Deque() {
		first = last = null;
		N = 0;
	}
	/*
	 * Method Name: isEmpty
	 * Usage: isEmpty()
	 * ----------------------
	 * Checks whether the deque is empty or not.
	 */
	public boolean isEmpty() {

		return first == null;
	}
	/*
	 * Method Name: size
	 * Usage: size()
	 * ------------------
	 * Returns the size of the deque.
	 */

	public int size() {

		return N;
	}
	/*
	 * Method Name: addFirst
	 * Usage: addFirst(item)
	 * ----------------------
	 * Adds an item to the start of the deque.
	 */

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		if (oldFirst == null)
			last = first;
		else {
			first.next = oldFirst;
			oldFirst.previous = first;
		}

		N++;

	}
	
	/*
	 * Method Name: addLast
	 * Usage: addLast(item)
	 * ---------------------
	 * Adds an item at the end of the deque.
	 */

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node oldLast = last;
		last = new Node();
		last.item = item;
		if (isEmpty())
			first = last;
		else {
			oldLast.next = last;
			last.previous = oldLast;
		}

		N++;
	}
	/*
	 * Method Name: removeFirst
	 * Usage: removeFirst()
	 * ------------------------
	 * Removes the item at the first position from the
	 * deque and returns that item.
	 */

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		N--;
		if(isEmpty()) last = null;

		return item;
	} 
	
	/*
	 * Method Name: removeLast
	 * Usage: removeLast()
	 * ------------------------
	 * Removes the item at the last position from the
	 * deque and returns that item.
	 */
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = last.item;
		if (last.previous == null)
			last = first = null;
		else {
			last = last.previous;
			last.next = null;
		}
		N--;
		if(isEmpty()) first = null;

		return item;
	} 
	
	/*
	 * Method Name: Iterator
	 * Usage: Iterator<item>()
	 * --------------------------
	 * Returns an iterator object to iterate
	 * over the deque items in order of front
	 * to end.
	 */
	public Iterator<Item> iterator() {
		return new DequeIterator();
	} 

	// unit testing
	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		//deque.removeFirst();
		//deque.addLast(null);
		deque.addLast("Sham");
		deque.addLast("Sam");
		deque.addLast("Shm");
		deque.addLast("ham");
		deque.addLast("am");
		deque.addLast("m");
		deque.addLast("Sham");
		deque.addLast("Donald");
		
		//while(!deque.isEmpty()) System.out.println(deque.removeFirst());
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		deque.removeFirst();
		System.out.println(deque.last.item);
		/*Iterator<String> itr = deque.iterator();
		itr.remove();*/

		// System.out.println(deque.removeFirst());
		// System.out.println(deque.removeFirst());
		// System.out.println(deque.removeFirst());
		// System.out.println(deque.removeFirst());
		// System.out.println(deque.removeFirst());

		for (String s : deque) {
			System.out.println(s);
		}
	}
	// Private Class
	private class DequeIterator implements Iterator<Item> {
		@Override
		public boolean hasNext() {

			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		private Node current = first;

	}

	// private class
	private class Node {
		private Item item = null;
		private Node next = null;
		private Node previous = null;
	}

	// private instance variables
	private Node first;
	private Node last;
	private int N;
}
