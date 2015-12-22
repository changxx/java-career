package com.changxx.practice.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * TimeClient3
 *
 * @author changxiangxiang
 * @date 15/12/12
 */
public class TimeClient3 {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 当客户端和服务端TCP链路建立成功后，netty的nio线程会调用channelActive方法
                                    // 调用writeAndFlush方法将请求消息发送给服务端
                                    byte[] req = "QUERY TIME ORDER".getBytes();
                                    ByteBuf message = Unpooled.buffer(req.length);
                                    message.writeBytes(req);
                                    ctx.writeAndFlush(message);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    // 当服务端返回应答消息时，channelRead方法会被调用
                                    ByteBuf buf = (ByteBuf) msg;
                                    byte[] req = new byte[buf.readableBytes()];
                                    buf.readBytes(req);
                                    String body = new String(req, "UTF-8");
                                    System.out.println("now is : " + body);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    ctx.close();
                                }
                            });
                        }
                    });


            // 发起异步连接操作
            ChannelFuture f = bootstrap.connect("127.0.0.1", 8050).sync();

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

}
