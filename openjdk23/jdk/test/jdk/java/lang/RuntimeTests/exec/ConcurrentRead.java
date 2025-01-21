/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4619744
 * @summary Test that Process input/out can be concurrently read/written
 * @author kladko
 */

import java.io.InputStream;
import java.io.OutputStream;

public class ConcurrentRead {

    static volatile Exception savedException;

    public static void main(String[] args) throws Exception {
        if (! UnixCommands.isUnix) {
            System.out.println("For UNIX only");
            return;
        }
        UnixCommands.ensureCommandsAvailable("tee");

        Process p = Runtime.getRuntime().exec(UnixCommands.tee());
        OutputStream out = p.getOutputStream();
        InputStream in = p.getInputStream();
        Thread t1 = new WriterThread(out, in);
        t1.start();
        Thread t2 = new WriterThread(out, in);
        t2.start();
        t1.join();
        t2.join();
        if (savedException != null)
            throw savedException;
    }

    static class WriterThread extends Thread {
        OutputStream out;
        InputStream in;
        WriterThread(OutputStream out, InputStream in) {
            this.out = out;
            this.in = in;
        }
        public void run(){
            try {
                out.write('a');
                out.flush();
                if (in.read() == -1) // got end-of-stream
                    throw new Exception("End of stream in writer thread");
            } catch (Exception e) {
                savedException = e;
            }
        }
    }
}
