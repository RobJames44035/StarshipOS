/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg;

import java.lang.annotation.*;

/**
 * This annotation is a non-documented annotation container for RegContaineeNotDoc.
 * It will be used to annotate Class C using a non-synthesized form.
 */
public @interface RegContainerNotDoc {

    RegContaineeNotDoc[] value();
}
