package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntPredicate;

public class FilterIterator implements StreamIterator {
    private final StreamIterator iterator;
    private final IntPredicate predicate;
    private int currentNext;

    public FilterIterator(StreamIterator iterator, IntPredicate predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        while (iterator.hasNext()) {
            currentNext = this.iterator.next();
            if (!predicate.test(currentNext)) {
                currentNext = this.next();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public int next() {
        return currentNext;
    }
}
