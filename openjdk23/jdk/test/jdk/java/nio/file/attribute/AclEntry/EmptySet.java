/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/* @test
 * @bug 7076310
 * @summary Test AclEntry.Builder setFlags and setPermissions with empty sets
 */

import java.nio.file.attribute.*;
import java.util.*;

/*
 * Test for bug 7076310 "(file) AclEntry.Builder setFlags throws
 * IllegalArgumentException if set argument is empty"
 * The bug is only applies when the given Set is NOT an instance of EnumSet.
 *
 * The setPermissions method also has the same problem.
 */
public class EmptySet {
    public static void main(String[] args) {
        Set<AclEntryFlag> flags = new HashSet<>();
        AclEntry.newBuilder().setFlags(flags);

        Set<AclEntryPermission> perms = new HashSet<>();
        AclEntry.newBuilder().setPermissions(perms);
    }
}
