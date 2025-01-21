/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class PatternCaseErrorRecovery {
    Object expressionLikeType(Object o1, Object o2) {
        final int a = 1;
        final int b = 2;
        return switch (o1) {
            case true t -> o2;
            case 1 + 1 e -> o2;
            case a < b ? a : b e -> o2;
            default -> null;
        };
    }
}
