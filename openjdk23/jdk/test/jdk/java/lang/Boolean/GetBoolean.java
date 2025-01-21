/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4252308
 * @summary test Boolean.getBoolean method with empty key
 */

public class GetBoolean {
    public static void main(String[] args) {
        Boolean.getBoolean("");
        Boolean.getBoolean(null);
    }
}
