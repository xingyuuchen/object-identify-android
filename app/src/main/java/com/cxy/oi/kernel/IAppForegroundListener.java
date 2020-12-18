package com.cxy.oi.kernel;

public interface IAppForegroundListener {

    void onAppForeground(String activity);

    void onAppBackground(String activity);

}
