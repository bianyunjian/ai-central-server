package com.hankutech.ax.centralserver;

import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.NettyServerException;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.centralserver.socket.app.AppByteSocketServerInitializer;
import com.hankutech.ax.centralserver.socket.plc.PlcByteSocketServerInitializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 艾信智慧医疗中心服务
 *
 * @author ZhangXi
 */
@SpringBootApplication
public class CentralServerApplication implements ApplicationRunner, DisposableBean, WebMvcConfigurer {
    @Value("${app.event.image-folder-path}")
    private String imageFolderPath;
    @Value("${app.event.image-format}")
    String imageFormat;
    @Value("${app.event.event-obsolete-seconds}")
    int eventObSeconds;

    private static SocketServer socketServer4Plc;
    private static SocketServer socketServer4App;

    public static void main(String[] args) {

        System.out.println(Common.SERVICE_NAME + "开始启动");
        SpringApplication.run(CentralServerApplication.class, args);

        socketServer4Plc = new SocketServer(SocketConst.LISTENING_PORT_PLC, new PlcByteSocketServerInitializer(SocketConst.FIXED_LENGTH_FRAME));
        try {
            socketServer4Plc.start();
        } catch (NettyServerException e) {
            e.printStackTrace();
        }

        socketServer4App = new SocketServer(SocketConst.LISTENING_PORT_APP, new AppByteSocketServerInitializer(SocketConst.FIXED_LENGTH_FRAME));
        try {
            socketServer4App.start();
        } catch (NettyServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(Common.SERVICE_NAME + "结束");
        if (socketServer4Plc != null) {
            socketServer4Plc.shutdown();
        }
        if (socketServer4App != null) {
            socketServer4App.shutdown();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(Common.SERVICE_NAME + "执行一些启动后自定义的方法......");

    }


    @Autowired
    public void init() {
        Common.IMAGE_FORMAT = imageFormat;
        Common.IMAGE_FOLDER_PATH = imageFolderPath;
        Common.EVENT_OBSOLETE_SECONDS = eventObSeconds;

        File f = new File(Common.IMAGE_FOLDER_PATH);
        f.mkdirs();
    }


    /**
     * 自定义静态资源访问
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/eventImage/**").addResourceLocations(
                "file:" + imageFolderPath);
        System.out.println("自定义静态资源目录:" + "/eventImage/**" + "-->" + "file:" + imageFolderPath);
    }


}
