/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// This loads the class affected by the --patch-module option.  For the test to pass
// it must load the class from the --patch-module directory, not the jimage file.
public class PatchMain {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("cdsutils.DynamicDumpHelper")) {
                break;
            }
            Class.forName(args[i]);
        }
    }
}
