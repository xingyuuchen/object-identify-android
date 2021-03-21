package com.cxy.oi.app.events;

import com.cxy.oi.kernel.event.IEvent;

public class NetDispatcherReadyEvent extends IEvent {

    public Data data = new Data();

    public static class Data {
        public boolean isReady;
    }
}
