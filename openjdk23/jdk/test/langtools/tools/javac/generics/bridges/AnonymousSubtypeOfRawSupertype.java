/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8189659
 * @summary Anonymous subtype of raw type causes VerifyError
 * @run main AnonymousSubtypeOfRawSupertype
 */

public class AnonymousSubtypeOfRawSupertype<_J extends AnonymousSubtypeOfRawSupertype> implements Comparable<_J> {
    static {
        System.err.println(System.getProperty("java.version"));
    }
    public static AnonymousSubtypeOfRawSupertype EMPTY = new AnonymousSubtypeOfRawSupertype() {
        void something() {
            System.out.println("This is something");
        }
    };
    public AnonymousSubtypeOfRawSupertype() {
    }
    @Override
    public int compareTo(_J o) {
        return 0;
    }
    public static void main(String[] args) {
        AnonymousSubtypeOfRawSupertype generic = AnonymousSubtypeOfRawSupertype.EMPTY;
    }
}