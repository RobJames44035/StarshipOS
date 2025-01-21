/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4319910
 * @summary Verify that exceptions are thrown as expected.
 */

public class Exceptions {
    void m0() {}
    public void m1() {}
    private void m2() {}
    protected void m4() {}

    private static final String [] npe = {null};
    private static final String [] nsme = {"m6"};
    private static final String [] pass = {"m0", "m1", "m2", "m4"};

    private void test(String s, Class ex) {
        Throwable t = null;
        try {
            getClass().getDeclaredMethod(s, new Class[] {});
        } catch (Throwable x) {
            if (ex.isAssignableFrom(x.getClass()))
                t = x;
        }
        if ((t == null) && (ex != null))
            throw new RuntimeException("expected " + ex.getName()
                                       + " for " + s);
        else
            System.out.println(s + " OK");
    }

    public static void main(String [] args) {
        Exceptions e = new Exceptions();
        for (int i = 0; i < npe.length; i++)
            e.test(npe[i], NullPointerException.class);
        for (int i = 0; i < nsme.length; i++)
            e.test(nsme[i], NoSuchMethodException.class);
        for (int i = 0; i < pass.length; i++)
            e.test(pass[i], null);
    }
}
