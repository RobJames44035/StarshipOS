/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */


// class to print thread-annotated output
class ThreadPrint {
    public static void println(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
        System.out.flush();
    }
}
