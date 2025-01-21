/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4275601 4758654 4761586
 * @summary Verify that method call via super finds correct method.
 * @author maddox (cribbed from bug report)
 *
 * @compile SuperMethodResolve.java
 */

/*
 * There are probably related cases that should be covered here.
 */

import java.util.*;

public abstract class SuperMethodResolve extends AbstractCollection {
    public boolean eqData(Object x) {
        return super.equals(x);
    }
}
