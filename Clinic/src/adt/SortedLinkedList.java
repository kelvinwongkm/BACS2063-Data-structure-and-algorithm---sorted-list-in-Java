package adt;

import java.util.Iterator;


/**
 *
 * @author Cheok Ding Wei
 */
public class SortedLinkedList<T extends Comparable<T>> implements SortedListInterface<T> {

    private Node firstNode;
    private int numberOfEntries;

    public SortedLinkedList() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);

        Node nodeBefore = null;
        Node currentNode = firstNode;
        while (currentNode != null && newEntry.compareTo(currentNode.data) > 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }

        if (isEmpty() || (nodeBefore == null)) { // CASE 1: add at beginning
            newNode.next = firstNode;
            firstNode = newNode;
        } else {	// CASE 2: add in the middle or at the end, i.e. after nodeBefore
            newNode.next = currentNode;
            nodeBefore.next = newNode;
        }
        numberOfEntries++;
        return true;
    }
    
    @Override
    public T search(T anEntry){
        if (firstNode == null) { //empty field
            return null;
        } else {
            //define two node pointers
            Node currentNode = firstNode;

            //step 1: to get the right references for beforenode and currentNode
            while (currentNode != null && currentNode.data.compareTo(anEntry) < 0) {
                currentNode = currentNode.next;
            }

            //step 2: target found 
            if (currentNode != null && currentNode.data.compareTo(anEntry)==0) {
                return currentNode.data;
            }
        }
        return null;
    }

    @Override
    public boolean remove(T anEntry) {
        if (firstNode == null) { //empty field
            return false;
        } else {
            //define two node pointers
            Node beforeNode = null;
            Node currentNode = firstNode;

            //step 1: to get the right references for beforenode and currentNode
            while (currentNode != null && currentNode.data.compareTo(anEntry) < 0) {
                beforeNode = currentNode;
                currentNode = currentNode.next;
            }

            //step 2: target found and remove it
            if (currentNode != null && currentNode.data.equals(anEntry)) {
                if (currentNode == firstNode) { // removing the first node
                    firstNode = firstNode.next;
                } else { // removing node in between
                    beforeNode.next = currentNode.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node tempNode = firstNode;

        while (!found && (tempNode != null)) {
            if (anEntry.compareTo(tempNode.data) <= 0) {
                found = true;
            } else {
                tempNode = tempNode.next;
            }
        }
        if (tempNode != null && tempNode.data.equals(anEntry)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final void clear() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return (numberOfEntries == 0);
    }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";;
            currentNode = currentNode.next;
        }
        return outputStr;
    }

    @Override
    public Iterator<T> getIterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {

        //private int index = 0;
        private Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T currentData = null;

            if (hasNext()) {
                currentData = currentNode.data;
                currentNode = currentNode.next;
            }
            return currentData;
        }
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            next = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
