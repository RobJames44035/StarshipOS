/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg;

@RegDoc(x=1)
public class D {

    @RegArryDoc(y={1})
    public void test1() {}

    @RegArryDoc(y={1,2})
    public void test2() {}

    @NonSynthDocContainer({@RegArryDoc(y={1})})
    public void test3() {}
}
