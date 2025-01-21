/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.bad.initializer
import java.util.List;
class ForeachBadInitialization {
    void m() {
        List<String> s = null;
        for (a : s) {}
    }
}
