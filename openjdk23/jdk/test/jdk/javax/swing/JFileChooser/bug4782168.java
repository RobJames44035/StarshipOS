/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4782168
 * @summary Tests if DefaultShellFolder.isHidden() crashes for the
           root folder on Solaris
 * @modules java.desktop/sun.awt.shell
 * @run main bug4782168
 */

public class bug4782168 {

    public static void main(String args[]) throws Exception {
        sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.
                getShellFolder(new java.io.File("/"));
        sf.isHidden();
    }
}
