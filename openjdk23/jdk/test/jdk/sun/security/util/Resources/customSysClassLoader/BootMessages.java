/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8168075
 * @summary Ensure that security messages can be formatted during system class
 *   loader initialization.
 * @build CustomClassLoader
 * @run main/othervm -Djava.system.class.loader=CustomClassLoader BootMessages
 */

public class BootMessages {

    public static void main(String[] args) throws Exception {

    }
}
