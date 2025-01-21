/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Access a jrt:/ resource in an observable module that is not in the boot
 * layer and hence not known to the built-in class loaders.
 */

public class OtherResources {
    public static void main(String[] args) throws IOException {

        // check that java.desktop is not in the set of readable modules
        try {
            Class.forName("java.awt.Component");
            throw new RuntimeException("Need to run with --limit-modules java.base");
        } catch (ClassNotFoundException expected) { }

        // access resource in the java.desktop module
        URL url = new URL("jrt:/java.desktop/java/awt/Component.class");
        URLConnection uc = url.openConnection();
        System.out.println(uc.getInputStream());
    }
}
