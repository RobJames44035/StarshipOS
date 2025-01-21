/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.nio.file.Path;

/*
 * The basic definitions for a product on SSL/TLS implementation.
 */
public interface Product {

    public String getName();
    public Path getPath();
}
