/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 *  The classfile for this class will be used to define a hidden class
 *  The load of this class will fail because hidden classes cannot
 *  use their names in method signatures.
 */
public class SelfRefMethod implements Test {

    private void realTest() {
        SelfRefMethod local = this;
        set_other(local); // method signature test
    }

    private void set_other(SelfRefMethod t) {
    }

    public void test() {
        realTest();
    }
}
