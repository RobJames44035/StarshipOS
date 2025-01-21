/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.time.LocalDate;

class IllegalParenthesis {
    String s = (Integer).toString(123);
    void f(){
        LocalDate date = (LocalDate).now();
    }
}
