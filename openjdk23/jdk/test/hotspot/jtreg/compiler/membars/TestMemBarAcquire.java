/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test TestMemBarAcquire
 * @bug 8048879
 * @summary Tests optimization of MemBarAcquireNodes
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation
 *                   compiler.membars.TestMemBarAcquire
 */

package compiler.membars;

public class TestMemBarAcquire {
    private volatile static Object defaultObj = new Object();
    private Object obj;

    public TestMemBarAcquire(Object param) {
        // Volatile load. MemBarAcquireNode is added after the
        // load to prevent following loads from floating up past.
        // StoreNode is added to store result of load in 'obj'.
        this.obj = defaultObj;
        // Overrides 'obj' and therefore makes previous StoreNode
        // and the corresponding LoadNode useless. However, the
        // LoadNode is still connected to the MemBarAcquireNode
        // that should now release the reference.
        this.obj = param;
    }

    public static void main(String[] args) throws Exception {
        // Make sure TestMemBarAcquire::<init> is compiled
        for (int i = 0; i < 100000; ++i) {
            TestMemBarAcquire p = new TestMemBarAcquire(new Object());
        }
    }
}

