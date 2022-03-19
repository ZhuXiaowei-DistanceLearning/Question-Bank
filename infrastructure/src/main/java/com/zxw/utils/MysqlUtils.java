package com.zxw.utils;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author zxw
 * @date 2022/3/19 17:46
 */
public class MysqlUtils {


    public static void main(String[] args) throws SQLException {
        Connection connect = getConnect("jdbc:mysql://127.0.0.1:3306/fescar?characterEncoding=utf-8");
        Connection connect2 = getConnect("jdbc:mysql://127.0.0.1:3306/fescar?characterEncoding=utf-8");
        MysqlXAConnection xaConnect = getXAConnect(connect);
        XAResource rm1 = xaConnect.getXAResource();
        MysqlXAConnection xaConnect2 = getXAConnect(connect);
        XAResource rm2 = xaConnect2.getXAResource();
        byte[] gtrId = UUID.randomUUID().toString().getBytes();
        int xaRes1 = executeXA(gtrId, rm1, "INSERT INTO `fescar`.`account_tbl` (`id`, `user_id`, `money`) VALUES (1, 'U100000', 1000);\n", connect);
        int xaRes2 = executeXA(gtrId, rm2, "", connect2);

    }

    public static int executeXA(byte[] gtrId, XAResource rm, String sql, Connection connect) {
        int status = -1;
        byte[] b1Id = UUID.randomUUID().toString().getBytes();
        Xid xid1 = new MysqlXid(gtrId, b1Id, 1);
        try {
            rm.start(xid1, XAResource.TMNOFLAGS);
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.execute();
            rm.end(xid1, XAResource.TMSUCCESS);
            status = rm.prepare(xid1);
        } catch (XAException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static Connection getConnect(String url) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static MysqlXAConnection getXAConnect(Connection connection) {
        MysqlXAConnection xaConnection = null;
        try {
            boolean logXA = true;
            xaConnection = new MysqlXAConnection((com.mysql.jdbc.Connection) connection, logXA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xaConnection;
    }
}
