package roa;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by roa on 24.09.2014.
 */
public class MinAbsSlice {

    private int sum(int p, int q, int a[]) {
        int sum = 0;
        for (int i = p; i < q; i++) {
            sum += a[i];
        }

        return sum;
    }

    public int solution(int a[]) {
        return a[0];
    }

    @Test
    public void testOneElement() {
        assertThat(solution(new int[] {1}), is(1));
    }
}
