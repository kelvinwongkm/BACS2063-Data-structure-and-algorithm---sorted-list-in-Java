/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author Cheok Ding Wei, Wong Kah Ming
 */
public interface SortedListInterface<T extends Comparable<T>> {

    public boolean add(T newEntry);

    public T search(T anEntry);

    public boolean remove(T anEntry);

    public boolean contains(T anEntry);

    public void clear();

    public int getNumberOfEntries();

    public boolean isEmpty();
    
    public Iterator<T> getIterator();
}

