/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.InetAddress;

/**
 * @test
 * @summary Test that provider which uses InetAddress APIs during its initialization
 * wouldn't cause stack overflow and will be successfully installed.
 * @library providers/recursive
 * @build recursive.init.provider/impl.InetAddressUsageInGetProviderImpl
 * @run testng/othervm InetAddressUsageInGetProviderTest
 */

public class InetAddressUsageInGetProviderTest {

    @Test
    public void testSuccessfulProviderInstantiationTest() throws Exception {
        System.err.println(InetAddress.getAllByName(InetAddress.getLocalHost().getHostName()));
    }
}
