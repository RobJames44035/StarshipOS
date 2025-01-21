/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
/*
 * @test
 * @bug 6844907
 * @modules java.security.jgss/sun.security.krb5.internal.crypto
 * @run main/othervm ETypeOrder
 * @summary krb5 etype order should be from strong to weak
 */

import sun.security.krb5.internal.crypto.EType;

public class ETypeOrder {
    public static void main(String[] args) throws Exception {

        // File does not exist, so that the system-default one won't be used
        System.setProperty("java.security.krb5.conf", "no_such_file");
        int[] etypes = EType.getBuiltInDefaults();

        // Reference order, note that 2 is not implemented in Java
        int correct[] = { 18, 17, 20, 19, 16, 23, 1, 3, 2 };

        int match = 0;
        loopi: for (int i=0; i<etypes.length; i++) {
            for (; match < correct.length; match++) {
                if (etypes[i] == correct[match]) {
                    System.out.println("Find " + etypes[i] + " at #" + match);
                    continue loopi;
                }
            }
            throw new Exception("No match or bad order for " + etypes[i]);
        }
    }
}
