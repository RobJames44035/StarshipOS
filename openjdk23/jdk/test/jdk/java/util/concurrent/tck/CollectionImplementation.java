/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea and Martin Buchholz with assistance from
 * members of JCP JSR-166 Expert Group and released to the public
 * domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.Collection;

/** Allows tests to work with different Collection implementations. */
public interface CollectionImplementation {
    /** Returns the Collection class. */
    public Class<?> klazz();
    /** Returns an empty collection. */
    public Collection emptyCollection();
    public Object makeElement(int i);
    public boolean isConcurrent();
    public boolean permitsNulls();
}
