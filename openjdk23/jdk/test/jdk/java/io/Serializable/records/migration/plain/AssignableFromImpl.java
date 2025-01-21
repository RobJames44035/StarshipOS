/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

public class AssignableFromImpl
    implements AssignableFrom, java.io.Serializable
{
    // ignored during record deserialization, but enforced for non-record deserialization
    private static final long serialVersionUID = 1L;

    private final Number number;

    public AssignableFromImpl(Number number) {
        this.number = number;
    }

    @Override
    public Number number() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("AssignableFromImpl[number=%s(%s)]",
                             number, number.getClass().getName());
    }
}
