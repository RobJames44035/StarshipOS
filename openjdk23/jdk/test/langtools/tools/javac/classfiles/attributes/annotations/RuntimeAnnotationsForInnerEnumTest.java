/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8044411
 * @summary Tests the RuntimeVisibleAnnotations/RuntimeInvisibleAnnotations attribute.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build WorkAnnotations TestCase ClassType TestAnnotationInfo
 * @build RuntimeAnnotationsForInnerEnumTest AnnotationsTestBase RuntimeAnnotationsTestBase
 * @run main RuntimeAnnotationsForInnerEnumTest
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The test checks that RuntimeVisibleAnnotationsAttribute and RuntimeInvisibleAnnotationsAttribute
 * are generated properly for inner classes enums, for constructors, for methods,
 * for fields (enum, class, annotation, interface). The test checks both
 * single and repeatable annotations. In addition, all possible combinations
 * of retention policies are tested.
 *
 * The test generates source code, compiles it and checks the byte code.
 *
 * See README.txt for more information.
 */
public class RuntimeAnnotationsForInnerEnumTest extends RuntimeAnnotationsTestBase {
    @Override
    public List<TestCase> generateTestCases() {
        List<TestCase> testCases = new ArrayList<>();
        for (List<TestAnnotationInfos> groupedAnnotations : groupAnnotations(getAllCombinationsOfAnnotations())) {
            for (ClassType outerClassType : ClassType.values()) {
                TestCase test = new TestCase();
                TestCase.TestClassInfo outerClassInfo = test.addClassInfo(outerClassType, "Test");
                for (int i = 0; i < groupedAnnotations.size(); i++) {
                    TestAnnotationInfos annotations = groupedAnnotations.get(i);
                    TestCase.TestClassInfo clazz = outerClassInfo.addInnerClassInfo(ClassType.ENUM, "Enum" + i);
                    annotations.annotate(clazz);

                    TestCase.TestMethodInfo innerClazzMethod = clazz.addMethodInfo("innerClassMethod" + i + "()");
                    annotations.annotate(innerClazzMethod);

                    TestCase.TestClassInfo localClassInClassMethod = innerClazzMethod.addLocalClassInfo("Local1" + i);
                    annotations.annotate(localClassInClassMethod);

                    TestCase.TestMethodInfo innerStaticClazzMethod = clazz.addMethodInfo("innerStaticClassMethod" + i + "()", "static");
                    annotations.annotate(innerStaticClazzMethod);

                    TestCase.TestClassInfo localClassInStaticMethod = innerStaticClazzMethod.addLocalClassInfo("Local2" + i);
                    annotations.annotate(localClassInStaticMethod);

                    TestCase.TestFieldInfo valueA = clazz.addFieldInfo("A" + i);
                    annotations.annotate(valueA);

                    TestCase.TestFieldInfo valueB = clazz.addFieldInfo("B" + i);
                    annotations.annotate(valueB);
                }
                testCases.add(test);
            }
        }
        return testCases;
    }

    public static void main(String[] args) throws TestFailedException {
        new RuntimeAnnotationsForInnerEnumTest().test();
    }
}
