package  com.zxw.stock;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        // 创建一个窗口
        JFrame frame = new JFrame("股票信息");
        frame.setSize(800, 600); // 设置窗口大小
        frame.setLocationRelativeTo(null); // 设置窗口居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时退出程序

        // 创建一个表头数组
        Object[] columnNames = {"股票代码", "股票名称", "最新价", "涨跌幅", "成交量", "成交额"};
        // 创建一个数据二维数组
        Object[][] rowData = {
                {"000001", "平安银行", "19.85", "+0.51%", "1.23亿", "24.38亿"},
                {"000002", "万科A", "25.01", "-0.16%", "1.07亿", "26.76亿"},
                {"000063", "中兴通讯", "32.00", "+0.63%", "1.11亿", "35.52亿"},
                {"000066", "中国长城", "18.50", "+2.21%", "1.15亿", "21.28亿"},
                {"000100", "TCL科技", "4.80", "+0.42%", "1.02亿", "4.90亿"}
        };
        // 创建一个JTable对象，并传入数据和表头
        JTable table = new JTable(rowData, columnNames);
        // 设置单元格的对齐方式为居中
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        // 创建一个带滚动条的面板，并将JTable对象添加到面板中
        JScrollPane scrollPane = new JScrollPane(table);
        // 将面板添加到窗口中
        frame.add(scrollPane);
        // 设置窗口可见
        frame.setVisible(true);

        // 模拟实时推送数据，每隔一秒添加一行数据
        Timer timer = new Timer(1000, e -> {
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
            // 创建一个一维数组作为一行数据
            Object[] row = {code, name, price, change, volume, amount};
            // 获取表格的数据模型
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // 添加一行数据
            model.addRow(row);
            // 更新表格的数据模型
            table.setModel(model);
        });
        // 启动定时器
        timer.start();
    }
}
