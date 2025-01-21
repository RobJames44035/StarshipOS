/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025868
 * @summary Lambda class can be generated with duplicate interfaces (ClassFormatError)
 * @run main DupIntf
 */

interface SAM<P1> {
    P1 m();
}

interface Other { }

public class DupIntf {
    public static void main(String argv[]) {
        SAM<?> sam = (SAM<?> & Other) () -> "Pass.";
        System.out.println(sam.m());
    }
}
