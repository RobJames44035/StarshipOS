/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


import java.awt.*;


public class FrameToFrontModelessTest {

    private volatile TestDialog dialog;
    private volatile TestFrame  leftFrame, rightFrame;

    private static final int delay = 500;
    private final ExtendedRobot robot;

    private boolean isModeless;

    private static final boolean IS_ON_WAYLAND =
            System.getenv("WAYLAND_DISPLAY") != null;

    public FrameToFrontModelessTest(boolean modeless) throws Exception {
        isModeless = modeless;
        robot = new ExtendedRobot();
        EventQueue.invokeLater(this::createGUI);
    }

    private void createGUI() {

        leftFrame = new TestFrame();
        leftFrame.setSize(200, 100);
        leftFrame.setLocation(50, 50);
        leftFrame.setVisible(true);

        dialog = new TestDialog(leftFrame);
        if (isModeless) { dialog.setModalityType(Dialog.ModalityType.MODELESS); }
        dialog.setSize(200, 100);
        dialog.setLocation(150, 50);
        dialog.setVisible(true);

        rightFrame = new TestFrame();
        rightFrame.setSize(200, 100);
        rightFrame.setLocation(250, 50);
        rightFrame.setVisible(true);
    }


    public void doTest() throws Exception {

        try {

            robot.waitForIdle(delay);

            EventQueue.invokeAndWait(() -> { leftFrame.toFront(); });
            robot.waitForIdle(delay);

            leftFrame.clickDummyButton(
                robot, 7, false, "Calling toFront method on the parent " +
                "left frame brought it to the top of the child dialog");
            robot.waitForIdle(delay);

            // show the right frame appear on top of the dialog
            if (IS_ON_WAYLAND) {
                rightFrame.toFront();
            }
            rightFrame.clickDummyButton(robot);
            robot.waitForIdle(delay);

            String msg = "The " + (isModeless ? "modeless" : "non-modal") +
                " dialog still on top of the right frame" +
                " even after a button on the frame is clicked";
            dialog.clickDummyButton(robot, 7, false, msg);

        } finally {
            EventQueue.invokeAndWait(this::closeAll);
        }
    }

    private void closeAll() {
        if (dialog != null) { dialog.dispose(); }
        if (leftFrame  != null) {  leftFrame.dispose(); }
        if (rightFrame != null) { rightFrame.dispose(); }
    }
}

