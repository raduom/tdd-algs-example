package roa;

import org.junit.Test;

import java.util.NavigableSet;
import java.util.TreeSet;

import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Write a function:
 *
 * int solution(int A[], int N);
 *
 * That, given a non-empty zero-indexed array A consisting of N integers,
 * returns the absolute sum of min abs slice.
 *
 * Created by roa on 24.09.2014.
 */
public class MinAbsSlice {

    public int solution(int... input) {
        int[] memoized = new int[input.length];
        memoized[0] = input[0];
        int min = abs(memoized[0]);
        TreeSet<Integer> mins = new TreeSet<>();

        for (int i=1; i<input.length; i++) {
            memoized[i] = memoized[i-1] + input[i];
            mins.add(memoized[i-1]);
            min = findMinSolution(mins, memoized[i], min);
        }

        return min;
    }

    private Integer key(int current, boolean low) {
        if ((low && current > 0) || (!low && current < 0))
            return 0;
        return current;
    }

    private Integer toKey(int current) {
        return key(current, false);
    }

    private Integer fromKey(int current) {
        return key(current, true);
    }

    private Integer findMinSolution(NavigableSet<Integer> s, int current, int min) {
        min = min(abs(current), min);
        s = s.subSet(fromKey(current), true, toKey(current), true);
        if (s.size() != 0) {
            return min(min,
                       abs(current - getBestValue(s, current)));
        }

        return min;
    }

    private int getBestValue(NavigableSet<Integer> s, int current) {
        return current > 0 ?
                s.first() : s.last();
    }

    @Test
    public void testComplexityIsCloseToN() {
        final int BASE = 5000000;
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
