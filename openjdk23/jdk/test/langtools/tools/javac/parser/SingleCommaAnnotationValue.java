/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8012722
 * @summary Single comma in array initializer should parse
 * @compile SingleCommaAnnotationValue.java
 */

public class SingleCommaAnnotationValue {
    @Foo({}) void a() { }
    @Foo({,}) void b() { }
    @Foo({0}) void c() { }
    @Foo({0,}) void d() { }
    @Foo({0,0}) void e() { }
    @Foo({0,0,}) void f() { }
}
@interface Foo { int[] value(); }
