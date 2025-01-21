/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary This is an interop compatibility test on basic handshaking and
 *     client authentication against all SSL/TLS protocols.
 *
 * @library /test/lib
 *          ../TLSCommon
 *          ../TLSCommon/interop
 * @compile -source 1.8 -target 1.8
 *          JdkInfoUtils.java
 *          ../TLSCommon/interop/JdkProcServer.java
 *          ../TLSCommon/interop/JdkProcClient.java
 * @run main/manual BasicConnectTest true
 * @run main/manual BasicConnectTest false
 */

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BasicConnectTest extends BaseInteropTest<UseCase> {

    private JdkInfo serverJdkInfo;
    private JdkInfo clientJdkInfo;

    public BasicConnectTest(JdkInfo serverJdkInfo, JdkInfo clientJdkInfo) {
        super(new Jdk(serverJdkInfo.version, serverJdkInfo.javaPath),
              new Jdk(clientJdkInfo.version, clientJdkInfo.javaPath));

        this.serverJdkInfo = serverJdkInfo;
        this.clientJdkInfo = clientJdkInfo;
    }

    @Override
    protected List<TestCase<UseCase>> getTestCases() {
        List<TestCase<UseCase>> useCases = new ArrayList<>();
        for (Protocol protocol : new Protocol[] {
                Protocol.SSLV3,
                Protocol.TLSV1,
                Protocol.TLSV1_1,
                Protocol.TLSV1_2,
                Protocol.TLSV1_3 }) {
            for (CipherSuite cipherSuite : Utilities.ALL_CIPHER_SUITES) {
                if (!cipherSuite.supportedByProtocol(protocol)) {
                    continue;
                }

                Cert cert = Utils.getCert(cipherSuite.keyExAlgorithm);
                CertTuple certTuple = new CertTuple(cert, cert);

                UseCase serverCase = UseCase.newInstance()
                        .setCertTuple(certTuple)
                        .setClientAuth(true);

                UseCase clientCase = UseCase.newInstance()
                        .setCertTuple(certTuple)
                        .setProtocols(protocol)
                        .setCipherSuites(cipherSuite);

                useCases.add(new TestCase<UseCase>(serverCase, clientCase));
            }
        }
        return useCases;
    }

    @Override
    protected boolean ignoreTestCase(TestCase<UseCase> testCase) {
        Protocol protocol = testCase.clientCase.getProtocol();
        CipherSuite cipherSuite = testCase.clientCase.getCipherSuite();
        return !supportsProtocol(protocol)
                || !supportsCipherSuite(cipherSuite)
                // DHE_DSS cipher suites cannot work with pre-TLSv1.2 protocols,
                // see JDK-8242928 for more details.
                || (protocol.id < Protocol.TLSV1_2.id
                        && (cipherSuite.keyExAlgorithm == KeyExAlgorithm.DHE_DSS
                                || cipherSuite.keyExAlgorithm == KeyExAlgorithm.DHE_DSS_EXPORT));
    }

    private boolean supportsProtocol(Protocol protocol) {
        return serverJdkInfo.enablesProtocol(protocol)
                && clientJdkInfo.supportsProtocol(protocol);
    }

    private boolean supportsCipherSuite(CipherSuite cipherSuite) {
        return serverJdkInfo.enablesCipherSuite(cipherSuite)
                && clientJdkInfo.supportsCipherSuite(cipherSuite);
    }

    @Override
    protected AbstractServer.Builder createServerBuilder(UseCase useCase)
            throws Exception {
        return serverJdkInfo == JdkInfo.DEFAULT
               ? createJdkServerBuilder(useCase)
               : createAltJdkServerBuilder(useCase);
    }

    private JdkServer.Builder createJdkServerBuilder(UseCase useCase) {
        JdkServer.Builder builder = new JdkServer.Builder();
        builder.setCertTuple(useCase.getCertTuple());
        builder.setProtocols(useCase.getProtocols());
        builder.setCipherSuites(useCase.getCipherSuites());
        builder.setClientAuth(useCase.isClientAuth());
        return builder;
    }

    private JdkProcServer.Builder createAltJdkServerBuilder(UseCase useCase) {
        JdkProcServer.Builder builder = new JdkProcServer.Builder();
        builder.setJdk((Jdk) serverProduct);
        builder.setSecPropsFile(Paths.get(Utils.SEC_PROPS_FILE));
        builder.setCertTuple(useCase.getCertTuple());
        builder.setProtocols(useCase.getProtocols());
        builder.setCipherSuites(useCase.getCipherSuites());
        builder.setClientAuth(useCase.isClientAuth());
        return builder;
    }

    @Override
    protected AbstractClient.Builder createClientBuilder(UseCase useCase)
            throws Exception {
        return serverJdkInfo == JdkInfo.DEFAULT
               ? createJdkClientBuilder(useCase)
               : createAltJdkClientBuilder(useCase);
    }

    private JdkClient.Builder createJdkClientBuilder(UseCase useCase) {
        JdkClient.Builder builder = new JdkClient.Builder();
        builder.setCertTuple(useCase.getCertTuple());
        builder.setProtocols(useCase.getProtocols());
        builder.setCipherSuites(useCase.getCipherSuites());
        return builder;
    }

    private JdkProcClient.Builder createAltJdkClientBuilder(UseCase useCase) {
        JdkProcClient.Builder builder = new JdkProcClient.Builder();
        builder.setJdk((Jdk) clientProduct);
        builder.setSecPropsFile(Paths.get(Utils.SEC_PROPS_FILE));
        builder.setCertTuple(useCase.getCertTuple());
        builder.setProtocols(useCase.getProtocols());
        builder.setCipherSuites(useCase.getCipherSuites());
        return builder;
    }

    public static void main(String[] args) throws Exception {
        Boolean defaultJdkAsServer = Boolean.valueOf(args[0]);

        System.setProperty("java.security.properties", Utils.SEC_PROPS_FILE);

        Set<JdkInfo> jdkInfos = Utils.jdkInfoList();
        for (JdkInfo jdkInfo : jdkInfos) {
            BasicConnectTest test = new BasicConnectTest(
                    defaultJdkAsServer ? JdkInfo.DEFAULT : jdkInfo,
                    defaultJdkAsServer ? jdkInfo : JdkInfo.DEFAULT);
            test.execute();
        }
    }
}
