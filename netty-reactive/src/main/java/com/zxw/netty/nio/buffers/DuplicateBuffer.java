package com.zxw.netty.nio.buffers;

import java.nio.Buffer;
import java.nio.CharBuffer;


public class DuplicateBuffer {

	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(8);
		for(int i= 0 ; i < buffer.capacity() ; i++) {
			buffer.put(String.valueOf(i).charAt(0));
		}
		printBuffer(buffer);

		buffer.flip();
		printBuffer(buffer);
		
 		buffer.position(3).limit(6).mark().position(5);
 		printBuffer(buffer);

		CharBuffer dupeBuffer = buffer.duplicate();
		buffer.clear();
 		printBuffer(buffer);
 		
		printBuffer(dupeBuffer);
		
 		dupeBuffer.clear();
		printBuffer(dupeBuffer);
		
	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[limit=" + buffer.limit() 
				+", position = " + buffer.position()
				+", capacity = " + buffer.capacity()
				+", array = " + buffer.toString()+"]");
	}

}
