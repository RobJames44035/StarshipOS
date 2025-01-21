/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package api;

import java.util.List;
import jdk.internal.javac.PreviewFeature;
import jdk.internal.javac.PreviewFeature.Feature;

/**
 * <p>{@link API#test()}</p>
 * <p>{@link API#testNoPreviewInSig()}</p>
 */
@PreviewFeature(feature=Feature.TEST, reflective=false)
public class API {

    public API test() {
        return null;
    }

    public void testNoPreviewInSig() {
    }

    public void typeArgs(List<API> api) {
    }
}
