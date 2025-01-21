/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8021820
 * @summary The total number of file descriptors is limited to
 * 1024(FDSET_SIZE) on MacOSX (the size of fd array passed to select()
 * call in java.net classes is limited to this value).
 * @run main/othervm SelectFdsLimit
 * @author aleksej.efimov@oracle.com
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;


/*
 * Test must be run in othervm mode to ensure that all files
 * opened by openFiles() are closed propertly.
*/
public class SelectFdsLimit {
    static final int FDTOOPEN = 1023;
    static final String TESTFILE = "testfile";
    static FileInputStream [] testFIS;

    static void prepareTestEnv() throws IOException {
            File fileToCreate = new File(TESTFILE);
            if (!fileToCreate.exists())
                if (!fileToCreate.createNewFile())
                    throw new RuntimeException("Can't create test file");
    }

    //If there will be some problem (i.e. ulimits on number of opened files will fail)
    //then this method will fail with exception and test will be considered as
    //failed. But allocated fds will be released because the test is executed by
    //dedicated VM (@run main/othervm).
    static void openFiles(int fn, File f) throws FileNotFoundException, IOException {
        testFIS = new FileInputStream[FDTOOPEN];
        for (;;) {
            if (0 == fn)
                break;
            FileInputStream fis = new FileInputStream(f);
            testFIS[--fn] = fis;
        }
    }

    public static void main(String [] args) throws IOException, FileNotFoundException {

        //The bug 8021820 is a Mac specific and because of that test will pass on all
        //other platforms
        if (!System.getProperty("os.name").contains("OS X")) {
           return;
        }

        //Create test directory with test files
        prepareTestEnv();

        //Consume FD ids for this java process to overflow the 1024
        openFiles(FDTOOPEN,new File(TESTFILE));

        //Wait for incoming connection and make the select() used in java.net
        //classes fail the limitation on FDSET_SIZE
        ServerSocket socket = new ServerSocket(0);

        //Set the minimal timeout, no one is
        //going to connect to this server socket
        socket.setSoTimeout(1);

        // The accept() call will throw SocketException if the
        // select() has failed due to limitation on fds size,
        // indicating test failure. A SocketTimeoutException
        // is expected, so it is caught and ignored, and the test
        // passes.
        try {
           socket.accept();
        } catch (SocketTimeoutException e) { }
    }
}
