/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8254557
 * @summary Method Attr.preFlow shouldn't visit class definitions that have not yet been entered and attributed.
 * @compile T8254557.java
 */

import java.util.Iterator;
import java.util.function.Function;

public class T8254557 {
    // test anonymous class in if statement
    public <T> void testIf(boolean b) {
        test(rs -> {
            if (b) {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public T next() {
                        return null;
                    }
                };
            } else {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public T next() {
                        return null;
                    }
                };
            }
        });
    }

    // test anonymous class in while statement
    public <T> void testWhile(boolean b) {
        test(rs -> {
            while (b) {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public T next() {
                        return null;
                    }
                };
            }
            return null;
        });
    }

    // test anonymous class in do while statement
    public <T> void testDoWhileLoop(boolean b) {
        test(rs -> {
            do {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public T next() {
                        return null;
                    }
                };
            } while (b);
        });
    }

    // test anonymous class in for statement
    public <T> void testForLoop(boolean b) {
        test(rs -> {
            for ( ; ; ) {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public T next() {
                        return null;
                    }
                };
            }
        });
    }

    private void test(Function function) { }
}