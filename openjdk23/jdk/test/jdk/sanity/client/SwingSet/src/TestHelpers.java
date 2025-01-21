/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.UIManager;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.testng.annotations.DataProvider;

public class TestHelpers {

    public static final long DELAY_BTWN_FRAME_STATE_CHANGE = 2000;

    /**
     * A DataProvider having the class name of all the available look and feels
     *
     * @return a 2d Object array containing the class name of all the available
     * look and feels
     */
    @DataProvider(name = "availableLookAndFeels")
    public static Object[][] provideAvailableLookAndFeels() {
        UIManager.LookAndFeelInfo LookAndFeelInfos[]
                = UIManager.getInstalledLookAndFeels();
        Object[][] lookAndFeels = new Object[LookAndFeelInfos.length][1];
        for (int i = 0; i < LookAndFeelInfos.length; i++) {
            lookAndFeels[i][0] = LookAndFeelInfos[i].getClassName();
        }
        return lookAndFeels;
    }

    public static void checkChangeLocation(ComponentOperator component,
            Point finalLocation) throws InterruptedException {
        Point initialLocation = component.getLocation();
        component.setLocation(finalLocation);
        component.waitComponentLocation(finalLocation);
        // TODO This is a workaround for JDK-8210638, this delay has to remove
        // after fixing this bug, this is an unstable code.
        delayBetweenFrameStateChange();
        component.setLocation(initialLocation);
        component.waitComponentLocation(initialLocation);
        // TODO This is a workaround for JDK-8210638, this delay has to remove
        // after fixing this bug, this is an unstable code.
        delayBetweenFrameStateChange();
    }

    public static void checkChangeSize(ComponentOperator component,
            Dimension dimensionFinal) throws InterruptedException {
        Dimension dimensionInitial = component.getSize();
        component.setSize(dimensionFinal);
        component.waitComponentSize(dimensionFinal);
        // TODO This is a workaround for JDK-8210638, this delay has to remove
        // after fixing this bug, this is an unstable code.
        delayBetweenFrameStateChange();
        component.setSize(dimensionInitial);
        component.waitComponentSize(dimensionInitial);
        // TODO This is a workaround for JDK-8210638, this delay has to remove
        // after fixing this bug, this is an unstable code.
        delayBetweenFrameStateChange();
    }

    // TODO This is a workaround for JDK-8210638, this delay has to remove
    // after fixing this bug, this is an unstable code.
    public static void delayBetweenFrameStateChange()
            throws InterruptedException {
        Thread.sleep(DELAY_BTWN_FRAME_STATE_CHANGE);
    }

}