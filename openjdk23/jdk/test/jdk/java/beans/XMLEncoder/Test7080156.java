/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7080156 7094245
 * @summary Tests beans with public arrays
 * @run main/othervm Test7080156
 * @author Sergey Malenkov
 */

public class Test7080156 extends AbstractTest {
    public static void main(String[] args) {
        new Test7080156().test();
    }

    protected Object getObject() {
        Bean bean = new Bean();
        bean.setArray("something");
        return bean;
    }

    @Override
    protected Object getAnotherObject() {
        Bean bean = new Bean();
        bean.setArray("some", "thing");
        return bean;
    }

    public static class Bean {
        public String[] array = {"default"};

        public void setArray(String... array) {
            this.array = array;
        }

        public String[] getArray() {
            return this.array;
        }
    }
}
