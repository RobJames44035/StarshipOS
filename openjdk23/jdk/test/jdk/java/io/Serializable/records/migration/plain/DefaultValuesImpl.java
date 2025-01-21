/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public class DefaultValuesImpl implements DefaultValues, java.io.Serializable {

    private static final long serialVersionUID = 51L;

    private final Point point;

    // no more fields, they will be added in the record.

    public DefaultValuesImpl(Point point) {
        this.point = point;
    }

    public Point point() {
        return point;
    }

    @Override
    public boolean bool() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public byte by() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public short sh() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public char ch() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public int i() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public long l() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public float f() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public double d() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public String str() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public int[] ia() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public Object[] oa() {
        throw new AssertionError("should not reach here");
    }

    @Override
    public Object obj() {
        throw new AssertionError("should not reach here");
    }
}
