/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

interface PrivateInterface {

    /**
     * Comment from parent.
     */
    public static final int fieldInheritedFromInterface = 0;

    /**
     * Comment from interface.
     * This is a link to myself: {@link #fieldInheritedFromInterface}
     *
     * @param p1 param from interface.
     * @throws Exception exception from interface.
     *
     * @see #fieldInheritedFromInterface
     */
    public void methodInterface(int p1) throws Exception;

    /**
     * Comment from interface.
     *
     * @param p1 param from interface.
     * @throws Exception exception from interface.
     */
    public void methodInterface2(int p1) throws Exception;
}
