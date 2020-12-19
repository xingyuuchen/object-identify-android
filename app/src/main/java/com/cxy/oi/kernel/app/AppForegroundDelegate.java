package com.cxy.oi.kernel.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.cxy.oi.kernel.util.Log;

import java.util.HashSet;
import java.util.Set;

public enum AppForegroundDelegate {
    INSTANCE;
    private static final String TAG = "AppForegroundDelegate";
    private Set<IAppForegroundListener> listeners = new HashSet<>();
    private boolean lastState;


    public void registerListener(IAppForegroundListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(IAppForegroundListener listener) {
        listeners.remove(listener);
    }

    private static final Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, "[onActivityCreated], activity: %s", activity.toString());

        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.i(TAG, "[onActivityStarted], activity: %s", activity.toString());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.i(TAG, "[onActivityResumed], activity: %s", activity.toString());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.i(TAG, "[onActivityPaused], activity: %s", activity.toString());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.i(TAG, "[onActivityStopped], activity: %s", activity.toString());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i(TAG, "[onActivitySaveInstanceState], activity: %s", activity.toString());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "[onActivityDestroyed], activity: %s", activity.toString());
        }
    };

    private void notifyForeground(boolean isForeground, String activity) {
        if (isForeground) {
            for (IAppForegroundListener l : listeners) {
                l.onAppForeground(activity);
            }
        } else {
            for (IAppForegroundListener l : listeners) {
                l.onAppBackground(activity);
            }
        }
    }

    static {
        if (OIApplicationContext.getContext() instanceof Application) {
            ((Application) OIApplicationContext.getContext()).registerActivityLifecycleCallbacks(callbacks);
        }
    }


}
