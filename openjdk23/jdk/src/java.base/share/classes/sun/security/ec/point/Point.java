/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ec.point;

import sun.security.util.math.IntegerFieldModuloP;

/**
 * A base interface for points on an elliptic curve over a finite field.
 * Implementations may use different representations for points, and this
 * interface creates a common API for manipulating points. This API has no
 * methods for point arithmetic, which depends on group structure and curve
 * parameters in addition to point representation.
 */
public interface Point {

    IntegerFieldModuloP getField();
    AffinePoint asAffine();
    boolean affineEquals(Point p);

    ImmutablePoint fixed();
    MutablePoint mutable();

}
