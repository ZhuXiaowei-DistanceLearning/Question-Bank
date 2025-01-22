package com.zxw.utils.fund;

import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FundInvestmentCalculator {

    public static void main(String[] args) throws ParseException {
        String[] dates = FundInvestmentChart.dates;
        double[] prices = FundInvestmentChart.netValues;
        int investmentDayOfWeek = Calendar.TUESDAY; // 星期日
        String startDateStr = "2024-10-16";
        List<FundInvestmentCalculator.InvestmentData> investmentDataList = FundInvestmentCalculator.calculateInvestment(dates, prices, investmentDayOfWeek, startDateStr);
        FundInvestmentCalculator.createCombinedCharts(investmentDataList);
    }

    public static void createCombinedCharts(List<FundInvestmentCalculator.InvestmentData> investmentDataList) {
        // 设置全局字体
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        chartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
        chartTheme.setLargeFont(new Font("宋体", Font.BOLD, 18));
        chartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 16));
        ChartFactory.setChartTheme(chartTheme);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // 创建 TimeSeries 对象
        TimeSeries totalInvestmentSeries = new TimeSeries("总投资");
        TimeSeries totalValueSeries = new TimeSeries("总价值");
        TimeSeries profitSeries = new TimeSeries("收益");
        TimeSeries profitRateSeries = new TimeSeries("收益率(%)");

        try {
            for (FundInvestmentCalculator.InvestmentData data : investmentDataList) {
                Date date = sdf.parse(data.getDate());
                totalInvestmentSeries.add(new Day(date), data.getTotalInvestment().doubleValue());
                totalValueSeries.add(new Day(date), data.getTotalValue().doubleValue());
                profitSeries.add(new Day(date), data.getProfit().doubleValue());
                profitRateSeries.add(new Day(date), data.getProfitRate().multiply(BigDecimal.valueOf(100)).doubleValue());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 创建 TimeSeriesCollection 对象
        TimeSeriesCollection combinedDataset = new TimeSeriesCollection();
        combinedDataset.addSeries(totalInvestmentSeries);
        combinedDataset.addSeries(totalValueSeries);

        TimeSeriesCollection profitDataset = new TimeSeriesCollection();
        profitDataset.addSeries(profitSeries);

        TimeSeriesCollection profitRateDataset = new TimeSeriesCollection();
        profitRateDataset.addSeries(profitRateSeries);

        // 创建图表
        JFreeChart combinedChart = ChartFactory.createTimeSeriesChart(
                "总投资 vs 总价值 趋势图", "日期", "金额", combinedDataset, true, true, false);
        XYPlot combinedPlot = combinedChart.getXYPlot();
        XYLineAndShapeRenderer combinedRenderer = new XYLineAndShapeRenderer();
        combinedPlot.setRenderer(combinedRenderer);

        JFreeChart profitChart = ChartFactory.createTimeSeriesChart(
                "收益 趋势图", "日期", "金额", profitDataset, true, true, false);
        XYPlot profitPlot = profitChart.getXYPlot();
        XYLineAndShapeRenderer profitRenderer = new XYLineAndShapeRenderer();
        profitPlot.setRenderer(profitRenderer);

        JFreeChart profitRateChart = ChartFactory.createTimeSeriesChart(
                "收益率 趋势图", "日期", "收益率(%)", profitRateDataset, true, true, false);
        XYPlot profitRatePlot = profitRateChart.getXYPlot();
        XYLineAndShapeRenderer profitRateRenderer = new XYLineAndShapeRenderer();
        profitRatePlot.setRenderer(profitRateRenderer);



        // 创建 ChartPanel 并添加鼠标监听器
        ChartPanel combinedChartPanel = new ChartPanel(combinedChart);
        addMouseListener(combinedChartPanel);
        ChartPanel profitChartPanel = new ChartPanel(profitChart);
        addMouseListener(profitChartPanel);
        ChartPanel profitRateChartPanel = new ChartPanel(profitRateChart);
        addMouseListener(profitRateChartPanel);

        // 创建并显示窗口
        JFrame combinedFrame = new JFrame("总投资 vs 总价值 趋势图");
        combinedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        combinedFrame.setContentPane(combinedChartPanel);
        combinedFrame.pack();
        combinedFrame.setVisible(true);

        JFrame profitFrame = new JFrame("收益 趋势图");
        profitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profitFrame.setContentPane(profitChartPanel);
        profitFrame.pack();
        profitFrame.setVisible(true);

        JFrame profitRateFrame = new JFrame("收益率 趋势图");
        profitRateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profitRateFrame.setContentPane(profitRateChartPanel);
        profitRateFrame.pack();
        profitRateFrame.setVisible(true);
    }


    private static void addMouseListener(ChartPanel chartPanel) {
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                JFreeChart chart = chartPanel.getChart();
                if (chart != null) {
                    XYPlot plot = (XYPlot) chart.getPlot();
                    if (plot != null) {
                        updateTitle(chartPanel, chart);
                    }
                }
            }
        });
    }

    private static void updateTitle(ChartPanel chartPanel, JFreeChart chart) {
        XYPlot plot = (XYPlot) chart.getPlot();
        try {
            Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, chartPanel);

            Rectangle2D dataArea = chartPanel.getScreenDataArea();
            if (dataArea != null) {
                ValueAxis xAxis = plot.getDomainAxis();
                ValueAxis yAxis = plot.getRangeAxis();

                double mouseX = xAxis.java2DToValue(p.getX(), dataArea, RectangleEdge.BOTTOM);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DecimalFormat valueFormat = new DecimalFormat("#.##");

                String dateString = "";
                String valueString = "";

                XYDataset dataset = plot.getDataset();
                if (dataset instanceof TimeSeriesCollection) {
                    TimeSeriesCollection tsc = (TimeSeriesCollection) dataset;
                    for (int series = 0; series < tsc.getSeriesCount(); series++) {
                        TimeSeries ts = tsc.getSeries(series);
                        TimeSeriesDataItem closestItem = null;
                        long minDistance = Long.MAX_VALUE;

                        for (int i = 0; i < ts.getItemCount(); i++) {
                            TimeSeriesDataItem item = ts.getDataItem(i);
                            Date date = item.getPeriod().getStart();
                            long distance = Math.abs(date.getTime() - (long) mouseX);
                            if (distance < minDistance) {
                                minDistance = distance;
                                closestItem = item;
                            }
                        }
                        if (closestItem != null) {
                            Date date = closestItem.getPeriod().getStart();
                            double value = closestItem.getValue().doubleValue();
                            dateString = dateFormat.format(date);
                            valueString = valueFormat.format(value);
                            String newTitle = chart.getTitle().getText().split(" \\(")[0] + " (日期: " + dateString + ", 值: " + valueString + ")";
                            chart.getTitle().setText(newTitle);
                            return;//找到一个值就返回，只显示一个series的值
                        }
                    }
                }


            }
        } catch (Exception e) {
            chart.getTitle().setText(chart.getTitle().getText().split(" \\(")[0]);
        }
    }

    public static List<InvestmentData> calculateInvestment(String[] dates, double[] prices, int investmentDayOfWeek, String startDateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        List<InvestmentData> investmentDataList = new ArrayList<>();
        BigDecimal totalInvestment = BigDecimal.ZERO;
        BigDecimal totalShares = BigDecimal.ZERO;

        boolean startDayInvested = false; // 标记开始日期是否已投资

        for (int i = 0; i < dates.length; i++) {
            Date currentDate = sdf.parse(dates[i]);
            calendar.setTime(currentDate);

            boolean isInvestmentDay = calendar.get(Calendar.DAY_OF_WEEK) == investmentDayOfWeek;
            boolean isStartDate = currentDate.equals(startDate);

            if (currentDate.compareTo(startDate) >= 0 && (isInvestmentDay || (isStartDate && !startDayInvested))) {
                BigDecimal price = BigDecimal.valueOf(prices[i]);
                BigDecimal sharesPurchased = FundInvestmentChart.investmentAmount.divide(price, 2, RoundingMode.HALF_UP);
                totalShares = totalShares.add(sharesPurchased);
                totalInvestment = totalInvestment.add(FundInvestmentChart.investmentAmount);
                if (isStartDate) startDayInvested = true;
            }

            BigDecimal currentValue = totalShares.multiply(BigDecimal.valueOf(prices[i]));
            BigDecimal profit = currentValue.subtract(totalInvestment);
            BigDecimal profitRate = totalInvestment.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : profit.divide(totalInvestment, 2, RoundingMode.HALF_UP);

            InvestmentData data = new InvestmentData();
            data.setDate(dates[i]);
            data.setTotalInvestment(totalInvestment);
            data.setTotalValue(currentValue);
            data.setProfit(profit);
            data.setProfitRate(profitRate);
            investmentDataList.add(data);
        }
        return investmentDataList;
    }

    public static void createChart(List<FundInvestmentCalculator.InvestmentData> investmentDataList) {
        XYSeries totalInvestmentSeries = new XYSeries("总投资");
        XYSeries totalValueSeries = new XYSeries("总价值");
        XYSeries profitSeries = new XYSeries("收益");
        XYSeries profitRateSeries = new XYSeries("收益率");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (FundInvestmentCalculator.InvestmentData data : investmentDataList) {
            try {
                Date date = sdf.parse(data.getDate());
                totalInvestmentSeries.add(date.getTime(), data.getTotalInvestment().doubleValue());
                totalValueSeries.add(date.getTime(), data.getTotalValue().doubleValue());
                profitSeries.add(date.getTime(), data.getProfit().doubleValue());
                profitRateSeries.add(date.getTime(), data.getProfitRate().multiply(BigDecimal.valueOf(100)).doubleValue()); // 收益率转换为百分比
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(totalInvestmentSeries);
        dataset.addSeries(totalValueSeries);
        dataset.addSeries(profitSeries);
        dataset.addSeries(profitRateSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "基金定投收益图", "日期", "金额/收益率(%)", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();
        DateAxis dateAxis = new DateAxis("日期");
        plot.setDomainAxis(dateAxis);

        NumberAxis rangeAxis = new NumberAxis("金额/收益率(%)");
        plot.setRangeAxis(rangeAxis);

        // 设置线条颜色和粗细
        plot.getRendererForDataset(dataset).setSeriesPaint(0, Color.BLUE); // 总投资
        plot.getRendererForDataset(dataset).setSeriesPaint(1, Color.GREEN); // 总价值
        plot.getRendererForDataset(dataset).setSeriesPaint(2, Color.ORANGE); // 收益
        plot.getRendererForDataset(dataset).setSeriesPaint(3, Color.MAGENTA); // 收益率
        plot.getRendererForDataset(dataset).setSeriesStroke(0, new BasicStroke(2f));
        plot.getRendererForDataset(dataset).setSeriesStroke(1, new BasicStroke(2f));
        plot.getRendererForDataset(dataset).setSeriesStroke(2, new BasicStroke(2f));
        plot.getRendererForDataset(dataset).setSeriesStroke(3, new BasicStroke(2f));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600)); // 设置图表面板大小

        JFrame frame = new JFrame("基金定投收益图");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static class InvestmentData {
        private String date;
        private BigDecimal totalInvestment;
        private BigDecimal totalValue;
        private BigDecimal profit;
        private BigDecimal profitRate;

        // Getters and setters
        // ...getters and setters for all fields
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public BigDecimal getTotalInvestment() {
            return totalInvestment;
        }

        public void setTotalInvestment(BigDecimal totalInvestment) {
            this.totalInvestment = totalInvestment;
        }

        public BigDecimal getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(BigDecimal totalValue) {
            this.totalValue = totalValue;
        }

        public BigDecimal getProfit() {
            return profit;
        }

        public void setProfit(BigDecimal profit) {
            this.profit = profit;
        }

        public BigDecimal getProfitRate() {
            return profitRate;
        }

        public void setProfitRate(BigDecimal profitRate) {
            this.profitRate = profitRate;
        }
    }
}