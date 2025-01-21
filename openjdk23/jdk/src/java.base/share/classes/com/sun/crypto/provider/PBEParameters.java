/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.io.*;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import sun.security.util.HexDumpEncoder;
import sun.security.util.*;


/**
 * This class implements the parameter set used with password-based
 * encryption, which is defined in PKCS#5 as follows:
 *
 * <pre>
 * PBEParameter ::=  SEQUENCE {
 *     salt   OCTET STRING SIZE(8),
 *     iterationCount   INTEGER }
 * </pre>
 *
 * @author Jan Luehe
 *
 */

public final class PBEParameters extends AlgorithmParametersSpi {

    // the salt
    private byte[] salt = null;

    // the iteration count
    private int iCount = 0;

    // the cipher parameter
    private AlgorithmParameterSpec cipherParam = null;

    protected void engineInit(AlgorithmParameterSpec paramSpec)
        throws InvalidParameterSpecException
   {
       if (!(paramSpec instanceof PBEParameterSpec)) {
           throw new InvalidParameterSpecException
               ("Inappropriate parameter specification");
       }
       this.salt = ((PBEParameterSpec)paramSpec).getSalt().clone();
       this.iCount = ((PBEParameterSpec)paramSpec).getIterationCount();
       this.cipherParam = ((PBEParameterSpec)paramSpec).getParameterSpec();
    }

    protected void engineInit(byte[] encoded)
        throws IOException
    {
        try {
            DerValue val = new DerValue(encoded);
            if (val.tag != DerValue.tag_Sequence) {
                throw new IOException("PBE parameter parsing error: "
                                      + "not a sequence");
            }
            val.data.reset();

            this.salt = val.data.getOctetString();
            this.iCount = val.data.getInteger();

            if (val.data.available() != 0) {
                throw new IOException
                    ("PBE parameter parsing error: extra data");
            }
        } catch (NumberFormatException e) {
            throw new IOException("iteration count too big");
        }
    }

    protected void engineInit(byte[] encoded, String decodingMethod)
        throws IOException
    {
        engineInit(encoded);
    }

    protected <T extends AlgorithmParameterSpec>
            T engineGetParameterSpec(Class<T> paramSpec)
        throws InvalidParameterSpecException
    {
        if (paramSpec.isAssignableFrom(PBEParameterSpec.class)) {
            return paramSpec.cast(
                new PBEParameterSpec(this.salt, this.iCount, this.cipherParam));
        } else {
            throw new InvalidParameterSpecException
                ("Inappropriate parameter specification");
        }
    }

    protected byte[] engineGetEncoded() throws IOException {
        DerOutputStream out = new DerOutputStream();
        DerOutputStream bytes = new DerOutputStream();

        bytes.putOctetString(this.salt);
        bytes.putInteger(this.iCount);

        out.write(DerValue.tag_Sequence, bytes);
        return out.toByteArray();
    }

    protected byte[] engineGetEncoded(String encodingMethod)
        throws IOException
    {
        return engineGetEncoded();
    }

    /*
     * Returns a formatted string describing the parameters.
     */
    protected String engineToString() {
        String LINE_SEP = System.lineSeparator();
        String saltString = LINE_SEP + "    salt:" + LINE_SEP + "[";
        HexDumpEncoder encoder = new HexDumpEncoder();
        saltString += encoder.encodeBuffer(salt);
        saltString += "]";

        return saltString + LINE_SEP + "    iterationCount:"
            + LINE_SEP + Debug.toHexString(BigInteger.valueOf(iCount))
            + LINE_SEP;
    }
}
