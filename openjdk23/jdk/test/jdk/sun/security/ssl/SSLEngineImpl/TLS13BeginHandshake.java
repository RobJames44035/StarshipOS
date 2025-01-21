/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Test SSLEngine.begineHandshake() triggers a KeyUpdate handshake
 * in TLSv1.3
 * @library /javax/net/ssl/templates
 * @run main/othervm TLS13BeginHandshake
 */

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;

public class TLS13BeginHandshake extends SSLEngineTemplate {
    SSLEngineResult clientResult, serverResult;

    public static void main(String args[]) throws Exception{
        new TLS13BeginHandshake().runDemo();
    }

    public TLS13BeginHandshake() throws Exception {
        super();
    }

    private void runDemo() throws Exception {
        int done = 0;

        while (!isEngineClosed(clientEngine) || !isEngineClosed(serverEngine)) {

            System.out.println("================");
            clientResult = clientEngine.wrap(clientOut, cTOs);
            System.out.println("client wrap: " + clientResult);
            runDelegatedTasks(clientEngine);
            serverResult = serverEngine.wrap(serverOut, sTOc);
            System.out.println("server wrap: " + serverResult);
            runDelegatedTasks(serverEngine);

            cTOs.flip();
            sTOc.flip();

            System.out.println("----");
            clientResult = clientEngine.unwrap(sTOc, clientIn);
            System.out.println("client unwrap: " + clientResult);
            if (clientResult.getStatus() == SSLEngineResult.Status.CLOSED) {
                break;
            }
            runDelegatedTasks(clientEngine);
            serverResult = serverEngine.unwrap(cTOs, serverIn);
            System.out.println("server unwrap: " + serverResult);
            runDelegatedTasks(serverEngine);

            cTOs.compact();
            sTOc.compact();

            //System.err.println("so limit="+serverOut.limit()+" so pos="+serverOut.position());
            //System.out.println("bf ctos limit="+cTOs.limit()+" pos="+cTOs.position()+" cap="+cTOs.capacity());
            //System.out.println("bf stoc limit="+sTOc.limit()+" pos="+sTOc.position()+" cap="+sTOc.capacity());
            if (done < 2  && (clientOut.limit() == serverIn.position()) &&
                    (serverOut.limit() == clientIn.position())) {

                if (done == 0) {
                    checkTransfer(serverOut, clientIn);
                    checkTransfer(clientOut, serverIn);
                    clientEngine.beginHandshake();
                    done++;
                    continue;
                }

                checkTransfer(serverOut, clientIn);
                checkTransfer(clientOut, serverIn);
                System.out.println("\tClosing...");
                clientEngine.closeOutbound();
                serverEngine.closeOutbound();
                done++;
                continue;
            }
        }
    }

    private static boolean isEngineClosed(SSLEngine engine) {
        if (engine.isInboundDone())
            System.out.println("inbound closed");
        if (engine.isOutboundDone())
            System.out.println("outbound closed");
        return (engine.isOutboundDone() && engine.isInboundDone());
    }

    @Override
    protected ContextParameters getServerContextParameters() {
        return new ContextParameters("TLSv1.3", "PKIX", "NewSunX509");
    }

    @Override
    protected ContextParameters getClientContextParameters() {
        return new ContextParameters("TLSv1.3", "PKIX", "NewSunX509");
    }
}
