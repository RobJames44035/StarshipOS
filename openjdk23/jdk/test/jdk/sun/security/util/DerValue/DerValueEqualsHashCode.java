/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @author Gary Ellison
 * @bug 4170635
 * @summary Verify equals()/hashCode() contract honored
 * @modules java.base/sun.security.util
 *          java.base/sun.security.x509
 */

import java.io.*;
import sun.security.util.*;
import sun.security.x509.*;


public class DerValueEqualsHashCode {

    public static void main(String[] args) throws Exception {

        String name = "CN=user";
        X500Name dn = new X500Name(name);

        DerOutputStream deros;
        byte[] ba;
        //
        // get busy
        deros = new DerOutputStream();
        dn.encode(deros);
        ba = deros.toByteArray();

        DerValue dv1 = new DerValue(ba);
        DerValue dv2 = new DerValue(ba);

        if ( (dv1.equals(dv2)) == (dv1.hashCode()==dv2.hashCode()) )
            System.out.println("PASSED");
        else
            throw new Exception("FAILED equals()/hashCode() contract");

    }

}
