/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;

/*
 * @test
 * @bug 8027648
 * @summary Tests overridden getter and overloaded setter
 * @author Sergey Malenkov
 */

public class Test8027648 {

    public static void main(String[] args) {
        test(false);
        test(true);
    }

    private static void test(boolean indexed) {
        Class<?> parent = getPropertyType(BaseBean.class, indexed);
        Class<?> child = getPropertyType(MyBean.class, indexed);
        if (parent.equals(child) || !parent.isAssignableFrom(child)) {
            throw new Error("the child property type is not override the parent property type");
        }
    }

    private static Class<?> getPropertyType(Class<?> type, boolean indexed) {
        PropertyDescriptor pd = BeanUtils.findPropertyDescriptor(type, indexed ? "index" : "value");
        if (pd instanceof IndexedPropertyDescriptor) {
            IndexedPropertyDescriptor ipd = (IndexedPropertyDescriptor) pd;
            return ipd.getIndexedPropertyType();
        }
        return pd.getPropertyType();
    }

    public static class BaseBean {
        private Object value;

        public Object getValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getIndex(int index) {
            return getValue();
        }

        public void setIndex(int index, Object value) {
            setValue(value);
        }
    }

    public static class MyBean extends BaseBean {
        @Override
        public String getValue() {
            return (String) super.getValue();
        }

        public void setValue(String value) {
            setValue((Object) value);
        }

        @Override
        public String getIndex(int index) {
            return getValue();
        }

        public void setIndex(int index, String value) {
            setValue(value);
        }
    }
}
