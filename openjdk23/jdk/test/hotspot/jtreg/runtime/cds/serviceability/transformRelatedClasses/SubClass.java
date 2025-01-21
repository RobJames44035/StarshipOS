/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

public class SubClass extends SuperClazz {
    public static void main(String[] args) {
        System.out.println("SubClass: entering main()");
        test();
    }

    public static void test() {
        // The line below will be used to check for successful class transformation
        System.out.println(TransformUtil.ChildCheckPattern +
                           TransformUtil.BeforePattern);
        (new SubClass()).callParent();
    }

    private void callParent() {
        super.testParent();
    }
}
