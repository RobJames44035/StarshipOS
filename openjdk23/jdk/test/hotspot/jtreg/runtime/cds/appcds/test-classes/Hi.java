/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

public class Hi extends Greet {
    public static void main(String args[]) {
        Greet g = new Greet();
        MyClass.doit(g.Greeting());
    }
    public static class MyClass {
        public static void doit(String greeting) {
            System.out.println("Hi" + greeting);
        }
    }
}
