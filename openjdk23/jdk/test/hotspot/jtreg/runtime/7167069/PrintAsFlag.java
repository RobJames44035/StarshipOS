/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * Note that in the run command below the only important flag is PrintCommandLineFlags.
 * The others are just flags of all types; bool, intx, uintx, uint64_t, double and ccstr.
 *
 * @test PrintAsFlag
 * @summary verify that Flag::print_as_flag() works correctly. This is used by "jinfo -flag" and -XX:+PrintCommandLineFlags.
 * @run main/othervm -XX:+PrintCommandLineFlags -XX:-ShowMessageBoxOnError -XX:ParallelGCThreads=4 -XX:MaxRAM=1G -XX:ErrorFile="file" PrintAsFlag
 */

public class PrintAsFlag {
  public static void main(String... args) {
    System.out.printf("Done");
  }
}
