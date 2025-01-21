/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Abstract test base for Fed list related test, this class extends DNSTestBase.
 *
 * @see DNSTestBase
 * @see TestBase
 */
abstract class ListFedBase extends DNSTestBase {
    private String key;

    public ListFedBase() {
        // set default test data
        setKey("host1");
    }

    /**
     * Verify given NamingEnumeration match expected count.
     *
     * @param enumObj       given NamingEnumeration instance
     * @param expectedCount given expected count
     * @throws NamingException
     */
    public void verifyNamingEnumeration(NamingEnumeration<Binding> enumObj,
            int expectedCount) throws NamingException {
        DNSTestUtils.debug("Enum is: " + enumObj);

        int count = 0;
        Binding res;

        while (enumObj.hasMore()) {
            res = enumObj.next();
            DNSTestUtils.debug(res);
            ++count;
        }

        if (count != expectedCount) {
            throw new RuntimeException(
                    "Expecting " + expectedCount + " entries but got " + count);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
