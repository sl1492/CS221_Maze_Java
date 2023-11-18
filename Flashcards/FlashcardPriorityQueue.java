import java.util.NoSuchElementException;
import java.util.Arrays;

/*
 * @author: Simeng Li
 */

public class FlashcardPriorityQueue implements PriorityQueue<Flashcard> {
    private Flashcard[] heap;      // Array of heap entries; ignore heap[0]
    private int lastIndex; // Index of last entry and number of entries
    private static final int DEFAULT_CAPACITY = 3;

    /**
     * Creates an empty priority queue with the default capacity.
     */
    public FlashcardPriorityQueue() {
        heap = new Flashcard[DEFAULT_CAPACITY];
        lastIndex = 0;
    }

    private void ensureCapacity()
	{
        int numberOfEntries = lastIndex;
        int capacity = heap.length - 1;
        if (numberOfEntries >= capacity) {
            int newCapacity = 2 * capacity;
            heap = Arrays.copyOf(heap, newCapacity + 1);
        } // end if
    } // end ensureCapacity

    /**
     * Adds the given item to the queue.
     */
    public void add(Flashcard item) {
        int newIndex = lastIndex + 1;
        int parentIndex = (newIndex + 1) / 3;
        while ( (parentIndex > 0) && item.compareTo(heap[parentIndex]) < 0)
        {
           heap[newIndex] = heap[parentIndex];
           newIndex = parentIndex;
           parentIndex = (newIndex + 1) / 3;
        } // end while

        heap[newIndex] = item;
        lastIndex++;
        ensureCapacity();
    }

    private void reheap(int rootIndex) {
        boolean done = false;
        Flashcard orphan = heap[rootIndex];
        int leftChildIndex = 3 * rootIndex - 1;

        while (!done && (leftChildIndex <= lastIndex) )
        {
           int smallestChildIndex = leftChildIndex; // Assume larger
           int middleChildIndex = leftChildIndex + 1;
           int rightChildIndex = leftChildIndex + 2;

           if ( (rightChildIndex <= lastIndex) &&
               heap[rightChildIndex].compareTo(heap[smallestChildIndex]) < 0) {
               smallestChildIndex = rightChildIndex;
           }

           if ((middleChildIndex <= lastIndex) &&
               heap[middleChildIndex].compareTo(heap[smallestChildIndex]) < 0) {
               smallestChildIndex = middleChildIndex;
           }

           if (orphan.compareTo(heap[smallestChildIndex]) > 0)
           {
              heap[rootIndex] = heap[smallestChildIndex];
              rootIndex = smallestChildIndex;
              leftChildIndex = 3 * rootIndex - 1;
           }
           else
              done = true;
        } // end while

        heap[rootIndex] = orphan;
    }

    /** Removes the first item according to compareTo from the queue, and returns it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard poll() {
        Flashcard root = null;
        if (!isEmpty()) {
            root = heap[1];            // Return value
            heap[1] = heap[lastIndex]; // Form a semiheap
            lastIndex--;               // Decrease size
            reheap(1);                 // Transform to a heap
        } else {
            throw new NoSuchElementException("The queue is empty.");
        }
        return root;
    } // end poll

    /** Returns the first item according to compareTo in the queue, without removing it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard peek() {
        Flashcard root = null;

		if (!isEmpty()) {
            root = heap[1];
        } else {
            throw new NoSuchElementException("The queue is empty.");
        }
		return root;
    }

    /** Returns true if the queue is empty. */
    public boolean isEmpty() {
        return lastIndex < 1;
    }

    /** Removes all items from the queue. */
    public void clear() {
        while (lastIndex > -1)
		{
			heap[lastIndex] = null;
			lastIndex--;
		} // end while

		lastIndex = 0;
    }

    public static void main(String[] args) {
        PriorityQueue<Flashcard> pq = new FlashcardPriorityQueue();
        Flashcard Canada = new Flashcard("2021-03-08T01:03","Ottawa","Canada");
        Flashcard China = new Flashcard("2021-03-02T01:03","Beijing","China");
        Flashcard Afghanistan = new Flashcard("2021-03-02T01:03","Kabul","Afghanistan");
        Flashcard Brazil = new Flashcard("2021-03-10T01:03","Brasilia","Brazil");
        pq.add(Canada);
        pq.add(China);
        pq.add(Afghanistan);
        pq.add(Brazil);
        System.out.println("This should return the Flashcard of China: " + pq.poll());
        System.out.println("This should return the Flashcard of Afghanistan: " + pq.peek());
        System.out.println("This should return false: " + pq.isEmpty());
        pq.clear();
        System.out.println("This should return true: " + pq.isEmpty());
        System.out.println("This should give a NoSuchElementException: " + pq.poll());

    }

}
