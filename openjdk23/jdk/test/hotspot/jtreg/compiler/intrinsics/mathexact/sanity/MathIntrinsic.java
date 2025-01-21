/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package compiler.intrinsics.mathexact.sanity;

import compiler.whitebox.CompilerWhiteBoxTest;

import java.lang.reflect.Executable;
import java.util.concurrent.Callable;

public class MathIntrinsic {

    enum IntIntrinsic implements CompilerWhiteBoxTest.TestCase {
        Add {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("addExact", int.class, int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.addExact(int1, int2);
            }
        },
       Subtract {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("subtractExact", int.class, int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.subtractExact(int1, int2);
            }
        },
        Multiply {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("multiplyExact", int.class, int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.multiplyExact(int1, int2);
            }
        },
        Increment {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("incrementExact", int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.incrementExact(int1);
            }
        },
        Decrement {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("decrementExact", int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.decrementExact(int1);
            }
        },
        Negate {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("negateExact", int.class);
            }

            @Override
            Object execMathMethod() {
                return intR = Math.negateExact(int1);
            }
        };

        protected int int1;
        protected int int2;
        protected int intR;

        abstract Executable testMethod() throws NoSuchMethodException, ClassNotFoundException;
        abstract Object execMathMethod();

        public Executable getTestMethod() {
            try {
                return testMethod();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Test bug, no such method: " + e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Test bug, no such class: " + e);
            }
        }

        @Override
        public Executable getExecutable() {
            try {
                return getClass().getDeclaredMethod("execMathMethod");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Test bug, no such method: " + e);
            }
        }

        @Override
        public Callable<Integer> getCallable() {
            return null;
        }

        @Override
        public boolean isOsr() {
            return false;
        }

    }

    enum LongIntrinsic implements CompilerWhiteBoxTest.TestCase {
        Add {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("addExact", long.class, long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.addExact(long1, long2);
            }
        },
        Subtract {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("subtractExact", long.class, long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.subtractExact(long1, long2);
            }
        },
        Multiply {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("multiplyExact", long.class, long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.multiplyExact(long1, long2);
            }
        },
        Increment {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("incrementExact", long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.incrementExact(long1);
            }
        },
        Decrement {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("decrementExact", long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.decrementExact(long1);
            }
        },
        Negate {
            @Override
            Executable testMethod() throws NoSuchMethodException, ClassNotFoundException {
                return Class.forName("java.lang.Math").getDeclaredMethod("negateExact", long.class);
            }

            @Override
            Object execMathMethod() {
                return longR = Math.negateExact(long1);
            }
        };
        protected long long1;
        protected long long2;
        protected long longR;

        abstract Executable testMethod() throws NoSuchMethodException, ClassNotFoundException;
        abstract Object execMathMethod();

        public Executable getTestMethod() {
            try {
                return testMethod();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Test bug, no such method: " + e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Test bug, no such class: " + e);
            }
        }

        @Override
        public Executable getExecutable() {
            try {
                return getClass().getDeclaredMethod("execMathMethod");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Test bug, no such method: " + e);
            }
        }

        @Override
        public Callable<Integer> getCallable() {
            return null;
        }

        @Override
        public boolean isOsr() {
            return false;
        }
    }
}
