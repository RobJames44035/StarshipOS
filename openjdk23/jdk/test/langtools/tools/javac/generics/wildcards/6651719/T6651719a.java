/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6651719
 * @summary Compiler crashes possibly during forward reference of TypeParameter
 * @compile T6651719a.java
 */

public class T6651719a<T extends S, S> {
    T6651719a<? extends T6651719a<?, ?>, ? extends T6651719a<?, ?>> crash = null;
}
