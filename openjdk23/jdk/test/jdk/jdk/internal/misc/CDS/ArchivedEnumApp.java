/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.lang.module.ModuleDescriptor.Requires.Modifier;
import java.util.Optional;

public class ArchivedEnumApp {
    public static void main(final String[] args) throws Exception {
        // Validate the archiving of the synthetic Modifier.$VALUES field:
        for (Modifier mod : Modifier.values()) {
            check(mod);
        }
        if (Modifier.values().length != 4) {
            throw new RuntimeException("Modifier.$VALUES.length expected: 4, actual: " + Modifier.values().length);
        }

        // All 4 enums must exist in synthetic Modifier.$VALUES
        check_in_array(Modifier.MANDATED);
        check_in_array(Modifier.STATIC);
        check_in_array(Modifier.SYNTHETIC);
        check_in_array(Modifier.TRANSITIVE);

        // Find this module from (archived) boot layer
        String moduleName = "java.management";
        Optional<Module> module = ModuleLayer.boot().findModule(moduleName);
        if (module.isEmpty()) {
            throw new RuntimeException(moduleName + " module is missing in boot layer");
        }
        ModuleDescriptor md = module.get().getDescriptor();
        System.out.println("Module: " + md);
        for (Requires r : md.requires()) {
            System.out.println("Requires: " + r);
            for (Modifier mod : r.modifiers()) {
                System.out.println("   modifier: " + mod);
                check(mod);
            }
        }

        System.out.println("Success");
    }

    static void check(Modifier mod) {
        // The archived Enum object must equal to one of the following
        // four values.
        if (mod != Modifier.MANDATED &&
            mod != Modifier.STATIC &&
            mod != Modifier.SYNTHETIC &&
            mod != Modifier.TRANSITIVE) {

            System.out.println("mod                 = " + info(mod));
            System.out.println("Modifier.MANDATED   = " + info(Modifier.MANDATED));
            System.out.println("Modifier.STATIC     = " + info(Modifier.STATIC));
            System.out.println("Modifier.SYNTHETIC  = " + info(Modifier.SYNTHETIC));
            System.out.println("Modifier.TRANSITIVE = " + info(Modifier.TRANSITIVE));

            throw new RuntimeException("Archived enum object does not match static fields in enum class: " + info(mod));
        }
    }

    static void check_in_array(Modifier mod) {
        for (Modifier m : Modifier.values()) {
            if (mod == m) {
                return;
            }
        }
        throw new RuntimeException("Enum object is not in $VALUES array: " + info(mod));
    }

    static String info(Object o) {
        return "@0x" + Integer.toHexString(System.identityHashCode(o)) + " " + o;
    }
}
