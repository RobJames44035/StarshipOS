/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 8029565
  @summary Conversion of a URI list to File list fails
  @library ../../regtesthelpers
  @library ../../regtesthelpers/process
  @build Util
  @build ProcessResults ProcessCommunicator
  @run main/othervm URIListToFileListBetweenJVMsTest main
 */

import test.java.awt.regtesthelpers.Util;
import test.java.awt.regtesthelpers.process.ProcessCommunicator;
import test.java.awt.regtesthelpers.process.ProcessResults;

import java.awt.*;
import java.awt.event.InputEvent;

import static java.lang.Thread.sleep;

public class URIListToFileListBetweenJVMsTest {

    // information related to the test in common
    static int VISIBLE_RAWS_IN_LIST=15;

    public void start() {

        SourceFileListFrame sourceFrame = new SourceFileListFrame();

        Util.waitForIdle(null);

        String [] args = new String [] {
                String.valueOf(sourceFrame.getNextLocationX()),
                String.valueOf(sourceFrame.getNextLocationY()),
                String.valueOf(sourceFrame.getDragSourcePointX()),
                String.valueOf(sourceFrame.getDragSourcePointY()),
                String.valueOf(sourceFrame.getSourceFilesNumber())
        };

        ProcessResults processResults = ProcessCommunicator.executeChildProcess(this.getClass(), args);

        verifyTestResults(processResults);

    }

    private static void verifyTestResults(ProcessResults processResults) {
        if ( InterprocessMessages.WRONG_FILES_NUMBER_ON_TARGET == processResults.getExitValue()) {
            processResults.printProcessErrorOutput(System.err);
            throw new RuntimeException("TEST IS FAILED: Target has recieved wrong number of files.");
        }
        processResults.verifyStdErr(System.err);
        processResults.verifyProcessExitValue(System.err);
        processResults.printProcessStandartOutput(System.out);
    }

    //We cannot make an instance of the applet without the default constructor
    public URIListToFileListBetweenJVMsTest() {
        super();
    }

    //We need in this constructor to pass frame position between JVMs
    public URIListToFileListBetweenJVMsTest(Point targetFrameLocation,
                                            Point dragSourcePoint,
                                            int transferredFilesNumber) throws InterruptedException
    {
        TargetFileListFrame targetFrame = new TargetFileListFrame(targetFrameLocation, transferredFilesNumber);

        Util.waitForIdle(null);

        final Robot robot = Util.createRobot();

        robot.mouseMove((int)dragSourcePoint.getX(),(int)dragSourcePoint.getY());
        sleep(100);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        sleep(100);

        Util.drag(robot, dragSourcePoint, targetFrame.getDropTargetPoint(), InputEvent.BUTTON1_MASK);

    }

    enum InterprocessArguments {
        TARGET_FRAME_X_POSITION_ARGUMENT,
        TARGET_FRAME_Y_POSITION_ARGUMENT,
        DRAG_SOURCE_POINT_X_ARGUMENT,
        DRAG_SOURCE_POINT_Y_ARGUMENT,
        FILES_IN_THE_LIST_NUMBER_ARGUMENT;

        int extract (String [] args) {
            return Integer.parseInt(args[this.ordinal()]);
        }
    }

    public static void main(final String [] args) throws Exception {
        if (args.length > 0 && args[0].equals("main")) {
            new URIListToFileListBetweenJVMsTest().start();
            return;
        }
        Point dragSourcePoint = new Point(InterprocessArguments.DRAG_SOURCE_POINT_X_ARGUMENT.extract(args),
                InterprocessArguments.DRAG_SOURCE_POINT_Y_ARGUMENT.extract(args));
        Point targetFrameLocation = new Point(InterprocessArguments.TARGET_FRAME_X_POSITION_ARGUMENT.extract(args),
                InterprocessArguments.TARGET_FRAME_Y_POSITION_ARGUMENT.extract(args));
        int transferredFilesNumber = InterprocessArguments.FILES_IN_THE_LIST_NUMBER_ARGUMENT.extract(args);

        new URIListToFileListBetweenJVMsTest(targetFrameLocation, dragSourcePoint, transferredFilesNumber);
    }
}
