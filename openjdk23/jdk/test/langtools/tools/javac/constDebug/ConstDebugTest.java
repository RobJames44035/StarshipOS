/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4645152 4785453
 * @summary javac compiler incorrectly inserts <clinit> when -g is specified
 * @run compile -g ConstDebugTest.java
 * @run main ConstDebugTest
 */
import java.nio.file.Paths;
import java.lang.classfile.*;

public class ConstDebugTest {

    public static final long l = 12;

    public static void main(String args[]) throws Exception {
        ClassModel classModel = ClassFile.of().parse(Paths.get(System.getProperty("test.classes"),
                ConstDebugTest.class.getSimpleName() + ".class"));
        for (MethodModel method: classModel.methods()) {
            if (method.methodName().equalsString("<clinit>")) {
                throw new AssertionError(
                    "javac should not create a <clinit> method for ConstDebugTest class");
            }
        }
    }

}
