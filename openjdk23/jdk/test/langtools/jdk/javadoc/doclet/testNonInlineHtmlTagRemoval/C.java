/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

public class C {
    /**
     * case1 <ul> <li> end of sentence. <li> more </ul>
     */
    public void case1() {}

    /**
     * case2 <ul compact> <li> end of sentence. <li> more </ul>
     */
    public void case2() {}

    /**
     * case3 <ul type="square"> <li> end of sentence. <li> more </ul>
     */
    public void case3() {}

    /**
     * case4 <ul type="a<b"> <li> end of sentence. <li> more </ul>
     */
    public void case4() {}

    /**
     * case5 <ul type="a>b"> <li> end of sentence. <li> more </ul>
     */
    public void case5() {}

    /**
     * case6 <ul type='a>b'> <li> end of sentence. <li> more </ul>
     */
    public void case6() {}

    /**
     * case7 <ul type='"a>b"'> <li> end of sentence. <li> more </ul>
     */
    public void case7() {}

    /**
     * case8 <ul type="'a>b'"> <li> end of sentence. <li> more </ul>
     */
    public void case8() {}

    /**
     * case9 <ul type="'a'>b"> <li> end of sentence. <li> more </ul>
     */
    public void case9() {}

    /**
     * caseA <ul type='"a">b'> <li> end of sentence. <li> more </ul>
     */
    public void caseA() {}

    /**
     * caseB <blockquote>A block quote example:</blockquote>
     */
    public void caseB() {}
}
