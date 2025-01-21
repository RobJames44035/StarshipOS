/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.*;
import java.lang.instrument.*;

public class RedefineSubclassWithTwoInterfacesApp {
    public static void main(String args[]) throws Exception {
        System.out.println("Hello from RedefineSubclassWithTwoInterfacesApp!");

        new RedefineSubclassWithTwoInterfacesApp().doTest();

        System.exit(0);
    }

    private void doTest() throws Exception {
        RedefineSubclassWithTwoInterfacesImpl impl
            = new RedefineSubclassWithTwoInterfacesImpl();
        RedefineSubclassWithTwoInterfacesRemote remote
            = new RedefineSubclassWithTwoInterfacesRemote(impl, impl);
        String mesg;

        // make echo() calls before any redefinitions:
        mesg = remote.echo1("test message #1.1");
        System.out.println("RedefineSubclassWithTwoInterfacesApp: echo1 mesg='"
            + mesg + "' before any redefines");
        mesg = remote.echo2("test message #2.1");
        System.out.println("RedefineSubclassWithTwoInterfacesApp: echo2 mesg='"
            + mesg + "' before any redefines");


        // redefining RedefineSubclassWithTwoInterfacesImpl before
        // RedefineSubclassWithTwoInterfacesTarget fails:
        do_redefine("RedefineSubclassWithTwoInterfacesImpl", 1);
        do_redefine("RedefineSubclassWithTwoInterfacesTarget", 1);

        mesg = remote.echo1("test message #1.2");
        System.out.println("RedefineSubclassWithTwoInterfacesApp: echo1 mesg='"
            + mesg + "' after redefine");
        mesg = remote.echo2("test message #2.2");
        System.out.println("RedefineSubclassWithTwoInterfacesApp: echo2 mesg='"
            + mesg + "' after redefine");
    }

    private static void do_redefine(String className, int counter)
            throws Exception {
        File f = new File(className + "_" + counter + ".class");
        System.out.println("Reading test class from " + f);
        InputStream redefineStream = new FileInputStream(f);

        byte[] redefineBuffer
            = NamedBuffer.loadBufferFromStream(redefineStream);

        ClassDefinition redefineParamBlock = new ClassDefinition(
            Class.forName(className), redefineBuffer);

        RedefineSubclassWithTwoInterfacesAgent.getInstrumentation().
            redefineClasses(new ClassDefinition[] {redefineParamBlock});
    }
}
