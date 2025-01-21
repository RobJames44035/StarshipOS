/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package vm.mlvm.mixed.func.regression.b7127687;

import nsk.share.Consts;
import vm.mlvm.share.MlvmTest;
import vm.mlvm.share.Env;
import vm.mlvm.share.CustomClassLoaders;

import java.lang.invoke.MethodType;

import java.util.List;
import java.util.ArrayList;

import org.objectweb.asm.ClassWriter;
import static org.objectweb.asm.Opcodes.*;

public class Test extends MlvmTest {

    final static int CLASSES_COUNT = 1000;

    public static void main(String[] args) { MlvmTest.launch(args); }

    @Override
    public boolean run() throws Throwable {
        List<Class> classes = new ArrayList<Class>();

        //generating list of unique classes
        for (int i = 0; i < CLASSES_COUNT; i++) {
            classes.add(generateClass("Class" + i));
        }

        for (Class a : classes) {
            for (Class b : classes) {
                Env.traceNormal("Perform call MethodType.methodType(" + a + ", " + b + ")");
                MethodType.methodType(a, b);
            }
        }

        return true;
    }


    private static Class generateClass(String name) throws ClassNotFoundException{
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_1, ACC_PUBLIC, name, null, "java/lang/Object", null);
        return CustomClassLoaders.makeClassBytesLoader(cw.toByteArray(), name).loadClass(name);
    }

}
