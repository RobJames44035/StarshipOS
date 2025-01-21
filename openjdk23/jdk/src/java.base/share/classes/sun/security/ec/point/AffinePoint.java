/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package sun.security.ec.point;

import sun.security.util.math.ImmutableIntegerModuloP;
import sun.security.util.math.IntegerFieldModuloP;
import sun.security.util.math.IntegerMontgomeryFieldModuloP;

import java.security.spec.ECPoint;
import java.util.Objects;

/**
 * Elliptic curve point represented using affine coordinates (x, y). This class
 * is not part of the sun.security.ec.point.Point hierarchy because it is not
 * used to hold intermediate values during point arithmetic, and so it does not
 * have a mutable form.
 */
public class AffinePoint {

    private final ImmutableIntegerModuloP x;
    private final ImmutableIntegerModuloP y;

    public AffinePoint(ImmutableIntegerModuloP x, ImmutableIntegerModuloP y) {
        this.x = x;
        this.y = y;
    }

    public static AffinePoint fromECPoint(
            ECPoint ecPoint, IntegerFieldModuloP field) {
        return new AffinePoint(
                field.getElement(ecPoint.getAffineX()),
                field.getElement(ecPoint.getAffineY()));
    }

    public ECPoint toECPoint() {
        return new ECPoint(getX().asBigInteger(), getY().asBigInteger());
    }

    public ImmutableIntegerModuloP getX() {
        return getX(true);
    }

    public ImmutableIntegerModuloP getX(boolean fieldCheck) {
        IntegerFieldModuloP field = x.getField();
        if (fieldCheck && field instanceof IntegerMontgomeryFieldModuloP) {
            return ((IntegerMontgomeryFieldModuloP)field).fromMontgomery(x);
        }
        return x;
    }

    public ImmutableIntegerModuloP getY() {
        return getY(true);
    }

    public ImmutableIntegerModuloP getY(boolean fieldCheck) {
        IntegerFieldModuloP field = y.getField();
        if (fieldCheck && field instanceof IntegerMontgomeryFieldModuloP) {
            return ((IntegerMontgomeryFieldModuloP)field).fromMontgomery(y);
        }
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AffinePoint)) {
            return false;
        }
        AffinePoint p = (AffinePoint) obj;
        boolean xEquals, yEquals;
        boolean thisMont = x.getField() instanceof IntegerMontgomeryFieldModuloP;
        boolean objMont = p.x.getField() instanceof IntegerMontgomeryFieldModuloP;
        if (thisMont ^ objMont == false) {
            // both fields same
            xEquals = x.asBigInteger().equals(p.x.asBigInteger());
            yEquals = y.asBigInteger().equals(p.y.asBigInteger());
        } else if (thisMont) {
            // mismatched fields should not happen in production, but useful in
            // testing
            IntegerMontgomeryFieldModuloP field =
                (IntegerMontgomeryFieldModuloP)x.getField();
            xEquals = x.asBigInteger().equals(
                field.getElement(p.x.asBigInteger()).asBigInteger());
            yEquals = y.asBigInteger().equals(
                field.getElement(p.y.asBigInteger()).asBigInteger());
        } else {
            IntegerMontgomeryFieldModuloP field =
                (IntegerMontgomeryFieldModuloP)p.x.getField();
            xEquals = field.getElement(
                x.asBigInteger()).asBigInteger().equals(p.x.asBigInteger());
            yEquals = field.getElement(
                y.asBigInteger()).asBigInteger().equals(p.y.asBigInteger());
        }
        return xEquals && yEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x.asBigInteger().toString() + "," +
            y.asBigInteger().toString() + ")";
    }
}
