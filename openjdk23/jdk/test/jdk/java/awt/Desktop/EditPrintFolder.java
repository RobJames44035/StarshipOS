/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * @test
 * @bug 7131400
 * @key headful
 * @summary IOException should be thrown if folder is passed to print()/edit()
 */
public final class EditPrintFolder {

    public static void main(String[] args) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        File file = FileSystems.getDefault().getPath(".").toFile();
        if (file.isDirectory()) {
            if (Desktop.getDesktop().isSupported(Desktop.Action.EDIT)) {
                try {
                    Desktop.getDesktop().edit(file);
                    throw new RuntimeException("IOException was not thrown");
                } catch (IOException ignored) {
                    // ok
                }
            }
            if (Desktop.getDesktop().isSupported(Desktop.Action.PRINT)) {
                try {
                    Desktop.getDesktop().print(file);
                    throw new RuntimeException("IOException was not thrown");
                } catch (IOException ignored) {
                    // ok
                }
            }
        }
    }
}
