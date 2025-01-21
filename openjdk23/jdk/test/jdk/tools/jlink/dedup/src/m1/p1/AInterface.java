/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package p1;
import p3.ServiceInterface;

public class AInterface implements ServiceInterface {

    public String getString() {
        return "A1_A2";
    }

    public String getServiceName() {
        return "AService";
    }
}
