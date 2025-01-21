/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T8255968_5 {
    T8255968_5_Test c = new T8255968_5_Test(0);
}

class T8255968_5_Test {
    private T8255968_5_Test(int x) {}  // This method is not at the end.
    T8255968_5_Test(String x) {}  // If this method is private, compiler will output the same error message.
    private T8255968_5_Test(int[] x) {}
}
