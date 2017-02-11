/*
 * Author: Himanshu Thakur
 * Filename: RandomizedQueue.java
 * ---------------------------------
 * The program implements a randomized queue API.
 * In this the items are added in first in mode 
 * but are retrieved randomly.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;
public class RandomizedQueue<Item> implements Iterable<Item> {
	/*
	 * Constructor: RandomizedQueue
	 * Usage: RandomizedQueue()
	 * -----------------------------
	 * Instantiates a randomized queue object.
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		itemList = (Item[]) new Object[2];
		N = 0;
	}
	/*
	 * Method Name: isEmpty
	 * Usage: isEmpty()
	 * ----------------------
	 * Checks whether the queue is empty or not.
	 */
	public boolean isEmpty() {

		return N == 0;
	}
	/*
	 * Method Name: size
	 * Usage: size()
	 * ------------------
	 * Returns the size of the queue.
	 */

	public int size() {

		return N;
	}
	/*
	 * Method Name: enqueue
	 * Usage: enqueue(item)
	 * ----------------------
	 * Adds an item to the queue in first in order.
	 */
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (N == itemList.length)
			resize(2 * N);
		itemList[N++] = item;

	}
	/*
	 * Method Name: resize
	 * Usage: resize()
	 * --------------------
	 * Shrinks or Expand the array according to the requirement.
	 */
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++) {
			temp[i] = itemList[i];
		}
		itemList = temp;
	}
	/*
	 * Method Name: dequeue
	 * Usage: dequeue()
	 * -----------------------
	 * Deletes a random item from the queue
	 * and returns it.
	 */
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();

		int randomIndex = StdRandom.uniform(0, N);
		Item item = itemList[randomIndex];
		itemList[randomIndex] = itemList[N - 1];
		itemList[N - 1] = null;
		N--;

		if (N > 0 && N == itemList.length / 4)
			resize(itemList.length / 2);

		return item;
	}
	/*
	 * Method Name: sample
	 * Usage: sample()
	 * ---------------------
	 * Returns a random item from the queue
	 * without deleting it.
	 */
	public Item sample() {
		if(isEmpty()) throw new NoSuchElementException();
		int randomIndex = StdRandom.uniform(0, N);
		Item item = itemList[randomIndex];
		return item;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Item> iterator() {

		return new ListIterator();
	}

	public static void main(String[] args) {
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		queue.enqueue("Ram");
		queue.enqueue("Sham");
		queue.enqueue("Kam");
		queue.enqueue("Dam");
		queue.enqueue("Akram");
		int i = 0;
		
		for(String s: queue) {
			for(String m: queue) {
				System.out.println(m);
				i++;
			}
		}
		System.out.println("--------------");
		System.out.println(i);
		
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		
		int j = 0;
		
		for(String s: queue) {
			for(String m: queue) {
				System.out.println(m);
				j++;
			}
		}
		System.out.println("--------------");
		System.out.println(j);
		
		queue.dequeue();
		
		int k = 0;
		
		for(String s: queue) {
			for(String m: queue) {
				System.out.println(m);
				k++;
			}
		}
		System.out.println("--------------");
		System.out.println(k);
	}
	// Private Class
	private class ListIterator implements Iterator<Item> {
		@SuppressWarnings("unchecked")
		public ListIterator() {
			this.tempList = (Item[])new Object[N];
			for(int i=0;i<N;i++) {
				this.tempList[i] = itemList[i];
			}
		}
		@Override
		public boolean hasNext() {
			
			return this.i > 0;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();
			
			int randomIndex = StdRandom.uniform(this.i);
			Item item = this.tempList[randomIndex];
			this.tempList[randomIndex] = this.tempList[i - 1];
			this.tempList[i - 1] = null;
			this.i--;
			return item;
		}
		
		public void remove() {
            throw new UnsupportedOperationException();
        }
		private int i = N;
		private Item[] tempList;
	}
	// Private Instance Variables.
	private Item[] itemList;
	private int N;
}