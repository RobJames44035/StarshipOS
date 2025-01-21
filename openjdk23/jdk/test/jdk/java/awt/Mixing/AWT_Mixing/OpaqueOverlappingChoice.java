/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


/**
 * AWT/Swing overlapping test for opaque Choice.
 *
 * This test case was separated from {@link OpaqueOverlapping} due to CR 6994264 (Choice autohides dropdown on Solaris 10)
 */
/*
 * @test
 * @key headful
 * @bug 6994264
 * @summary Opaque overlapping test for Choice AWT component
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/java.awt.peer
 *          java.desktop/sun.awt
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main OpaqueOverlappingChoice
 */
public class OpaqueOverlappingChoice extends OpaqueOverlapping  {
    {
        onlyClassName = "Choice";
        skipClassNames = null;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new OpaqueOverlappingChoice();
        OverlappingTestBase.doMain(args);
    }
}

