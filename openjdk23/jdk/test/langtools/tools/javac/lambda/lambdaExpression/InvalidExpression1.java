/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.Comparator;

public class InvalidExpression1 {

    void test() {
        Comparator<Number> c = (Number n1, Number n2)-> { 42; }; //not a statement
        Comparator<Number> c = (Number n1, Number n2)-> { return 42 }; //";" expected
    }
}
