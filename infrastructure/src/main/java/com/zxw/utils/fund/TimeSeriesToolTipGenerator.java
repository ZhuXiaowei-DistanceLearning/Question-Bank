package com.zxw.utils.fund;

import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// 自定义 TimeSeriesToolTipGenerator
public class TimeSeriesToolTipGenerator implements XYToolTipGenerator {
    private final SimpleDateFormat dateFormat;
    private final DecimalFormat valueFormat;

    public TimeSeriesToolTipGenerator(SimpleDateFormat dateFormat, DecimalFormat valueFormat) {
        this.dateFormat = dateFormat;
        this.valueFormat = valueFormat;
    }

    @Override
    public String generateToolTip(XYDataset dataset, int series, int item) {
        if (dataset instanceof TimeSeriesCollection) {
            TimeSeriesCollection timeSeriesCollection = (TimeSeriesCollection) dataset;
            TimeSeries seriesData = timeSeriesCollection.getSeries(series);
            TimeSeriesDataItem dataItem = seriesData.getDataItem(item);
            if (dataItem != null) {
                Date date = dataItem.getPeriod().getStart();
                double value = dataItem.getValue().doubleValue();
                if (seriesData.getKey().toString().equals("收益率(%)")) {
                    return seriesData.getKey() + ": (" + dateFormat.format(date) + ", " + valueFormat.format(value) + "%)";

                } else {
                    return seriesData.getKey() + ": (" + dateFormat.format(date) + ", " + valueFormat.format(value) + ")";
                }

            }
        }
        return null;
    }
}