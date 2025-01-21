/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cant.resolve.args

import java.util.*;

class CantResolveArgs {
    void m() {
        new Runnable() {
            { unknown(); }
            public void run() { }
        };
    }
}
