/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6448069
  @summary namefilter is not called for file dialog on windows
  @library ../../regtesthelpers
  @build Util
  @run main/othervm FilenameFilterTest
*/

import java.awt.*;

import java.io.File;
import java.io.FilenameFilter;

import test.java.awt.regtesthelpers.Util;

public class FilenameFilterTest {

    static volatile boolean filter_was_called = false;
    static FileDialog fd;

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    fd = new FileDialog(new Frame(""), "hello world", FileDialog.LOAD);
                    fd.setFilenameFilter(new FilenameFilter() {
                            public boolean accept(File dir, String name) {
                                filter_was_called = true;
                                System.out.println(Thread.currentThread() + " name = " + name );
                                return true;
                            }
                        });
                    fd.setDirectory(System.getProperty("test.src"));
                    fd.setVisible(true);
                }
            });
        Util.waitForIdle(null);
        if (fd == null) {
            throw new RuntimeException("fd is null (very unexpected thing :(");
        }
        //Wait a little; some native dialog implementations may take a while
        //to initialize and call the filter. See 6959787 for an example.
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
        }
        fd.dispose();
        if (!filter_was_called) {
            throw new RuntimeException("Filter was not called");
        }
    }
}// class FilenameFilterTest
