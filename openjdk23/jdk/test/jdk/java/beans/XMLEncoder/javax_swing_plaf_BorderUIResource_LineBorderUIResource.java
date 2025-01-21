/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests LineBorderUIResource encoding
 * @run main/othervm javax_swing_plaf_BorderUIResource_LineBorderUIResource
 * @author Sergey Malenkov
 */

import java.awt.Color;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;

public final class javax_swing_plaf_BorderUIResource_LineBorderUIResource extends AbstractTest<LineBorderUIResource> {
    public static void main(String[] args) {
        new javax_swing_plaf_BorderUIResource_LineBorderUIResource().test();
    }

    protected LineBorderUIResource getObject() {
        return new LineBorderUIResource(Color.RED, 2);
    }

    protected LineBorderUIResource getAnotherObject() {
        return null; // TODO: could not update property
        // return new LineBorderUIResource(Color.BLACK);
    }
}
