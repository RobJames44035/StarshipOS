/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class SortPerf {
    private static final int NUM_SETS = 5;
    private static final int[] lengths = { 10, 100, 1000, 10000, 1000000 };

    // Returns the number of repetitions as a function of the list length
    private static int reps(int n) {
        return (int) (12000000 / (n * Math.log10(n)));
    }

    public static void main(String[] args) {
        Sorter.warmup();

        System.out.print("Strategy,Length");
        for (Sorter sorter : Sorter.values())
            System.out.print("," + sorter);
        System.out.println();

        for (ArrayBuilder ab : ArrayBuilder.values()) {
            for (int n : lengths) {
                System.out.printf("%s,%d", ab, n);
                int reps = reps(n);
                Object[] proto = ab.build(n);
                for (Sorter sorter : Sorter.values()) {
                    double minTime = Double.POSITIVE_INFINITY;
                    for (int set = 0; set < NUM_SETS; set++) {
                        long startTime = System.nanoTime();
                        for (int k = 0; k < reps; k++) {
                            Object[] a = proto.clone();
                            sorter.sort(a);
                        }
                        long endTime = System.nanoTime();
                        double time = (endTime - startTime) / (1000000. * reps);
                        minTime = Math.min(minTime, time);
                    }
                    System.out.printf(",%5f", minTime);
                }
                System.out.println();
            }
        }
    }
}
