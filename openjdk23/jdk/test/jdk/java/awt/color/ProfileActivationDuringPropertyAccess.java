/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.util.concurrent.CountDownLatch;

/**
 * @test
 * @bug 6986863
 * @summary Verifies MT safety of profile activation while a profile is accessed
 */
public final class ProfileActivationDuringPropertyAccess {

    private static volatile boolean failed;
    private static volatile boolean end;

    public static void main(String[] args) throws Exception {
        test(ICC_Profile.getInstance(ColorSpace.CS_sRGB));
        test(ICC_Profile.getInstance(ColorSpace.CS_GRAY));
        test(ICC_Profile.getInstance(ColorSpace.CS_CIEXYZ));
        test(ICC_Profile.getInstance(ColorSpace.CS_LINEAR_RGB));
        test(ICC_Profile.getInstance(ColorSpace.CS_PYCC));
    }

    private static void test(ICC_Profile profile) throws Exception {
        Thread[] ts = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException ex) {
                }
                try {
                    while (!end) {
                        profile.getColorSpaceType(); // try use deferred info
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                    failed = true;
                }
            });
        }
        for (Thread t : ts) {
            t.start();
        }
        Thread.sleep(1500);
        profile.getPCSType(); // activate profile
        end = true;
        for (Thread t : ts) {
            t.join();
        }
        if (failed) {
            throw new RuntimeException();
        }
    }
}
