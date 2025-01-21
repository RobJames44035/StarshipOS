/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8182649
 * @summary Unable to integrate due to compilation error
 * @compile AddCheckForPartiallyInferredTypesTest.java
 */

class AddCheckForPartiallyInferredTypesTest {
    interface Signed {}
    interface WordBase {}

    <S extends Signed> S signed(int offset) { return null; }
    <W1 extends WordBase> W1 readWord(WordBase w,String s) { return null; }
    <W2 extends WordBase> W2 readWord(int i,String s) { return null; }
    <W3 extends WordBase> W3 test() {
        return readWord(signed(10), "");
    }
}
