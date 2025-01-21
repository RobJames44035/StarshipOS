/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.small;

import org.openjdk.jmh.annotations.Param;

public abstract class KeyAgreementBench extends
    org.openjdk.bench.javax.crypto.full.KeyAgreementBench {

    public static class EC extends
        org.openjdk.bench.javax.crypto.full.KeyAgreementBench.EC {

        @Param({"EC"})
        private String kpgAlgorithm;

        @Param({"ECDH"})
        private String algorithm;

        @Param({"256"})
        private int keyLength;

    }

    public static class XDH extends
        org.openjdk.bench.javax.crypto.full.KeyAgreementBench.XDH {

        @Param({"XDH"})
        private String kpgAlgorithm;

        @Param({"XDH"})
        private String algorithm;

        @Param({"255"})
        private int keyLength;

    }
}

