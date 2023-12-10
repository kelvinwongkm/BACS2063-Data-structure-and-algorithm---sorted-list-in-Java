package adt;

import java.io.Serializable;

/**
 *
 * @author Vickham Foo
 */
public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 20;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public void add(T newEntry) {
        if(isFull()){
        doubleArray();
        }
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
    }

    @Override
    public boolean add(int newIndex, T newEntry) {
        boolean isSuccessful = true;

        if ((newIndex >= 1) && (newIndex <= numberOfEntries + 1)) {
            makeRoom(newIndex);
            array[newIndex] = newEntry;
            numberOfEntries++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public void remove(int index) {
        T result = null;

        if ((index >= 0) && (index <numberOfEntries)) {
            result = array[index];

            if (index < numberOfEntries) {
                removeGap(index);
            }

            numberOfEntries--;
        }
    }

    @Override
    public void clear() {
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int index, T newEntry) {
        boolean isSuccessful = true;

        if ((index >= 0) && (index < numberOfEntries)) {
            array[index] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int index) {
        T result = null;

        if ((index >= 0) && (index <= numberOfEntries)) {
            result = array[index];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < numberOfEntries); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }
        return found;
    }



    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return numberOfEntries == array.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

//Iterative Insertion Sort
    @Override
    public <T extends Comparable<T>> void insertionSort(T[] a, int n) {
// assume the first index will be the sorted region
        for (int unsorted = 1; unsorted < n; unsorted++) {
            T firstUnsorted = a[unsorted];
            insertInOrder(firstUnsorted, a, unsorted - 1);

        }
    }

    private static <T extends Comparable<T>> void insertInOrder(T element, T[] a, int end) {
        int index = end;
        while ((index >= 0) && (element.compareTo(a[index]) < 0)) {
            a[index + 1] = a[index];
            index--;
            a[index + 1] = element;
        }

    }
    
        private void makeRoom(int newPosition) {
        int newIndex = newPosition-1;
        int lastIndex = numberOfEntries-1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition;
        int lastIndex = numberOfEntries;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

        private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;

        array = (T[]) new Object[2 * oldSize];

        for (int index = 0; index < oldSize; index++) {
            array[index] = oldList[index];
        }
    }
        
}