package com.zxw;

/**
 * @author zxw
 * @date 2022/6/27 15:30
 */
public class TransactionTest {

    /**
     * Atomicity：原子性
     * Consistency：一致性
     * Isolation：隔离性
     * Durability：持久性
     *
     * 读未提交(read uncommitted)：一个事务还没提交时，它做的变更就能被别的事务看到。
     * * 直接返回记录上的最新值，没有视图概念
     * 读已提交(read committed)：一个事务提交之后，它做的变更才会被其他事务看到。
     * * 视图是在每个SQL语句开始执行的时候创建的
     * 可重复度(repeatable read)：一个事务执行过程中看到的数据，总是跟这个事务在启动时看到的数据是一致的。当然在可重复读隔离级别下，未提交变更对其他事务也是不可见的。
     * * 是在事务启动时创建的，整个事务存在期间都用这个视图
     * 串行化(serializable)：顾名思义是对于同一行记录，“写”会加“写锁”，“读”会加“读锁”。当出现读写锁冲突的时候，后访问的事务必须等前一个事务执行完成，才能继续执行。
     *
     * 脏独(dirty read)：
     * 不可重复读(non-repeatable-read)：
     * 幻读(phantom read)：
     * @param args
     */
    public static void main(String[] args) {

    }

}
