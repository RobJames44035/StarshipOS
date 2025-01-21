/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027281
 * @summary As per JVMS 4.9.2, invokespecial can only refer to direct superinterfaces
 * @compile TestDirectSuperInterfaceInvoke.java
 * @run main TestDirectSuperInterfaceInvoke
 */

import java.io.File;
import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.constantpool.MemberRefEntry;
import java.lang.classfile.instruction.InvokeInstruction;

interface BaseInterface {
    public default int testedMethod(){ return 1; }
}

interface IntermediateInterface extends BaseInterface {
}

interface TestInterface extends IntermediateInterface {
    public default void test() {
        IntermediateInterface.super.testedMethod();
    }
}

abstract class BaseClass implements BaseInterface { }

class TestClass extends BaseClass implements BaseInterface {
    public int testedMethod() {return 9;}
    public void test() {
        if (super.testedMethod() != 1)
            throw new IllegalStateException();
        if (TestClass.super.testedMethod() != 1)
            throw new IllegalStateException();
        new Runnable() {
            public void run() {
                if (TestClass.super.testedMethod() != 1)
                    throw new IllegalStateException();
            }
        }.run();
    }
}

public class TestDirectSuperInterfaceInvoke {
    public static void main(String... args) throws Exception {
        new TestDirectSuperInterfaceInvoke().run();
    }

    public void run() throws Exception {
        new TestClass().test();
        verifyDefaultBody("TestClass.class");
        new TestInterface() {}.test();
        verifyDefaultBody("TestInterface.class");
    }

    void verifyDefaultBody(String classFile) {
        String workDir = System.getProperty("test.classes");
        File file = new File(workDir, classFile);
        try {
            final ClassModel cf = ClassFile.of().parse(file.toPath());
            for (MethodModel m : cf.methods()) {
                CodeAttribute codeAttr = m.findAttribute(Attributes.code()).orElseThrow();
                for (CodeElement ce : codeAttr.elementList()) {
                    if (ce instanceof InvokeInstruction instr && instr.opcode() == Opcode.INVOKESPECIAL) {
                        MemberRefEntry ref = instr.method();
                        String className = ref.owner().asInternalName();
                        if (className.equals("BaseInterface"))
                            throw new IllegalStateException("Must not directly refer to TestedInterface");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("error reading " + file +": " + e);
        }
    }

}
