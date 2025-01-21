/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import vm.mlvm.share.Env;
import vm.mlvm.share.MlvmTest;
import vm.share.options.Option;

public class SimpleUnitTest extends MlvmTest {

    @Option(name = "failOnce", default_value = "false", description = "exit after the first failure")
    private boolean failOnce = true;

    public SimpleUnitTest() {}

    public SimpleUnitTest test1(int a, float b) {
        Env.traceNormal("test1(%d, %f) called", a, b);
        return this;
    }

    private static SimpleUnitTest sut = new SimpleUnitTest();

    public static SimpleUnitTest test2(int a, float b) {
        Env.traceNormal("test2(%d, %f) called", a, b);
        return sut;
    }

    public static int test3(int a) {
        Env.traceNormal("test3(%d) called", a);
        return a;
    }

    public void test4() {
        Env.traceNormal("test4() called");
    }

    public SimpleUnitTest test5() {
        Env.traceNormal("test5() called");
        return this;
    }

    public static void main(String[] args) { MlvmTest.launch(args); }

    @Override
    public boolean run() throws Throwable {
        try {
            Argument retArg;
            retArg = new Argument(SimpleUnitTest.class, sut);
            retArg.setPreserved(true);

            Argument intArg = new Argument(int.class, Integer.valueOf(1));

            for ( ;; ) {
                try {
                    switch ( Env.getRNG().nextInt(5) ) {
                    case 0:
                        MHTransformationGen.createAndCallSequence(
                            retArg,
                            sut,
                            MethodHandles.lookup().findVirtual(
                                    SimpleUnitTest.class,
                                    "test1",
                                    MethodType.methodType(SimpleUnitTest.class, int.class, float.class)
                            ),
                            new Argument[] { new Argument(int.class, Integer.valueOf(1)), new Argument(float.class, Float.valueOf(1.0)) },
                            true);
                        break;

                    case 1:
                        MHTransformationGen.createAndCallSequence(
                                retArg,
                                null,
                                MethodHandles.lookup().findStatic(
                                        SimpleUnitTest.class,
                                        "test2",
                                        MethodType.methodType(SimpleUnitTest.class, int.class, float.class)
                                ),
                                new Argument[] { new Argument(int.class, Integer.valueOf(1)), new Argument(float.class, Float.valueOf(1.0)) },
                                true);
                            break;

                    case 2:
                        MHTransformationGen.createAndCallSequence(
                                intArg,
                                null,
                                MethodHandles.lookup().findStatic(
                                        SimpleUnitTest.class,
                                        "test3",
                                        MethodType.methodType(int.class, int.class)
                                ),
                                new Argument[] { intArg },
                                true);
                            break;

                    case 3:
                        MHTransformationGen.createAndCallSequence(
                                new Argument(void.class, null),
                                sut,
                                MethodHandles.lookup().findVirtual(
                                        SimpleUnitTest.class,
                                        "test4",
                                        MethodType.methodType(void.class)
                                ),
                                new Argument[0],
                                false);
                            break;

                    default:
                        MHTransformationGen.createAndCallSequence(
                                retArg,
                                sut,
                                MethodHandles.lookup().findVirtual(
                                        SimpleUnitTest.class,
                                        "test5",
                                        MethodType.methodType(SimpleUnitTest.class)
                                ),
                                new Argument[0],
                                true);
                            break;

                    }
                } catch ( Throwable e ) {
                    Env.getLog().complain("Caught exception", e);
                    if ( failOnce )
                        return false;
                }
            }
        } catch ( Throwable t ) {
            t.printStackTrace();
            return false;
        }
    }

}
