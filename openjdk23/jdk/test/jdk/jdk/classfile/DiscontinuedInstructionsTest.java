/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Testing ClassFile handling JSR and RET instructions.
 * @run junit DiscontinuedInstructionsTest
 */
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.ArrayList;
import java.util.List;
import java.lang.classfile.*;
import java.lang.classfile.instruction.DiscontinuedInstruction;
import helpers.ByteArrayClassLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.constant.ConstantDescs.*;
import static java.lang.classfile.ClassFile.*;

class DiscontinuedInstructionsTest {

    @Test
    void testJsrAndRetProcessing() throws Exception {
        var testClass = "JsrAndRetSample";
        var testMethod = "testMethod";
        var cd_list = ArrayList.class.describeConstable().get();
        var cc = ClassFile.of();
        var bytes = cc.build(ClassDesc.of(testClass), clb -> clb
                .withVersion(JAVA_5_VERSION, 0)
                .withMethodBody(testMethod, MethodTypeDesc.of(CD_void, cd_list), ACC_PUBLIC | ACC_STATIC, cob -> cob
                        .block(bb -> {
                            bb.loadConstant("Hello")
                              .with(DiscontinuedInstruction.JsrInstruction.of(bb.breakLabel()));
                            bb.loadConstant("World")
                              .with(DiscontinuedInstruction.JsrInstruction.of(Opcode.JSR_W, bb.breakLabel()))
                              .return_();
                        })
                        .astore(355)
                        .aload(0)
                        .swap()
                        .invokevirtual(cd_list, "add", MethodTypeDesc.of(CD_boolean, CD_Object))
                        .pop()
                        .with(DiscontinuedInstruction.RetInstruction.of(355))));

        var c = (CodeAttribute) cc.parse(bytes).methods().get(0).code().get();
        assertEquals(356, c.maxLocals());
        assertEquals(6, c.maxStack());


        var list = new ArrayList<String>();
        new ByteArrayClassLoader(DiscontinuedInstructionsTest.class.getClassLoader(), testClass, bytes)
                .getMethod(testClass, testMethod)
                .invoke(null, list);
        assertEquals(list, List.of("Hello", "World"));

        bytes = cc.transformClass(cc.parse(bytes), ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL));

        new ByteArrayClassLoader(DiscontinuedInstructionsTest.class.getClassLoader(), testClass, bytes)
                .getMethod(testClass, testMethod)
                .invoke(null, list);
        assertEquals(list, List.of("Hello", "World", "Hello", "World"));

        var clm = cc.parse(bytes);

        //test failover stack map generation
        cc.transformClass(clm, ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)
                 .andThen(ClassTransform.endHandler(clb -> clb.withVersion(JAVA_6_VERSION, 0))));

        //test failure of stack map generation for Java 7
        assertThrows(IllegalArgumentException.class, () ->
                cc.transformClass(clm, ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)
                         .andThen(ClassTransform.endHandler(clb -> clb.withVersion(JAVA_7_VERSION, 0)))));

        //test failure of stack map generation when enforced to generate
        assertThrows(IllegalArgumentException.class, () ->
                ClassFile.of(ClassFile.StackMapsOption.GENERATE_STACK_MAPS)
                         .transformClass(clm, ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)));
    }
}
