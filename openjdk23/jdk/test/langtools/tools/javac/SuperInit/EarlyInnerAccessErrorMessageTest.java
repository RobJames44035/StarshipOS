/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class EarlyInnerAccessErrorMessageTest {
    int x;
    EarlyInnerAccessErrorMessageTest() {
        class Inner {
            { System.out.println(x); }
        }
        super();
    }
}
