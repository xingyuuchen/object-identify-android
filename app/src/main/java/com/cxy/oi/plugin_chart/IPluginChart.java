package com.cxy.oi.plugin_chart;

import android.content.Context;
import android.view.View;

import com.cxy.oi.kernel.modelbase.IPlugin;

import java.util.List;


public interface IPluginChart extends IPlugin {

    View createTrainProgressChartView(Context context, String title, String subtitle, List<Float> hitRates);

}
