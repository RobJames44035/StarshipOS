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
 * This class describes a theme using "blue-green" colors.
 *
 * @author Steve Wilson
 * @author Alexander Kouznetsov
 */
public class AquaMetalTheme extends DefaultMetalTheme {

    @Override
    public String getName() {
        return "Oxide";
    }
    private final ColorUIResource primary1 = new ColorUIResource(102, 153, 153);
    private final ColorUIResource primary2 = new ColorUIResource(128, 192, 192);
    private final ColorUIResource primary3 = new ColorUIResource(159, 235, 235);

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
}
