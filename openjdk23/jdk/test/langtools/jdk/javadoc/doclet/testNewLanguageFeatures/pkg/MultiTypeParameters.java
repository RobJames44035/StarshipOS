/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package pkg;

import java.util.*;

public class MultiTypeParameters {

    public <T extends Number & Runnable> T foo(T t) {
        return null;
    }

}
