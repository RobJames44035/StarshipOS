/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;
import static jdk.test.lib.Asserts.*;

// DD: Dialog -> Dialog

public class BlockingDDTest {

    private TestDialog parent, dialog;

    private static final int delay = 1000;
    private final ExtendedRobot robot;

    private final Dialog.ModalityType modalityType;
    private final boolean setModal;

    private BlockingDDTest(Dialog.ModalityType modType, boolean modal) throws Exception {

        modalityType = modType;
        setModal = modal;
        robot = new ExtendedRobot();
        createGUI();
    }

    public BlockingDDTest(Dialog.ModalityType modType) throws Exception {
        this(modType, false);
    }

    public BlockingDDTest() throws Exception {
        this(null, true);
    }


    private void showParent() {

        parent = new TestDialog((Frame) null);
        parent.setTitle("Parent");
        parent.setLocation(50, 50);
        parent.setVisible(true);
    }

    private void showChild() {

        dialog = new TestDialog(parent);
        if (setModal) {
            dialog.setModal(true);
        } else if (modalityType != null) {
            dialog.setModalityType(modalityType);
        }

        dialog.setLocation(250, 50);
        dialog.setVisible(true);
    }


    private void createGUI() throws Exception {

        EventQueue.invokeAndWait(this::showParent);
        robot.waitForIdle(delay);
        EventQueue.invokeLater(this::showChild);
        robot.waitForIdle(delay);
    }

    public void doTest() throws Exception {

        try {
            dialog.activated.waitForFlagTriggered();
            assertTrue(dialog.activated.flag(), "Dialog did not trigger " +
                "Window Activated event when it became visible");

            dialog.closeGained.waitForFlagTriggered();
            assertTrue(dialog.closeGained.flag(), "the 1st Dialog button " +
                "did not gain focus when it became visible");

            assertTrue(dialog.closeButton.hasFocus(), "the 1st Dialog button " +
                "gained the focus but lost it afterwards");

            dialog.checkUnblockedDialog(robot, "Modal Dialog shouldn't be blocked.");

            if ((modalityType == Dialog.ModalityType.APPLICATION_MODAL) ||
                (modalityType == Dialog.ModalityType.DOCUMENT_MODAL) ||
                (modalityType == Dialog.ModalityType.TOOLKIT_MODAL) ||
                dialog.isModal())
            {
                parent.checkBlockedDialog(robot,
                    "Dialog is the parent of a visible " + modalityType + " Dialog.");
            } else {
                parent.checkUnblockedDialog(robot,
                    "Dialog is the parent of a visible " + modalityType + " Dialog.");
            }

            robot.waitForIdle(delay);
        } finally {
            EventQueue.invokeAndWait(this::closeAll);
        }
    }

    private void closeAll() {
        if (parent != null) { parent.dispose(); }
        if (dialog != null) { dialog.dispose(); }
    }
}
