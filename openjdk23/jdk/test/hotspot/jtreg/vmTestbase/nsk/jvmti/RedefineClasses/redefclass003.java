/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

import java.io.*;

/**
 * This test checks that the values of preexisting static variables of
 * the redefined class will remain as they were prior to the redifinition;<br>
 * check that completely uninitialized (new) static variables will be
 * assigned their default value.
 */
public class redefclass003 {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int NO_RESULTS = 3;
    static final int JCK_STATUS_BASE = 95;

    static boolean DEBUG_MODE = false;
    static String fileDir = ".";

    private PrintStream out;

    static {
        try {
            System.loadLibrary("redefclass003");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Could not load redefclass003 library");
            System.err.println("java.library.path:" +
                System.getProperty("java.library.path"));
            throw e;
        }
    }

    native static int makeRedefinition(int vrb, Class redefClass,
        byte[] classBytes);
    native static int checkNewFields(int vrb, Class redefClass);

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        System.exit(run(argv, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new redefclass003().runIt(argv, out);
    }

    private int runIt(String argv[], PrintStream out) {
        File newRedefClassFile = null;
        byte[] redefClassBytes;
        int retValue = 0;
        boolean totalRes = true;

        this.out = out;
        for (int i = 0; i < argv.length; i++) {
            String token = argv[i];

            if (token.equals("-v")) // verbose mode
                DEBUG_MODE = true;
            else
                fileDir = token;
        }

        redefclass003r redefClsObj = new redefclass003r();
        if ((retValue=redefClsObj.checkIt(out, DEBUG_MODE)) == 19 &&
            redefClsObj.byteFld == 1 && redefClsObj.shortFld == 2 &&
            redefClsObj.intFld == 3 && redefClsObj.longFld == 4L &&
            redefClsObj.floatFld == 5.1F && redefClsObj.doubleFld == 6.0D &&
            redefClsObj.charFld == 'a' && redefClsObj.booleanFld == false &&
            redefClsObj.stringFld.equals("OLD redefclass003r")) {
            if (DEBUG_MODE)
                out.println("Successfully check the OLD version of redefclass003r");
        } else {
            out.println("TEST: failed to check the OLD version of redefclass003r");
            return FAILED;
        }

// try to redefine class redefclass003r
        String fileName = fileDir + File.separator + "newclass" + File.separator +
            redefclass003r.class.getName().replace('.', File.separatorChar) +
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
            retValue=makeRedefinition(1, redefClsObj.getClass(),
                redefClassBytes);
        else
            retValue=makeRedefinition(0, redefClsObj.getClass(),
                redefClassBytes);
        switch (retValue) {
            case FAILED:
                out.println("TEST: failed to redefine class");
                return FAILED;
            case NO_RESULTS:
                return PASSED;
            case PASSED:
                break;
        }

        if ((retValue=redefClsObj.checkIt(out, DEBUG_MODE)) == 73) {
            if (DEBUG_MODE)
                out.println("Successfully check the NEW version of redefclass003r\n");

/* Check that preexisting fields of the redefined class retain their
   previous values after the redefinition */
            if (redefClsObj.byteFld == 1 && redefClsObj.shortFld == 2 &&
                redefClsObj.intFld == 3 && redefClsObj.longFld == 4L &&
                redefClsObj.floatFld == 5.1F && redefClsObj.doubleFld == 6.0D &&
                redefClsObj.charFld == 'a' && redefClsObj.booleanFld == false &&
                redefClsObj.stringFld.equals("OLD redefclass003r")) {
                if (DEBUG_MODE)
                    out.println("Check #1 PASSED: Preexisting static fields of the " +
                        "redefined class retain their previous values\n");
            } else {
                out.println("TEST FAILED: Preexisting static fields of the " +
                    "redefined class do not retain their previous values");
                out.println("Current static field values of the redefined class:\n");
                out.println("\tbyteFld = " + redefClsObj.byteFld +
                    ",\texpected 1");
                out.println("\tshortFld = " + redefClsObj.shortFld +
                    ",\texpected 2");
                out.println("\tintFld = " + redefClsObj.intFld +
                    ",\texpected 3");
                out.println("\tlongFld = " + redefClsObj.longFld +
                    ",\texpected 4");
                out.println("\tfloatFld = " + redefClsObj.floatFld +
                    ",\texpected 5.1");
                out.println("\tdoubleFld = " + redefClsObj.doubleFld +
                    ",\texpected 6.0");
                out.println("\tcharFld = '" + redefClsObj.charFld +
                "',\texpected 'a'");
                out.println("\tbooleanFld = " + redefClsObj.booleanFld +
                    ",\texpected false");
                out.println("\tstringFld = \"" + redefClsObj.stringFld +
                "\"\texpected \"OLD redefclass003r\"\n");
                totalRes = false;
            }

/* Check that all completely uninitialized (new) static variables
   were assigned their default value after redefinition */
            if (DEBUG_MODE)
                retValue=checkNewFields(1, redefClsObj.getClass());
            else
                retValue=checkNewFields(0, redefClsObj.getClass());
            if (retValue == PASSED) {
                if (DEBUG_MODE)
                    out.println("Check #2 PASSED: All completely uninitialized " +
                        "static variables of the redefined class\n" +
                        "\twere assigned their default value\n");
            } else {
                out.println("TEST FAILED: Completely uninitialized static " +
                    "variables of the redefined class were not assigned " +
                    "their default value\n");
                totalRes = false;
            }
        } else {
            if (retValue == 19)
                out.println("TEST FAILED: the method redefclass003r.checkIt() is still old");
            else
                out.println("TEST: failed to call method redefclass003r.checkIt()");

            return FAILED;
        }

        if (totalRes) return PASSED;
        else return FAILED;
    }
}
