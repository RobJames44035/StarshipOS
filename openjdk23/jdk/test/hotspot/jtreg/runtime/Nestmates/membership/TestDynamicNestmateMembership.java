/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Test the rules for dynamic nest membership using the Lookup.defineHiddenClass API
 * @compile OtherPackage.java
 * @run main/othervm TestDynamicNestmateMembership
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.lang.invoke.MethodHandles;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;

/* package */ class DynamicNestmate { }

/* package */ class DynamicNestmate2 { }

/* package */ class StaticHost {
    static class StaticMember {
    }
}

public class TestDynamicNestmateMembership {

    static class Member {
        static MethodHandles.Lookup getLookup() {
            return MethodHandles.lookup();
        }
    }

    static final String CLASSES = System.getProperty("test.classes");
    static final Path CLASSES_DIR = Paths.get(CLASSES);

    static byte[] getBytesForClass(String name) throws IOException {
        Path classFile;
        if (name.indexOf('.') > 0) {
            // it's in a package
            String[] paths = name.split("\\.");
            classFile = CLASSES_DIR.resolve(paths[0]);
            classFile = classFile.resolve(paths[1] + ".class");
        }
        else {
            classFile = CLASSES_DIR.resolve(name + ".class");
        }
        return Files.readAllBytes(classFile);
    }

    static final Class<?> cls = TestDynamicNestmateMembership.class;
    static final MethodHandles.Lookup main_lookup = MethodHandles.lookup();

    public static void main(String[] args) throws Throwable {
        test_validInjection();
        test_hostIsMember();
        test_otherPackage();
        test_alreadyNestMember();
        test_alreadyNestHost();
    }

    // Inject a valid class into the nest of the current class
    static void test_validInjection() {
        String name = "DynamicNestmate";
        inject(name, null);
    }

    // Try to inject a class into a "host" that is itself a member.
    // This is redirected at the defineClass level to the member's
    // host and so will succeed.
    static void test_hostIsMember() {
        String name = "DynamicNestmate2";
        inject(name, Member.getLookup(), null);
    }

    // Try to inject a class that has a static NestHost attribute
    // No error since the nest host has been set when it's created.
    // Static nest membership is effectively ignored.
    static void test_alreadyNestMember() {
        String name = "StaticHost$StaticMember";
        inject(name, null);
    }

    // Try to inject a class that has the NestMembers attribute.
    // No error since the nest host has been set when it's created.
    // Static nest membership is effectively ignored.
    static void test_alreadyNestHost() {
        String name = "StaticHost";
        inject(name, null);
    }

    // Try to inject a class that is in another package
    static void test_otherPackage() {
        String name = "test.OtherPackage";
        inject(name, IllegalArgumentException.class);
    }

    static void inject(String name, Class<? extends Throwable> ex) {
        inject(name, main_lookup, ex);
    }

    static void inject(String name, MethodHandles.Lookup lookup,
                       Class<? extends Throwable> ex) {
        Class<?> target = lookup.lookupClass();
        String action = "Injecting " + name + " into the nest of " +
            target.getSimpleName();
        try {
            byte[] bytes = getBytesForClass(name);
            Class<?> nestmate = lookup.defineHiddenClass(bytes, false, NESTMATE).lookupClass();
            if (ex != null) {
                throw new RuntimeException(action + " was expected to throw " +
                                           ex.getSimpleName());
            }
            Class<?> actualHost = nestmate.getNestHost();
            Class<?> expectedHost = target.getNestHost();
            if (actualHost != expectedHost) {
                throw new RuntimeException(action + " expected a nest-host of "
                                           + expectedHost.getSimpleName() +
                                           " but got " + actualHost.getSimpleName());
            }
            System.out.print("Ok: " + action + " succeeded: ");
            if (actualHost != target) {
                System.out.print("(re-directed to target's nest-host) ");
            }
            System.out.println("Nesthost of " + nestmate.getName() +
                               " is " + actualHost.getName());
        } catch (Throwable t) {
            if (t.getClass() == ex) {
                System.out.println("Ok: " + action + " got expected exception: " +
                                   t.getClass().getSimpleName() + ":" +
                                   t.getMessage());
            }
            else {
                throw new RuntimeException(action + " got unexpected exception " +
                                           t.getClass().getSimpleName(), t);
            }
        }
    }

}

