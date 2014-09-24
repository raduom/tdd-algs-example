package roa;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by roa on 24.09.2014.
 */
public class MinAbsSlice {

    private int sum(int a[], int p, int q) {
        int sum = 0;
        for (int i = p; i < q; i++) {
            sum += a[i];
        }

        return Math.abs(sum);
    }

    public int solution(int... a) {
        int min = Integer.MAX_VALUE;

        for (int i=0; i<a.length; i++) {  // --> This is n
            min = Math.min(sum(a, i, i+1), min);
            for (int j=0; j<i; j++) {     // --> This is n
                min = Math.min(sum(a, j, i+1), min);
            }
        }

//        if (a.length >= 1) {
//            min = sum(a, 0, 1);
//        }
//
//        if (a.length >= 2) {
//            min = Math.min(sum(a, 1, 2), min);
//            min = Math.min(sum(a, 0, 2), min);
//        }
//
//        if (a.length >= 3) {
//            min = Math.min(sum(a, 2, 3), min);
//            min = Math.min(sum(a, 1, 3), min);
//            min = Math.min(sum(a, 0, 3), min);
//        }
//
//        if (a.length >= 4) {
//            min = Math.min(sum(a, 0, 4), min);
//            min = Math.min(sum(a, 1, 4), min);
//            min = Math.min(sum(a, 2, 4), min);
//            min = Math.min(sum(a, 3, 4), min);
//        }

        return min;
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
