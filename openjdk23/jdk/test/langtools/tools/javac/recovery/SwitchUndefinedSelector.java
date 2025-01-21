/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class SwitchUndefinedSelector {
    private static final Object D = null;
    public void switchTest() {
        switch (undefined) {
            case A -> {}
            case B, C -> {}
            case D -> {}
        }
        var v = switch (undefined) {
            case A -> 0;
            case B, C -> 0;
            case D -> 0;
        };
        switch (undefined) {
            case SwitchUndefinedSelector.D -> {}
            case SwitchUndefinedSelector.UNDEF -> {}
        }
    }
}
