/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.type.found.req
// key: compiler.misc.type.parameter
// key: compiler.misc.type.req.class

import java.util.*;

class X<T> {
    List<T<Integer>> list;
}
