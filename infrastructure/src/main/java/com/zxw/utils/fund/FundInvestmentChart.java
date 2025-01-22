package com.zxw.utils.fund;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FundInvestmentChart {

    public static BigDecimal investmentAmount = BigDecimal.valueOf(100); // 假设每次定投 1000 元

    public static String dates[] = {"2024-05-30","2024-05-31","2024-06-03","2024-06-04","2024-06-05","2024-06-06","2024-06-07","2024-06-11","2024-06-12","2024-06-13","2024-06-14","2024-06-17","2024-06-18","2024-06-19","2024-06-20","2024-06-21","2024-06-24","2024-06-25","2024-06-26","2024-06-27","2024-06-28","2024-06-30","2024-07-01","2024-07-02","2024-07-03","2024-07-04","2024-07-05","2024-07-08","2024-07-09","2024-07-10","2024-07-11","2024-07-12","2024-07-15","2024-07-16","2024-07-17","2024-07-18","2024-07-19","2024-07-22","2024-07-23","2024-07-24","2024-07-25","2024-07-26","2024-07-29","2024-07-30","2024-07-31","2024-08-01","2024-08-02","2024-08-05","2024-08-06","2024-08-07","2024-08-08","2024-08-09","2024-08-12","2024-08-13","2024-08-14","2024-08-15","2024-08-16","2024-08-19","2024-08-20","2024-08-21","2024-08-22","2024-08-23","2024-08-26","2024-08-27","2024-08-28","2024-08-29","2024-08-30","2024-09-02","2024-09-03","2024-09-04","2024-09-05","2024-09-06","2024-09-09","2024-09-10","2024-09-11","2024-09-12","2024-09-13","2024-09-18","2024-09-19","2024-09-20","2024-09-23","2024-09-24","2024-09-25","2024-09-26","2024-09-27","2024-09-30","2024-10-08","2024-10-09","2024-10-10","2024-10-11","2024-10-14","2024-10-15","2024-10-16","2024-10-17","2024-10-18","2024-10-21","2024-10-22","2024-10-23","2024-10-24","2024-10-25","2024-10-28","2024-10-29","2024-10-30","2024-10-31","2024-11-01","2024-11-04","2024-11-05","2024-11-06","2024-11-07","2024-11-08","2024-11-11","2024-11-12","2024-11-13","2024-11-14","2024-11-15","2024-11-18","2024-11-19","2024-11-20","2024-11-21","2024-11-22","2024-11-25","2024-11-26","2024-11-27","2024-11-28","2024-11-29","2024-12-02","2024-12-03","2024-12-04","2024-12-05","2024-12-06","2024-12-09","2024-12-10","2024-12-11","2024-12-12","2024-12-13","2024-12-16","2024-12-17","2024-12-18","2024-12-19","2024-12-20","2024-12-23","2024-12-24","2024-12-25","2024-12-26","2024-12-27","2024-12-30","2024-12-31","2025-01-02","2025-01-03","2025-01-06","2025-01-07","2025-01-08","2025-01-09","2025-01-10","2025-01-13","2025-01-14","2025-01-15","2025-01-16","2025-01-17","2025-01-20"
    };

    public static double[] netValues = {1.5679,1.5671,1.5723,1.5766,1.6074,1.6065,1.6051,1.6208,1.6411,1.6497,1.6573,1.6766,1.6770,1.6756,1.6638,1.6597,1.6418,1.6604,1.6634,1.6670,1.6584,1.6584,1.6685,1.6835,1.6980,1.6978,1.7126,1.7147,1.7162,1.7342,1.6931,1.7016,1.7042,1.7052,1.6572,1.6490,1.6351,1.6600,1.6543,1.5977,1.5816,1.5961,1.6008,1.5811,1.6266,1.5885,1.5543,1.5097,1.5242,1.5101,1.5558,1.5635,1.5667,1.6046,1.6044,1.6417,1.6448,1.6642,1.6584,1.6663,1.6380,1.6591,1.6378,1.6455,1.6263,1.6261,1.6419,1.6395,1.5925,1.5904,1.5876,1.5458,1.5662,1.5830,1.6168,1.6324,1.6356,1.6184,1.6608,1.6490,1.6510,1.6578,1.6526,1.6675,1.6529,1.6563,1.6751,1.6844,1.6863,1.6886,1.7008,1.6812,1.6911,1.6928,1.7044,1.6996,1.7068,1.6824,1.6969,1.7015,1.7066,1.7220,1.7114,1.6686,1.6773,1.6733,1.6899,1.7332,1.7743,1.7704,1.7777,1.7765,1.7752,1.7628,1.7232,1.7328,1.7446,1.7437,1.7496,1.7525,1.7540,1.7633,1.7509,1.7487,1.7632,1.7815,1.7900,1.8094,1.8030,1.8176,1.8030,1.7979,1.8281,1.8165,1.8290,1.8543,1.8470,1.7828,1.7753,1.7885,1.8060,1.8302,1.8300,1.8264,1.8018,1.7799,1.7640,1.7603,1.7867,1.8050,1.7741,1.7747,1.7747,1.7471,1.7419,1.7373,1.7747,1.7617,1.7896,1.7885};

    // 日期格式化
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws ParseException {
        // 示例数据，实际可以从文件或API读取
        // 定投100元
        double investAmountPerWeek = 1000;

        // 调用方法生成收益数据集
        DefaultCategoryDataset dataset = createDataset(dates, netValues, investAmountPerWeek);

        // 创建图表
        JFreeChart chart = ChartFactory.createLineChart(
                "Fund Investment Growth", // 图表标题
                "Date", // 横坐标
                "Amount (CNY)", // 纵坐标
                dataset, // 数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 工具提示
                false // URL
        );

        // 显示图表
        JFrame frame = new JFrame("Fund Investment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    // 创建数据集
    private static DefaultCategoryDataset createDataset(String[] dates, double[] netValues, double investAmountPerWeek) throws ParseException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double totalInvestment = 0;
        double totalShares = 0;

        for (int i = 0; i < dates.length; i++) {
            Date date = dateFormat.parse(dates[i]);

            // 每周三定投
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // 如果是星期三，进行定投
            if (dayOfWeek == Calendar.WEDNESDAY) {
                totalInvestment += investAmountPerWeek; // 更新总投资金额
                totalShares += investAmountPerWeek / netValues[i]; // 买入的份额
            }

            // 计算当前市值
            double totalValue = totalShares * netValues[i];

            // 计算纯收益
            double pureProfit = totalValue - totalInvestment;

            // 将总市值、总投资金额和纯收益加入数据集
            dataset.addValue(totalValue, "Total Value", dates[i]);
            dataset.addValue(totalInvestment, "Total Investment", dates[i]);
            dataset.addValue(pureProfit, "Pure Profit", dates[i]);
        }

        return dataset;
    }
}
