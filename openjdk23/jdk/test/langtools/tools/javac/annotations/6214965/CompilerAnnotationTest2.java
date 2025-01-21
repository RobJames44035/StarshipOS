/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

@CompilerAnnotationTest(@CompilerAnnotationTest2(name="test"))
@interface CompilerAnnotationTest2
{
   String name();
   String name2() default "test"; //compile and change this to name3, then recompile
}
