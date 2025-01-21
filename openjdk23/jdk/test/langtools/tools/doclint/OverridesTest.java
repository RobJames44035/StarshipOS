/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8004832
 * @summary Add new doclint package
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs:all OverridesTest.java
 */

/*
 * This is a test that missing comments on methods may be inherited
 * from overridden methods. As such, there should be no errors due
 * to missing comments (or any other types of error) in this test.
 */

/** An interface. */
interface I1 {
    /**
     * A method
     * @param p a param
     * @throws Exception an exception
     * @return an int
     */
    int m(int p) throws Exception;
}

/** An extending interface. */
interface I2 extends I1 { }

/** An abstract class. */
abstract class C1 {
    /**
     * A method
     * @param p a param
     * @throws Exception an exception
     * @return an int
     */
    int m(int p) throws Exception;
    /** . */ C1() { }
}

/** An implementing class. */
class C2 implements I1 {
    int m(int  p) throws Exception { return p; }
    /** . */ C2() { }
}

/** An extending class. */
class C3 extends C1 {
    int m(int  p) throws Exception { return p; }
    /** . */ C3() { }
}

/** An extending and implementing class. */
class C4 extends C1 implements I1 {
    int m(int  p) throws Exception { return p; }
    /** . */ C4() { }
}

/** An implementing class using inheritdoc. */
class C5 implements I1 {
    /** {@inheritDoc} */
    int m(int  p) throws Exception { return p; }
    /** . */ C5() { }
}

/** An implementing class with incomplete documentation. */
class C6 implements I1 {
    /** Overriding method */
    int m(int  p) throws Exception { return p; }
    /** . */ C6() { }
}

/** A class implementing an inherited interface. */
class C7 implements I2 {
    int m(int  p) throws Exception { return p; }
    /** . */ C7() { }
}
