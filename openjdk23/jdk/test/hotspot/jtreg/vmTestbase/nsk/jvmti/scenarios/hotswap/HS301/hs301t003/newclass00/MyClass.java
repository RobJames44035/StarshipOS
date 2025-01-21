/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t003;
/*
This class object is immutable class so
all method calls should be call bty value.
*/
public class MyClass {
    private int count =0;

    public int getCount() {
        return count;
    }

    public MyClass(int count) {
        System.out.println(" In side the MyClass(int ) ");
        this.count = count;
    }

    public MyClass DummyMethod() {
        return new MyClass(count+1);
    }

}
