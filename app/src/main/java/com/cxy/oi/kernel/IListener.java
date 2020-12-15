package com.cxy.oi.kernel;

public abstract class IListener<T extends IEvent> {

    private int eventID;

    public int getEventID() {
        if (eventID == 0) {
            eventID = getClass().hashCode();
        }
        return eventID;
    }

    public abstract void callback();

}
