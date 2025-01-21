/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

class FileListTransferable implements Transferable {

    final private DataFlavor[] supportedFlavors =
            {DataFlavor.javaFileListFlavor};

    private java.util.List<File> list;

    public FileListTransferable(java.util.List<File> list) {
        this.list = list;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (DataFlavor df:supportedFlavors) {
            if (df.equals(flavor)) return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.javaFileListFlavor)) {
            return list;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
