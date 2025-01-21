/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4093217
 * @summary Duplicate import-on-demand declarations should be no-ops.
 * @author turnidge
 *
 * @compile DuplicateImport.java
 */

package nonexistent.pack;

import nonexistent.pack.*;

class DuplicateImport {
}
