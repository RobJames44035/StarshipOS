/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/* @test
 * @bug 6875716
 * @summary JLayer.remove((Component)null) should behave consistently in (not) throwing NPE
 * @author Alexander Potochkin
 */

import javax.swing.*;
import java.awt.*;

public class bug6875716 {

    public static void main(String[] args) throws Exception {
        JLayer<Component> layer = new JLayer<Component>(new Component(){});
        layer.setGlassPane(null);
        try {
            layer.remove((Component)null);
        } catch (NullPointerException e) {
            //this is what we expect
            return;
        }
        throw new RuntimeException("Test failed");
    }
}
