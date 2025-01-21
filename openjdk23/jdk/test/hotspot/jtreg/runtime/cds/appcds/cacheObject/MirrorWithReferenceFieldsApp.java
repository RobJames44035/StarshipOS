/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.io.File;
import java.net.URL;
import jdk.test.whitebox.WhiteBox;

//
// - Test static final String field with initial value in cached mirror should be also archived.
// - GC should not crash when reference fields in cached mirror are updated at runtime
//     - Reference fields are updated to point to runtime created objects
//     - Reference fields are nullified
//
public class MirrorWithReferenceFieldsApp {

    // Static String field with initial value
    static final String archived_field = "abc";

    // Static object field
    static Object non_archived_field_1;

    // Instance field
    Integer non_archived_field_2;

    public MirrorWithReferenceFieldsApp() {
        non_archived_field_1 = new Object();
        non_archived_field_2 = Integer.valueOf(1);
    }

    public static void main(String args[]) throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();

        if (!wb.areOpenArchiveHeapObjectsMapped()) {
            System.out.println("Archived open_archive_heap objects are not mapped.");
            System.out.println("This may happen during normal operation. Test Skipped.");
            return;
        }

        MirrorWithReferenceFieldsApp m = new MirrorWithReferenceFieldsApp();
        m.test(wb);
    }

    public void test(WhiteBox wb) {
        Class c = MirrorWithReferenceFieldsApp.class;
        if (wb.isSharedClass(c)) {
            if (wb.isSharedInternedString(archived_field)) {
                System.out.println("archived_field is archived as excepted");
            } else {
                throw new RuntimeException(
                    "FAILED. archived_field is not archived.");
            }

            // GC should not crash
            System.gc();
            System.gc();
            System.gc();

            non_archived_field_1 = null;
            non_archived_field_2 = null;

            System.gc();
            System.gc();
            System.gc();

            System.out.println("Done.");
        }
    }
}
