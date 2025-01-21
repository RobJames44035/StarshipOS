/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4482966
 * @summary Verify that ObjectStreamClass.getSerialVersionUID() will not mask
 *          Errors (other than NoSuchMethodError) triggered by JNI query for
 *          static initializer method.
 */

import java.io.*;

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class A implements Serializable {
    static {
        // compiler prohibits direct throw
        throwMe(new RuntimeException("blargh"));
    }

    static void throwMe(RuntimeException ex) throws RuntimeException {
        throw ex;
    }
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class B implements Serializable {
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class C implements Serializable {
    static { System.out.println("C.<clinit>"); }
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class B1 extends B {
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class B2 extends B {
    static { System.out.println("B2.<clinit>"); }
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class C1 extends C {
}

@SuppressWarnings("serial") /* Incorrect declarations are being tested. */
class C2 extends C {
    static { System.out.println("C2.<clinit>"); }
}

public class GetSuidClinitError {
    public static void main(String[] args) throws Exception {
        Class<?> cl = Class.forName(
            "A", false, GetSuidClinitError.class.getClassLoader());
        for (int i = 0; i < 2; i++) {
            try {
                ObjectStreamClass.lookup(cl).getSerialVersionUID();
                throw new Error();
            } catch (ExceptionInInitializerError er) {
            } catch (NoClassDefFoundError er) {
                /*
                 * er _should_ be an ExceptionInInitializerError; however,
                 * hotspot currently throws a NoClassDefFoundError in this
                 * case, which runs against the JNI spec.  For the purposes of
                 * testing this fix, however, permit either.
                 */
                System.out.println("warning: caught " + er +
                    " instead of ExceptionInInitializerError");
            }
        }

        Class<?>[] cls = {
            B.class, B1.class, B2.class,
            C.class, C1.class, C2.class
        };
        long[] suids = new long[] {     // 1.3.1 default serialVersionUIDs
            369445310364440919L, 7585771686008346939L, -8952923334200087495L,
            3145821251853463625L, 327577314910517070L, -92102021266426451L
        };
        for (int i = 0; i < cls.length; i++) {
            if (ObjectStreamClass.lookup(cls[i]).getSerialVersionUID() !=
                suids[i])
            {
                throw new Error();
            }
        }
    }
}
