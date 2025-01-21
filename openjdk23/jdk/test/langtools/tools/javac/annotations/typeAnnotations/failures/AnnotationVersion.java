/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;

class myNumber<T extends @A Number> { }

@Target(ElementType.TYPE_USE)
@interface A { }
