package roa;

import org.junit.Test;

import java.util.*;

import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by roa on 24.09.2014.
 */
public class MinAbsSlice {

    private static class IndexedInteger implements Comparable<IndexedInteger> {
        public final int index;
        public final int value;

        private IndexedInteger(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(IndexedInteger o) {
            if (this.value < o.value) {
                return -1;
            } else if (this.value > o.value) {
                return 1;
            }

            return 0;
        }

        public String toString() {
            return "["+index+"] => " + value;
        }
    }

//    public int solution(int... input) {
//        int[] memoized = new int[input.length];
//        memoized[0] = input[0];
//        int min = abs(memoized[0]);
//
//        for (int i=1; i<input.length; i++) {
//            memoized[i] = memoized[i-1] + input[i];
//            min = min(abs(memoized[i]), min);
//            for (int j=0; j<i; j++)
//                min = min(abs(memoized[i] - memoized[j]), min);
//        }
//
//        return min;
//    }

    public int solution(int... input) {
        return firstSolution(input);
    }

    public int firstSolution(int... a) {
        int min = Integer.MAX_VALUE;
        int[] memoized = new int [a.length];
        TreeSet<IndexedInteger> mins = new TreeSet<>();

        if (a.length >= 1) {
            memoized[0] = a[0];
            mins.add(new IndexedInteger(0, memoized[0]));
            min = abs(memoized[0]);
        }

        if (a.length >= 2) {
            memoized[1] = memoized[0] + a[1];
            mins.add(new IndexedInteger(1, memoized[1]));
            Optional<Integer> opMin = findMinSolution(mins, a[1], min);
            if (opMin.isPresent()) {
                min = opMin.get();
            }
        }

        if (a.length >= 3) {
            memoized[2] = memoized[1] + a[2];
            mins.add(new IndexedInteger(2, memoized[2]));
            Optional<Integer> opMin = findMinSolution(mins, a[2], min);
            if (opMin.isPresent()) {
                min = opMin.get();
            }
        }

        if (a.length >= 4) {
            memoized[3] = memoized[2] + a[3];
            min = Math.min(abs(memoized[3]), min);
            min = Math.min(abs(memoized[3] - memoized[0]), min);
            min = Math.min(abs(memoized[3] - memoized[1]), min);
            min = Math.min(abs(memoized[3] - memoized[2]), min);
        }

        return min;
    }

    private IndexedInteger toKey(int current) {
        if (current > 0) {
            return new IndexedInteger(0, current);
        }

        return new IndexedInteger(0, 0);
    }

    private IndexedInteger fromKey(int current) {
        if (current > 0) {
            return new IndexedInteger(0, 0);
        }

        return new IndexedInteger(0, current);
    }

    public Optional<Integer> findMinSolution(NavigableSet<IndexedInteger> s, int current, int min) {
        s = s.subSet(fromKey(current), true, toKey(current), true);
        if (s.size() != 0) {
            int bestValue = current > 0 ?
                    s.first().value : s.last().value;
            if (min > (current + bestValue)) {
                return Optional.of(abs(current + bestValue));
            }
        }

        return Optional.empty();
    }

    @Test
    public void testComplexityIsCloseToN() {
        final int BASE = 5000;
        final int SIZE_MULTIPLIER = 4;
        int[] low = new int[BASE];
        int[] high = new int[BASE*SIZE_MULTIPLIER];

        long lowTime = timeSolution(low);
        long highTime = timeSolution(high);
        long timeMultiplier = round((double) highTime / lowTime);
        assertThat("The complexity should be close to N (which is N^1) ~ 1",
                        approximateExponent(timeMultiplier, SIZE_MULTIPLIER), is(1L));
    }

    private long approximateExponent(long timeMultiplier, int sizeMultiplier) {
        return round(log(timeMultiplier) / log(sizeMultiplier));
    }

    private long timeSolution(int[] input) {
        long now = currentTimeMillis();
        solution(input);
        now = currentTimeMillis() - now;
        return now;
    }

    @Test
    public void testFourMixedElements() {
        assertThat(solution(1, 2, -1, 4), is(1));
        assertThat(solution(5, 2, -1, -1), is(0));
        assertThat(solution(2, -4, 6, -3, 9), is(1));
    }

    @Test
    public void testThreeMixedElements() {
        assertThat(solution(-1, 2, 3), is(1));
        assertThat(solution(-1, 1, -1), is(0));
        assertThat(solution(5, 2, -1), is(1));
    }

    @Test
    public void testTowMixedElements() {
        assertThat(solution(-1, 2), is(1));
    }

    @Test
    public void testOneNegativeElement() {
        assertThat(solution(-1), is(1));
    }

    @Test
    public void testOnePositiveElement() {
        assertThat(solution(1), is(1));
    }

    @Test
    public void testTwoPositiveElements() {
        assertThat(solution(1, 2), is(1));
    }
}
