/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

public class PointImpl implements Point, Serializable {

    private static final long serialVersionUID = 5L;

    final InetAddress moreCruft = InetAddress.getLoopbackAddress();  // some stream cruft
    final boolean a = true;
    final int x;
    final int cruft = 9;
    final int y;
    final InetAddress addr;

    public PointImpl(int x, int y) {
        this(x, y, null);
    }

    public PointImpl(int x, int y, InetAddress addr) {
        this.x = x;
        this.y = y;
        this.addr = addr;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Point[x=%d, y=%d, addr=%s]", x, y, addr);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            if (this.x == other.x() && this.y == other.y())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
