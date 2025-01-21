/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.misc.where.fresh.typevar
// key: compiler.misc.where.description.typevar
// key: compiler.err.prob.found.req
// key: compiler.misc.inconvertible.types
// options: --diags=formatterOptions=where,simpleNames
// run: simple

import java.util.*;

class WhereFreshTvar {
    <T extends List<T>> T m() {}

    { Object o = (List<String>)m(); }
}
