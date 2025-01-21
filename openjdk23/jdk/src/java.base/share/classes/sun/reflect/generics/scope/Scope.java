/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.reflect.generics.scope;

import java.lang.reflect.TypeVariable;


public interface Scope {
    TypeVariable<?> lookup(String name);
}
