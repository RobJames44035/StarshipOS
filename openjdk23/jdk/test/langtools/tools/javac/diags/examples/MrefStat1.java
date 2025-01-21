/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.note.mref.stat.1
// options: --debug=dumpLambdaToMethodStats

class MrefStat1 {

    void m() { }

    static class Sub extends MrefStat1 {
        Runnable r = super::m;
    }
}
