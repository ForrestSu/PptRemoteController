package com.tqd.client;

import org.apache.log4j.Logger;

import com.sunquan.pptclients.PPTClient;

import android.os.Handler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
	//private static final Logger logger = Logger.getLogger(TcpClientHandler.class);
	Handler mHandler;
	public TcpClientHandler(Handler handler) {
		// TODO Auto-generated constructor stub
		mHandler=handler;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//messageReceived方法,名称很别扭，像是一个内部方法.
		//logger.info("client接收到服务器返回的消息:"+msg);
		if(msg instanceof byte [])
		{
			byte []data=(byte[])msg;
			if(data.length>1024)
			mHandler.obtainMessage(PPTClient.BITMAP_RECEIVED, msg).sendToTarget();
			else
				System.out.println(new String(data));
			
				
		}
		
//		if(msg instanceof Byte [])
//		{
//			mHandler.obtainMessage(PPTClient.BITMAP_RECEIVED, msg).sendToTarget();
//		}
//		
////		if(msg instanceof ByteBuf)
////		{
//			ByteBuf bbf=(ByteBuf) msg;
//			
//			mHandler.obtainMessage(PPTClient.BITMAP_RECEIVED, bbf.array()).sendToTarget();
//	//	}
		 
		//	System.out.println("client接收到服务器返回的消息:"+msg);
		
	}

   
}
