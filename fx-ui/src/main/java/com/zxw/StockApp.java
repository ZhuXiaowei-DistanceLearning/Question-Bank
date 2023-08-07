package com.zxw;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Random;

public class StockApp extends Application {
    public static void main(String[] args) {
        // 启动应用程序
        launch(args);
    }


    // 创建一个用于存储股票数据的内部类
    public static class Stock {
        private final SimpleStringProperty code; // 股票代码
        private final SimpleStringProperty name; // 股票名称
        private final SimpleStringProperty price; // 最新价
        private final SimpleStringProperty change; // 涨跌幅
        private final SimpleStringProperty volume; // 成交量
        private final SimpleStringProperty amount; // 成交额

        public Stock(String code, String name, String price, String change, String volume, String amount) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.change = new SimpleStringProperty(change);
            this.volume = new SimpleStringProperty(volume);
            this.amount = new SimpleStringProperty(amount);
        }

        public String getCode() {
            return code.get();
        }

        public void setCode(String code) {
            this.code.set(code);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getPrice() {
            return price.get();
        }

        public void setPrice(String price) {
            this.price.set(price);
        }

        public String getChange() {
            return change.get();
        }

        public void setChange(String change) {
            this.change.set(change);
        }

        public String getVolume() {
            return volume.get();
        }

        public void setVolume(String volume) {
            this.volume.set(volume);
        }

        public String getAmount() {
            return amount.get();
        }

        public void setAmount(String amount) {
            this.amount.set(amount);
        }
    }

    @Override
        public void start(Stage stage) throws Exception {

        // 创建一个用于存储股票数据的可观察列表
        ObservableList<Stock> data = FXCollections.observableArrayList();

        // 创建一个表格视图，并设置数据源为可观察列表
        TableView<Stock> table = new TableView<>(data);

        // 创建一个用于显示股票代码的列，并设置单元格值工厂为股票代码属性
        TableColumn<Stock, String> codeColumn = new TableColumn<>("股票代码");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        // 创建一个用于显示股票名称的列，并设置单元格值工厂为股票名称属性
        TableColumn<Stock, String> nameColumn = new TableColumn<>("股票名称");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // 创建一个用于显示最新价的列，并设置单元格值工厂为最新价属性
        TableColumn<Stock, String> priceColumn = new TableColumn<>("最新价");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // 创建一个用于显示涨跌幅的列，并设置单元格值工厂为涨跌幅属性
        TableColumn<Stock, String> changeColumn = new TableColumn<>("涨跌幅");
        changeColumn.setCellValueFactory(new PropertyValueFactory<>("change"));

        // 创建一个用于显示成交量的列，并设置单元格值工厂为成交量属性
        TableColumn<Stock, String> volumeColumn = new TableColumn<>("成交量");
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));

        // 创建一个用于显示成交额的列，并设置单元格值工厂为成交额属性
        TableColumn<Stock, String> amountColumn = new TableColumn<>("成交额");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // 将所有的列添加到表格视图中
        table.getColumns().addAll(codeColumn, nameColumn, priceColumn, changeColumn, volumeColumn, amountColumn);

        // 创建一个垂直布局容器，并将表格视图添加到其中
        VBox root = new VBox(table);

        // 创建一个场景，并将垂直布局容器添加到其中
        Scene scene = new Scene(root);

        // 设置舞台的标题、场景和可见性
        stage.setTitle("股票数据观测");
        stage.setScene(scene);
        stage.show();

        // 模拟实时推送数据，每隔一秒添加或更新一条数据
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
            // 创建一个随机数生成器
            Random random = new Random();
            // 生成一个随机的股票代码，格式为000xxx
            String code = String.format("000%03d", random.nextInt(1000));
            // 生成一个随机的股票名称，格式为XX科技
            String name = String.format("%s科技", (char) (random.nextInt(26) + 'A'));
            // 生成一个随机的最新价，范围在1-100之间，保留两位小数
            String price = String.format("%.2f", random.nextDouble() * 100);
            // 生成一个随机的涨跌幅，范围在-10%到+10%之间，保留两位小数，带正负号和百分号
            String change = String.format("%+.2f%%", random.nextDouble() * 20 - 10);
            // 生成一个随机的成交量，范围在1-10亿之间，保留两位小数，带单位亿
            String volume = String.format("%.2f亿", random.nextDouble() * 10);
            // 生成一个随机的成交额，范围在1-100亿之间，保留两位小数，带单位亿
            String amount = String.format("%.2f亿", random.nextDouble() * 100);
            // 创建一个股票对象，用于存储生成的数据
            Stock stock = new Stock(code, name, price, change, volume, amount);
            // 判断可观察列表中是否已经存在相同代码的股票
            boolean exists = false;
            for (Stock s : data) {
                if (s.getCode().equals(code)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                // 如果已经存在相同代码的股票，就更新它的数据
                data.forEach(s -> {
                    if (s.getCode().equals(code)) {
                        s.setName(name);
                        s.setPrice(price);
                        s.setChange(change);
                        s.setVolume(volume);
                        s.setAmount(amount);
                    }
                });
            } else {
                // 如果不存在相同代码的股票，就添加它到可观察列表中
                data.add(stock);
            }
        }));
        // 设置定时器的循环次数为无限次
        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        // 启动定时器
        timeline.play();
    }
}