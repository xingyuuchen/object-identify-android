package com.cxy.oi.kernel.event;

public abstract class IEvent {

    public abstract void callback();

    private int eventID;

    public int getEventID() {
        if (eventID == 0) {
            eventID = getClass().hashCode();
        }
        return eventID;
    }

}
