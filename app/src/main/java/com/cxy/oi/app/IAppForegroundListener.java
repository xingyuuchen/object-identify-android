package com.cxy.oi.app;

public interface IAppForegroundListener {

    void onAppForeground(String activity);

    void onAppBackground(String activity);

}
