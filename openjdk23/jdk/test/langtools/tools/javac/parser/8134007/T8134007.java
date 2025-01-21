/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8134007
 * @summary Improve string folding
 * @compile T8134007.java
 */
class T8134007 {
    String v = "";

    //interleaved non-literals
    String s1 = "Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v;

    //heading non-literal
    String s2 = v + "Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9";

    //trailing non-literal
    String s3 = "Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9"
        +"Str0" + "Str1" + "Str2" + "Str3" + "Str4" + "Str5" + "Str6" + "Str7" + "Str8" + "Str9" + v;
}
