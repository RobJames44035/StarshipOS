/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test id=default
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:TieredStopAtLevel=0 -Xmx16m TestArrayCopyCheckCast
 */

/*
 * @test id=generational
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:TieredStopAtLevel=0 -Xmx16m TestArrayCopyCheckCast -XX:ShenandoahGCMode=generational
 */
public class TestArrayCopyCheckCast {

    static class Foo {}
    static class Bar {}

    public static void main(String[] args) throws Exception {
        try {
            Object[] array1 = new Object[1];
            array1[0] = new Bar();
            Foo[] array2 = new Foo[1];
            System.arraycopy(array1, 0, array2, 0, 1);
            throw new RuntimeException();
        } catch (ArrayStoreException ex) {
            // expected
        }
    }

}
