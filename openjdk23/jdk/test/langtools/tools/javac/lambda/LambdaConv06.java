/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  ensure that definite assignment analysis doesn't mess up with lambda attribution
 * @author Jan Lahoda
 * @author  Maurizio Cimadamore
 * @compile LambdaConv06.java
 */

class LambdaConv06 {

    private int t() {
        return a((final Object indexed) -> {
            return b(new R() {
                public String build(final Object index) {
                    return "";
                }
            });
        });
    }

    private int a(R r) {return 0;}
    private String b(R r) {return null;}

    public static interface R {
        public String build(Object o);
    }
}
