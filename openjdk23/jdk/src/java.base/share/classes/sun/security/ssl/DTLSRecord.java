/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.security.ssl;

/**
 * DTLS record
 */
interface DTLSRecord extends Record {

    int    headerSize = 13;            // DTLS record header

    int    handshakeHeaderSize = 12;   // DTLS handshake header

    /*
     * The size of the header plus the max IV length
     */
    int    headerPlusMaxIVSize =      headerSize        // header
                                    + maxIVLength;      // iv

    /*
     * The maximum size that may be increased when translating plaintext to
     * ciphertext fragment.
     */
    int    maxPlaintextPlusSize =     headerSize        // header
                                    + maxIVLength       // iv
                                    + maxMacSize        // MAC or AEAD tag
                                    + maxPadding;       // block cipher padding

    /*
     * the maximum record size
     */
    int    maxRecordSize =            headerPlusMaxIVSize   // header + iv
                                    + maxDataSize           // data
                                    + maxPadding            // padding
                                    + maxMacSize;           // MAC or AEAD tag

    /*
     * Minimum record size of Certificate handshake message.
     * Client sends a certificate message containing no certificates if no
     * suitable certificate is available.  That is, the certificate_list
     * structure has a length of zero.
     *
     *   struct {
     *       ASN.1Cert certificate_list<0..2^24-1>;
     *   } Certificate;
     */
    int    minCertPlaintextSize =     headerSize            // record header
                                    + handshakeHeaderSize   // handshake header
                                    + 3;                    // cert list length
}
