/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/* @test
   @bug 6550546
   @summary Win LAF: JFileChooser -> Look in Drop down should not display any shortcuts created on desktop
   @author Pavel Porvatov
   @modules java.desktop/sun.awt
            java.desktop/sun.awt.shell
   @run main bug6550546
*/

import sun.awt.OSInfo;
import sun.awt.shell.ShellFolder;

import javax.swing.*;
import java.io.File;

public class bug6550546 {
    public static void main(String[] args) throws Exception {
        if (OSInfo.getOSType() != OSInfo.OSType.WINDOWS) {
            System.out.println("The test is suitable only for Windows, skipped.");

            return;
        }

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                File[] files = (File[]) ShellFolder.get("fileChooserComboBoxFolders");

                for (File file : files) {
                    if (file instanceof ShellFolder && ((ShellFolder) file).isLink()) {
                        throw new RuntimeException("Link shouldn't be in FileChooser combobox, " + file.getPath());
                    }
                }
            }
        });
    }
}
