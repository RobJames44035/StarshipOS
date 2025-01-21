/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7192955 8000183
 * @summary Tests that all properties are bound
 * @author Sergey Malenkov
 */

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.util.List;

public class Test7192955 {

    public static void main(String[] args) throws IntrospectionException {
        if (!BeanUtils.findPropertyDescriptor(MyBean.class, "test").isBound()) {
            throw new Error("a simple property is not bound");
        }
        if (!BeanUtils.findPropertyDescriptor(MyBean.class, "list").isBound()) {
            throw new Error("a generic property is not bound");
        }
        if (!BeanUtils.findPropertyDescriptor(MyBean.class, "readOnly").isBound()) {
            throw new Error("a read-only property is not bound");
        }
        PropertyDescriptor[] pds = Introspector.getBeanInfo(MyBean.class, BaseBean.class).getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("test") && pd.isBound()) {
                throw new Error("a simple property is bound without superclass");
            }
        }
    }

    public static class BaseBean {

        private List<String> list;

        public List<String> getList() {
            return this.list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }

        public List<String> getReadOnly() {
            return this.list;
        }
    }

    public static class MyBean extends BaseBean {

        private String test;

        public String getTest() {
            return this.test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }
}
