/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.share;

public class StringGoldChecker extends AbstractGoldChecker {

    private final String goldenString;

    public StringGoldChecker(String goldenString) {
        this.goldenString = goldenString;
    }

    @Override
    protected String getGoldenString() {
        return goldenString;
    }
}
