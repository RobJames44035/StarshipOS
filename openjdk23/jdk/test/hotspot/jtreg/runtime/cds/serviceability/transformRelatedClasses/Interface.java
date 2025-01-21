/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

public interface Interface {
    public static final String stringToBeTransformed =
        TransformUtil.ParentCheckPattern + TransformUtil.BeforePattern;

    default void printString() {
        System.out.println(stringToBeTransformed);
    }
}
