/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.unit.events.redefineCFLH;

import java.io.*;

public class JvmtiTest {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;

    static boolean DEBUG_MODE = false;
    static String fileDir = ".";

    private PrintStream out;

    static {
        try {
            System.loadLibrary("redefineCFLH");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Could not load redefineCFLH library");
            System.err.println("java.library.path:" +
                System.getProperty("java.library.path"));
            throw e;
        }
    }

    native static int GetResult();
    native static int makeRedefinition(int verbose, Class redefClass,
        byte[] classBytes);

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        System.exit(run(argv, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new JvmtiTest().runIt(argv, out);
    }

    private int runIt(String argv[], PrintStream out) {
        File newRedefClassFile = null;
        byte[] redefClassBytes;
        int retValue = 0;

        this.out = out;
        for (int i = 0; i < argv.length; i++) {
            String token = argv[i];

            if (token.equals("-v")) // verbose mode
                DEBUG_MODE = true;
            else
                fileDir = token;
        }

        JvmtiTestr redefClsObj = new JvmtiTestr();
        if ((retValue=redefClsObj.checkIt(DEBUG_MODE, out)) == 19) {
            if (DEBUG_MODE)
                out.println("Successfully invoke method checkIt() of OLD JvmtiTestr");
        } else {
            out.println("TEST: failed to invoke method JvmtiTestr.checkIt()");
            return FAILED;
        }

// try to redefine class JvmtiTestr
        String fileName = fileDir + File.separator + "newclass" + File.separator +
            JvmtiTestr.class.getName().replace('.', File.separatorChar) +
            ".class";
        if (DEBUG_MODE)
            out.println("Trying to redefine class from the file: " + fileName);
        try {
            FileInputStream in = new FileInputStream(fileName);
            redefClassBytes = new byte[in.available()];
            in.read(redefClassBytes);
            in.close();
        } catch (Exception ex) {
            out.println("# Unexpected exception while reading class file:");
            out.println("# " + ex);
            return FAILED;
        }

// make real redefinition
        if (DEBUG_MODE)
            retValue=makeRedefinition(2, redefClsObj.getClass(),
                redefClassBytes);
        else
            retValue=makeRedefinition(1, redefClsObj.getClass(),
                redefClassBytes);
        if (retValue != PASSED) {
            out.println("TEST: failed to redefine class");
            return FAILED;
        }

        if ((retValue=redefClsObj.checkIt(DEBUG_MODE, out)) == 73) {
            if (DEBUG_MODE)
                out.println("Successfully invoke method checkIt() of NEW JvmtiTestr");
        } else {
            if (retValue == 19)
                out.println("TEST: the method JvmtiTestr.checkIt() is still old");
            else
                out.println("TEST: failed to call method JvmtiTestr.checkIt()");
            return FAILED;
        }

        return GetResult();
    }
}
