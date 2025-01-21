/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package pkg1;

/**
 * @hidden
 * @param <T>
 */
public abstract class InvisibleParent<T extends InvisibleParent> implements Intf {

    @Override
    public void visibleInterfaceMethod() {}

    /**
     * An invisible method made visible in an implementing class.
     */
    @Override
    public void invisibleInterfaceMethod() {}

    /**
     * A visible inner class.
     */
    public static class VisibleInner {
        /**
         * An invisible constructor
         * @hidden invisible
         */
        public VisibleInner() {}
    }

    /**
     * An invisible inner class.
     * @hidden
     */
    public static class InvisibleInner {}

}
