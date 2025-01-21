/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4741757
 * @summary Tests stack overflow if equals is defined
 * @author Sergey Malenkov
 */

public final class TestStackOverflow extends AbstractTest {
    public static void main(String[] args) {
        test(new TestStackOverflow(5));
    }

    /**
     * The name of this field is the same as the name of property.
     */
    private int value = -1;

    private int property;

    public TestStackOverflow(int value) {
        this.property = value;
    }

    public int getValue() {
        return this.property;
    }

    public boolean equals(Object object) {
        if (object instanceof TestStackOverflow) {
            TestStackOverflow test = (TestStackOverflow) object;
            return test.property == this.property;
        }
        return false;
    }
}
