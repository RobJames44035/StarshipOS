/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.image.AreaAveragingScaleFilter;

/*
 * @test
 * @summary Check that AreaAveragingScaleFilter constructor and clone() method
 *          do not throw unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessAreaAveragingScaleFilter
 */

public class HeadlessAreaAveragingScaleFilter {
    public static void main(String args[]) {
        new AreaAveragingScaleFilter(100, 100).clone();
    }
}
