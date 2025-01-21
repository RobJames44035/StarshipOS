/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

/**
 * @deprecated enum_test1 passes.
 */
@Deprecated(forRemoval=true)
public enum TestEnum {

    /**
     * @deprecated enum_test2 passes.
     */
    ONE, TWO, THREE,

    /**
     * @deprecated enum_test3 passes.
     */
    @Deprecated(forRemoval=true)
    FOR_REMOVAL;
}
