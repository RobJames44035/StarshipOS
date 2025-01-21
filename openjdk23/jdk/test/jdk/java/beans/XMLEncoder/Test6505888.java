/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests bean with the property that is guarded by UnmodifiableList
 * @run main/othervm Test6505888
 * @author Sergey Malenkov
 */

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Test6505888 extends AbstractTest {
    public static void main(String[] args) {
        new Test6505888().test();
    }

    protected ListBean getObject() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(26));
        list.add(Integer.valueOf(10));
        list.add(Integer.valueOf(74));
        return new ListBean(list);
    }

    protected ListBean getAnotherObject() {
        return null; // TODO: could not update property
        // return new ListBean(new ArrayList<Integer>());
    }

    public static final class ListBean {
        private List<Integer> list;

        @ConstructorProperties("list")
        public ListBean(List<Integer> list) {
            this.list = new ArrayList<Integer>(list);
        }

        public List<Integer> getList() {
            return Collections.unmodifiableList(this.list);
        }
    }
}
