package com.tqd.server;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tqd.core.PPtControlCore;
import com.tqd.core.PPtControlCore.CommandType;
import com.tqd.entity.Message;
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {
		
		private com.tqd.core.PPtControlCore mPPtControlCore;
		Gson mGson=new Gson();

	    private static final Logger logger = Logger.getLogger(TcpServerHandler.class);

	    @Override
	    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
	       //logger.info("SERVER���յ���Ϣ:"+msg);
	       System.out.println("SERVER���յ���Ϣ:"+msg);
	       
	       Message message=null;
	       try{
	       message=mGson.fromJson((String)msg, Message.class);
	       }catch(JsonSyntaxException e)
	       {
	    	   e.printStackTrace();
	       }
	       
	       if(message!=null)
	       {
	    	   if(message.getcType()==CommandType.CONNECTION_REQUEST)
	    	   {
	    		   mPPtControlCore=new PPtControlCore(message.getPointX()[0], message.getPointY()[0]);
	    	 
	    		   PictureSender ps=new PictureSender(ctx, mPPtControlCore);
	    		   ps.start();
	    	   }
	    	   else
	    	   {
	    		   if(mPPtControlCore!=null)
	    			   
	    			   mPPtControlCore.processCommand(message.getcType());
	    		   
	    		 }
	       }
	       
	       
	       
			ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
	    	
	    	 System.out.println("Unexpected exception from downstream.");
	    	 
	        ctx.close();
	    }
	}
	
	
	class PictureSender extends Thread
	{PPtControlCore pcc;
		ChannelHandlerContext ctx;
		PictureSender(ChannelHandlerContext pctx, PPtControlCore ppcc)
		{
			ctx=pctx;
			pcc=ppcc;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
				System.out.println("start to send a picture");
				
			while(!ctx.isRemoved())
			{
				System.out.println(" send a picture");
				BufferedImage bi=pcc.screenCapture();
				ByteBuf bbf=ByteBufAllocator.DEFAULT.buffer();
				ByteBufOutputStream bb=new ByteBufOutputStream(bbf);
				try {
					ImageIO.write(bi, "jpeg", bb);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 
				ChannelFuture cf=	ctx.channel().writeAndFlush(bbf);
				try {
					cf.sync();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(cf.isSuccess())
				{
					System.out.println("send picture success");
				}
				else
				{
					
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		
		
	}
	
