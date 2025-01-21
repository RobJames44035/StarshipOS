/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


package nsk.jdi.ClassUnloadRequest.addClassFilter;

import java.io.*;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>filter001a</code> is deugee's part of the test.
 *  It contains the descriptions classes, which are used by debugger's part of the test.
 */
public class filter001a {

    static public String[] testedClasses = {
            "Superfilter001b",
            "Subfilter0011",
            "Subfilter0021",
            "Subfilter0031",
            "Superfilter002b",
            "Subfilter0012",
            "Subfilter0022",
            "Subfilter0032"
      };

    static public Log log = null;
    static public IOPipe pipe = null;

    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(filter001.SGNL_READY);

        // define directory to load class files
        String classDir = (argHandler.getArguments())[0] + File.separator + "loadclass";

        ClassUnloader unloader = null;
        String instr = pipe.readln();

        while (!instr.equals(filter001.SGNL_BREAK)) {

            log.display("<" + instr + ">" + " arrived");
            if (instr==null) {
                break;
            }

            // loading of tested classes
            if (instr.equals(filter001.SGNL_LOAD)) {
                unloader = loadClasses(classDir);

            // unloading of tested classes
            } else if (instr.equals(filter001.SGNL_UNLOAD)) {

                unloadClasses(unloader);
            }
            pipe.println(filter001.SGNL_READY);

            instr = pipe.readln();
        }

        instr = pipe.readln();
        log.display("<" + instr + ">" + " arrived");

        if (instr != null) {
            if (instr.equals(filter001.SGNL_QUIT)) {
                log.display("DEBUGEE> completed succesfully.");
                System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
            }
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    private static ClassUnloader loadClasses(String classDir) {
        ClassUnloader unloader = new ClassUnloader();
        for (int i = 0; i < testedClasses.length; i++) {
            try {
                unloader.loadClass(filter001.prefix + testedClasses[i], classDir);
            } catch(ClassNotFoundException e) {
                log.complain("DEBUGEE> class " + testedClasses[i] + " not found");
                log.complain(" " + e);
                System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
            }
        }
        return unloader;
    }

    private static void unloadClasses(ClassUnloader unloader) {
        unloader.unloadClass();
    }
}
