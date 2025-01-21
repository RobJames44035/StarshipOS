/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import annot.AnnotatedElementInfo;

/**
 * Class to hold annotations for TestElementsAnnotatedWith.
 */

@AnnotatedElementInfo(annotationName="java.lang.SuppressWarnings",
                      expectedSize=2,
                      names={"SurfaceAnnotations",
                             "foo"})
@SuppressWarnings("")
public class SurfaceAnnotations {
    @SuppressWarnings("")
    private void foo() {return;};
}
