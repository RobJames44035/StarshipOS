/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package pkg;

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;

// This class is used by test HiddenGetModule.java.
public class HasNamedModule {

    public static void compareModules(byte[] klassbuf) throws Throwable {
        String moduleName = HasNamedModule.class.getModule().toString();
        System.out.println("HasNamedModule module: " + moduleName);
        Lookup lookup = MethodHandles.lookup();
        Class<?> cl = lookup.defineHiddenClass(klassbuf, false, NESTMATE).lookupClass();
        if (cl.getModule() != HasNamedModule.class.getModule()) {
            System.out.println("HasNamedModule: " + moduleName +
                               ", hidden class module: " + cl.getModule());
            throw new RuntimeException("hidden class and lookup class have different modules");
        }
        if (!moduleName.contains("HiddenModule")) {
            throw new RuntimeException("wrong module name: " + moduleName);
        }
    }
}
