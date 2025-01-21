/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package pkg;

public class CtorTypeParam {
    /**
     * Generic constructor. {@link T}
     *
     * @param <T> the type parameter
     * @see T link to type parameter
     */
    public <T extends Runnable> CtorTypeParam() {
    }
}
