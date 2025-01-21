/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8013442
 * @summary Tests that at least one file filter is selected
 * @author Sergey Malenkov
 */

import java.io.File;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;

public class Test8013442 extends FileFilter implements Runnable, Thread.UncaughtExceptionHandler {
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Test8013442());
        LATCH.await(); // workaround for jtreg
    }

    private int index;
    private LookAndFeelInfo[] infos;
    private JFileChooser chooser;

    @Override
    public boolean accept(File file) {
        return !file.isFile() || file.getName().toLowerCase().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text files";
    }

    @Override
    public void run() {
        if (this.infos == null) {
            this.infos = UIManager.getInstalledLookAndFeels();
            Thread.currentThread().setUncaughtExceptionHandler(this);
        }
        if (this.infos.length == this.index) {
            LATCH.countDown(); // release main thread
        } else if (this.chooser == null) {
            // change LaF before creation of Swing components
            LookAndFeelInfo info = this.infos[this.index];
            System.out.println(info.getName());
            try {
                UIManager.setLookAndFeel(info.getClassName());
            }
            catch (Exception exception) {
                throw new Error("could not change look and feel", exception);
            }
            // create and show new file chooser
            JFrame frame = new JFrame(getClass().getSimpleName());
            frame.add(this.chooser = new JFileChooser());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            SwingUtilities.invokeLater(this);
        }
        else {
            int count = this.chooser.getChoosableFileFilters().length;
            System.out.println("count = " + count + "; " + this.chooser.isAcceptAllFileFilterUsed());
            if (count == 0) {
                if (null != this.chooser.getFileFilter()) {
                    throw new Error("file filter is selected");
                }
                // close window and stop testing file chooser for current LaF
                SwingUtilities.getWindowAncestor(this.chooser).dispose();
                this.chooser = null;
                this.index++;
            } else {
                if (null == this.chooser.getFileFilter()) {
                    throw new Error("file filter is not selected");
                }
                if (count == 2) {
                    // remove default file filter
                    this.chooser.setAcceptAllFileFilterUsed(false);
                } else if (this.chooser.isAcceptAllFileFilterUsed()) {
                    // remove add file filter
                    this.chooser.addChoosableFileFilter(this);
                } else {
                    // remove custom file filter
                    this.chooser.removeChoosableFileFilter(this);
                }
            }
            SwingUtilities.invokeLater(this);
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();
        throw new RuntimeException(throwable);
    }
}
