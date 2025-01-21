/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

class ClassLoadingThread extends Thread {

    private String className;
    private ClassLoader ldr;

    public ClassLoadingThread(String name, ClassLoader loader) {
        className = name;
        ldr = loader;
    }

    private boolean success = true;
    public boolean report_success() { return success; }

    public void callForName(String cls, ClassLoader ldr) {
        try {
            ThreadPrint.println("Starting forName thread ...");
            // Initiate class loading using specified type
            Class<?> a = Class.forName(cls, true, ldr);
            Object obj = a.getConstructor().newInstance();
        } catch (Throwable e) {
            ThreadPrint.println("Exception is caught: " + e);
            e.printStackTrace();
            success = false;
        }
    }

    public void run() {
       callForName(className, ldr);
    }
}
