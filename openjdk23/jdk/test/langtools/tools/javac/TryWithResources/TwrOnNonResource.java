/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class TwrOnNonResource {
    public static void meth() {
        try(TwrOnNonResource aonr = new TwrOnNonResource()) {
            System.out.println(aonr.toString());
        }
        try(TwrOnNonResource aonr = new TwrOnNonResource()) {
            System.out.println(aonr.toString());
        } finally {;}
        try(TwrOnNonResource aonr = new TwrOnNonResource()) {
            System.out.println(aonr.toString());
        } catch (Exception e) {;}
    }

    /*
     * A close method, but the class is <em>not</em> Closeable or
     * AutoCloseable.
     */
    public void close() {
        throw new AssertionError("I'm not Closable!");
    }
}
