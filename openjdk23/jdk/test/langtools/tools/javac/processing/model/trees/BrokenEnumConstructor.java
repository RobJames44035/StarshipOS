/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public enum BrokenEnumConstructor {

    A;

    BrokenEnumConstructor() {super(); /*illegal*/}
}
