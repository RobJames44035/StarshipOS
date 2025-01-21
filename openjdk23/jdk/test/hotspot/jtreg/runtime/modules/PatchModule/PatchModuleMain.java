/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// This loads the class affected by the --patch-module option.  For the test to pass
// it must load the class from the --patch-module directory, not the jimage file.
public class PatchModuleMain {
    public static void main(String[] args) throws Exception {
        Class.forName(args[0]);
    }
}
