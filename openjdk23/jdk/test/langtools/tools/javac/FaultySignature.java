/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4085633
 * @summary javac used to pass the uplevel arguments into insideMember's
 *          constructor in the wrong order
 * @author turnidge
 *
 * @compile FaultySignature.java
 * @run main FaultySignature
 */

public
class FaultySignature {
    void method() throws Exception {
        final int value = 888;

        class local {
            local() throws Exception {
                (new insideMember()).tester();
            }

            class insideMember {
                void tester() throws Exception {
                    if (value != 888) {
                        throw new Exception("Signature is out of order.");
                    }
                }
            }
        }

        new local();
    }

    public static void main(String[] args) throws Exception {
        (new FaultySignature()).method();
    }
}
