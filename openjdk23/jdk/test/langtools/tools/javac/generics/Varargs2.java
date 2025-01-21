/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5014324
 * @summary generics + varargs API changes
 * @author gafter
 *
 * @compile  Varargs2.java
 * @run main Varargs2
 */

import java.util.*;

public enum Varargs2 {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
    public static void main(String[] args) {
        System.out.println(EnumSet.of(B));
        System.out.println(EnumSet.of(B, C));
        System.out.println(EnumSet.of(B, C, D));
        System.out.println(EnumSet.of(B, C, D, F));
        System.out.println(EnumSet.of(B, C, D, F, G));
        System.out.println(EnumSet.of(B, C, D, F, G, H));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J, K));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J, K, L));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J, K, L, M));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J, K, L, M, N));
        System.out.println(EnumSet.of(B, C, D, F, G, H, J, K, L, M, N, P));
    }
}
