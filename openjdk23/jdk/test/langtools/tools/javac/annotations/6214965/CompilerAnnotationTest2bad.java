/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

@CompilerAnnotationTest(@CompilerAnnotationTest2(name="test"))
@interface CompilerAnnotationTest2
{
   String name();
   String name3() default "test";
}
