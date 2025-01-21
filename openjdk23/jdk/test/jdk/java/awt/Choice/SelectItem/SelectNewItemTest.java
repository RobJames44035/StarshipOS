/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.TimeUnit;

/*
 * @test
 * @bug 8215921
 * @summary Test that selecting a different item does send an ItemEvent
 * @key headful
 * @run main SelectNewItemTest
*/
public final class SelectNewItemTest
        extends SelectCurrentItemTest {

    private SelectNewItemTest() throws AWTException {
        super();
    }

    @Override
    protected void checkItemStateChanged() throws InterruptedException {
        if (!itemStateChanged.await(500, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("ItemEvent is not received");
        }
    }

    @Override
    protected void checkSelectedIndex(final int initialIndex,
                                      final int currentIndex) {
        if (initialIndex == currentIndex) {
            throw new RuntimeException("Selected index in Choice should've changed");
        }
    }

    @Override
    protected Point getClickLocation(final Rectangle choiceRect) {
        // Click a different item the popup, not the first one
        return new Point(choiceRect.x + choiceRect.width / 2,
                         choiceRect.y + choiceRect.height * 3);
    }

    public static void main(String... args) throws Exception {
        new SelectNewItemTest().runTest();
    }

}
