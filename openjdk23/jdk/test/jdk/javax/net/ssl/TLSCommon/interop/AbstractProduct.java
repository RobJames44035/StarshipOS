/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.nio.file.Path;

/*
 * An abstract product definition.
 */
public abstract class AbstractProduct implements Product {

    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jdk other = (Jdk) obj;
        if (getPath() == null) {
            if (other.getPath() != null)
                return false;
        } else if (!getPath().equals(other.getPath()))
            return false;
        return true;
    }
}
