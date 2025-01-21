/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6851973 8194486 8279520
 * @summary ignore incoming channel binding if acceptor does not set one
 * @library /test/lib
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts IgnoreChannelBinding
 */

import java.net.InetAddress;
import org.ietf.jgss.ChannelBinding;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;
import sun.security.jgss.GSSUtil;

public class IgnoreChannelBinding {

    public static void main(String[] args)
            throws Exception {

        new OneKDC(null).writeJAASConf();
        test(GSSUtil.GSS_KRB5_MECH_OID);
        test(GSSUtil.GSS_SPNEGO_MECH_OID);
    }

    static void test(Oid mech) throws Exception {

        Context c = Context.fromJAAS("client");
        Context s = Context.fromJAAS("server");

        // All silent
        c.startAsClient(OneKDC.SERVER, mech);
        s.startAsServer(mech);
        Context.handshake(c, s);

        // Initiator req, acceptor ignore
        c.startAsClient(OneKDC.SERVER, mech);
        c.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[0]
                ));
        s.startAsServer(mech);
        Context.handshake(c, s);

        // Both req, and match
        c.startAsClient(OneKDC.SERVER, mech);
        c.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[0]
                ));
        s.startAsServer(mech);
        s.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[0]
                ));
        Context.handshake(c, s);

        // Both req, NOT match
        c.startAsClient(OneKDC.SERVER, mech);
        c.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[0]
                ));
        s.startAsServer(mech);
        s.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[1]     // 0 -> 1
                ));
        try {
            Context.handshake(c, s);
            throw new Exception("Acceptor should reject initiator");
        } catch (GSSException ge) {
            // Expected bahavior
        }

        // Acceptor req, reject
        c.startAsClient(OneKDC.SERVER, mech);
        s.startAsServer(mech);
        s.x().setChannelBinding(new ChannelBinding(
                InetAddress.getByName("client.rabbit.hole"),
                InetAddress.getByName("host.rabbit.hole"),
                new byte[0]
                ));
        try {
            Context.handshake(c, s);
            throw new Exception("Acceptor should reject initiator");
        } catch (GSSException ge) {
            // Expected bahavior
            if (ge.getMajor() != GSSException.BAD_BINDINGS) {
                throw ge;
            }
        }
    }
}
