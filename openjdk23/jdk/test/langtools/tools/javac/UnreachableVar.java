/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4704247
 * @summary code generator fails to assign a register to a var with an unreachable def
 * @author gafter
 *
 * @compile UnreachableVar.java
 * @run main UnreachableVar
 */

public class UnreachableVar {
    static String result = "";
    public static void main(String[] args) {
        switch (args.length) {
        case 20000:
            if (true)
                break;
            int i = 1;
        default:
            i = 2;
            result += i + " ";
        }
        result += args.getClass().getName();
        if (!result.equals("2 [Ljava.lang.String;")) throw new Error(result);
    }
}
