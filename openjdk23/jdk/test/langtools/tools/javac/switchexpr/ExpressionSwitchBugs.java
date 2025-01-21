/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986 8214114 8214529
 * @summary Verify various corner cases with nested switch expressions.
 * @compile ExpressionSwitchBugs.java
 * @run main ExpressionSwitchBugs
 */

public class ExpressionSwitchBugs {
    public static void main(String... args) {
        new ExpressionSwitchBugs().testNested();
        new ExpressionSwitchBugs().testAnonymousClasses();
        new ExpressionSwitchBugs().testFields();
        check(3, new C(-1, 3).test(false));
        check(3, new C(3, -1).test(true));
    }

    private void testNested() {
        int i = 0;
        check(42, id(switch (42) {
            default: i++; yield 42;
        }));
        i = 0;
        check(43, id(switch (42) {
            case 42: while (i == 0) {
                i++;
            }
            yield 42 + i;
            default: i++; yield 42;
        }));
        i = 0;
        check(42, id(switch (42) {
            case 42: if (i == 0) {
                yield 42;
            }
            default: i++; yield 43;
        }));
        i = 0;
        check(42, id(switch (42) {
            case 42: if (i == 0) {
                yield 41 + switch (0) {
                    case 0 -> 1;
                    default -> -1;
                };
            }
            default: i++; yield 43;
        }));
    }

    private void testAnonymousClasses() {
        for (int i : new int[] {1, 2}) {
            check(3, id((switch (i) {
                case 1: yield new I() {
                    public int g() { return 3; }
                };
                default: yield (I) () -> { return 3; };
            }).g()));
            check(3, id((switch (i) {
                case 1 -> new I() {
                    public int g() { return 3; }
                };
                default -> (I) () -> { return 3; };
            }).g()));
        }
    }

    private void testFields() {
        check(3, field);
        check(3, ExpressionSwitchBugs.staticField);
    }

    private final int value = 2;
    private final int field = id(switch(value) {
        case 0 -> -1;
        case 2 -> {
            int temp = 0;
            temp += 3;
            yield temp;
        }
        default -> throw new IllegalStateException();
    });

    private static final int staticValue = 2;
    private static final int staticField = new ExpressionSwitchBugs().id(switch(staticValue) {
        case 0 -> -1;
        case 2 -> {
            int temp = 0;
            temp += 3;
            yield temp;
        }
        default -> throw new IllegalStateException();
    });

    private int id(int i) {
        return i;
    }

    private int id(Object o) {
        return -1;
    }

    private static void check(int actual, int expected) {
        if (actual != expected) {
            throw new AssertionError("Unexpected result: " + actual);
        }
    }

    public interface I {
        public int g();
    }

    static class Super {
        public final int i;

        public Super(int i) {
            this.i = i;
        }

    }
    static class C extends Super {
        public final int i;

        public C(int superI, int i) {
            super(superI);
            this.i = i;
        }

        public int test(boolean fromSuper) {
            return switch (fromSuper ? 0 : 1) {
                case 0 -> {
                    yield super.i;
                }
                default -> {
                    yield this.i;
                }
            };
        }
    }
}
