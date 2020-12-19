package com.cxy.oi.kernel;

import com.cxy.oi.kernel.util.Log;

import java.util.HashMap;
import java.util.Map;

public class OIKernel {
    private static final String TAG = "OIKernel";


    private static CoreStorage coreStorage;
    private static CoreNetwork coreNetwork;
    private static final Map<Class<? extends IPlugin>, IPlugin> pluginMap = new HashMap<>();

    public static CoreStorage storage() {
        if (coreStorage == null) {
            throw new NullPointerException("coreStorage == null");
        }
        return coreStorage;
    }

    public static CoreNetwork network() {
        if (coreNetwork == null) {
            throw new NullPointerException("coreNetwork == null");
        }
        return coreNetwork;
    }

    public static <T extends IPlugin> T plugin(Class<T> clazz) {
        return findPlugin(clazz);
    }

    private static <T extends IPlugin> T findPlugin(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("[findPlugin] plugin class must be an Interface, given " + clazz.getName());
        }
        for (Map.Entry<Class<? extends IPlugin>, IPlugin> plugin : pluginMap.entrySet()) {
            if (plugin.getKey() == clazz) {
                return (T) plugin.getValue();
            }
        }
        Log.e(TAG, "cannot found plugin %s, please make sure it has been installed", clazz.getName());
        return null;
    }


    static <T extends IPlugin> void installPlugin(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("[installPlugin] plugin class must be an Interface, given " + clazz.getName());
        }
        if (pluginMap.containsKey(clazz)) {
            Log.e(TAG, "[installPlugin] already installed plugin %s, do NOT reinstall", clazz.getName());
            return;
        }
        try {
            IPlugin plugin = clazz.newInstance();
            plugin.doWhenBoot();
            pluginMap.put(clazz, plugin);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            Log.printErrStackTrace(TAG, e, "[installPlugin]");
        }
    }



    public static void makeKernel() {
        coreNetwork = new CoreNetwork();
        coreStorage = new CoreStorage();

        DefaultBootStep.installPlugins();
    }
}
