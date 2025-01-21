/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4999689
 * @summary Compiler incorrectly create Integer in "Character c = 95"
 * @author gafter
 */

public class Boxing4 {

    public static void main(String[] args) {
        Character ch = 95;
        ch++;
        System.out.println(ch + 0);
    }

}
