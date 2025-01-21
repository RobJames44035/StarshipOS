/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.internal.access;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Interface to specify methods for accessing {@code ObjectInputStream}.
 */
@FunctionalInterface
public interface JavaObjectInputStreamReadString {
    String readString(ObjectInputStream ois) throws IOException;
}

