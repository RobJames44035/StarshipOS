/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;
import java.awt.*;

/*
 * @test
 * @summary Check that OverlayLayout constructor does not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessOverlayLayout
 */

public class HeadlessOverlayLayout {
    public static void main(String args[]) {
        OverlayLayout msm = new OverlayLayout(new Container());
    }
}
