package com.cxy.oi.kernel;

import com.cxy.oi.plugin_storage.IPluginStorage;

import static com.cxy.oi.kernel.OIKernel.installPlugin;



public class DefaultBootStep {


    /**
     *
     * add new PLUGINS to install here
     *
     */
    public static void installPlugins() {

        installPlugin(IPluginStorage.class);


    }

}
