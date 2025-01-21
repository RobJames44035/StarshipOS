/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/* @test
 * @key headful
 * @bug 8211826
 * @summary StringIndexOutOfBoundsException happens via GetStringUTFRegion()
 * @modules java.desktop/sun.awt
 * @requires (os.family == "linux")
 * @run main GtkIconTest
 */

import java.awt.Toolkit;
import sun.awt.UNIXToolkit;

public class GtkIconTest {
    public static void main(String[] args) throws Exception {
        UNIXToolkit utk = (UNIXToolkit)Toolkit.getDefaultToolkit();
        if (utk.loadGTK()) {
            for (String s : new String[]{ "abc", "\u3042" }) {
                Object obj = utk.getGTKIcon(s);
            }
        }
    }
}
