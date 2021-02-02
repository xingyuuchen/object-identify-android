package com.cxy.oi.kernel;

import com.cxy.oi.plugin_chart.PluginChart;
import com.cxy.oi.plugin_storage.PluginStorage;

import static com.cxy.oi.kernel.OIKernel.installPlugin;



public class DefaultBootStep {


    /**
     *
     * add new PLUGINS to install here
     *
     */
    public static void installPlugins() {

        installPlugin(PluginStorage.class);
        installPlugin(PluginChart.class);

    }

}
