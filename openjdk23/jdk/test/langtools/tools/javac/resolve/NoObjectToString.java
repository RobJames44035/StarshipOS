/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8272564
 * @summary Correct resolution of toString() (and other similar calls) on interfaces
 * @compile NoObjectToString.java
 * @run main NoObjectToString
 */

import java.io.*;
import java.lang.classfile.*;
import java.lang.classfile.constantpool.*;

public class NoObjectToString {
    public static void main(String... args) throws Exception {
        NoObjectToString c = new NoObjectToString();
        c.run(args);
    }

    void run(String... args) throws Exception {
         //Verify there are no references to Object.toString() in a Test:
        try (InputStream in = NoObjectToString.class.getResourceAsStream("NoObjectToString$Test.class")) {
            assert in != null;
            ClassModel cm = ClassFile.of().parse(in.readAllBytes());
            for (PoolEntry pe : cm.constantPool()) {
                if (pe instanceof MethodRefEntry ref) {
                    String methodDesc = ref.owner().name() + "." + ref.nameAndType().name() + ":" + ref.nameAndType().type();

                    if ("java/lang/Object.toString:()Ljava/lang/String;".equals(methodDesc)) {
                        throw new AssertionError("Found call to j.l.Object.toString");
                    }
                }
            }
        }
    }

    class Test {
        void test(I i, J j, K k) {
            i.toString();
            j.toString();
            k.toString();
        }
    }

    interface I {
        public String toString();
    }
    interface J extends I {}
    interface K {}

}
