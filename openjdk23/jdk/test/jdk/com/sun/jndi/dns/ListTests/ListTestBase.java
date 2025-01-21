/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.util.Hashtable;

/**
 * Abstract test base for List related Tests, this class extends DNSTestBase.
 *
 * This test base will also been referenced outside of current open test folder,
 * please double check all usages when modify this.
 *
 * @see DNSTestBase
 * @see TestBase
 */
public abstract class ListTestBase extends DNSTestBase {
    private String key;
    private String[] children;

    public ListTestBase() {
        // set default test data
        setKey("subdomain");
        setChildren("host1", "host2", "host3", "host4", "host5", "host6",
                "host7", "host8", "host9");
    }

    /**
     * Verify given entries, will throw RuntimeException if any child missing.
     *
     * @param entries given entries
     */
    public void verifyEntries(Hashtable<?, ?> entries) {
        if (entries.size() != children.length) {
            throw new RuntimeException(
                    "Expected " + children.length + " entries but found "
                            + entries.size());
        } else {
            for (String child : children) {
                if (entries.get(child.toLowerCase()) == null) {
                    throw new RuntimeException("Missing " + child);
                }
            }
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setChildren(String... children) {
        this.children = children;
    }
}
