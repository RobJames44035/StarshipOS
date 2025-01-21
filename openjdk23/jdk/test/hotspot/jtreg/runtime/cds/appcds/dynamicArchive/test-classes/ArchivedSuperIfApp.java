/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package pkg;

public class ArchivedSuperIfApp {

    public static void main(String ... args) throws Exception {
        Class.forName("pkg.Bar");
        System.out.println(Bar.class.getName());
        if (args.length > 0 && args[0].equals("Baz")) {
            Class.forName("pkg.Baz");

            Baz baz = new Baz();
            baz.foo();
            System.out.println(Baz.class.getName());
        }
    }
}
