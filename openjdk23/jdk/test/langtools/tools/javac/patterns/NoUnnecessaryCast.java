/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8237528
 * @summary Verify there are no unnecessary checkcasts and conditions generated
 *          for the pattern matching in instanceof.
 * @compile NoUnnecessaryCast.java
 * @run main NoUnnecessaryCast
 */

import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.constantpool.ConstantPool;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NoUnnecessaryCast {
    public static void main(String[] args) throws IOException {
        new NoUnnecessaryCast()
                .checkClassFile(new File(System.getProperty("test.classes", "."),
                    NoUnnecessaryCast.class.getName() + ".class"));
    }

    void checkClassFile(File file) throws IOException {
        ClassModel classFile = ClassFile.of().parse(file.toPath());

        MethodModel method = classFile.methods().stream()
                              .filter(m -> getName(m).equals("test"))
                              .findAny()
                              .get();
        String expectedInstructions = """
                                      ALOAD_1
                                      INSTANCEOF
                                      IFEQ
                                      ALOAD_1
                                      CHECKCAST
                                      ASTORE_2
                                      ALOAD_2
                                      INVOKEVIRTUAL
                                      IFEQ
                                      ICONST_1
                                      GOTO
                                      ICONST_0
                                      IRETURN
                                      """;
        CodeAttribute code = method.findAttribute(Attributes.code()).orElseThrow();
        String actualInstructions = printCode(code);
        if (!expectedInstructions.equals(actualInstructions)) {
            throw new AssertionError("Unexpected instructions found:\n" +
                                     actualInstructions);
        }
    }

    String printCode(CodeAttribute code) {
        return code.elementList().stream()
                            .filter(e -> e instanceof Instruction)
                            .map(ins -> ((Instruction) ins).opcode().name())
                            .collect(Collectors.joining("\n", "", "\n"));
    }

    String getName(MethodModel m) {
        return m.methodName().stringValue();
    }

    boolean test(Object o) {
        return o instanceof String s && s.isEmpty();
    }
}
