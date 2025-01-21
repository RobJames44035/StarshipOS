/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

public interface Interface<IE> {

    /**
     * The public modifier of this interface field should not show up
     * in the documentation.
     */
    public final int field = 1;

    /**
     * The public modifier of this interface method should not show up in the
     * documentation.
     *
     * @return some dummy integer.
     */
    public int method();
}
