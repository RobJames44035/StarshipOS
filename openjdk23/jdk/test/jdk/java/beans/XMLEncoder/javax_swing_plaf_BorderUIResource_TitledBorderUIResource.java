/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests TitledBorderUIResource encoding
 * @run main/othervm javax_swing_plaf_BorderUIResource_TitledBorderUIResource
 * @author Sergey Malenkov
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

public final class javax_swing_plaf_BorderUIResource_TitledBorderUIResource extends AbstractTest<TitledBorderUIResource> {
    public static void main(String[] args) {
        new javax_swing_plaf_BorderUIResource_TitledBorderUIResource().test();
    }

    protected TitledBorderUIResource getObject() {
        return new TitledBorderUIResource(
                new EmptyBorder(1, 2, 3, 4),
                "TITLE",
                TitledBorder.CENTER,
                TitledBorder.ABOVE_TOP,
                new Font("Serif", Font.ITALIC, 12),
                Color.RED);
    }

    protected TitledBorderUIResource getAnotherObject() {
        return null; // TODO: could not update property
        // return new TitledBorderUIResource("");
    }
}
