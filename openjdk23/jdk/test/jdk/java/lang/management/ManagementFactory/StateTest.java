/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

public class StateTest implements StateTestMBean {
    private int state;
    public StateTest(int state) { this.state = state; }
    public int getState() {
        return state;
    }
}
