/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @bug 4482471
 * @summary Verify that even if an incoming ObjectStreamClass is not resolvable
 *          to a local class, the ObjectStreamClass object itself is still
 *          deserializable (without incurring a ClassNotFoundException).
 */

public class Foo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
}
