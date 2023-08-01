package com.zxw.utils;

import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import org.junit.Assert;

import java.io.IOException;

/**
 * @Author Xiaowei.Zhu
 * @Date 2023-08-01 10:11:21
 * @Description
 **/
public class ProtobufUtil {

    public static void main(String[] args) throws IOException {
        Message protobuf = ProtobufUtil.fromJson("{\"boolean\":true,\"color\":\"gold\",\"object\":{\"a\":\"b\",\"c\":\"d\"},\"string\":\"Hello World\"}");
        String json = ProtobufUtil.toJson(protobuf);
        Assert.assertTrue(json.contains("\"boolean\": true"));
        Assert.assertTrue(json.contains("\"string\": \"Hello World\""));
        Assert.assertTrue(json.contains("\"color\": \"gold\""));
    }

    public static Message fromJson(String json) throws IOException {
        Struct.Builder structBuilder = Struct.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(json, structBuilder);
        return structBuilder.build();
    }

    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().print(messageOrBuilder);
    }
}
