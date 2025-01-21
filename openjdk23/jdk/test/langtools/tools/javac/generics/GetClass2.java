/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4982096 5004321
 * @summary the type of x.getClass() is Class<? extends |X|>
 * @author seligman
 *
 * @compile  GetClass2.java
 * @run main GetClass2
 */

public class GetClass2 {
    public static void main(String[] args) {
        Class<? extends Class> x = GetClass2.class.getClass();
    }
}
