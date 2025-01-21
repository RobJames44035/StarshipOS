/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * The classfile does not fail verification but would fail when
 * getMethod
 */
public class HiddenCantReflect implements HiddenTest {

    HiddenCantReflect other = null;

    private String realTest() {
        Object o = other;
        HiddenCantReflect local = this;
        local = other;
        local = (HiddenCantReflect) o;
        local = new HiddenCantReflect();

        set_other(null);

        local = getThis();

        set_other_maybe(new Object());
        set_other_maybe(this);
        return "HiddenCantReflect";
    }

    private HiddenCantReflect getThis() {
        return null;
    }

    private void set_other(HiddenCantReflect t) {
        other = t;
    }

    private void set_other_maybe(Object o) {
        if (o instanceof HiddenCantReflect) {
        }
    }

    public void test() {
        String result = realTest();
        // Make sure that the Utf8 constant pool entry for "HiddenCantReflect" is okay.
        if (!result.substring(0, 7).equals("HiddenC") ||
            !result.substring(7).equals("antReflect")) {
            throw new RuntimeException("'HiddenCantReflect string is bad: " + result);
        }

    }
}
