package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntUnaryOperator;

public class MapIterator implements StreamIterator {
    private final IntUnaryOperator operator;
    private final StreamIterator iterator;

    public MapIterator(StreamIterator iterator, IntUnaryOperator operator) {
        this.iterator = iterator;
        this.operator = operator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public int next() {
        return operator.apply(iterator.next());
    }
}
