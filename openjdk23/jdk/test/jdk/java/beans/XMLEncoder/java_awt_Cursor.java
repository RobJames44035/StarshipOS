/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests Cursor encoding
 * @run main/othervm java_awt_Cursor
 * @author Sergey Malenkov
 */

import java.awt.Cursor;

public final class java_awt_Cursor extends AbstractTest<Cursor> {
    public static void main(String[] args) {
        new java_awt_Cursor().test();
    }

    protected Cursor getObject() {
        return new Cursor(Cursor.MOVE_CURSOR);
    }

    protected Cursor getAnotherObject() {
        return null; // TODO: could not update property
        // return Cursor.getDefaultCursor();
    }
}
