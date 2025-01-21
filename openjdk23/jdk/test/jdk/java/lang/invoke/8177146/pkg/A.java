/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package pkg;

public class A {
    protected String m1() {
        return this.getClass().getSimpleName();
    }

    public String m2() {
        return this.getClass().getSimpleName();
    }

    protected String m3(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args)
            sb.append(s);
        return this.getClass().getSimpleName() + sb.toString();
    }
}
