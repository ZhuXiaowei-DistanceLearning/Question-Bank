package com.zxw.netty.nio.buffers;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class BufferCreate {
	public static void main(String[] args) {
		ByteBuffer buffer0 = ByteBuffer.allocate(10);
		if (buffer0.hasArray()) {
			System.out.println("buffer0 array: " + buffer0.array());
			System.out.println("Buffer0 array offset: " + buffer0.arrayOffset());

		}
		System.out.println("Capacity: " + buffer0.capacity());
		System.out.println("Limit: " + buffer0.limit());
		System.out.println("Position: " + buffer0.position());
		System.out.println("Remaining: " + buffer0.remaining());
		System.out.println();

		ByteBuffer buffer1 = ByteBuffer.allocateDirect(10);
		if (buffer1.hasArray()) {
			System.out.println("buffer1 array: " + buffer1.array());
			System.out.println("Buffer1 array offset: " + buffer1.arrayOffset());
		}
		System.out.println("Capacity: " + buffer1.capacity());
		System.out.println("Limit: " + buffer1.limit());
		System.out.println("Position: " + buffer1.position());
		System.out.println("Remaining: " + buffer1.remaining());
		System.out.println();

		byte[] bytes = new byte[10];
		ByteBuffer buffer2 = ByteBuffer.wrap(bytes);
		if (buffer2.hasArray()) {
			System.out.println("buffer2 array: " + buffer2.array());
			System.out.println("Buffer2 array offset: " + buffer2.arrayOffset());

		}
		System.out.println("Capacity: " + buffer2.capacity());
		System.out.println("Limit: " + buffer2.limit());
		System.out.println("Position: " + buffer2.position());
		System.out.println("Remaining: " + buffer2.remaining());
		System.out.println();

		byte[] bytes2 = new byte[10];
		ByteBuffer buffer3 = ByteBuffer.wrap(bytes2, 2, 3);
		if (buffer3.hasArray()) {
			System.out.println("buffer3 array: " + buffer3.array());
			System.out.println("Buffer3 array offset: " + buffer3.arrayOffset());
		}
		System.out.println("Capacity: " + buffer3.capacity());
		System.out.println("Limit: " + buffer3.limit());
		System.out.println("Position: " + buffer3.position());
		System.out.println("Remaining: " + buffer3.remaining());

	}
}
