/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class DeclarationAnnotation {
    Object e1 = new @DA int[5];
    Object e2 = new @DA String[42];
    Object e3 = new @DA Object();
    Object e4 = new @DA Object() { };
}

@interface DA { }
