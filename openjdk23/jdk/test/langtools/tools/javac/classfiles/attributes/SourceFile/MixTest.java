/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary sourcefile attribute test for complex structure of nested classes and other types.
 * @bug 8040129
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase SourceFileTestBase
 * @run main MixTest
 */

public class MixTest extends SourceFileTestBase {
    public static void main(String[] args) throws Exception {
        new InnerClass();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Runnable run = () -> {
                    class Local {
                    }
                };
            }

            class innerInAnonymous {
            }
        };

        new MixTest().run();
    }

    public void run() throws Exception {
        String fileName = getClass().getName() + ".java";
        test("MixTest", fileName);
        test("MixTest$1", fileName);
        test("MixTest$InnerClass", fileName);
        test("MixTest$1$innerInAnonymous", fileName);
        test("MixTest$1$1Local", fileName);
        test("MixTest$InnerClass$innerEnum", fileName);
        test("MixTest$InnerClass$innerInterface", fileName);
        test("MixTest$InnerClass$innerEnum$innerClassInnerEnum", fileName);
        test("MixTest$InnerClass$innerEnum$innerClassInnerEnum$1InnerLocal", fileName);
    }

    static class InnerClass {
        private InnerClass() {
        }

        enum innerEnum {
            E;

            class innerClassInnerEnum {
                void method() {
                    class InnerLocal {
                    }
                }
            }
        }

        @interface innerInterface {
        }
    }
}
