/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6976577
 * @summary Tests public methods in non-public beans
 * @author Sergey Malenkov
 */

import test.Accessor;

import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class Test6976577 {

    public static void main(String[] args) throws Exception {
        Class<?> bt = Accessor.getBeanType();
        Class<?> lt = Accessor.getListenerType();

        // test PropertyDescriptor
        PropertyDescriptor pd = new PropertyDescriptor("boolean", bt);
        test(pd.getReadMethod());
        test(pd.getWriteMethod());

        // test IndexedPropertyDescriptor
        IndexedPropertyDescriptor ipd = new IndexedPropertyDescriptor("indexed", bt);
        test(ipd.getReadMethod());
        test(ipd.getWriteMethod());
        test(ipd.getIndexedReadMethod());
        test(ipd.getIndexedWriteMethod());

        // test EventSetDescriptor
        EventSetDescriptor esd = new EventSetDescriptor(bt, "test", lt, "process");
        test(esd.getAddListenerMethod());
        test(esd.getRemoveListenerMethod());
        test(esd.getGetListenerMethod());
        test(esd.getListenerMethods());
    }

    private static void test(Method... methods) {
        for (Method method : methods) {
            if (method == null) {
                throw new Error("public method is not found");
            }
        }
    }
}
