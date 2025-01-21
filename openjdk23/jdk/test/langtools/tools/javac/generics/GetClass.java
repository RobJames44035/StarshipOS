/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class GetClass {
    public static void meth() {
        Class<? extends Class<GetClass>> x = GetClass.class.getClass();
    }
}
