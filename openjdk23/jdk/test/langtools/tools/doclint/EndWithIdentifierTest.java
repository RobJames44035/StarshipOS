/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * .
 * @deprecated*/
public class EndWithIdentifierTest {

    /**{@link*/
    private void unfinishedInlineTagName() {}

    /**
     * .
     * @see List*/
    private void endsWithIdentifier() {}

    /**&amp*/
    private void entityName() {}

    /**<a*/
    private void tag() {}

    /**</a*/
    private void tagEnd() {}

    /**<a name*/
    private void attribute() {}

    /** . */
    EndWithIdentifierTest() { }
}

