/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package gc;

/*
 * @test TestFillerObjectInstantiation
 * @summary Test that GC filler objects can not be instantiated by Java programs.
 * @library /test/lib
 * @run driver gc.TestFillerObjectInstantiation
 */

public class TestFillerObjectInstantiation {

    private static void testInstantiationFails(String classname) throws Exception {
        System.out.println("trying to instantiate " + classname);
        try {
            Object o = ClassLoader.getSystemClassLoader().loadClass(classname).newInstance();
            throw new Error("Have been able to instantiate " + classname);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Could not instantiate " + classname + " as expected");
            System.out.println("Message: " + e.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        testInstantiationFails("jdk.internal.vm.FillerObject");
        testInstantiationFails("jdk.internal.vm.FillerElement");
    }
}
