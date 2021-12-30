package com.zxw.netty.demo.encode;

import lombok.Data;

@Data
public class MessageProtocol {
		private int len;
		private byte[] content;
}