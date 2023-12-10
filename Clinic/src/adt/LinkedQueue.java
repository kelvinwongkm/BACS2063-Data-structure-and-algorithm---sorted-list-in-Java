/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

/**
 *
 * @author Yau Hui Yi
 */
import java.util.Iterator;
import java.io.Serializable;

public class LinkedQueue<T> implements QueueInterface<T>, Serializable {

    // Class attributes
    private Node firstNode, lastNode;
    private int numberOfEntries;

    // Private Node class
    private class Node implements Serializable {

        private T data;
        private Node next;

        // Node constructor
        public Node(T newEntry) {
            this.data = newEntry;
            this.next = null;
        }
    }

    // Constructors
    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    // Methods
    @Override
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry);
        if (isEmpty()) {
            // Point firstNode and lastNode to the only node
            firstNode = newNode;
            lastNode = newNode;
        } else {
            // Append newNode to the end of the queue
            lastNode.next = newNode;
            lastNode = newNode;
        }
        numberOfEntries++;
    }

    @Override
    public T dequeue() {
        // Return error if there are no elements to dequeue
        if (isEmpty()) {
            return (T) "The LinkedQueue is empty, no data to dequeue. ";
        }

        // Keep reference of the data to be returned
        T output = firstNode.data;

        // Update firstNode (and lastNode if queue is empty after dequeue operation)
        firstNode = firstNode.next;
        if (isEmpty()) {
            lastNode = null;
        }
        numberOfEntries--;
        return output;
    }

    @Override
    public T getFront() {
        return firstNode.data;
    }

    @Override
    public boolean isEmpty() {
        // Only check if firstNode is null because 
        // if firstNode is null, then lastNode must also be null
        return firstNode == null;
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
    }
    
    @Override
    public int getNumberOfEntries() {
    return numberOfEntries;
  }
    
    
  @Override
  public T getEntry(int givenPosition) {
    T result = null;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      Node currentNode = firstNode;
      for (int i = 0; i < givenPosition - 1; ++i) {
        currentNode = currentNode.next;		// advance currentNode to next node
      }
      result = currentNode.data;	// currentNode is pointing to the node at givenPosition
    }

    return result;
  }


    // Iterator class
    private class LinkedQueueIterator implements Iterator<T> {

        private Node iterNode;

        public LinkedQueueIterator() {
            iterNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return iterNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T output = iterNode.data;
                iterNode = iterNode.next;
                return output;
            } else {
                return null;
            }
        }
    }

    // Iterator
    @Override
    public Iterator<T> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public String toString() {
        Node tempnode = firstNode;
        String str = "";
        while (tempnode != null) {
            str += tempnode.data + "\n";
            tempnode = tempnode.next;
        }

        return str;

    }

}
