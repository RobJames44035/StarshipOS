/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package selectionresolution;

import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

class TestBuilder extends Builder {
    private final ClassConstruct testClass;
    private final Method mainMethod;

    public TestBuilder(int classId, SelectionResolutionTestCase testcase) {
        super(testcase);

        // Make a public class Test that contains all our test methods
        testClass = new Clazz("Test", null, ACC_PUBLIC, -1);

        // Add a main method
        mainMethod = testClass.addMethod("main", "([Ljava/lang/String;)V", ACC_PUBLIC + ACC_STATIC);

    }

    public ClassConstruct getMainTestClass() {
        mainMethod.done();
        return testClass;
    }

    public void addTest(ClassConstruct clazz, ClassBuilder.ExecutionMode execMode) {
        Method m = clazz.addMethod("test", "()Ljava/lang/Integer;", ACC_PUBLIC + ACC_STATIC, execMode);
        m.defaultInvoke(getInvokeInstruction(testcase.invoke),
                    getName(testcase.methodref),
                    getName(testcase.objectref),
                    testcase.hier.isInterface(testcase.methodref));

        mainMethod.makeStaticCall(clazz.getName(), "test", "()Ljava/lang/Integer;", false).done();
    }

    private static int getInvokeInstruction(SelectionResolutionTestCase.InvokeInstruction instr) {
        switch (instr) {
            case INVOKESTATIC:
                return Opcodes.INVOKESTATIC;
            case INVOKESPECIAL:
                return Opcodes.INVOKESPECIAL;
            case INVOKEINTERFACE:
                return Opcodes.INVOKEINTERFACE;
            case INVOKEVIRTUAL:
                return Opcodes.INVOKEVIRTUAL;
            default:
                throw new AssertionError(instr.name());
        }
    }
}
