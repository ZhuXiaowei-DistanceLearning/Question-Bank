package com.zxw;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FlowPaneExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建一个FlowPane对象
        FlowPane flowPane = new FlowPane();

        // 设置对齐方式
        flowPane.setAlignment(Pos.CENTER);

        // 设置间隔
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        // 设置内边距
        flowPane.setPadding(new Insets(10, 10, 10, 10));

        // 设置方向
        flowPane.setOrientation(Orientation.HORIZONTAL);

        // 添加子组件
        for (int i = 1; i <= 9; i++) {
            Button button = new Button("Button " + i);
            flowPane.getChildren().add(button);
        }

        // 添加一个标签
        Label label = new Label("This is a FlowPane example");
        flowPane.getChildren().add(label);

        // 创建一个场景
        Scene scene = new Scene(flowPane, 400, 300);

        // 设置舞台
        primaryStage.setTitle("FlowPane Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
