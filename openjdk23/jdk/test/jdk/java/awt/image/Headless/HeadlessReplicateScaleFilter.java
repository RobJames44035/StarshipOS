/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.image.ReplicateScaleFilter;

/*
 * @test
 * @summary Check that ReplicateScaleFilter constructor and clone() method
 *          do not throw unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessReplicateScaleFilter
 */

public class HeadlessReplicateScaleFilter {
    public static void main(String args[]) {
        new ReplicateScaleFilter(100, 100).clone();
    }
}
