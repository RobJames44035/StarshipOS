/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

enum Color { red, green, blue }

@interface Colored {
    Color value() default Color.teal;
}
