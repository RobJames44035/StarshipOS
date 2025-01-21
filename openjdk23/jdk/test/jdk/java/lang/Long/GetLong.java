/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4252322
 * @summary test Long.getLong method with empty key
 */

public class GetLong {
    public static void main(String[] args) throws Exception {
        Long.getLong("", 1);
        Long.getLong(null, 1);
    }
}
