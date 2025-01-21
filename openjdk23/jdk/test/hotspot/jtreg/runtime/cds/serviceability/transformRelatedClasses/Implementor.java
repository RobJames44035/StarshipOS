/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

public class Implementor implements Interface {
    public static void main(String[] args) {
        System.out.println("Implementor: entering main()");
        test();
    }

    public static void test() {
        // from interface
        (new Implementor()).printString();
        // from implementor
        System.out.println(TransformUtil.ChildCheckPattern +
                           TransformUtil.BeforePattern);
    }
}
