
package adt;

/**
 *
 * @author Wong Kah Ming
 */
public class Map<T> {

    T entry;
    int index;

    Map() {
        this(null, -1);
    }

    Map(Map<T> object) {
        this.entry = object.entry;
        this.index = object.index;
    }

    Map(T entry, int index) {
        this.entry = entry;
        this.index = index;
    }

    public T getEntry() {
        return entry;
    }

    public void setEntry(T entry) {
        this.entry = entry;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
