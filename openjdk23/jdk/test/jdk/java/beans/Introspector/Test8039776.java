/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.util.Set;
import java.util.SortedSet;

import static java.beans.Introspector.getBeanInfo;

/*
 * @test
 * @bug 8039776
 * @summary Tests that Introspector does not throw NPE
 * @author Sergey Malenkov
 */

public class Test8039776 {
    public static void main(String[] args) throws Exception {
        getBeanInfo(Base.class, Object.class);
        getBeanInfo(Child.class, Base.class);
        getBeanInfo(Child.class, Object.class);
    }

    public static class Base {
        private SortedSet<Object> value;

        public Set<Object> getValue() {
            return this.value;
        }

        public void setValue(SortedSet<Object> value) {
            this.value = value;
        }
    }

    public static class Child extends Base {
        public Set<Object> getValue() {
            return super.getValue();
        }

        public void setValue(SortedSet<Object> items) {
            super.setValue(items);
        }
    }
}
