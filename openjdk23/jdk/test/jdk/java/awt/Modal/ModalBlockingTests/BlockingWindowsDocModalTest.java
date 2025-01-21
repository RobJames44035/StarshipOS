/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


import java.awt.*;
import java.awt.event.KeyEvent;
import static jdk.test.lib.Asserts.*;

import java.util.List;
import java.util.ArrayList;

public class BlockingWindowsDocModalTest {

    private ParentDialog parentDialog;
    private ParentFrame  parentFrame;
    private CustomDialog dialog;
    private TestDialog secondDialog, childDialog;
    private TestFrame  secondFrame;
    private TestWindow window, childWindow, secondWindow;


    private static final int delay = 500;
    private final ExtendedRobot robot;

    private List<Window> allWindows;

    public enum Parent {DIALOG, FRAME};
    private Parent root;

    public BlockingWindowsDocModalTest(Parent p) throws Exception {

        root = p;

        robot = new ExtendedRobot();
        EventQueue.invokeLater(this::createGUI);
    }

    private void createGUI() {

        allWindows = new ArrayList<>();

        switch (root) {
            case DIALOG:
                parentDialog = new ParentDialog((Dialog) null);
                parentDialog.setLocation(50, 50);
                parentDialog.setVisible(true);
                allWindows.add(parentDialog);

                dialog = new CustomDialog(parentDialog);
                secondDialog = new TestDialog(parentDialog);
                window = new TestWindow(parentDialog);
                break;
            case FRAME:
                parentFrame = new ParentFrame();
                parentFrame.setLocation(50, 50);
                parentFrame.setVisible(true);
                allWindows.add(parentFrame);

                dialog = new CustomDialog(parentFrame);
                secondDialog = new TestDialog(parentFrame);
                window = new TestWindow(parentFrame);
                break;
        }

        allWindows.add(dialog);
        allWindows.add(secondDialog);
        allWindows.add(window);

        dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setLocation(250, 50);
        window.setLocation(450, 50);
        secondDialog.setLocation(450, 250);

        secondFrame = new TestFrame();
        allWindows.add(secondFrame);
        secondFrame.setLocation(50, 250);

        secondWindow = new TestWindow(secondFrame);
        allWindows.add(secondWindow);
        secondWindow.setLocation(250, 250);

        childDialog = new TestDialog(dialog);
        allWindows.add(childDialog);
        childDialog.setLocation(250, 450);

        childWindow = new TestWindow(dialog);
        allWindows.add(childWindow);
        childWindow.setLocation(50, 450);
    }

    public void doTest() throws Exception {

        try {
            robot.waitForIdle(delay);

            if (root == Parent.DIALOG) {
                parentDialog.clickOpenButton(robot);
            } else { //Parent.FRAME
                parentFrame.clickOpenButton(robot);
            }
            robot.waitForIdle(delay);

            dialog.activated.waitForFlagTriggered();
            assertTrue(dialog.activated.flag(), "Dialog did not trigger " +
                "Window Acivated event when it became visible");

            dialog.closeGained.waitForFlagTriggered();
            assertTrue(dialog.closeGained.flag(),
                "the 1st Dialog button didn't gain focus");

            assertTrue(dialog.closeButton.hasFocus(), "the 1st Dialog button " +
                "gained focus but lost it afterwards");

            dialog.openGained.reset();
            robot.type(KeyEvent.VK_TAB);

            dialog.openGained.waitForFlagTriggered();
            assertTrue(dialog.openGained.flag(), "Tab navigation did not happen properly on Dialog; " +
                "Open button did not gain focus on tab press when parent frame is visible");

            dialog.clickOpenButton(robot);
            robot.waitForIdle(delay);

            secondFrame.checkUnblockedFrame(robot,
                "A document modal dialog and its parent are visible.");
            secondWindow.checkUnblockedWindow(robot,
                "A Frame and a document modal Dialog are visible.");

            if (root == Parent.DIALOG) {
                parentDialog.checkBlockedDialog(robot, "Dialog is a parent of a document modal dialog.");
            } else { //Parent.FRAME
                parentFrame.checkBlockedFrame(robot, "Frame is a parent of a document modal dialog.");
            }

            secondDialog.checkBlockedDialog(robot,
                "The parent of the Dialog is also the parent of a document modal dialog");
            window.checkBlockedWindow(robot,
                "The parent of the Window is also the parent of a document modal dialog");

            childWindow.checkUnblockedWindow(robot,
                "The parent of the Window is a document modal dialog");
            childDialog.checkUnblockedDialog(robot,
                "The parent of the Dialog is a document modal dialog");
            robot.waitForIdle(delay);

        } finally {
            EventQueue.invokeAndWait(this::closeAll);
        }
    }

    private void closeAll() {
        for (Window w: allWindows) {
            if (w != null) { w.dispose(); }
        }
    }

    class ParentDialog extends TestDialog {

        public ParentDialog(Dialog d) { super(d); }

        @Override
        public void doOpenAction() {
            if (dialog != null) { dialog.setVisible(true); }
        }
    }

    class ParentFrame extends TestFrame {

        @Override
        public void doOpenAction() {
            if (dialog != null) { dialog.setVisible(true); }
        }

    }

    class CustomDialog extends TestDialog {

        public CustomDialog(Dialog d) { super(d); }
        public CustomDialog(Frame f)  { super(f); }

        @Override
        public void doOpenAction() {
            if (secondFrame  != null) {  secondFrame.setVisible(true); }
            if (secondWindow != null) { secondWindow.setVisible(true); }
            if (secondDialog != null) { secondDialog.setVisible(true); }
            if (window != null) { window.setVisible(true); }
            if (childWindow != null) { childWindow.setVisible(true); }
            if (childDialog != null) { childDialog.setVisible(true); }
        }
    }
}
