/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug     4902078
 * @summary concurrent modification not detected on 2nd to last iteration
 * @author  Josh Bloch
 *
 * @ignore Bug fix temporarily removed as it uncovered other bugs (4992226)
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class CheckForComodification {
    private static final int LENGTH = 10;
    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < LENGTH; i++)
            list.add(i);
        try {
            for (int i : list)
                if (i == LENGTH - 2)
                    list.remove(i);
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("No ConcurrentModificationException");
    }
}
