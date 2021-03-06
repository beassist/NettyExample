package com.github.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class EchoServerV4FirstHandler extends ChannelInboundHandlerAdapter {	//상속받은 클래스가 달라짐
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {	//메서드도 변경됨
        ByteBuf readMessage = (ByteBuf)msg;
        System.out.println("FirstHandler channelRead : " + readMessage.toString(Charset.defaultCharset()));
        ctx.write(msg);
        
        //second핸들러에서도 channelRead 메서드가 실행되게 하려면?
        ctx.fireChannelRead(msg);
    }
    
//    //이 메서드를 추가 후 flush 부분이 없을경우 텔넷 접속 후 문자열 입력 시 에코서버 콘솔에만 찍히고
//    //문자열이 터미널로 돌아오진 않는다.
//    @Override
//	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//    	System.out.println("channelReadCpmplete 발생");
//    	ctx.flush();
//    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
