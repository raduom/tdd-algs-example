package roa;

import org.junit.Test;

import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by roa on 24.09.2014.
 */
public class MinAbsSlice {

    private int sum(int input[], int p, int q) {
        int sum = 0;
        for (int i = p; i < q; i++) {    // --> This is (also) n
            sum += input[i];
        }

        return abs(sum);
    }

    public int solution(int... input) {
        int min = Integer.MAX_VALUE;

        for (int i=0; i<input.length; i++) {  // --> This is n
            min = min(abs(input[i]), min);
            for (int j=0; j<i; j++) {     // --> This is n
                min = min(sum(input, j, i + 1), min);
            }
        }

        return min;
    }

    @Test
    public void testComplexityIsCloseToN() {
        final int BASE = 1000;
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
