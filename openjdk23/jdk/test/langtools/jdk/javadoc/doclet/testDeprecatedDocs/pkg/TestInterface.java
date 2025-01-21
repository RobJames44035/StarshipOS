/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @deprecated interface_test1 passes.
 */
@Deprecated(forRemoval=true)
public class TestInterface {

    /**
     * @deprecated interface_test2 passes.
     */
    public int field;

    /**
     * @deprecated interface_test3 passes.
     */
    public TestInterface() {}

    /**
     * @deprecated interface_test4 passes.
     */
    public void method() {}

    /**
     * @deprecated methodA
     */
    public void methodA(Class<?> a) {}

    /**
     * @deprecated methodB
     */
    public void methodB(List<String> list){}

    /**
     * @deprecated methodC
     */
    public void methodC(List<List<String>> list){}
    /**
     * @deprecated methodD
     */
    public void methodD(List<? extends Iterator<String>> list){}
    /**
     * @deprecated methodE
     */
    public void methodE(Map<Integer, String> map){}
    /**
     * @deprecated methodF
     */
    public void methodF(List<? super Integer> list){}
}
