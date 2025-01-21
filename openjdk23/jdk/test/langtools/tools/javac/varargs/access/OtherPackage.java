/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * Auxiliary file for VarargsInferredPrivateType
 */

package otherpackage;

public class OtherPackage {
    public static Private getPrivate() {
        return new Private();
    }

    private static class Private {}
}