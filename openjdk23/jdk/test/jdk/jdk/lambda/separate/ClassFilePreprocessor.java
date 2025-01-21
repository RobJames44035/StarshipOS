/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package separate;

public interface ClassFilePreprocessor {
    public byte[] preprocess(String name, byte[] classfile);
};
