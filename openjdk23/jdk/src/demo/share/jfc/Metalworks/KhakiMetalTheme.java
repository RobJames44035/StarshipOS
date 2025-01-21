/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */



import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;


/**
 * This class describes a theme using "khaki" colors.
 *
 * @author Steve Wilson
 * @author Alexander Kouznetsov
 */
public class KhakiMetalTheme extends DefaultMetalTheme {

    @Override
    public String getName() {
        return "Sandstone";
    }
    private final ColorUIResource primary1 = new ColorUIResource(87, 87, 47);
    private final ColorUIResource primary2 = new ColorUIResource(159, 151, 111);
    private final ColorUIResource primary3 = new ColorUIResource(199, 183, 143);
    private final ColorUIResource secondary1 =
            new ColorUIResource(111, 111, 111);
    private final ColorUIResource secondary2 =
            new ColorUIResource(159, 159, 159);
    private final ColorUIResource secondary3 =
            new ColorUIResource(231, 215, 183);

    @Override
    protected ColorUIResource getPrimary1() {
        return primary1;
    }

    @Override
    protected ColorUIResource getPrimary2() {
        return primary2;
    }

    @Override
    protected ColorUIResource getPrimary3() {
        return primary3;
    }

    @Override
    protected ColorUIResource getSecondary1() {
        return secondary1;
    }

    @Override
    protected ColorUIResource getSecondary2() {
        return secondary2;
    }

    @Override
    protected ColorUIResource getSecondary3() {
        return secondary3;
    }
}
