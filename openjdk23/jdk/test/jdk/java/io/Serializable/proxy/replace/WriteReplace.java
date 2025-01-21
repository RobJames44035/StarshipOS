/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @summary Ensure that serialization invokes writeReplace/readResolve methods
 *          on dynamic proxies, just as with normal objects.
 */

public interface WriteReplace {
    public Object writeReplace() throws java.io.ObjectStreamException;
}
