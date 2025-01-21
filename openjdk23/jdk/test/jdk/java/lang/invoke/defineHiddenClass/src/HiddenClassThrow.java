/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 *  The classfile for this class will be loaded directly and used to define
 *  a hidden class.
 */
public class HiddenClassThrow implements HiddenTest {

    public void test() {
        throw new RuntimeException(this.getClass().getName());
    }
}
