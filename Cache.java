import java.util.Arrays;
import java.util.LinkedList;

/**
 * Cache is a "Cache" implementation using linked list data structure.
 * 
 * @author SajiaZafeen
 *
 * @param <T>
 */
public class Cache<T> {
	private LinkedList<T> cacheLinkedList;
	private int size;

	/**
	 * Constructor: creates a Cache Object
	 */
	public Cache(int size) {
		cacheLinkedList = new LinkedList<T>();
		this.size = size;
	}

	/**
	 * Returns Cache Linked List
	 * 
	 * @return Cache Linked List
	 */
	public LinkedList<T> getCacheLinkedList() {
		return cacheLinkedList;
	}

	/**
	 * Sets Cache Linked List
	 * 
	 * @param cacheLinkedList
	 */
	public void setCacheLinkedList(LinkedList<T> cacheLinkedList) {
		this.cacheLinkedList = cacheLinkedList;
	}

	/**
	 * Adds the element at the top of the Cache
	 * 
	 * @param element which is added to the top of the Cache
	 */
	public void addToCache(T element) {
		cacheLinkedList.addFirst(element);
	}

	/**
	 * Removes the last element in the Cache
	 */
	public void removeLastCache() {
		cacheLinkedList.removeLast();
	}

	/**
	 * Removes the element in the index of the Cache
	 * 
	 * @param index is the index of the element to be removed
	 */
	public void removeFromCache(int index) {
		cacheLinkedList.remove(index);
	}

	/**
	 * Removes the element from the Cache
	 * 
	 * @param element to be removed from the Cache
	 */
	public void removeFromCache(T element) {
		cacheLinkedList.remove(element);
	}

	/**
	 * Empties the Cache
	 */
	public void clearCache() {
		cacheLinkedList.clear();
	}

	/**
	 * Returns true if the Cache is full
	 * 
	 * @return true if Cache is full
	 */
	public boolean cacheFull() {
		return (cacheLinkedList.size() == size);
	}

	/**
	 * Returns true if the Cache is empty
	 * 
	 * @return true if the Cache is empty
	 */
	public boolean emptyCache() {
		return (cacheLinkedList.size() == 0);
	}

	/**
	 * Returns the Cache size
	 * 
	 * @return the Cache size
	 */
	public int cacheSize() {
		return cacheLinkedList.size();

	}

	/**
	 * String output of the Cache Linked List
	 */
	public String toString() {
		return Arrays.deepToString(cacheLinkedList.toArray());
	}
}
