/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026394 8251414
 * @summary test interface resolution when clone is declared abstract within
 *          an interface and when they are not
 * @compile InterfaceObj.jasm
 * @run main InterfaceObjectTest
 */
interface IClone extends Cloneable {
    Object clone();
}

interface ICloneExtend extends IClone { }

public class InterfaceObjectTest implements ICloneExtend {

    public Object clone() {
        System.out.println("In InterfaceObjectTest's clone() method\n");
        return null;
    }

    public static void tryIt(ICloneExtend o1) {
        try {
            Object o2 = o1.clone();
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }


    public static void main(String[] args) throws Exception {
        // Test with abstract public clone() method.
        InterfaceObjectTest o1 = new InterfaceObjectTest();
        tryIt(o1);


        // Test with reflection without abstract public clone() method.
        Class cls = Class.forName("InterfaceObj");

        try {
            java.lang.reflect.Method m = cls.getMethod("testClone");
            m.invoke(cls);
            throw new RuntimeException("Failed to throw NoSuchMethodError for clone()");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (!e.getCause().toString().contains("NoSuchMethodError")) {
                throw new RuntimeException("wrong ITE: " + e.getCause().toString());
            }
        }

    }
}
