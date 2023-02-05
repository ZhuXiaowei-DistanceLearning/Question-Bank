package com.zxw.utils;

public class IdGenerater {

    public static void main(String[] args) throws Exception {
        IdGenerater worker = new IdGenerater(1, 1, 1);
        for (int i = 0; i < 5; i++) {
            long id = worker.nextId();
            System.out.println("id为：" + id);
            System.out.println("二进制id为：" + Long.toBinaryString(id));
        }
    }

    //数据中心id：外部传入
    private long datacenterId;
    //机器id：外部传入
    private long workerId;
    //12位的序列号
    private long sequence;
    //数据中心id的bit位数
    private long datacenterIdBits = 5L;
    //工作id的bit位数
    private long workerIdBits = 5L;
    //序列号bit位数
    private long sequenceBits = 12L;
    //工作id向左移动位数：12位
    private long workerIdShift = sequenceBits;
    //数据id向左移动位数： 12 + 5 = 17位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    //时间戳需要左移位数 12 + 5 + 5 = 22位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //序列号最大值
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    //初始时间戳
    private long initialTimestamp = 1288834974657L;
    //上次时间戳，初始值为负数是不想浪费 lastTimestamp + 1 = 0 这个序列号
    private long lastTimestamp = -1L;

    public IdGenerater(long workerId, long datacenterId, long sequence) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    //通过雪花算法生成一个ID
    public synchronized long nextId() throws Exception {
        long timestamp = System.currentTimeMillis();
        //获取当前时间戳：当前时间戳不能小于上次的获取的时间戳
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("当前时间戳小于上次的时间戳，可能时钟回拨了！");
        }
        //当前时间戳，如果等于上次时间戳（同一毫秒内），则序列号加一
        //否则序列号赋值为0，从0开始
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
//序列号超过12个bit位、也就是已经生成4096个ID了
            //那这一毫秒内就不能再生成新的ID了，切换到下一毫秒内
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else { //第一个ID的序列号，从0开始
            sequence = 0;
        }
        //将当前时间戳设置为最近的一次时间戳
        lastTimestamp = timestamp;
        /**
         * 返回结果：
         * (timestamp - initialTimestamp) << timestampLeftShift) 表示将时间
         戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         */
        return ((timestamp - initialTimestamp) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    //获取时间戳，并与上次时间戳比较
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}