/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 *  The classfile for this class will be loaded directly and used to define
 *  a hidden class.
 */
public class HiddenClass implements HiddenTest {

    HiddenClass other = null;

    private String realTest() {
        Object o = other;
        HiddenClass local = this;
        local = other;
        local = (HiddenClass) o;
        local = new HiddenClass();

        set_other_maybe(new Object());
        set_other_maybe(this);
        return "HiddenClass";
    }

    private void set_other_maybe(Object o) {
        if (o instanceof HiddenClass) {
        }
    }

    public void test() {
        String result = realTest();
        // Make sure that the Utf8 constant pool entry for "HiddenClass" is okay.
        if (!result.substring(0, 7).equals("HiddenC") ||
            !result.substring(7).equals("lass")) {
            throw new RuntimeException("'HiddenClass string is bad: " + result);
        }

    }
}
