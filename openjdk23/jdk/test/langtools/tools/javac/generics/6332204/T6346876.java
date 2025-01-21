/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6332204
 * @summary com.sun.tools.javac.code.Types.lub() throws NPE
 * @compile T6346876.java
 */

public final class T6346876 {

    private double[][] _d;

    public void foo() {
        Object o =_d==null ? new double[0] : _d;
    }

}
