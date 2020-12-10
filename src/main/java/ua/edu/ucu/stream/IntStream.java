package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

public interface IntStream {

    double average();

    int max();

    int min();
    
    IntStream flatMap(IntToIntStreamFunction func);

    long count();

    IntStream filter(IntPredicate predicate);

    void forEach(IntConsumer action);

    IntStream map(IntUnaryOperator mapper);

    int reduce(int identity, IntBinaryOperator op);

    int sum();

    int[] toArray();
}
