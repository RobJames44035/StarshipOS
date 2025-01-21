/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package pack;

@SuppressWarnings("deprecation")
public class ImplicitMain {
    private Object test() {
        return new ImplicitUse();
    }
}

@Deprecated
class Dep {

}
