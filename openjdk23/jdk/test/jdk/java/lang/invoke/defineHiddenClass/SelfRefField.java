/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 *  The classfile for this class will be used to define a hidden class
 *  The load of this class will fail because hidden classes cannot
 *  use their name in field signatures.
 */
public class SelfRefField implements Test {

    SelfRefField other = null;

    private void realTest() {
        other = this;  // field signature test
    }

    public void test() {
        realTest();
    }
}
