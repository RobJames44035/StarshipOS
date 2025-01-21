/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T8255968_11 {
    T8255968_11_TestMethodReference c = T8255968_11_Test::new;
}

interface T8255968_11_TestMethodReference {
    T8255968_11_Test create(int x);
}

class T8255968_11_Test {
    T8255968_11_Test(String x) {}  // If this method is private, compiler will output the same error message.
}
