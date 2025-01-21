/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;

interface Language {
    static final long nbrOfWords = 99999;
    public abstract long getNbrOfWords();
    default boolean hasScript() {
        return true;
    }
}

class ParselTongue implements Language {
    public long getNbrOfWords() {
        return nbrOfWords * 4;
    }
}

class SlytherinSpeak extends ParselTongue {
    public boolean hasScript() {
        return false;
    }
}

public class LingeredAppWithDefaultMethods extends LingeredApp {

    public static void main(String args[]) {
        ParselTongue lang = new ParselTongue();
        SlytherinSpeak slang = new SlytherinSpeak();
        System.out.println(lang.hasScript() || slang.hasScript());

        LingeredApp.main(args);
    }
 }
