/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import nsk.share.test.TestUtils;

public class Argument {

    private final Class<?> type;
    private final Object value;
    private boolean isPreserved;
    private String tag;

    public Argument(Class<?> type, Object value) {
        this(type, value, false, "");
    }

    public Argument(Class<?> type, Object value, boolean isPreserved, String tag) {
        this.type = type;
        this.value = value;
        this.isPreserved = isPreserved;
        this.tag = tag;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public void setPreserved(boolean newValue) {
        this.isPreserved = newValue;
    }

    public boolean isPreserved() {
        return this.isPreserved;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String newTag) {
        this.tag = newTag;
    }

    public static Argument fromValue(Object value) {
        return new Argument(value.getClass(), value);
    }

    public static Argument fromPrimitiveValue(Object boxedValue) {
        TestUtils.assertInCollection(TestTypes.UNBOX_MAP.keySet(), boxedValue.getClass());
        return new Argument(TestTypes.UNBOX_MAP.get(boxedValue.getClass()), boxedValue);
    }

    public static Argument fromArray(Object[] a) {
        boolean isProtected = false;
        if ( a.length > 2 && a[2].getClass().equals(Boolean.class) )
            isProtected = (Boolean) a[2];

        return new Argument((Class<?>) a[0], a[1], isProtected, "");
    }

    @Override
    public String toString() {
        return getType().getName().replaceFirst("^java.lang.", "") + "="
             + (getType().equals(String.class) ? "{" + getValue() + "}" : getValue() == null ? "null" : getValue() )
             + ((! getTag().isEmpty() || isPreserved()) ? ("[" + (isPreserved() ? "!" : "") + getTag() + "]") : "");
    }

    @Override
    public Argument clone() {
        return new Argument(getType(), getValue(), isPreserved(), getTag());
    }
}
