/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.beans.PropertyDescriptor;

/*
 * @test
 * @bug 8027905
 * @summary Tests that GC does not affect a property type
 * @run main/othervm -Xmx16m Test8027905
 */

public class Test8027905 {
    public static void main(String[] args) {
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(Sub.class, "foo");
        Class<?> type = pd.getPropertyType();

        int[] array = new int[10];
        while (array != null) {
            try {
                array = new int[array.length + array.length];
            }
            catch (OutOfMemoryError error) {
                array = null;
            }
        }
        if (type != pd.getPropertyType()) {
            throw new Error("property type is changed");
        }
    }

    public static class Super<T> {
        public T getFoo() {
            return null;
        }

        public void setFoo(T t) {
        }
    }

    public static class Sub extends Super<String> {
        @Override
        public String getFoo() {
            return null;
        }

        @Override
        public void setFoo(String t) {
        }
    }
}
