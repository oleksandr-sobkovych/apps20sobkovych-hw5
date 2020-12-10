package ua.edu.ucu.iterators;

import lombok.Getter;

public class BaseStreamIterator implements StreamIterator {
    private final int[] values;
    private int i = 0;

    public BaseStreamIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return i < this.values.length;
    }

    @Override
    public int next() {
        return this.values[this.i++];
    }
}
