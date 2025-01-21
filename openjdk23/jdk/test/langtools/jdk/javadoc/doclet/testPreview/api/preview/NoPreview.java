/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package preview;

import jdk.internal.javac.PreviewFeature;
import jdk.internal.javac.PreviewFeature.Feature;

public class NoPreview {

    @PreviewFeature(feature=Feature.TEST)
    public T get() {
        return null;
    }

    @PreviewFeature(feature=Feature.TEST)
    public static class T {}

    // Preview support feature without JEP should not be listed
    @PreviewFeature(feature=Feature.LANGUAGE_MODEL)
    public void supportMethod() {}
}
