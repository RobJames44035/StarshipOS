/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8022718
 * @summary Runtime accessibility checking: protected class, if extended, should be accessible from another package
 * @compile -XDignore.symbol.file BogoLoader.java MethodInvoker.java Test.java anotherpkg/MethodSupplierOuter.java
 * @run main/othervm Test
 */

import java.lang.classfile.ClassTransform;
import java.lang.classfile.attribute.InnerClassInfo;
import java.lang.classfile.attribute.InnerClassesAttribute;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.classfile.ClassFile.ACC_PRIVATE;
import static java.lang.classfile.ClassFile.ACC_PROTECTED;
import static java.lang.classfile.ClassFile.ACC_PUBLIC;

interface MyFunctionalInterface {

    void invokeMethodReference();
}

public class Test {

    public static void main(String[] argv) throws Throwable {
        ClassTransform makeProtectedNop = ClassTransform.ACCEPT_ALL;
        ClassTransform makeProtectedMod = (cb, ce) -> {
            if (ce instanceof InnerClassesAttribute ica) {
                cb.accept(InnerClassesAttribute.of(ica.classes().stream().map(ici -> {
                    // AccessFlags doesn't support inner class flags yet
                    var flags = (ACC_PROTECTED | ici.flagsMask()) & ~(ACC_PRIVATE | ACC_PUBLIC);
                    System.out.println("visitInnerClass: name = " + ici.innerClass().asInternalName()
                            + ", outerName = " + ici.outerClass().map(ClassEntry::asInternalName).orElse("null")
                            + ", innerName = " + ici.innerName().map(Utf8Entry::stringValue).orElse("null")
                            + ", access original = 0x" + Integer.toHexString(ici.flagsMask())
                            + ", access modified to 0x" + Integer.toHexString(flags));
                    return InnerClassInfo.of(ici.innerClass(), ici.outerClass(), ici.innerName(), flags);
                }).toList()));
            } else {
                cb.accept(ce);
            }
        };

        int errors = 0;
        errors += tryModifiedInvocation(makeProtectedNop);
        errors += tryModifiedInvocation(makeProtectedMod);

        if (errors > 0) {
            throw new Error("FAIL; there were errors");
        }
    }

    private static int tryModifiedInvocation(ClassTransform makeProtected)
            throws Throwable {
        var replace = new HashMap<String, ClassTransform>();
        replace.put("anotherpkg.MethodSupplierOuter$MethodSupplier", makeProtected);
        var in_bogus = new HashSet<String>();
        in_bogus.add("MethodInvoker");
        in_bogus.add("MyFunctionalInterface");
        in_bogus.add("anotherpkg.MethodSupplierOuter"); // seems to be never loaded
        in_bogus.add("anotherpkg.MethodSupplierOuter$MethodSupplier");

        BogoLoader bl = new BogoLoader(in_bogus, replace);
        try {
            Class<?> isw = bl.loadClass("MethodInvoker");
            Method meth = isw.getMethod("invoke");
            Object result = meth.invoke(null);
        } catch (Throwable th) {
            System.out.flush();
            Thread.sleep(250); // Let Netbeans get its I/O sorted out.
            th.printStackTrace();
            System.err.flush();
            Thread.sleep(250); // Let Netbeans get its I/O sorted out.
            return 1;
        }
        return 0;
    }
}
