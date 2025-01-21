/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class SwitchArrowBrokenConstant {

    private String likeLambda(int i) {
        switch (i) {
            case (a, b) -> {}
        }
        return switch (i) {
            case (a, b) -> {}
        };
        switch (i) {
            case a;
        }
        return switch (i) {
            case a;
        };
        switch (i) {
            default ;
        }
        return switch (i) {
            default ;
        };
    }

}
