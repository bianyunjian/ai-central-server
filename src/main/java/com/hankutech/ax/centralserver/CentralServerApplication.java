package com.hankutech.ax.centralserver;

import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.service.DeviceCache;
import com.hankutech.ax.centralserver.service.PersonService;
import com.hankutech.ax.centralserver.socket.NettyServerException;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.centralserver.socket.app.AppByteSocketServerInitializer;
import com.hankutech.ax.centralserver.socket.plc.PlcByteSocketServerInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 艾信智慧医疗中心服务
 *
 * @author ZhangXi
 */
@Slf4j
@SpringBootApplication
public class CentralServerApplication implements ApplicationRunner, DisposableBean, WebMvcConfigurer {
    @Value("${app.event.image-folder-path}")
    private String imageFolderPath;
    @Value("${app.event.image-format}")
    String imageFormat;
    @Value("${app.event.event-obsolete-seconds}")
    int eventObSeconds;

    @Value("${app.face.syncdata}")
    boolean faceSyncData;

    @Value("${app.face.faceNotifyServiceUrl}")
    String faceNotifyServiceUrl;

    @Autowired
    private PersonService personService;

    @Resource
    private DeviceDao deviceDao;

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
        // 更新所有设备数据缓存
        List<Device> allDevices = deviceDao.selectList(null);
        DeviceCache.refreshAllCache(allDevices);
        log.info("更新设备数据缓存，数量：{}", allDevices.size());
    }


    @Autowired
    public void init() {
        Common.IMAGE_FORMAT = imageFormat;
        Common.IMAGE_FOLDER_PATH = imageFolderPath;
        if (Common.IMAGE_FOLDER_PATH.endsWith(File.separator) == false) {
            Common.IMAGE_FOLDER_PATH += File.separator;
        }
        Common.EVENT_OBSOLETE_SECONDS = eventObSeconds;

        File f = new File(Common.IMAGE_FOLDER_PATH);
        f.mkdirs();

        Common.FACE_SYNC_DATA = faceSyncData;

        int faceLibraryId = personService.getFaceLibraryId();
        System.out.println("人脸库编号：" + faceLibraryId);

        Common.FACE_NOTIFY_SERVICE_URL=faceNotifyServiceUrl;
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
