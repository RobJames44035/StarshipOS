/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8267555
 * @requires vm.jvmti
 * @summary Class redefinition with a different class file version
 * @library /test/lib
 * @compile TestClassOld.jasm TestClassNew.jasm
 * @run main RedefineClassHelper
 * @run main/othervm -javaagent:redefineagent.jar ClassVersionAfterRedefine
 */

import java.io.InputStream;
import java.lang.reflect.Method;

import static jdk.test.lib.Asserts.assertTrue;

public class ClassVersionAfterRedefine extends ClassLoader {

    private static String myName = ClassVersionAfterRedefine.class.getName();

    private static byte[] getBytecodes(String name) throws Exception {
        InputStream is = ClassVersionAfterRedefine.class.getResourceAsStream(name + ".class");
        byte[] buf = is.readAllBytes();
        System.out.println("sizeof(" + name + ".class) == " + buf.length);
        return buf;
    }

    private static int getStringIndex(String needle, byte[] buf) {
        return getStringIndex(needle, buf, 0);
    }

    private static int getStringIndex(String needle, byte[] buf, int offset) {
        outer:
        for (int i = offset; i < buf.length - offset - needle.length(); i++) {
            for (int j = 0; j < needle.length(); j++) {
                if (buf[i + j] != (byte)needle.charAt(j)) continue outer;
            }
            return i;
        }
        return 0;
    }

    private static void replaceString(byte[] buf, String name, int index) {
        for (int i = index; i < index + name.length(); i++) {
            buf[i] = (byte)name.charAt(i - index);
        }
    }

    private static void replaceAllStrings(byte[] buf, String oldString, String newString) throws Exception {
        assertTrue(oldString.length() == newString.length(), "must have same length");
        int index = -1;
        while ((index = getStringIndex(oldString, buf, index + 1)) != 0) {
            replaceString(buf, newString, index);
        }
    }

    public static void main(String[] s) throws Exception {

        byte[] buf = getBytecodes("TestClassOld");
        // Poor man's renaming of class "TestClassOld" to "TestClassXXX"
        replaceAllStrings(buf, "TestClassOld", "TestClassXXX");
        ClassVersionAfterRedefine cvar = new ClassVersionAfterRedefine();
        Class<?> old = cvar.defineClass(null, buf, 0, buf.length);
        Method foo = old.getMethod("foo");
        Object result = foo.invoke(null);
        assertTrue("java-lang-String".equals(result));
        System.out.println(old.getSimpleName() + ".foo() = " + result);

        buf = getBytecodes("TestClassNew");
        // Rename class "TestClassNew" to "TestClassXXX" so we can use it for
        // redefining the original version of "TestClassXXX" (i.e. "TestClassOld").
        replaceAllStrings(buf, "TestClassNew", "TestClassXXX");
        // Now redine the original version of "TestClassXXX" (i.e. "TestClassOld").
        RedefineClassHelper.redefineClass(old, buf);
        result = foo.invoke(null);
        assertTrue("java.lang.String".equals(result));
        System.out.println(old.getSimpleName() + ".foo() = " + result);
    }
}
