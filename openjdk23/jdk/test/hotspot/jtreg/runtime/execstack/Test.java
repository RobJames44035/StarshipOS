/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

class Test {

    static boolean loadLib(String libName){
        try {
            System.loadLibrary(libName);
            System.out.println("Loaded library "+ libName + ".");
            return true;
        } catch (SecurityException e) {
            System.out.println("loadLibrary(\"" + libName + "\") throws: " + e + "\n");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("loadLibrary(\"" + libName + "\") throws: " + e + "\n");
        }
        return false;
    }

    public static int counter = 1;

    static int Runner() {
        counter = counter * -1;
        int i = counter;
        if (counter < 2) counter += Runner();
        return i;
    }

    public static int run() {
        try{
            Runner();
        } catch (StackOverflowError e) {
            System.out.println("Caught stack overflow error.");
            return 0;
        } catch (OutOfMemoryError e) {
            return 0;
        }
        return 2;
    }

    public static void main(String argv[]) {
        loadLib(argv[0]);
        System.exit(run());
    }
}
