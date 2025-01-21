/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
import jdk.test.lib.apps.LingeredApp;

public class LingeredAppWithExtendedChars extends LingeredApp {

    public static int \u00CB = 1;

    public static void main(String args[]) {
        LingeredApp.main(args);
    }
 }
