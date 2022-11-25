package com.zxw.utils;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.MysqlXAConnection;
import com.mysql.cj.jdbc.MysqlXid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class MysqlUtils {


    public static void main(String[] args) throws SQLException {
        Connection connect = getConnect("jdbc:mysql://127.0.0.1:3306/fescar?characterEncoding=utf-8&useSSL=false");
        Connection connect2 = getConnect("jdbc:mysql://127.0.0.1:3306/fescar_2?characterEncoding=utf-8&useSSL=false");
        XAResource rm1 = getXAResource(getXAConnect(connect));
        XAResource rm2 = getXAResource(getXAConnect(connect2));
        byte[] gtrId = UUID.randomUUID().toString().getBytes();
        XAResult xaRes1 = executeXA(gtrId, rm1, "update storage_tbl set count = 110 where id = 30", connect);
        XAResult xaRes2 = executeXA(gtrId, rm2, "update storage_tbl set count = 110 where id = 30", connect2);
        if (xaRes1.getStatus() == XAResource.XA_OK && xaRes1.getStatus() == XAResource.XA_OK) {
            commit(rm1, xaRes1.getXid());
            commit(rm2, xaRes2.getXid());
        } else {
            rollback(rm1, xaRes1.getXid());
            rollback(rm2, xaRes2.getXid());
        }
        connect.close();
        connect2.close();
    }

    public static void rollback(XAResource xaResource, Xid xid) {
        try {
            xaResource.rollback(xid);
        } catch (XAException e) {
            e.printStackTrace();
        }
    }

    public static void commit(XAResource xaResource, Xid xid) {
        try {
            xaResource.commit(xid, true);
        } catch (XAException e) {
            e.printStackTrace();
        }
    }

    public static XAResult executeXA(byte[] gtrId, XAResource rm, String sql, Connection connect) {
        int status = -1;
        byte[] b1Id = UUID.randomUUID().toString().getBytes();
        Xid xid1 = new MysqlXid(gtrId, b1Id, 1);
        PreparedStatement preparedStatement = null;
        try {
            rm.start(xid1, XAResource.TMNOFLAGS);
            preparedStatement = connect.prepareStatement(sql);
            try {
                log.info("开始执行sql");
                preparedStatement.executeUpdate(sql);
                rm.end(xid1, XAResource.TMSUCCESS);
            } catch (SQLException e) {
                e.printStackTrace();
                rm.end(xid1, XAResource.TMFAIL);
            }
            status = rm.prepare(xid1);
        } catch (XAException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new XAResult(status, xid1);
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
        boolean logXA = true;
        xaConnection = new MysqlXAConnection((JdbcConnection) connection, logXA);
        return xaConnection;
    }

    public static XAResource getXAResource(MysqlXAConnection connection) {
        XAResource xaResource = null;
        try {
            xaResource = connection.getXAResource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xaResource;
    }

    @Data
    @AllArgsConstructor
    static class XAResult {
        int status;
        Xid xid;
    }
}
