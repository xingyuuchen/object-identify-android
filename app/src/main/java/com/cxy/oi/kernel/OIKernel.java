package com.cxy.oi.kernel;

import com.cxy.oi.kernel.modelbase.IPlugin;
import com.cxy.oi.kernel.network.NetSceneQueue;
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
            throw new NullPointerException("[storage] coreStorage == null");
        }
        return coreStorage;
    }

    public static CoreNetwork network() {
        if (coreNetwork == null) {
            throw new NullPointerException("[network] coreNetwork == null");
        }
        return coreNetwork;
    }

    public static <T extends IPlugin> T plugin(Class<T> clazz) {
        return findPlugin(clazz);
    }

    private static <T extends IPlugin> T findPlugin(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException(
                    "[findPlugin] plugin class must be an Interface, given " + clazz.getName());
        }
        for (Map.Entry<Class<? extends IPlugin>, IPlugin> plugin : pluginMap.entrySet()) {
            if (plugin.getKey() == clazz) {
                return (T) plugin.getValue();
            }
        }
        throw new IllegalStateException("[findPlugin] cannot found plugin "
                + clazz.getName() + ", please make sure it has been installed");
    }


    static <T extends IPlugin> T installPlugin(Class<T> clazz) {
        if (clazz.isInterface()) {
            throw new IllegalArgumentException("[installPlugin] plugin class must NOT be an Interface," +
                    " need the implementation class, given " + clazz.getName());
        }
        if (!IPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(
                    "[installPlugin] plugin class must be an IPlugin, given " + clazz.getName());
        }
        if (pluginMap.containsKey(clazz)) {
            Log.i(TAG, "[installPlugin] already installed plugin %s, do NOT REInstall", clazz.getName());
            return findPlugin(clazz);
        }
        try {
            T plugin = clazz.newInstance();
            plugin.doWhenBoot();
            pluginMap.put(clazz, plugin);
            installAlias(clazz, plugin);
            return plugin;

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            Log.printErrStackTrace(TAG, e, "[installPlugin]");
        }
        return null;
    }

    private static <T extends IPlugin> void installAlias(Class<T> clazz, T plugin) {
        for (Class<?> theInterface : clazz.getInterfaces()) {
            if (IPlugin.class.isAssignableFrom(theInterface)) {
                pluginMap.put((Class<? extends IPlugin>) theInterface, plugin);
            }
        }
    }

    public static NetSceneQueue getNetSceneQueue() {
        return network().getNetSceneQueue();
    }

    public static void makeKernel() {
        coreNetwork = new CoreNetwork();
        coreStorage = new CoreStorage();

        DefaultBootStep.installPlugins();
    }
}
