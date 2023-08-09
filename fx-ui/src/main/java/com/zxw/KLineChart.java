package com.zxw;

import javafx.application.Application;
import javafx.scene.Scene;  
import javafx.scene.chart.LineChart;  
import javafx.scene.chart.NumberAxis;  
import javafx.scene.chart.XYChart;  
import javafx.stage.Stage;  
  
public class KLineChart extends Application {  
  
    private static final double[] VALUES = {1, 3, 6, 2, 7, 5}; // 只是一个示例，你需要根据实际情况更新这些数据  
  
    @Override  
    public void start(Stage stage) {  
        stage.setTitle("K线图");  
        final NumberAxis xAxis = new NumberAxis();  
        final NumberAxis yAxis = new NumberAxis();  
        xAxis.setLabel("时间");  
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);  
        lineChart.setTitle("K线图");  
        XYChart.Series series = new XYChart.Series();  
        series.setName("K线数据");  
        // 根据实际情况添加数据点  
        for (int i = 0; i < VALUES.length; i++) {  
            series.getData().add(new XYChart.Data<>(i, VALUES[i]));  
        }  
        Scene scene  = new Scene(lineChart,800,600);  
        lineChart.getData().add(series);  
        stage.setScene(scene);  
        stage.show();  
    }  
  
    public static void main(String[] args) {  
        launch(args);  
    }  
}