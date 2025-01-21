/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;

public class JCmdTestLingeredApp extends LingeredApp {
    public JCmdTestLingeredApp() {
        // Do not use default test.class.path in class path.
        setUseDefaultClasspath(false);
    }

    public static void main(String args[]) {
        try {
            Class.forName("Hello");
        } catch (Exception e) {
            System.out.print("Could not load Hello "+ e);
        }
        LingeredApp.main(args);
    }
}
