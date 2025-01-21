/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public class CheckContainerized {
    public static String OUTSIDE_OF_CONTAINER =
        "CheckContainerized: Running outside of a container";
    public static String INSIDE_A_CONTAINER =
        "CheckContainerized: Running inside a container";

    public static void main(String[] args) {
        System.out.println("CheckContainerized: Entering");
        WhiteBox wb = WhiteBox.getWhiteBox();

        if (wb.isContainerized()) {
            System.out.println(INSIDE_A_CONTAINER);

        } else {
            System.out.println(OUTSIDE_OF_CONTAINER);
        }
    }
}
