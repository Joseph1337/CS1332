import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Joseph Guo
 * @version 1.0
 * @userid jguo345
 * @GTID 903437631
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not within range of list.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);
        if (index == 0) {
            this.addToFront(data);
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            int i = 0;
            while (i < index - 1) {
                // while (--index > 0) {
                curr = curr.getNext();
                i++;
            }
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
            size++;
        }

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (head == null) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
        } else {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(head.getData());
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            newNode.setData(head.getData());
            head.setData(data);
        }
        size++;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        this.addToFront(data);
        head = head.getNext();
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not within range of list.");
        }
        if (index == 0) {
            return this.removeFromFront();
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            int i = 0;
            while (i < index - 1) {
                // while (--index > 0) {
                curr = curr.getNext();
                i++;
            }
            T removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("No data available to be removed");
        }
        if (size == 1) {
            T temp = head.getData();
            head = null;
            size--;
            return temp;
        } else {
            T removed = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("No element available to me removed.");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        if (size == 1) {
            T temp = head.getData();
            size--;
            head = null;
            return temp;
        } else {
            return this.removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not within range of list.");
        } else if (index == 0) {
            T item = head.getData();
            return item;
        } else {
            int i = 0;
            CircularSinglyLinkedListNode<T> curr = head;
            while (i < index) {
                curr = curr.getNext();
                i++;
            }
            T item = curr.getData();
            return item;
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        head = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data given cannot be null.");
        }
        int index = -1;
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(data)) {
                index = i;
            }
            curr = curr.getNext();
        }
        if (index == -1) {
            throw new NoSuchElementException("The data given was not found.");
        }
        T removedData = this.get(index);
        this.removeAtIndex(index);
        return removedData;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        int count = 0;
        for (int i = 0; i < size; i++) {
            arr[count] = curr.getData();
            curr = curr.getNext();
            count++;
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
