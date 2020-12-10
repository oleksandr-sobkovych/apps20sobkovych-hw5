package ua.edu.ucu;

import ua.edu.ucu.stream.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AsIntStreamTest {
    private static final double DELTA = 1e-5;
    private IntStream intStream;
    private IntStream emptyStream;

    @Before
    public void setUp() {
        int[] intArr = {-1, 0, 1, 2, 3};
        intStream = AsIntStream.of(intArr);
        emptyStream = AsIntStream.of();
    }

    @Test
    public void testEmptyConstructor() {
        assertEquals(0, emptyStream.count());
    }

    @Test
    public void testConstructorSafety() {
        int[] intArr = {-1, 0, 1, 2, 3, 7, 10};
        int[] expectedArr = intArr.clone();
        IntStream intStream = AsIntStream.of(intArr);
        intArr[2] = 100;
        assertArrayEquals(expectedArr, intStream.toArray());
    }

    @Test
    public void testMax() {
        assertEquals(3, intStream.max());
    }

    @Test
    public void testMin() {
        assertEquals(-1, intStream.min());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMax() {
        emptyStream.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMin() {
        emptyStream.min();
    }

    @Test
    public void testMaxTerminality() {
        intStream.max();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testMinTerminality() {
        intStream.min();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testCount() {
        assertEquals(5, intStream.count());
    }

    @Test
    public void testCountTerminality() {
        intStream.count();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testSum() {
        assertEquals(5, intStream.sum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySum() {
        emptyStream.sum();
    }

    @Test
    public void testSumTerminality() {
        intStream.sum();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testZeroSum() {
        assertEquals(0, AsIntStream.of(0, 0, 0, 0).sum());
    }

    @Test
    public void testAverage() {
        assertEquals(1.0, intStream.average(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAverage() {
        emptyStream.average();
    }

    @Test
    public void testAverageTerminality() {
        intStream.average();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testAverageSumDependency() {
        int[] array = new int[]{-10, 15, 4, 5, -9, 50};
        IntStream avgStream = AsIntStream.of(array);
        IntStream sumStream = AsIntStream.of(array);
        assertEquals(avgStream.average(), sumStream.sum() / (double) array.length, DELTA);
    }

    @Test
    public void testToArray() {
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3}, intStream.toArray());
    }

    @Test
    public void testToArrayEmpty() {
        assertArrayEquals(new int[0], emptyStream.toArray());
    }

    @Test
    public void testToArrayTerminality() {
        intStream.toArray();
        assertEquals(0, intStream.count());
    }

    @Test
    public void testForEach() {
        List<Integer> list = new LinkedList<>();
        intStream.forEach(a -> list.add(0, a));
        assertArrayEquals(new Object[]{3, 2, 1, 0, -1}, list.toArray());
    }

    @Test
    public void testForEachTerminality() {
        intStream.forEach(System.out::println);
        assertEquals(0, intStream.count());
    }

    @Test
    public void testForEachEmpty() {
        List<Integer> list = new LinkedList<>();
        emptyStream.forEach(a -> list.add(0, a));
        assertArrayEquals(new Object[0], list.toArray());
    }

    @Test
    public void testReduce() {
        assertEquals(2, intStream.reduce(-3, (or, x) -> or ^= x));
    }

    @Test
    public void testReduceTerminality() {
        intStream.reduce(-3, (or, x) -> or ^= x);
        assertEquals(0, intStream.count());
    }

    @Test
    public void testReduceEmpty() {
        assertEquals(-3, emptyStream.reduce(-3, (or, x) -> or ^= x));
    }

    @Test
    public void testFilter() {
        assertArrayEquals(new int[]{-1, 0}, intStream.filter(x -> x <= 0).toArray());
    }

    @Test
    public void testMap() {
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, intStream.map(x -> x + 1).toArray());
    }

    @Test
    public void testFlatMap() {
        assertArrayEquals(new int[]{-2, -1, 0, -1, 0, 1, 0,
                        1, 2, 1, 2, 3, 2, 3, 4},
                intStream.flatMap(x -> AsIntStream.of(x - 1,
                        x,
                        x + 1)).toArray());
    }

    @Test
    public void testSequence() {
        assertArrayEquals(new int[]{10, 10, 20, 10, 20, 30, 20, 30, 40},
                intStream.flatMap(x -> AsIntStream.of(x - 1,
                        x,
                        x + 1))
                    .filter(x -> x > 0)
                    .map(x -> x * 10)
                    .toArray());
    }
}
