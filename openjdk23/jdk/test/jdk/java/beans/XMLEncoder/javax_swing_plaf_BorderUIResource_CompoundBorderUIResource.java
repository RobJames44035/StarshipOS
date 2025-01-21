/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests CompoundBorderUIResource encoding
 * @run main/othervm javax_swing_plaf_BorderUIResource_CompoundBorderUIResource
 * @author Sergey Malenkov
 */

import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;

public final class javax_swing_plaf_BorderUIResource_CompoundBorderUIResource extends AbstractTest<CompoundBorderUIResource> {
    public static void main(String[] args) {
        new javax_swing_plaf_BorderUIResource_CompoundBorderUIResource().test();
    }

    protected CompoundBorderUIResource getObject() {
        return new CompoundBorderUIResource(null, new CompoundBorderUIResource(null, null));
    }

    protected CompoundBorderUIResource getAnotherObject() {
        return null; // TODO: could not update property
        // return new CompoundBorderUIResource(null, null);
    }
}
