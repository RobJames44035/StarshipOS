/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5021445
 * @summary Calling inherited generics method via interface causes AbstractMethodError
 * @author gafter
 *
 * @compile  MissingBridge.java
 * @run main MissingBridge
 */

class GenObject<T>
{
    public void foo(T obj)
    {
        System.out.println("obj = "+obj);
    }
}

interface TestInterface
{
    void foo(String blah);
}

public class MissingBridge extends GenObject<String> implements TestInterface
{
    public static void main(String[] args)
    {
        MissingBridge test = new MissingBridge();
        TestInterface test2 = test;

        // works
        test.foo("blah");

        // fails with AbstractMethodError
        test2.foo("blah");
    }
}
