/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package pkg2;

abstract class UndocumentedGenericParent<T, E extends Throwable, F extends Throwable> {
    /**
     * A field.
     */
    public T parentField;

    /**
     * Returns some value with an {@index "inherited search tag"}.
     *
     * @param t a parameter
     * @return some value
     * @throws E a generic error
     * @throws IllegalStateException illegal state
     */
    protected abstract T parentMethod(T t) throws F, E, IllegalStateException;

    /**
     * Method with systemProperty tag. {@systemProperty parent.system.prop}.
     */
    public abstract void parentMethod2();
}
