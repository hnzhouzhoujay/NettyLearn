package com.zj.netty.protocol_define;
import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import io.netty.buffer.ByteBuf;

public class MarshallingMessageEncoder {
	private Marshaller marshaller;
	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	 
	public MarshallingMessageEncoder(){
		this.marshaller=MarshallingCodeCFactory.buildMarshalling();
	}

	public void encode(Object value, ByteBuf buf) throws Exception {
		int startIndex=buf.writerIndex();
		buf.writeBytes(LENGTH_PLACEHOLDER);
		ChannelBufferByteOut byteout=new ChannelBufferByteOut(buf);
		marshaller.start(byteout);
		marshaller.writeObject(value);
		marshaller.finish();
		marshaller.close();
		int writerIndex=buf.writerIndex();
		buf.setInt(startIndex, writerIndex-4-startIndex);
		
	}

}
