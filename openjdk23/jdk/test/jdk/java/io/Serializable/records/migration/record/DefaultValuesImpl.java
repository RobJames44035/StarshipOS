/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record DefaultValuesImpl(boolean bool, byte by, short sh, char ch, Point point, int i, long l, float f, double d, Object obj, String str, int[] ia, Object[] oa)
    implements DefaultValues, java.io.Serializable
{
    private static final long serialVersionUID = 51L;
}
