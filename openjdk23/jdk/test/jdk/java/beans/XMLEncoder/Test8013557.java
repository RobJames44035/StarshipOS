/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8013557
 * @summary Tests beans with public fields
 * @run main/othervm Test8013557
 * @author Sergey Malenkov
 */

public class Test8013557 extends AbstractTest {
    public static void main(String[] args) {
        new Test8013557().test();
    }

    protected Object getObject() {
        return new Bean(new Value("something"));
    }

    @Override
    protected Object getAnotherObject() {
        return new Bean(new Value());
    }

    public static class Bean {
        public Value value;

        public Bean() {
            this.value = new Value();
        }

        public Bean(Value value) {
            this.value = value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public Value getValue() {
            return this.value;
        }
    }

    public static class Value {
        private String string;

        public Value() {
            this.string = "default";
        }

        public Value(String value) {
            this.string = value;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getString() {
            return this.string;
        }
    }
}
