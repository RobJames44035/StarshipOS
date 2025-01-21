/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class VarErrors {
    void testIf(CharSequence cs) {
        if (cs instanceof var v) {}
    }
    void testSwitchStatement(CharSequence cs) {
        switch (cs) {
            case var v -> {}
        }
    }
    void testSwitchExpression(CharSequence cs) {
        int i = switch (cs) {
            case var v -> 0;
        };
    }
}
