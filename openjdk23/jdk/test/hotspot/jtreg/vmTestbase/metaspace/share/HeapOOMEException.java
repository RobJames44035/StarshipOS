/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package metaspace.share;

/**
 * This class is used to distinguish between OOME in metaspace and OOME in heap when triggering class unloading.
 */
public class HeapOOMEException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HeapOOMEException(String string) {
        super(string);
    }

}
