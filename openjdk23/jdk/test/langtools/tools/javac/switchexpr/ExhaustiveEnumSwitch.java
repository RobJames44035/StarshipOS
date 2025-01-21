/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986 8243548
 * @summary Verify that an switch expression over enum can be exhaustive without default.
 * @compile --release 20 ExhaustiveEnumSwitch.java
 * @compile ExhaustiveEnumSwitchExtra.java
 * @run main ExhaustiveEnumSwitch IncompatibleClassChangeError
 * @compile ExhaustiveEnumSwitch.java
 * @compile ExhaustiveEnumSwitchExtra.java
 * @run main ExhaustiveEnumSwitch MatchException
 */

public class ExhaustiveEnumSwitch {
    public static void main(String... args) throws ClassNotFoundException {
        boolean matchException = "MatchException".equals(args[0]);
        new ExhaustiveEnumSwitch().run(matchException);
    }

    private void run(boolean matchException) throws ClassNotFoundException {
        ExhaustiveEnumSwitchEnum v = ExhaustiveEnumSwitchEnum.valueOf("F");

        try {
            print(v);
            throw new AssertionError("Expected exception did not occur.");
        } catch (IncompatibleClassChangeError err) {
            if (matchException) {
                throw new AssertionError("Expected IncompatibleClassChangeError, but got MatchException!");
            }
        } catch (Exception ex) {
            //cannot refer to MatchException directly, as it used to be preview API in JDK 20:
            if (ex.getClass() == Class.forName("java.lang.MatchException")) {
                if (!matchException) {
                    throw new AssertionError("Expected MatchException, but got IncompatibleClassChangeError!");
                }
            } else {
                throw ex;
            }
        }
    }

    private String print(ExhaustiveEnumSwitchEnum t) {
        return switch (t) {
            case A -> "A";
            case B -> "B";
        };
    }

}
enum ExhaustiveEnumSwitchEnum {
    A, B;
    class NestedClass {}
    enum NestedEnum {}
    interface NestedInterface {}
    @interface NestedAnnotation {}
    void nestedMethod() {}
    static void nestedStaticMethod() {}
    int nestedField;
    static int nestedStaticField;
}
