/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package listpkg;

import java.util.Collection;
import java.util.Map;

/**
 * Example class containing "list" but not matching at any word boundary. {@index "other
 * search tag"}.
 */
public class Nolist {

    public final int SOME_INT_CONSTANT = 0;

    public Nolist() {}

    public void nolist() {}


    public static List withTypeParams(Map<String, ? extends Collection> map) {
        return null;
    }

    public static List withVarArgs(Object... args) {
        return null;
    }

    public static List withArrayArg(int[] args) {
        return null;
    }
}
