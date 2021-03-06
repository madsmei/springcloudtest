package com.example;

import com.example.io.NettyServer;
import com.example.nio2.EchoServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettydemoApplication implements CommandLineRunner  {
    private int port = 9091;

    private String url = "127.0.0.1";

    @Autowired
    private EchoServer echoServer;

    public static void main(String[] args) {

        /**
         * 启动springboot
         */
        SpringApplication.run(NettydemoApplication.class, args);
        /**
         * 启动netty服务端服务
         */
        //启动服务端
        /*NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress("127.0.0.1", 8090));
        System.out.println( "Hello World!" );*/


    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = echoServer.start(url,port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                echoServer.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}
