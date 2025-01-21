/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8170387
 * @summary JLightweightFrame#syncCopyBuffer() may throw IOOBE
 * @modules java.desktop/sun.swing
 * @run main JLightweightFrameRoundTest
 */

import sun.swing.JLightweightFrame;
import sun.swing.LightweightContent;

import javax.swing.*;

public class JLightweightFrameRoundTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLightweightFrame jLightweightFrame = new JLightweightFrame();
            jLightweightFrame.setContent(new XLightweightContent());
            jLightweightFrame.setSize(600, 600);
            jLightweightFrame.notifyDisplayChanged(1.0001, 1.0001);
        });
    }

    static class XLightweightContent implements LightweightContent {
        @Override
        public JComponent getComponent() {
            return new JPanel();
        }

        @Override
        public void paintLock() {}

        @Override
        public void paintUnlock() {}

        @Override
        public void imageBufferReset(int[] data, int x, int y, int width,
                                     int height, int linestride,
                                     double scaleX,
                                     double scaleY) {}

        @Override
        public void imageReshaped(int x, int y, int width, int height) {}

        @Override
        public void imageUpdated(int dirtyX, int dirtyY, int dirtyWidth,
                                 int dirtyHeight) {}

        @Override
        public void focusGrabbed() {}

        @Override
        public void focusUngrabbed() {}

        @Override
        public void preferredSizeChanged(int width, int height) {}

        @Override
        public void maximumSizeChanged(int width, int height) {}

        @Override
        public void minimumSizeChanged(int width, int height) {}
    }
}
