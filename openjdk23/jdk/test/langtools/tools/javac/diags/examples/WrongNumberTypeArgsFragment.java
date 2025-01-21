/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// key: compiler.err.cant.apply.symbol.noargs
// key: compiler.misc.wrong.number.type.args

import java.util.*;

class WrongNumberTypeArgsFragment {
    void test() {
        Arrays.<String, Integer>asList("");
    }
}
