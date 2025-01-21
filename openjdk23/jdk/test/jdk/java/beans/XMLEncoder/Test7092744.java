/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7092744
 * @summary Tests for ambiguous methods
 * @run main/othervm Test7092744
 * @author Sergey Malenkov
 */

public class Test7092744 extends AbstractTest {

    public static void main(String[] args) {
        new Test7092744().test();
    }

    protected Object getObject() {
        return new Bean();
    }

    protected Object getAnotherObject() {
        Bean bean = new Bean();
        bean.setValue(99);
        return bean;
    }

    public static interface I<T extends Number> {

        T getValue();

        void setValue(T value);
    }

    public static class Bean implements I<Integer> {

        private Integer value;

        public Integer getValue() {
            return this.value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
