package com.changxx.practice.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeServer3
 *
 * @author changxiangxiang
 * @date 15/12/12
 *
 * netty time server 有半包问题
 */
public class TimeServer3 {

    public static void main(String[] args) {
        // 一个用于服务端接受客户端的连接，一个用于进行SocketChannel的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf buf = (ByteBuf) msg;
                                    byte[] req = new byte[buf.readableBytes()];
                                    buf.readBytes(req);
                                    String body = new String(req, "UTF-8");
                                    System.out.println("receive order : " + body);
                                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date());
                                    System.out.println(time);
                                    ByteBuf resp = Unpooled.copiedBuffer(time.getBytes());
                                    ctx.write(resp);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    // 为了防止频繁唤醒selector进行消息发送，nett的write方法并不直接将消息写入SocketChannel中
                                    // 调用write方法只是把待发送的消息放到发送缓冲数组中，再通过flush方法，将缓冲区中的消息全部写入SocketChannel中
                                    ctx.flush();
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    ctx.close();
                                }
                            });
                        }
                    });

            int port = 8050;
            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            System.out.println("server start in port " + port);

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
