/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4252315
 * @summary test Integer.getInteger method with empty key
 */

public class GetInteger {
    public static void main(String[] args) throws Exception {
        Integer.getInteger("", 1);
        Integer.getInteger(null, 1);
    }
}
