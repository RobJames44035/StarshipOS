/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.Cursor;

/**
 * @test
 * @key headful
 * @bug 8039269
 * @author Sergey Bylokhov
 */
public final class GetSystemCustomCursor {

    public static void main(final String[] args) throws AWTException {
        // This list is copied from cursors.properties
        String[] names = {"CopyDrop.32x32", "MoveDrop.32x32", "LinkDrop.32x32",
                          "CopyNoDrop.32x32", "MoveNoDrop.32x32",
                          "LinkNoDrop.32x32", "Invalid.32x32"};
        for (final String name : names) {
            if (Cursor.getSystemCustomCursor(name) == null) {
                throw new RuntimeException("Cursor is null: " + name);
            }
        }
        System.out.println("Test passed");
    }
}
