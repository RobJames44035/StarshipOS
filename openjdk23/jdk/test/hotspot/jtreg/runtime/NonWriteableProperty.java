/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243936
 * @summary Ensure non-writeable system properties are not writeable
 *
 * @run main/othervm -Djava.vm.name=Unexpected NonWriteableProperty java.vm.name Unexpected
 */

public class NonWriteableProperty {
    public static void main(String[] args) {
        if (System.getProperty(args[0]).equals(args[1])) {
            throw new RuntimeException("Non-writeable system property " +
                                       args[0] + " was rewritten");
        }
    }
}
