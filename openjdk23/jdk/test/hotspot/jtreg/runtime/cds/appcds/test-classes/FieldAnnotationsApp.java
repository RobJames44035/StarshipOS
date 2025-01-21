/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldAnnotationsApp {
    @MyAnnotation(name="myField1",  value="myValue1")
    public String myField1 = null;

    @MyAnnotation(name="myField2",  value="myValue2")
    public String myField2 = null;

    public static void main(String args[]) throws Exception {
        for (int i=1; i<=2; i++) {
            Field field = FieldAnnotationsApp.class.getField("myField" + i);
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation anno : annotations){
                if (anno instanceof MyAnnotation){
                    MyAnnotation myAnno = (MyAnnotation) anno;
                    String name = myAnno.name();
                    String value = myAnno.value();

                    System.out.println("Field         : " + field.getName());
                    System.out.println("  myAnno.name : " + name);
                    System.out.println("  myAnno.value: " + value);

                    if (!(name.equals("myField" + i) && value.equals("myValue" + i))) {
                        throw new Exception("Unexpected annotation values: " + i + " = " + value);
                    }
                }
            }
        }
        System.out.println("Field annotations are OK.");
    }
}
