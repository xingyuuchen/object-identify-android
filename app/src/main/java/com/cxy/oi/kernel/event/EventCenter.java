package com.cxy.oi.kernel.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventCenter {

    private static final String TAG = "EventCenter";
    public static EventCenter INSTANCE = new EventCenter();

    private final byte[] lock = new byte[0];

    private EventCenter() {
    }

    private Map<Integer, Set<IListener<?>>> listenerMap = new HashMap<>();


    public void publish(IEvent event) {
        int eventId = event.getEventID();
        Set<IListener<?>> listeners = listenerMap.get(eventId);
        if (listeners != null) {
            for (IListener<?> listener : listeners) {
                listener.callback();
            }
        }
    }

    public void addListener(IListener<?> listener) {
        int eventId = listener.getEventID();
        synchronized (lock) {
            if (listenerMap.containsKey(eventId)) {
                Set<IListener<?>> set = listenerMap.get(eventId);
                if (set == null) {
                    listenerMap.put(eventId, new HashSet<IListener<?>>());
                } else {
                    set.add(listener);
                }
            } else {
                Set<IListener<?>> set = new HashSet<>();
                set.add(listener);
                listenerMap.put(eventId, set);
            }
        }
    }


}
