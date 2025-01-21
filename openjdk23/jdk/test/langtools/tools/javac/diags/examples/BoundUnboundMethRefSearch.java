/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.note.method.ref.search.results.multi
// key: compiler.misc.bound
// key: compiler.misc.applicable.method.found.2
// key: compiler.misc.static
// key: compiler.misc.non.static
// key: compiler.misc.unbound
// options: --debug=dumpMethodReferenceSearchResults

import java.util.function.*;

class BoundUnboundMethRefSearch {
    public String foo(Object o) { return "foo"; }
    public static String foo(String o) { return "bar"; }

    void m() {
        Function<String, String> f = BoundUnboundMethRefSearch::foo;
    }
}
