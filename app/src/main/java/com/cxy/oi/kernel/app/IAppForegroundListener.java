package com.cxy.oi.kernel.app;

public interface IAppForegroundListener {

    void onAppForeground(String activity);

    void onAppBackground(String activity);

}
