package com.zxw.netty.demo.coder.define;

import lombok.Data;

@Data
public class MessageProtocol {
		private int len;
		private byte[] content;
}