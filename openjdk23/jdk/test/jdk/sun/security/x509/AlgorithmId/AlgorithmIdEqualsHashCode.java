/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @author Gary Ellison
 * @bug 4170635 8258247
 * @summary Verify equals()/hashCode() contract honored
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 */

import java.io.*;
import java.security.AlgorithmParameters;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

import sun.security.util.DerValue;
import sun.security.x509.*;

public class AlgorithmIdEqualsHashCode {

    public static void main(String[] args) throws Exception {

        AlgorithmId ai1 = AlgorithmId.get("DH");
        AlgorithmId ai2 = AlgorithmId.get("DH");
        AlgorithmId ai3 = AlgorithmId.get("DH");

        // supposedly transitivity is broken
        // System.out.println(ai1.equals(ai2));
        // System.out.println(ai2.equals(ai3));
        // System.out.println(ai1.equals(ai3));

        if ( (ai1.equals(ai2)) == (ai2.equals(ai3)) == (ai1.equals(ai3)))
            System.out.println("PASSED transitivity test");
        else
            throw new Exception("Failed equals transitivity() contract");

        if ( (ai1.equals(ai2)) == (ai1.hashCode()==ai2.hashCode()) )
            System.out.println("PASSED equals()/hashCode() test");
        else
            throw new Exception("Failed equals()/hashCode() contract");

        // check that AlgorithmIds with same name but different params
        // are not equal
        AlgorithmParameters algParams1 =
            AlgorithmParameters.getInstance("RSASSA-PSS");
        AlgorithmParameters algParams2 =
            AlgorithmParameters.getInstance("RSASSA-PSS");
        algParams1.init(new PSSParameterSpec("SHA-1", "MGF1",
            MGF1ParameterSpec.SHA1, 20, PSSParameterSpec.TRAILER_FIELD_BC));
        algParams2.init(new PSSParameterSpec("SHA-256", "MGF1",
            MGF1ParameterSpec.SHA1, 20, PSSParameterSpec.TRAILER_FIELD_BC));
        ai1 = new AlgorithmId(AlgorithmId.RSASSA_PSS_oid, algParams1);
        ai2 = new AlgorithmId(AlgorithmId.RSASSA_PSS_oid, algParams2);
        if (ai1.equals(ai2)) {
            throw new Exception("Failed equals() contract");
        } else {
            System.out.println("PASSED equals() test");
        }

        // check that two AlgorithmIds created with the same parameters but
        // one with DER encoded parameters and the other with
        // AlgorithmParameters are equal
        byte[] encoded = ai1.encode();
        ai3 = AlgorithmId.parse(new DerValue(encoded));
        if (!ai1.equals(ai3)) {
            throw new Exception("Failed equals() contract");
        } else {
            System.out.println("PASSED equals() test");
        }

        // check that two AlgorithmIds created with different parameters but
        // one with DER encoded parameters and the other with
        // AlgorithmParameters are not equal
        if (ai2.equals(ai3)) {
            throw new Exception("Failed equals() contract");
        } else {
            System.out.println("PASSED equals() test");
        }
    }
}
