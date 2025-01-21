/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t003;
/**
 *
 * This object is mutable so
 *
 **/
public class MyClass {
    private int count =0;

    public int getCount() {
        return count;
    }

    public MyClass(int count) {
        System.out.println(" Info :: In side the constructor MyClass (int).");
        this.count = count;
    }

    public MyClass DummyMethod() {
        // This change in state (count) makes
        // this object mutable.
        return new MyClass(count++);
    }

}
