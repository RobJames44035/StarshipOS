/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedSignatureContainer {
    ExpectedSignature[] value();
}