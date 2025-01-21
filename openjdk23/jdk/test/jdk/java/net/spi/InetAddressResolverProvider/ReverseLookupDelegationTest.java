/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import impl.DelegatingProviderImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static impl.DelegatingProviderImpl.changeReverseLookupAddress;
import static impl.DelegatingProviderImpl.lastReverseLookupThrowable;

/*
 * @test
 * @summary checks delegation of illegal reverse lookup request to the built-in
 *  InetAddressResolver.
 * @library providers/delegating
 * @build delegating.provider/impl.DelegatingProviderImpl
 * @run testng/othervm ReverseLookupDelegationTest
 */
public class ReverseLookupDelegationTest {

    @Test
    public void delegateHostNameLookupWithWrongByteArray() throws UnknownHostException {
        // The underlying resolver implementation will ignore the supplied
        // byte array and will replace it with byte array of incorrect size.
        changeReverseLookupAddress = true;
        String canonicalHostName = InetAddress.getByAddress(new byte[]{1, 2, 3, 4}).getCanonicalHostName();
        // Output canonical host name and the exception thrown by the built-in resolver
        System.err.println("Canonical host name:" + canonicalHostName);
        System.err.println("Exception thrown by the built-in resolver:" + lastReverseLookupThrowable);

        // Check that originally supplied byte array was used to construct canonical host name after
        // failed reverse lookup.
        Assert.assertEquals("1.2.3.4", canonicalHostName, "unexpected canonical hostname");

        // Check that on a provider side the IllegalArgumentException has been thrown by the built-in resolver
        Assert.assertTrue(lastReverseLookupThrowable instanceof IllegalArgumentException,
                "wrong exception type is thrown by the built-in resolver");
    }
}
