package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntToIntStreamFunction;

public class FlatMapIterator implements StreamIterator {
    private final StreamIterator iterator;
    private final IntToIntStreamFunction function;
    private int[] current = new int[0];
    private int currentI = 0;

    public FlatMapIterator(StreamIterator iterator, IntToIntStreamFunction function) {
        this.iterator = iterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        if (currentI >= current.length && iterator.hasNext()) {
            current = this.function.applyAsIntStream(iterator.next()).toArray();
            currentI = 0;
        }
        return currentI < current.length;
    }

    @Override
    public int next() {
        return current[currentI++];
    }
}
