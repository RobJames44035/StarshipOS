/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6788531
 * @summary Tests public method lookup problem in Statement
 * @author Sergey Malenkov
 */

import java.beans.Statement;

public class Test6788531 {
    public static void main(String[] args) throws Exception {
        new Statement(new Private(), "run", null).execute();
        new Statement(new PrivateGeneric(), "run", new Object[] {"generic"}).execute();
    }

    public static class Public {
        public void run() {
            throw new Error("method is overridden");
        }
    }

    static class Private extends Public {
        public void run() {
            System.out.println("default");
        }
    }

    public static class PublicGeneric<T> {
        public void run(T object) {
            throw new Error("method is overridden");
        }
    }

    static class PrivateGeneric extends PublicGeneric<String> {
        public void run(String string) {
            System.out.println(string);
        }
    }
}
