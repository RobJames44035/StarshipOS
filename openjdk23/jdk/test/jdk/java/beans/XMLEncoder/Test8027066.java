/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027066
 * @summary Tests that the same array can be encoded twice
 * @run main/othervm Test8027066
 * @author Anton Nashatyrev
 */
public class Test8027066 extends AbstractTest<String[][]> {
    public static void main(String[] args) {
        new Test8027066().test();
    }

    @Override
    protected String[][] getObject() {
        String[] strings = {"first", "second"};
        String[][] arrays = {strings, strings};
        return arrays;
    }
}
