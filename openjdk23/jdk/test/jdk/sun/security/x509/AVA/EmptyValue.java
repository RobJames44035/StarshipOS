/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4721433
 * @summary AVA throws StringIndexOutOfBoundsException for empty values
 */

import javax.security.auth.x500.*;

public class EmptyValue {
    public static void main(String[] args) throws Exception {
        X500Principal p = new X500Principal("cn=");
        System.out.println(p);
        System.out.println("Test Passed");
    }
}
