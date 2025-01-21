/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8182774
 * @summary test on class with a verification error
 * @modules jdk.jdeps/com.sun.tools.javap
 */

import java.io.*;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.file.Path;
import java.util.*;

public class VerificationTest {
    public static void main(String... args) throws Exception {
        new VerificationTest().run();
    }

    void run() throws Exception {
        String testClasses = System.getProperty("test.classes");
        String invalidClass = "InvalidClass";
        ClassFile.of(ClassFile.StackMapsOption.DROP_STACK_MAPS).buildTo(Path.of(testClasses, invalidClass + ".class"), ClassDesc.of(invalidClass), clb ->
                clb.withMethodBody("methodWithMissingStackMap", ConstantDescs.MTD_void, 0, cob ->
                        cob.iconst_0().ifThen(tb -> tb.nop()).return_()));
        String out = javap("-verify", "-classpath", testClasses, invalidClass);
        if (!out.contains("Expecting a stackmap frame at branch target")) {
            throw new Exception("Expected output not found");
        }
    }

    String javap(String... args) throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        int rc = com.sun.tools.javap.Main.run(args, out);
        out.close();
        System.out.println(sw.toString());
        if (rc < 0)
            throw new Exception("javap exited, rc=" + rc);
        return sw.toString();
    }
}
