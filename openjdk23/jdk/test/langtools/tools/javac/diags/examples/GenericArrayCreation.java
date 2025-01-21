/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.generic.array.creation

import java.util.*;

class GenericArrayCreation<T> {
    ArrayList<T>[] array = new ArrayList<T>[5];
}
