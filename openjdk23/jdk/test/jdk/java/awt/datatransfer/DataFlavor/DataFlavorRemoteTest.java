/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 8051636
  @summary DataTransferer optional dependency on RMI
  @author Semyon Sadetsky
  @library ../../regtesthelpers/process
  @build ProcessResults ProcessCommunicator
  @run main DataFlavorRemoteTest
*/

import test.java.awt.regtesthelpers.process.ProcessCommunicator;
import test.java.awt.regtesthelpers.process.ProcessResults;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

interface Hello extends java.rmi.Remote {
    String sayHello();
}

public class DataFlavorRemoteTest {

    public static void main(String[] args) throws Exception {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Producer contents = new Producer();
        clipboard.setContents(contents, null);
        ProcessResults processResults =
                ProcessCommunicator
                        .executeChildProcess(Consumer.class, new String[0]);
        if (!"Hello".equals(processResults.getStdOut())) {
            throw new RuntimeException("transfer of remote object failed");
        }
        System.out.println("ok");
    }

    static class Consumer {
        public static void main(String[] args) throws Exception {
            Clipboard clipboard =
                    Toolkit.getDefaultToolkit().getSystemClipboard();
            DataFlavor dataFlavor = new DataFlavor(DataFlavor.javaRemoteObjectMimeType +
                    ";class=Hello" );
            Object data = clipboard.getData(dataFlavor);
            System.out.print(((Hello) data).sayHello());
        }

    }
}

class Producer implements Transferable {

    private final DataFlavor dataFlavor;
    private final HelloImpl impl;

    private static class HelloImpl implements Hello, Serializable {
        @Override
        public String sayHello() {
            return "Hello";
        }
    }

    public Producer() throws Exception {
        dataFlavor = new DataFlavor(DataFlavor.javaRemoteObjectMimeType +
                ";class=Hello" );
        impl = new HelloImpl();
        System.out.println(impl.hashCode());
    }

    Hello getImpl() {
        return impl;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{dataFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(dataFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        return impl;
    }

}
