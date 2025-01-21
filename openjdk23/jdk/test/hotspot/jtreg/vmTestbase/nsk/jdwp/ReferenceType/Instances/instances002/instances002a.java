/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdwp.ReferenceType.Instances.instances002;

import nsk.share.jdwp.*;

class TestClass {

}

public class instances002a extends AbstractJDWPDebuggee {
    public static final int expectedInstanceCount = 5;

    public static TestClass instance1 = new TestClass();

    public static TestClass instance2 = new TestClass();

    public static TestClass instance3 = new TestClass();

    public static TestClass instance4 = new TestClass();

    public static TestClass instance5 = new TestClass();

    public static void main(String args[]) {
        new instances002a().doTest(args);
    }
}
