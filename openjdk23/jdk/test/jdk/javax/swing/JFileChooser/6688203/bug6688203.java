/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
   @bug 6688203
   @summary Memory leak and performance problems in the method getFileSystemView of FileSystemView
   @author Pavel Porvatov
   @modules java.desktop/javax.swing.filechooser:open
   @run main bug6688203
*/

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.lang.reflect.Field;

public class bug6688203 {
    public static void main(String[] args) {
        // Create an instance of FileSystemView
        FileSystemView.getFileSystemView();

        int startCount = UIManager.getPropertyChangeListeners().length;

        for (int i = 0; i < 100; i++) {
            FileSystemView.getFileSystemView();
        }

        if (startCount != UIManager.getPropertyChangeListeners().length) {
            throw new RuntimeException("New listeners were added into UIManager");
        }

        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file = new File("Some file");

        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            try {
                UIManager.setLookAndFeel(lafInfo.getClassName());
            } catch (Exception e) {
                // Ignore such errors
                System.out.println("Cannot set LAF " + lafInfo.getName());

                continue;
            }

            fileSystemView.getSystemDisplayName(file);

            try {
                Field field = FileSystemView.class.getDeclaredField("useSystemExtensionHiding");

                field.setAccessible(true);

                Boolean value = field.getBoolean(fileSystemView);

                if (value != UIManager.getDefaults().getBoolean("FileChooser.useSystemExtensionHiding")) {
                    throw new RuntimeException("Invalid cached value of the FileSystemView.useSystemExtensionHiding field");
                }
            } catch (Exception e) {
                throw new RuntimeException("Cannot read the FileSystemView.useSystemExtensionHiding field", e);
            }
        }
    }
}
