/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.Annotation;
class AtNonAnnotationTypeTest<Override extends Annotation> {
  AtNonAnnotationTypeTest(@Override String foo) {}
}