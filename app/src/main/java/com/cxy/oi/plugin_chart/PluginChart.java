package com.cxy.oi.plugin_chart;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;

import java.util.List;


public class PluginChart implements IPluginChart {
    private static final String TAG = "PluginChart";


    @Override
    public void doWhenBoot() {

    }

    @Override
    public View createTrainProgressChartView(Context context, String title, String subtitle, List<Float> hitRates) {
        if (context == null || Util.isNullOrNil(title) || Util.isNullOrNil(subtitle)) {
            Log.e(TAG, "[createTrainProgressChartView] params illegal");
            return null;
        }
        LinearLayout lv = (LinearLayout) View.inflate(context, R.layout.train_progress_chart, null);
        AAChartView chart = lv.findViewById(R.id.progress_chart);
        TextView trainProgressTv = lv.findViewById(R.id.train_progress_tv);

        trainProgressTv.setText(title);
        Object[] hitRateArr = hitRates.toArray();
        if (hitRateArr == null) {
            return lv;
        }

        AAChartModel model = new AAChartModel();
        AASeriesElement[] elements = new AASeriesElement[1];
        elements[0] = new AASeriesElement().name("Training Epoch").data(hitRateArr);
        model.chartType(AAChartType.Area)
                .title(subtitle)
//                .subtitle(subtitle)
                .backgroundColor("#08D160")
                .dataLabelsEnabled(true)
                .series(elements)
                .setYAxisTitle("Accuracy");
        chart.aa_drawChartWithChartModel(model);
        return lv;
    }

}
