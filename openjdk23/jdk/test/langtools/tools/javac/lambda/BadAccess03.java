/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class BadAccess03 {
    void test() {
        int k = 0;
        int n = 2; //effectively final variable
        Runnable r = ()-> { k = n; }; //error
    }
}
