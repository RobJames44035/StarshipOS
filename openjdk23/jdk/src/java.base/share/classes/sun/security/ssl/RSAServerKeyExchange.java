/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.CryptoPrimitive;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Locale;
import sun.security.ssl.RSAKeyExchange.EphemeralRSACredentials;
import sun.security.ssl.RSAKeyExchange.EphemeralRSAPossession;
import sun.security.ssl.SSLHandshake.HandshakeMessage;
import sun.security.ssl.X509Authentication.X509Credentials;
import sun.security.ssl.X509Authentication.X509Possession;
import sun.security.util.HexDumpEncoder;

/**
 * Pack of the ServerKeyExchange handshake message.
 */
final class RSAServerKeyExchange {
    static final SSLConsumer rsaHandshakeConsumer =
        new RSAServerKeyExchangeConsumer();
    static final HandshakeProducer rsaHandshakeProducer =
        new RSAServerKeyExchangeProducer();

    /**
     * The ephemeral RSA ServerKeyExchange handshake message.
     *
     * Used for RSA_EXPORT, SSL 3.0 and TLS 1.0 only.
     */
    private static final
            class RSAServerKeyExchangeMessage extends HandshakeMessage {
        // public key encapsulated in this message
        private final byte[] modulus;     // 1 to 2^16 - 1 bytes
        private final byte[] exponent;    // 1 to 2^16 - 1 bytes

        // signature bytes, none-null as no anonymous RSA key exchange.
        private final byte[] paramsSignature;

        private RSAServerKeyExchangeMessage(HandshakeContext handshakeContext,
                X509Possession x509Possession,
                EphemeralRSAPossession rsaPossession) throws IOException {
            super(handshakeContext);

            // This happens in server side only.
            ServerHandshakeContext shc =
                    (ServerHandshakeContext)handshakeContext;

            RSAPublicKey publicKey = rsaPossession.popPublicKey;
            RSAPublicKeySpec spec = JsseJce.getRSAPublicKeySpec(publicKey);
            this.modulus = Utilities.toByteArray(spec.getModulus());
            this.exponent = Utilities.toByteArray(spec.getPublicExponent());
            byte[] signature;
            try {
                Signature signer = RSASignature.getInstance();
                signer.initSign(x509Possession.popPrivateKey,
                        shc.sslContext.getSecureRandom());
                updateSignature(signer,
                          shc.clientHelloRandom.randomBytes,
                          shc.serverHelloRandom.randomBytes);
                signature = signer.sign();
            } catch (NoSuchAlgorithmException |
                    InvalidKeyException | SignatureException ex) {
                throw shc.conContext.fatal(Alert.INTERNAL_ERROR,
                        "Failed to sign ephemeral RSA parameters", ex);
            }

            this.paramsSignature = signature;
        }

        RSAServerKeyExchangeMessage(HandshakeContext handshakeContext,
                ByteBuffer m) throws IOException {
            super(handshakeContext);

            // This happens in client side only.
            ClientHandshakeContext chc =
                    (ClientHandshakeContext)handshakeContext;

            this.modulus = Record.getBytes16(m);
            this.exponent = Record.getBytes16(m);
            this.paramsSignature = Record.getBytes16(m);

            X509Credentials x509Credentials = null;
            for (SSLCredentials cd : chc.handshakeCredentials) {
                if (cd instanceof X509Credentials) {
                    x509Credentials = (X509Credentials)cd;
                    break;
                }
            }

            if (x509Credentials == null) {
                throw chc.conContext.fatal(Alert.ILLEGAL_PARAMETER,
                    "No RSA credentials negotiated for server key exchange");
            }

            try {
                Signature signer = RSASignature.getInstance();
                signer.initVerify(x509Credentials.popPublicKey);
                updateSignature(signer,
                          chc.clientHelloRandom.randomBytes,
                          chc.serverHelloRandom.randomBytes);
                if (!signer.verify(paramsSignature)) {
                    throw chc.conContext.fatal(Alert.HANDSHAKE_FAILURE,
                        "Invalid signature of RSA ServerKeyExchange message");
                }
            } catch (NoSuchAlgorithmException |
                    InvalidKeyException | SignatureException ex) {
                throw chc.conContext.fatal(Alert.INTERNAL_ERROR,
                    "Failed to sign ephemeral RSA parameters", ex);
            }
        }

        @Override
        SSLHandshake handshakeType() {
            return SSLHandshake.SERVER_KEY_EXCHANGE;
        }

        @Override
        int messageLength() {
            return 6 + modulus.length + exponent.length
                   + paramsSignature.length;
        }

        @Override
        void send(HandshakeOutStream hos) throws IOException {
            hos.putBytes16(modulus);
            hos.putBytes16(exponent);
            hos.putBytes16(paramsSignature);
        }

        @Override
        public String toString() {
            MessageFormat messageFormat = new MessageFormat(
                    """
                            "RSA ServerKeyExchange": '{'
                              "parameters": '{'
                                "rsa_modulus": '{'
                            {0}
                                '}',
                                "rsa_exponent": '{'
                            {1}
                                '}'
                              '}',
                              "digital signature":  '{'
                                "signature": '{'
                            {2}
                                '}',
                              '}'
                            '}'""",
                Locale.ENGLISH);

            HexDumpEncoder hexEncoder = new HexDumpEncoder();
            Object[] messageFields = {
                Utilities.indent(
                        hexEncoder.encodeBuffer(modulus), "      "),
                Utilities.indent(
                        hexEncoder.encodeBuffer(exponent), "      "),
                Utilities.indent(
                        hexEncoder.encodeBuffer(paramsSignature), "      ")
            };
            return messageFormat.format(messageFields);
        }

        /*
         * Hash the nonces and the ephemeral RSA public key.
         */
        private void updateSignature(Signature signature,
                byte[] clntNonce, byte[] svrNonce) throws SignatureException {
            signature.update(clntNonce);
            signature.update(svrNonce);

            signature.update((byte)(modulus.length >> 8));
            signature.update((byte)(modulus.length & 0x0ff));
            signature.update(modulus);

            signature.update((byte)(exponent.length >> 8));
            signature.update((byte)(exponent.length & 0x0ff));
            signature.update(exponent);
        }
    }

    /**
     * The RSA "ServerKeyExchange" handshake message producer.
     */
    private static final
            class RSAServerKeyExchangeProducer implements HandshakeProducer {
        // Prevent instantiation of this class.
        private RSAServerKeyExchangeProducer() {
            // blank
        }

        @Override
        public byte[] produce(ConnectionContext context,
                HandshakeMessage message) throws IOException {
            // The producing happens in server side only.
            ServerHandshakeContext shc = (ServerHandshakeContext)context;

            EphemeralRSAPossession rsaPossession = null;
            X509Possession x509Possession = null;
            for (SSLPossession possession : shc.handshakePossessions) {
                if (possession instanceof EphemeralRSAPossession) {
                    rsaPossession = (EphemeralRSAPossession)possession;
                    if (x509Possession != null) {
                        break;
                    }
                } else if (possession instanceof X509Possession) {
                    x509Possession = (X509Possession)possession;
                    if (rsaPossession != null) {
                        break;
                    }
                }
            }

            if (rsaPossession == null) {
                // The X.509 certificate itself should be used for RSA_EXPORT
                // key exchange.  The ServerKeyExchange handshake message is
                // not needed.
                return null;
            } else if (x509Possession == null) {
                // unlikely
                throw shc.conContext.fatal(Alert.ILLEGAL_PARAMETER,
                    "No RSA certificate negotiated for server key exchange");
            } else if (!"RSA".equals(
                    x509Possession.popPrivateKey.getAlgorithm())) {
                // unlikely
                throw shc.conContext.fatal(Alert.ILLEGAL_PARAMETER,
                        "No X.509 possession can be used for " +
                        "ephemeral RSA ServerKeyExchange");
            }

            RSAServerKeyExchangeMessage skem =
                    new RSAServerKeyExchangeMessage(
                            shc, x509Possession, rsaPossession);
            if (SSLLogger.isOn && SSLLogger.isOn("ssl,handshake")) {
                SSLLogger.fine(
                    "Produced RSA ServerKeyExchange handshake message", skem);
            }

            // Output the handshake message.
            skem.write(shc.handshakeOutput);
            shc.handshakeOutput.flush();

            // The handshake message has been delivered.
            return null;
        }
    }

    /**
     * The RSA "ServerKeyExchange" handshake message consumer.
     */
    private static final
            class RSAServerKeyExchangeConsumer implements SSLConsumer {
        // Prevent instantiation of this class.
        private RSAServerKeyExchangeConsumer() {
            // blank
        }

        @Override
        public void consume(ConnectionContext context,
                ByteBuffer message) throws IOException {
            // The consuming happens in client side only.
            ClientHandshakeContext chc = (ClientHandshakeContext)context;

            RSAServerKeyExchangeMessage skem =
                    new RSAServerKeyExchangeMessage(chc, message);
            if (SSLLogger.isOn && SSLLogger.isOn("ssl,handshake")) {
                SSLLogger.fine(
                    "Consuming RSA ServerKeyExchange handshake message", skem);
            }

            //
            // validate
            //
            // check constraints of RSA PublicKey
            RSAPublicKey publicKey;
            try {
                KeyFactory kf = KeyFactory.getInstance("RSA");
                RSAPublicKeySpec spec = new RSAPublicKeySpec(
                    new BigInteger(1, skem.modulus),
                    new BigInteger(1, skem.exponent));
                publicKey = (RSAPublicKey)kf.generatePublic(spec);
            } catch (GeneralSecurityException gse) {
                throw chc.conContext.fatal(Alert.INSUFFICIENT_SECURITY,
                        "Could not generate RSAPublicKey", gse);
            }

            if (!chc.algorithmConstraints.permits(
                    EnumSet.of(CryptoPrimitive.KEY_AGREEMENT), publicKey)) {
                throw chc.conContext.fatal(Alert.INSUFFICIENT_SECURITY,
                        "RSA ServerKeyExchange does not comply to " +
                        "algorithm constraints");
            }

            //
            // update
            //
            chc.handshakeCredentials.add(
                    new EphemeralRSACredentials(publicKey));

            //
            // produce
            //
            // Need no new handshake message producers here.
        }
    }
}

