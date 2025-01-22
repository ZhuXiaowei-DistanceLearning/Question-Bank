package com.zxw.utils.fund;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DraggableChart extends Application {

    private double mouseX;
    private double mouseY;
    private double translateX = 0;
    private double translateY = 0;
    private double scale = 1;

    public static String[] dates;
    public static Map<String, List<Double>> result;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawChart(gc); // 初始绘制图表

        // 拖动事件处理
        canvas.setOnMousePressed(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            double deltaX = event.getX() - mouseX;
            double deltaY = event.getY() - mouseY;
            translateX += deltaX;
            translateY += deltaY;
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // 清除画布
            gc.translate(deltaX, deltaY); // 平移画布
            drawChart(gc); // 重新绘制图表
            gc.translate(-deltaX, -deltaY); // 恢复画布平移
            mouseX = event.getX();
            mouseY = event.getY();
        });

        // 缩放事件处理 (使用鼠标滚轮)
        canvas.setOnScroll(event -> {
            double delta = event.getDeltaY();
            scale += delta * 0.01; // 调整缩放比例
            if (scale < 0.1) {
                scale = 0.1; // 限制最小缩放
            }
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // 清除画布
            gc.scale(scale, scale); // 缩放画布
            drawChart(gc); // 重新绘制图表
            gc.scale(1 / scale, 1 / scale);
            event.consume();
        });


        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("可拖动和缩放的图表");
        primaryStage.show();
    }

    private void drawChart(GraphicsContext gc) {
        // 在此处使用 GraphicsContext 绘制图表
        // 可以根据 result 中的数据绘制折线图
        // 注意要考虑平移 (translateX, translateY) 和缩放 (scale)
        if (result != null && dates != null) {
            List<Double> totalInvestments = result.get("totalInvestments");
            List<Double> totalValues = result.get("totalValues");
            List<Double> profitAmounts = result.get("profitAmounts");
            List<Double> profitRates = result.get("profitRates");
            double xSpacing = 20;
            double ySpacing = 20;
            double startX = 50;
            double startY = 750;

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd");

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(startX, startY, startX + dates.length * xSpacing, startY);
            gc.strokeLine(startX, startY, startX, startY - 200);

            gc.setLineWidth(2);
            for (int i = 0; i < totalInvestments.size(); i++) {
                try {
                    LocalDate date = LocalDate.parse(dates[i], inputFormatter);
                    String formattedDate = date.format(outputFormatter);

                    gc.fillText(formattedDate, startX + (i + 1) * xSpacing - 10, startY + 15);

                    gc.setStroke(Color.BLUE);
                    if (i > 0) {
                        gc.strokeLine(startX + i * xSpacing, startY - totalInvestments.get(i - 1) / 20, startX + (i + 1) * xSpacing, startY - totalInvestments.get(i) / 20);
                    }

                    gc.setStroke(Color.RED);
                    if (i > 0) {
                        gc.strokeLine(startX + i * xSpacing, startY - totalValues.get(i - 1) / 20, startX + (i + 1) * xSpacing, startY - totalValues.get(i) / 20);
                    }

                    gc.setStroke(Color.GREEN);
                    if (i > 0) {
                        gc.strokeLine(startX + i * xSpacing, startY - profitAmounts.get(i - 1) * 2, startX + (i + 1) * xSpacing, startY - profitAmounts.get(i) * 2);
                    }

                    gc.setStroke(Color.YELLOW);
                    if (i > 0) {
                        gc.strokeLine(startX + i * xSpacing, startY - profitRates.get(i - 1) * 2, startX + (i + 1) * xSpacing, startY - profitRates.get(i) * 2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static void showChart(String[] dates, Map<String, List<Double>> result, String[] args) { // 添加 args 参数
        DraggableChart.dates = dates;
        DraggableChart.result = result;
        launch(); // 传递 args 参数
    }
}