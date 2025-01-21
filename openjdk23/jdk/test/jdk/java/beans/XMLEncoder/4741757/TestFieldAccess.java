/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4741757
 * @summary Tests encoding with wrong field name
 * @author Sergey Malenkov
 */

public final class TestFieldAccess extends AbstractTest {
    public static void main(String[] args) {
        test(new TestFieldAccess(5));
    }

    /**
     * The name of this field is the same as the name of property.
     */
    private int value = -1;

    private int property;

    public TestFieldAccess(int value) {
        this.property = value;
    }

    public int getValue() {
        return this.property;
    }
}
