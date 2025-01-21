/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package separate;

import java.lang.classfile.*;
import java.lang.classfile.instruction.InvokeInstruction;
import static java.lang.constant.ConstantDescs.INIT_NAME;
import static java.lang.classfile.ClassFile.*;

public class ClassToInterfaceConverter implements ClassFilePreprocessor {

    private final String whichClass;

    public ClassToInterfaceConverter(String className) {
        this.whichClass = className;
    }

    private byte[] convertToInterface(ClassModel classModel) {
        //  Convert method tag. Find Methodref which is only invoked by other methods
        //  in the interface, convert it to InterfaceMethodref.  If opcode is invokevirtual,
        //  convert it to invokeinterface
        CodeTransform ct = (b, e) -> {
            if (e instanceof InvokeInstruction i && i.owner() == classModel.thisClass()) {
                Opcode opcode = i.opcode() == Opcode.INVOKEVIRTUAL ? Opcode.INVOKEINTERFACE : i.opcode();
                b.invoke(opcode, i.owner().asSymbol(),
                        i.name().stringValue(), i.typeSymbol(), true);
            } else {
                b.with(e);
            }
        };

        return ClassFile.of().transformClass(classModel,
            ClassTransform.dropping(ce -> ce instanceof MethodModel mm && mm.methodName().equalsString(INIT_NAME))
                          .andThen(ClassTransform.transformingMethodBodies(ct))
                          .andThen(ClassTransform.endHandler(b -> b.withFlags(ACC_INTERFACE | ACC_ABSTRACT | ACC_PUBLIC)))
        );
    }

    public byte[] preprocess(String classname, byte[] bytes) {
        ClassModel classModel = ClassFile.of().parse(bytes);
        if (classModel.thisClass().asInternalName().equals(whichClass)) {
            return convertToInterface(classModel);
        } else {
            return bytes; // unmodified
        }
    }

/*
    public static void main(String argv[]) throws Exception {
        File input = new File(argv[0]);
        byte[] buffer = new byte[(int)input.length()];
        new FileInputStream(input).read(buffer);

        ClassFilePreprocessor cfp = new ClassToInterfaceConverter("Hello");
        byte[] cf = cfp.preprocess(argv[0], buffer);
        new FileOutputStream(argv[0] + ".mod").write(cf);
    }
*/
}
