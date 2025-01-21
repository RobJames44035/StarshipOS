/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4715938
 * @summary     ConfigFile throws exception if module option is empty ("")
 *
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/EmptyOption.config EmptyOption
 */

import com.sun.security.auth.login.*;

public class EmptyOption {
    public static void main(String[] args) throws Exception {
        ConfigFile c = new ConfigFile();
        System.out.println("Test passed");
    }
}
