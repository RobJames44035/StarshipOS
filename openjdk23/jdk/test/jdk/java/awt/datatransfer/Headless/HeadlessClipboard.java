/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.datatransfer.Clipboard;

/*
 * @test
 * @summary Check Clipboard constructor and getName() method do not throw
 *          exceptions in headless mode
 * @modules java.datatransfer
 * @run main/othervm -Djava.awt.headless=true HeadlessClipboard
 */

public class HeadlessClipboard {
    public static void main(String args[]) {
        Clipboard cb = new Clipboard("dummy");
        cb.getName();
    }
}
