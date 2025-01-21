/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public class MissingDependent {
    public static void main(String args[]) throws Exception {
        try {
            Object obj = new StrConcatApp();
        } catch (Throwable e) {
            String cause = e.getCause().toString();
            if (cause.equals("java.lang.ClassNotFoundException: StrConcatApp")) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }
}
