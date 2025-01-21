/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/* @test
 * @bug 8059309
 * @summary Test that querrying the mac address of the loopback interface
 *          returns null and doesn't throw a SocketException.
 * @library /test/lib
 * @run testng/othervm NullMacAddress
 * @run testng/othervm -Djava.net.preferIPv6Addresses=true NullMacAddress
 * @run testng/othervm -Djava.net.preferIPv4Stack=true NullMacAddress
 */

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Locale;

import jdk.test.lib.net.IPSupport;

public class NullMacAddress {

    @BeforeTest
    void setup() {
        IPSupport.throwSkippedExceptionIfNonOperational();
    }

    @Test
    public void testNetworkInterfaces() throws SocketException {
        NetworkInterface.networkInterfaces().forEach(this::testMacAddress);
    }

    private void testMacAddress(NetworkInterface ni) {
        try {
            var name = ni.getDisplayName();
            System.out.println("Testing: " + name);
            var loopback = ni.isLoopback();
            var macAddress = ni.getHardwareAddress();
            var hdr = macAddress == null ? "null"
                    : "0x" + new BigInteger(1, macAddress)
                    .toString(16).toUpperCase(Locale.ROOT);
            System.out.println("   MAC address: " + hdr + (loopback ? " (loopback)" : ""));
            if (loopback) {
                assertNull(macAddress, "Loopback interface \""
                        + name + "\" doesn't have a null MAC Address");
            }
        } catch (SocketException ex) {
            throw new UncheckedIOException(ex);
        }
    }
 }

