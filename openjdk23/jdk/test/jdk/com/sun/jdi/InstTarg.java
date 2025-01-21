/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

public class InstTarg {

    static InstTarg first = new InstTarg();
    static InstTarg second = new InstTarg();
    static InstTarg third = new InstTarg();

    public static void main(String args[]) {
        start();
        // Sleep before exiting to allow disconnect efforts done on the JDI side to complete.
        // Note that not sleeping long enough is for the most part harmless, but might render
        // the testing insufficient because the debuggee will quickly exit naturally
        // once the debuggee does the vm.resume(), rather than waiting for disconnect
        // efforts to complete first.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void start() {
        first.go();
        second.go();
        third.go();
    }

    void go() {
        one();
        two();
        three();
    }

    void one() {
    }

    void two() {
    }

    void three() {
    }
}
