/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package pkg;

/**
 * Test record.
 * @since 3.2
 */
public record TestRecord(int x, int y) {

    /**
     * Test record getter.
     * @return x
     * @since 5
     */
    @Override
    public int x() {
        return x;
    }
}
