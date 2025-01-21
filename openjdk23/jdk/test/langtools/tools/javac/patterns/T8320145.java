/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @bug 8320145
 * @summary Compiler should accept final variable in Record Pattern
 * @compile T8320145.java
 */
public class T8320145 {
    record ARecord(String aComponent) {}
    record BRecord(ARecord aComponent) {}
    record CRecord(ARecord aComponent1, ARecord aComponent2) {}

    public String match(Object o) {
        return switch(o) {
            case ARecord(final String s) -> s;
            case BRecord(ARecord(final String s)) -> s;
            case CRecord(ARecord(String s), ARecord(final String s2)) -> s;
            default -> "No match";
        };
    }
}
