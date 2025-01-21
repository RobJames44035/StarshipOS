/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public interface NotificationSenderMBean {
    public void sendNotifs(String type, int count);

    public int getListenerCount();
}
