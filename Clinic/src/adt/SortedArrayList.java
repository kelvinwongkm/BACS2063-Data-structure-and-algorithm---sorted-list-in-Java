package adt;

import java.util.Iterator;

/**
 *
 * @author Wong Kah Ming
 */
public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T> {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 20;

    public SortedArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public SortedArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Comparable[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        int i = 0;
        if (isArrayFull()) {
            doubleArray();
        }
        
        while (i < numberOfEntries && newEntry.compareTo(array[i]) > 0) {
            i++;
        }

        makeRoom(i + 1);
        array[i] = newEntry;
        numberOfEntries++;
        return true;
    }

    public T getEntry(int index) {
        if (index >= 0 && index < numberOfEntries) {
            return array[index];
        } else {
            return null;
        }
    }
    
    @Override
    public boolean contains(T anEntry) {
        boolean contain = false;
        for (int x = 0; x < numberOfEntries && !contain; x++) {
            if (anEntry.equals(array[x])) {
                contain = true;
            }
        }
        return contain;
    }
    
    @Override
    public void clear() {
        numberOfEntries = 0;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;

        array = (T[]) new Object[2 * oldSize];

        for (int index = 0; index < oldSize; index++) {
            array[index] = oldList[index];
        }
    }

    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    public boolean remove(int index) {
        if (index >= 0 && index < numberOfEntries) {
            removeGap(index + 1);
            numberOfEntries--;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(T anEntry) {
        int index = binarySearch(anEntry).getIndex();
        return remove(index);
    }

    private void removeGap(int givenPosition) {
        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }
    
    @Override
    public T search(T anEntry){
        return binarySearch(anEntry).getEntry();
    }

    public Map<T> binarySearch(T searchEntry) {
        Map<T> result = new Map();
        int first = 0;
        int last = numberOfEntries - 1;

        while (first <= last) {
            int mid = (first + last) / 2;
            if (searchEntry.compareTo(array[mid]) == 0) {
                result.entry = array[mid];
                result.index = mid;
                return result;
            } else if (searchEntry.compareTo(array[mid]) < 0) {
                last = mid - 1;
            } else {
                first = mid + 1;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; index++) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    @Override
        public Iterator<T> getIterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {

        private int counter = 0;

        @Override
        public boolean hasNext() {
            return counter < numberOfEntries;
        }

        @Override
        public T next() {
            T currentData = array[counter];

            if (hasNext()) {
                currentData = array[counter];
                counter++;
            }
            return currentData;
        }
    }
    
    

}
