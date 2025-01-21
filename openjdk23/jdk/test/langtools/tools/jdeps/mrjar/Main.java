/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import test.Version;
import p.Foo;

public class Main {
    public void run() {
        Version v = new Version();
        v.getVersion();
    }

    public static void main(String[] args) {
        (new Main()).run();
        Foo foo = new Foo();
    }
}
