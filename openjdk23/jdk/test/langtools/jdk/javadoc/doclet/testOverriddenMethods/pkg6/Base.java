/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package pkg6;

import java.io.IOException;

public class Base<T> {
    /**
     *  This is Base::m1.
     *  @return something
     */
    public Object m1() { }

    /**
     *  This is Base::m2.
     *  @return something
     */
    public Object m2() { }

    /**
     *  This is Base::m3.
     *  @return something
     */
    public T m3() { }

    /**
     * This is Base::m4.
     */
    protected void m4() { }

    /**
     * This is Base::m5.
     * @throws IOException an error
     */
    public Object m5() throws IOException { }

    /**
     * This is Base::m6.
     */
    public Object m6() { }

    /**
     * This is Base::m7.
     */
    public abstract Object m7();

    /**
     * This is Base::m8.
     */
    public Object m8() { }

    /**
     * This is Base::m9.
     */
    public abstract Object m9();
}
