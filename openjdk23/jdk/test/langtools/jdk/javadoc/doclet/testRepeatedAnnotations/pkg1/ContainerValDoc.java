/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg1;

import java.lang.annotation.*;

/**
 * This annotation is a documented annotation container for ContaineeNotDoc.
 * It will be used to annotate Class C using a non-synthesized form.
 */
@Documented
public @interface ContainerValDoc {

    ContaineeNotDoc[] value();

    int x();
}
