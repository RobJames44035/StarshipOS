/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests ColorUIResource encoding
 * @run main/othervm javax_swing_plaf_ColorUIResource
 * @author Sergey Malenkov
 */

import javax.swing.plaf.ColorUIResource;

public final class javax_swing_plaf_ColorUIResource extends AbstractTest<ColorUIResource> {
    public static void main(String[] args) {
        new javax_swing_plaf_ColorUIResource().test();
    }

    protected ColorUIResource getObject() {
        return new ColorUIResource(0x44, 0x22, 0x11);
    }

    protected ColorUIResource getAnotherObject() {
        return null; // TODO: could not update property
        // return new ColorUIResource(Color.BLACK);
    }
}
