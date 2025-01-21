/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5105890
 * @summary (codegen) constant folding broken for conditional operator
 * @author Peter von der Ah\u00e9
 */

public class T5105890 {
    public static final String str1  = (false ? "YYY" : null  );
    public static final String str2  = (true  ? null  : "YYY" );
    public static void main(String[] args) {
        if (str1 != null)
            throw new RuntimeException("str1 != null");
        if (str2 != null)
            throw new RuntimeException("str2 != null");
    }
}
