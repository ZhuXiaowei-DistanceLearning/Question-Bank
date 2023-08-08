package com.zxw;// 导入需要的包
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// 创建一个继承Application的类
public class KLineChart extends Application {

    // 创建一个存储K线数据的类
    public static class KLineData {
        private final String date; // 日期
        private final double open; // 开盘价
        private final double high; // 最高价
        private final double low; // 最低价
        private final double close; // 收盘价

        // 构造方法
        public KLineData(String date, double open, double high, double low, double close) {
            this.date = date;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
        }

        // 获取日期
        public String getDate() {
            return date;
        }

        // 获取开盘价
        public double getOpen() {
            return open;
        }

        // 获取最高价
        public double getHigh() {
            return high;
        }

        // 获取最低价
        public double getLow() {
            return low;
        }

        // 获取收盘价
        public double getClose() {
            return close;
        }
    }

    // 创建一个存储K线数据的列表
    private ObservableList<KLineData> kLineDataList = FXCollections.observableArrayList();

    // 创建一个更新K线数据的方法，参数为日期，开盘价，最高价，最低价，收盘价
    public void updateKLineData(String date, double open, double high, double low, double close) {
        // 创建一个KLineData对象
        KLineData kLineData = new KLineData(date, open, high, low, close);
        // 将对象添加到列表中
        kLineDataList.add(kLineData);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 设置舞台标题
        stage.setTitle("K线图示例");

        // 创建一个类别轴，用于显示日期
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("日期");

        // 创建一个数值轴，用于显示价格
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("价格");

        // 创建一个蜡烛图，用于显示K线图，并指定X和Y的类型为String和Number
        final CandleStickChart<String, Number> candleStickChart = new CandleStickChart<>(xAxis, yAxis);
        candleStickChart.setTitle("股票价格走势");

        // 调用更新K线数据的方法，添加一些示例数据（您可以根据实际情况修改或删除）
        updateKLineData("2021-08-01", 100.0, 110.0, 90.0, 105.0);
        updateKLineData("2021-08-02", 105.0, 115.0, 95.0, 100.0);
        updateKLineData("2021-08-03", 100.0, 120.0, 80.0, 90.0);
        updateKLineData("2021-08-04", 90.0, 100.0, 85.0, 95.0);
        updateKLineData("2021-08-05", 95.0, 105.0, 75.0, 80.0);

        // 将列表中的数据转换为Series对象，并添加到蜡烛图中
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (KLineData kLineData : kLineDataList) {
            series.getData().add(new XYChart.Data<>(kLineData.getDate(), kLineData.getClose(), kLineData));
        }

        ObservableList<XYChart.Series<String, Number>> data = candleStickChart.getData();
         if(data == null){
            data = FXCollections.observableArrayList();
        }
        data.add(series);

        // 创建一个堆栈面板，用于容纳蜡烛图
        StackPane root = new StackPane();
        root.getChildren().add(candleStickChart);

        // 创建一个场景，用于显示堆栈面板
        Scene scene = new Scene(root, 800, 600);

        // 将场景添加到舞台中，并显示舞台
        stage.setScene(scene);
        stage.show();
    }

    // 主方法，启动应用程序
    public static void main(String[] args) {
      launch(args);
    }
}

// 创建一个继承XYChart的类，用于实现蜡烛图
// 创建一个继承XYChart的类，用于实现蜡烛图
class CandleStickChart<X, Y> extends XYChart<X, Y> {

    // 构造方法，调用父类的构造方法
    public CandleStickChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
    }

    // 重写布局图表的方法，用于绘制K线图
    @Override
    protected void layoutPlotChildren() {
        // 遍历所有的数据
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            // 获取当前的数据系列
            Series<X, Y> series = getData().get(seriesIndex);
            // 获取当前的数据项
            ObservableList<Data<X, Y>> data = series.getData();
            // 遍历所有的数据项
            for (int itemIndex = 0; itemIndex < data.size(); itemIndex++) {
                // 获取当前的数据项
                Data<X, Y> item = data.get(itemIndex);
                // 获取当前的K线数据
                KLineChart.KLineData kLineData = (KLineChart.KLineData) item.getExtraValue();
                // 如果K线数据不为空
                if (kLineData != null) {
                    // 获取X轴和Y轴
                    Axis<X> xAxis = getXAxis();
                    Axis<Y> yAxis = getYAxis();

                    // 计算X轴和Y轴的长度和位置
                    double x = xAxis.getDisplayPosition(item.getXValue());
                    double y = yAxis.getDisplayPosition(item.getYValue());
                    double candleWidth = 10; // 设置蜡烛的宽度
                    double open = yAxis.getDisplayPosition((Y) (Number) kLineData.getOpen());
                    double close = yAxis.getDisplayPosition((Y) (Number) kLineData.getClose());
                    double high = yAxis.getDisplayPosition((Y) (Number) kLineData.getHigh());
                    double low = yAxis.getDisplayPosition((Y) (Number) kLineData.getLow());

                    // 创建一个组，用于存放蜡烛和影线
                    Group candle = (Group) item.getNode();
                    // 如果组为空，则创建一个新的组，并添加到数据项中
                    if (candle == null) {
                        candle = new Group();
                        item.setNode(candle);
                    }
                    // 创建一个矩形，用于表示蜡烛
                    Rectangle rect = (Rectangle) candle.getChildren().get(0);
                    // 如果矩形为空，则创建一个新的矩形，并添加到组中
                    if (rect == null) {
                        rect = new Rectangle(candleWidth, 0);
                        candle.getChildren().add(rect);
                    }
                    // 创建一条线，用于表示影线
                    Line line = (Line) candle.getChildren().get(1);
                    // 如果线为空，则创建一条新的线，并添加到组中
                    if (line == null) {
                        line = new Line(0, 0, 0, 0);
                        candle.getChildren().add(line);
                    }

                    // 设置蜡烛和影线的颜色，根据开盘价和收盘价的大小判断是阳线还是阴线
                    if (kLineData.getOpen() > kLineData.getClose()) {
                        rect.setFill(Color.GREEN); // 阴线为绿色
                        line.setStroke(Color.GREEN); // 影线为绿色
                    } else {
                        rect.setFill(Color.RED); // 阳线为红色
                        line.setStroke(Color.RED); // 影线为红色
                    }

                    // 设置蜡烛和影线的位置和大小
                    rect.setX(x - candleWidth / 2);
                    rect.setY(y - candleWidth / 2);
                    line.setStartX(x);
                    line.setStartY(high);
                    line.setEndX(x);
                    line.setEndY(low);

                }
            }
        }
    }

    @Override
    protected void dataItemChanged(Data<X, Y> item) {

    }

    @Override
    protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {

    }

    @Override
    protected void dataItemRemoved(Data<X, Y> item, Series<X, Y> series) {

    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex) {

    }

    @Override
    protected void seriesRemoved(Series<X, Y> series) {

    }
}