/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.override.unchecked.ret
// key: compiler.misc.unchecked.clash.with
// options: -Xlint:unchecked

import java.util.*;

interface Intf {
    List<String> m();
}

interface UncheckedClash extends Intf {
    public List m();
}
