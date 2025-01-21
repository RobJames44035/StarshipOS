/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8009170
 * @summary Regression: javac generates redundant bytecode in assignop involving
 * arrays
 * @run main RedundantByteCodeInArrayTest
 */

import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.constantpool.ConstantPool;
import java.io.File;
import java.io.IOException;

public class RedundantByteCodeInArrayTest {
    public static void main(String[] args)
            throws IOException {
        new RedundantByteCodeInArrayTest()
                .checkClassFile(new File(System.getProperty("test.classes", "."),
                    RedundantByteCodeInArrayTest.class.getName() + ".class"));
    }

    void arrMethod(int[] array, int p, int inc) {
        array[p] += inc;
    }

    void checkClassFile(File file)
            throws IOException {
        ClassModel classFile = ClassFile.of().parse(file.toPath());
        ConstantPool constantPool = classFile.constantPool();

        //lets get all the methods in the class file.
        for (MethodModel method : classFile.methods()) {
            if (method.methodName().equalsString("arrMethod")) {
                CodeAttribute code = method.findAttribute(Attributes.code()).orElse(null);
                assert code != null;
                if (code.maxLocals() > 4)
                    throw new AssertionError("Too many locals for method arrMethod");
            }
        }
    }
}