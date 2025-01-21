/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS104.hs104t001;
public class MyClass {
        private String message;
        private int state;
        private int size;
        public MyClass(String message, int size) {
                this.message = message;
                state=900;
        }
        public void doThis() {
                for(int i=0; i < 10; i++) {
                        System.out.println(" .... Message "+message+" Message");
                        state++;
                }
        }
        public int getState() {
                return state+10;
        }
}
