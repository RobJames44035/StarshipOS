/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.swing.JPanel;

/**
 * @test
 * @bug 8308152
 * @summary PropertyDescriptor should work with overridden generic getter method
 */
public class OverriddenGetter {

    static class Parent<T> {
        private T value;
        public T getValue() {return value;}
        public final void setValue(T value) {this.value = value;}
    }

    static class ChildO extends Parent<Object> {
        public ChildO() {}
        @Override
        public Object getValue() {return super.getValue();}
    }

    static class ChildA extends Parent<ArithmeticException> {
        public ChildA() {}
        @Override
        public ArithmeticException getValue() {return super.getValue();}
    }

    static class ChildS extends Parent<String> {
        public ChildS() {}
        @Override
        public String getValue() {return super.getValue();}
    }

    public static void main(String[] args) throws Exception {
        test("UI", JPanel.class, "getUI", "setUI");
        test("value", ChildO.class, "getValue", "setValue");
        test("value", ChildA.class, "getValue", "setValue");
        test("value", ChildS.class, "getValue", "setValue");
    }

    private static void test(String name, Class<?> beanClass,
                             String read, String write) throws Exception
    {
        var gold = new PropertyDescriptor(name, beanClass, read, write);
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(gold.getName())) {
                if (pd.getReadMethod() != gold.getReadMethod()) {
                    System.err.println("Expected: " + gold.getReadMethod());
                    System.err.println("Actual: " + pd.getReadMethod());
                    throw new RuntimeException("Wrong read method");
                }
                if (pd.getWriteMethod() != gold.getWriteMethod()) {
                    System.err.println("Expected: " + gold.getWriteMethod());
                    System.err.println("Actual: " + pd.getWriteMethod());
                    throw new RuntimeException("Wrong write method");
                }
                if (pd.getPropertyType() != gold.getPropertyType()) {
                    System.err.println("Expected: " + gold.getPropertyType());
                    System.err.println("Actual: " + pd.getPropertyType());
                    throw new RuntimeException("Wrong property type");
                }
                return;
            }
        }
        throw new RuntimeException("The PropertyDescriptor is not found");
    }
}
