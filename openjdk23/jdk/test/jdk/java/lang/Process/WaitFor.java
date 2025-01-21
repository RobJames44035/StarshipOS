/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8220684
 * @summary Process.waitFor(long, TimeUnit) can return false for a process
 *          that exited within the timeout
 * @run main/othervm WaitFor
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class WaitFor {
    public static void main(String[] args) throws Throwable {
        int failCnt = 0;
        for (int i = 0; i < 30; ++i) {
            Process proc = new MyProcess(new ProcessBuilder("true").start());
            boolean exited = proc.waitFor(100, TimeUnit.MILLISECONDS);
            if (!exited && !proc.isAlive()) failCnt++;
        }
        if (failCnt > 10) {
            throw new RuntimeException(failCnt + " processes were still alive"
                + " after timeout");
        }
    }
}

/**
 * This class uses the default implementation of java.lang.Process#waitFor(long,
 * TimeUnit), and delegates all other calls to the actual implementation of
 * Process.
 */
class MyProcess extends Process {
    Process impl;
    public MyProcess(Process impl) { this.impl = impl; }
    public OutputStream getOutputStream() { return impl.getOutputStream(); }
    public InputStream getInputStream() { return impl.getInputStream(); }
    public InputStream getErrorStream() { return impl.getErrorStream(); }
    public int waitFor() throws InterruptedException { return impl.waitFor(); }
    public int exitValue() { return impl.exitValue(); }
    public void destroy() { impl.destroy(); }
    public ProcessHandle toHandle() { return impl.toHandle(); }
}
