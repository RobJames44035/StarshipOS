/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class EarlyAssignmentNoPreview2 {

    Runnable r;

    public EarlyAssignmentNoPreview2() {
        this(this.r = () -> System.out.println("hello"));
    }

    public EarlyAssignmentNoPreview2(Runnable r) {
    }

    public static void main(String[] args) {
        new EarlyAssignmentNoPreview2();
    }
}
