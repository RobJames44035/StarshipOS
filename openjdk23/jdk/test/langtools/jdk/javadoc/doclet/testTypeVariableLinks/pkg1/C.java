/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package pkg1;

import java.util.List;

public class C<T, F extends List<String>, G extends Additional & I> {
    /**
     * Linking to Object.equals() {@link T#equals(Object)}
     */
    public void m1() {}
    /**
     * Linking to List.clear() {@link F#clear()}
     */
    public void m2() {}
    /**
     * Linking to Additional.doAction() {@link G#doAction()}
     */
    public void m3() {}
    /**
     * Linking to I.abstractAction() {@link G#abstractAction()}
     */
    public void m4() {}
}

class Additional {
    public void doAction() {}
}

interface I {
    void abstractAction();
}
