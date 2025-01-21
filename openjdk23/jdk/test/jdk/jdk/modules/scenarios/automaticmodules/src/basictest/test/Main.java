/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package test;

import http.HttpServer;

/**
 * Basic test using automatic modules.
 */

public class Main {
    public static void main(String[] args) throws Exception {

        Module httpModule = HttpServer.class.getModule();

        // automatic modules are named
        assertTrue(httpModule.isNamed());

        // and read all unnamed modules
        ClassLoader cl;
        cl = ClassLoader.getPlatformClassLoader();
        assertTrue(httpModule.canRead(cl.getUnnamedModule()));
        cl = ClassLoader.getSystemClassLoader();
        assertTrue(httpModule.canRead(cl.getUnnamedModule()));

        // and read all modules in the boot layer
        ModuleLayer layer = ModuleLayer.boot();
        layer.modules().forEach(m -> assertTrue(httpModule.canRead(m)));

        // run code in the automatic modue, ensures access is allowed
        HttpServer http = HttpServer.create(80);
        http.start();
    }

    static void assertTrue(boolean e) {
        if (!e)
            throw new RuntimeException();
    }
}
