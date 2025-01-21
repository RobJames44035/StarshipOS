/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Dotted numeric version string. E.g.: 1.0.37, 10, 0.5
 */
final class DottedVersion {

    DottedVersion(String version) {
        this(version, true);
    }

    private DottedVersion(String version, boolean greedy) {
        this.value = version;
        if (version.isEmpty()) {
            if (greedy) {
                throw new IllegalArgumentException(I18N.getString("error.version-string-empty"));
            } else {
                this.components = new BigInteger[0];
                this.suffix = "";
            }
        } else {
            var ds = new DigitsSupplier(version);
            components = Stream.generate(ds::getNextDigits).takeWhile(Objects::nonNull).map(
                    digits -> {
                        if (digits.isEmpty()) {
                            if (!greedy) {
                                return null;
                            } else {
                                throw new IllegalArgumentException(MessageFormat.format(I18N.
                                        getString("error.version-string-zero-length-component"),
                                        version));
                            }
                        }

                        try {
                            return new BigInteger(digits);
                        } catch (NumberFormatException ex) {
                            if (!greedy) {
                                return null;
                            } else {
                                throw new IllegalArgumentException(MessageFormat.format(I18N.
                                        getString("error.version-string-invalid-component"), version,
                                        digits));
                            }
                        }
                    }).takeWhile(Objects::nonNull).toArray(BigInteger[]::new);
            suffix = ds.getUnprocessedString();
            if (!suffix.isEmpty() && greedy) {
                throw new IllegalArgumentException(MessageFormat.format(I18N.getString(
                        "error.version-string-invalid-component"), version, suffix));
            }
        }
    }

    private static class DigitsSupplier {

        DigitsSupplier(String input) {
            this.input = input;
        }

        public String getNextDigits() {
            if (stoped) {
                return null;
            }

            var sb = new StringBuilder();
            while (cursor != input.length()) {
                var chr = input.charAt(cursor++);
                if (Character.isDigit(chr)) {
                    sb.append(chr);
                } else {
                    var curStopAtDot = (chr == '.');
                    if (!curStopAtDot) {
                        if (lastDotPos >= 0) {
                            cursor = lastDotPos;
                        } else {
                            cursor--;
                        }
                        stoped = true;
                    } else if (!sb.isEmpty()) {
                        lastDotPos = cursor - 1;
                    } else {
                        cursor = Math.max(lastDotPos, 0);
                        stoped = true;
                    }
                    return sb.toString();
                }
            }

            if (sb.isEmpty()) {
                if (lastDotPos >= 0) {
                    cursor = lastDotPos;
                } else {
                    cursor--;
                }
            }

            stoped = true;
            return sb.toString();
        }

        public String getUnprocessedString() {
            return input.substring(cursor);
        }

        private int cursor;
        private int lastDotPos = -1;
        private boolean stoped;
        private final String input;
    }

    static DottedVersion greedy(String version) {
        return new DottedVersion(version);
    }

    static DottedVersion lazy(String version) {
        return new DottedVersion(version, false);
    }

    static int compareComponents(DottedVersion a, DottedVersion b) {
        int result = 0;
        BigInteger[] aComponents = a.getComponents();
        BigInteger[] bComponents = b.getComponents();
        for (int i = 0; i < Math.max(aComponents.length, bComponents.length)
                && result == 0; ++i) {
            final BigInteger x;
            if (i < aComponents.length) {
                x = aComponents[i];
            } else {
                x = BigInteger.ZERO;
            }

            final BigInteger y;
            if (i < bComponents.length) {
                y = bComponents[i];
            } else {
                y = BigInteger.ZERO;
            }
            result = x.compareTo(y);
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Arrays.deepHashCode(this.components);
        hash = 29 * hash + Objects.hashCode(this.suffix);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DottedVersion other = (DottedVersion) obj;
        if (!Objects.equals(this.suffix, other.suffix)) {
            return false;
        }
        return Arrays.deepEquals(this.components, other.components);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getUnprocessedSuffix() {
        return suffix;
    }

    String toComponentsString() {
        return Stream.of(components).map(BigInteger::toString).collect(Collectors.joining("."));
    }

    BigInteger[] getComponents() {
        return components;
    }

    private final BigInteger[] components;
    private final String value;
    private final String suffix;
}
