/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package pkg1;

public class BaseWithProtectedMethod {
    protected void protectedMethod(String s) {
        Thread.dumpStack();
        System.out.println("Called BaseWithProtectedMethod::protectedMethod(" + s + ")");
    }
}
