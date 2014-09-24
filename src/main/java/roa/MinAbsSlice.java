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
        return sum(a, 0, a.length);
    }


    @Test
    public void testThreeMixedElements() {
        assertThat(solution(-1, 2, 3), is(1));
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
        assertThat(solution(1, 2), is(3));
    }
}
