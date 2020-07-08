package com.hankutech.ax.centralserver;

import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.ByteSocketServerInitializer;
import com.hankutech.ax.centralserver.socket.NettyServerException;
import com.hankutech.ax.centralserver.socket.SocketServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 艾信智慧医疗中心服务
 *
 * @author ZhangXi
 */
@SpringBootApplication
public class CentralServerApplication implements ApplicationRunner, DisposableBean {
    private static SocketServer server;

    public static void main(String[] args) {

        System.out.println(Common.SERVICE_NAME + "开始启动");
        SpringApplication.run(CentralServerApplication.class, args);

        server = new SocketServer(SocketConst.LISTENING_PORT, new ByteSocketServerInitializer(SocketConst.FIXED_LENGTH_FRAME));
        try {
            server.start();
        } catch (NettyServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(Common.SERVICE_NAME + "结束");
        if (server != null) {
            server.shutdown();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(Common.SERVICE_NAME + "执行一些启动后自定义的方法......");
    }
}
