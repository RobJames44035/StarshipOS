/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 7176515 8299760
 * @summary ExceptionInInitializerError for an enum with multiple switch statements
 */

import java.math.RoundingMode;

public class EnumLookupTableExceptionInInitializer {

    public enum MyEnum {
        FIRST(RoundingMode.CEILING),
        SECOND(RoundingMode.HALF_DOWN),
        THIRD(RoundingMode.UNNECESSARY),
        FOURTH(RoundingMode.HALF_EVEN),
        FIFTH(RoundingMode.HALF_DOWN),
        SIXTH(RoundingMode.CEILING),
        SEVENTH(RoundingMode.UNNECESSARY);

        private final RoundingMode mode;

        private MyEnum(RoundingMode mode) {
            switch (mode) {
            case CEILING:
            case HALF_DOWN:
            case UNNECESSARY:
            case HALF_EVEN:
                break;
            default:
                throw new IllegalArgumentException();
            }
            this.mode = mode;
        }

        public boolean isOdd() {
            switch (this) {
            case FIRST:
            case THIRD:
            case FIFTH:
            case SEVENTH:
                return true;
            default:
                return false;
            }
        }
    }

    public enum Nested {
        AAA(MyEnum.FIRST),
        BBB(MyEnum.THIRD),
        CCC(MyEnum.FIFTH),
        DDD(MyEnum.SEVENTH),
        EEE(MyEnum.SECOND);

        private Nested(MyEnum x) {
            switch (x) {
            default:
                break;
            }
        }
    }

    public static void main(String[] args) {
        boolean shouldBeOdd = true;
        for (MyEnum x : MyEnum.values()) {
            if (x.isOdd() != shouldBeOdd)
                throw new RuntimeException("failed");
            shouldBeOdd = !shouldBeOdd;
        }
        Nested.class.hashCode();
    }
}
