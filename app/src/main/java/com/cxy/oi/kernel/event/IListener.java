package com.cxy.oi.kernel.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class IListener<T extends IEvent> {

    private int eventID = 0;

    public int getEventID() {
        if (eventID == 0) {
            Type supClz = getClass().getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) supClz;
            Type type = pt.getActualTypeArguments()[0];
            eventID = ((Class) type).getName().hashCode();
        }
        return eventID;
    }

    public abstract void callback(IEvent event);

}
