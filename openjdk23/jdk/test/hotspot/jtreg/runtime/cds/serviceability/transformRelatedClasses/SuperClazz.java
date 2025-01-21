/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */


public class SuperClazz {
    public static void testParent() {
        System.out.println("SuperClazz: entering testParent()");

        // The line below will be used to check for successful class transformation
        System.out.println(TransformUtil.ParentCheckPattern + TransformUtil.BeforePattern);
    }
}
