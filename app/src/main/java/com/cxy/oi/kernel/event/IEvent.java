package com.cxy.oi.kernel.event;

public class IEvent {

    private int eventID = 0;

    public int getEventID() {
        if (eventID == 0) {
            eventID = getClass().getName().hashCode();
        }
        return eventID;
    }

}
