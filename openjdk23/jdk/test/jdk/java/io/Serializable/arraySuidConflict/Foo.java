/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @bug 4490677
 * @summary Verify that array serialVersionUID conflicts caused by changes in
 *          package scope do not cause deserialization to fail.
 */

public class Foo implements java.io.Serializable {
    private static final long serialVersionUID = 0L;
}
