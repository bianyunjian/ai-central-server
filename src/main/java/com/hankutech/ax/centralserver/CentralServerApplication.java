package com.hankutech.ax.centralserver;

import com.hankutech.ax.centralserver.constant.Common;
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

    public static void main(String[] args) {

        System.out.println(Common.SERVICE_NAME + "开始启动");
        SpringApplication.run(CentralServerApplication.class, args);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(Common.SERVICE_NAME + "结束");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(Common.SERVICE_NAME + "执行一些启动后自定义的方法......");
    }
}
