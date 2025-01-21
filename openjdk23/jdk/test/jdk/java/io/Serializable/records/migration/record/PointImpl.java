/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public record PointImpl (int x, int y)
    implements Point, java.io.Serializable
{
    private static final long serialVersionUID = 5L;
}
