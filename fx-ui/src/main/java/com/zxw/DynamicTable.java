package com.zxw;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DynamicTable extends Application {

    // 传入的表头列表
    private List<String> headerList;

    // 传入的数据列表
    private List<List<String>> dataList;

    // 表格对象
    private TableView<DynamicData> tableView;

    public DynamicTable(List<String> headerList, List<List<String>> dataList) {
        this.headerList = headerList;
        this.dataList = dataList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建表格对象
        tableView = new TableView<>();

        // 添加列
        for (int i = 0; i < headerList.size(); i++) {
            String header = headerList.get(i);
            TableColumn<DynamicData, String> column = new TableColumn<>(header);
            int finalI = i;
//            column.setCellValueFactory(cellData -> getCellValue(cellData.getValue(), finalI));
            tableView.getColumns().add(column);
        }

        // 填充数据
        ObservableList<DynamicData> data = FXCollections.observableArrayList();
        for (List<String> row : dataList) {
            data.add(new DynamicData(row));
        }
        tableView.setItems(data);

        // 创建布局
        VBox root = new VBox();
        root.getChildren().add(tableView);

        // 创建场景
        Scene scene = new Scene(root, 600, 400);

        // 设置舞台
        primaryStage.setTitle("Dynamic Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 获取单元格值的方法
    public String getCellValue(DynamicData data, int index) {
        try {
            // 使用反射机制获取字段名
            String fieldName = "field" + index;

            // 使用反射机制获取字段对象
            Field field = data.getClass().getDeclaredField(fieldName);

            // 使用反射机制获取字段值
            return (String) field.get(data);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 自定义的类，用于封装数据列表
    public static class DynamicData {

        // 动态创建字段，根据数据列表的大小
        public String field0;
        public String field1;
        public String field2;
        public String field3;
        public String field4;
        public String field5;
        public String field6;
        public String field7;
        public String field8;
        public String field9;

        // 构造方法，根据数据列表初始化字段值
        public DynamicData(List<String> dataList) {
            for (int i = 0; i < dataList.size(); i++) {
                try {
                    // 使用反射机制获取字段名
                    String fieldName = "field" + i;

                    // 使用反射机制获取字段对象
                    Field field = this.getClass().getDeclaredField(fieldName);

                    // 使用反射机制设置字段值
                    field.set(this, dataList.get(i));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // 测试数据
        List<String> headerList = List.of("Name", "Age", "Gender");
        List<List<String>> dataList = new ArrayList<>();
        dataList.add(List.of("Alice", "20", "Female"));
        dataList.add(List.of("Bob", "25", "Male"));
        dataList.add(List.of("Charlie", "30", "Male"));

        // 启动程序
        DynamicTable dynamicTable = new DynamicTable(headerList, dataList);
        launch(dynamicTable.getClass());
    }
}
