package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.iterators.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AsIntStream implements IntStream {
    private final StreamIterator iterator;

    private AsIntStream(StreamIterator iterator) {
        this.iterator = iterator;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(new BaseStreamIterator(values));
    }

    @Override
    public double average() {
        checkEmpty("cannot determine average in empty strea");
        int count = 0;
        double avg = 0;
        while (this.iterator.hasNext()) {
            int val = this.iterator.next();
            count++;
            avg += (val - avg) / count;
        }
        return avg;
    }

    private interface IntComparator {
        boolean compare(int first, int second);
    }

    private static class LessComparator implements IntComparator {
        @Override
        public boolean compare(int first, int second) {
            return first < second;
        }
    }

    private static class GreaterComparator implements IntComparator {
        @Override
        public boolean compare(int first, int second) {
            return first > second;
        }
    }

    private void checkEmpty(String errorMessage) {
        if (!this.iterator.hasNext()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private int extreme(IntComparator comparator, String extremeType) {
        checkEmpty("cannot determine" + extremeType + "in empty stream");
        int current = this.iterator.next();
        while (this.iterator.hasNext()) {
            int val = this.iterator.next();
            if (comparator.compare(val, current)) {
                current = val;
            }
        }
        return current;
    }

    @Override
    public int max() {
        return extreme(new GreaterComparator(), "maximum");
    }

    @Override
    public int min() {
        return extreme(new LessComparator(), "minimum");
    }

    @Override
    public long count() {
        long count = 0;
        while (this.iterator.hasNext()) {
            this.iterator.next();
            count++;
        }
        return count;
    }

    @Override
    public int sum() {
        checkEmpty("cannot determine sum in empty stream");
        int sum = 0;
        while (this.iterator.hasNext()) {
            sum += this.iterator.next();
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return new AsIntStream(new FilterIterator(this.iterator, predicate));
    }

    @Override
    public void forEach(IntConsumer action) {
        while (this.iterator.hasNext()) {
            action.accept(this.iterator.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(new MapIterator(this.iterator, mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(new FlatMapIterator(this.iterator, func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        while (this.iterator.hasNext()) {
            result = op.apply(result, this.iterator.next());
        }
        return result;
    }

    @Override
    public int[] toArray() {
        List<Integer> list = new LinkedList<>();
        while (this.iterator.hasNext()) {
            list.add(this.iterator.next());
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.listIterator().next();
        }
        return array;
    }
}
