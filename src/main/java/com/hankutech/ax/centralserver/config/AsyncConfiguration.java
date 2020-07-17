package com.hankutech.ax.centralserver.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 异步任务配置
 *
 * @author ZhangXi
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfiguration {


    /**
     * 通过@Async("myAsyncExecutor")使用自定义线程池执行异步方法
     * @return
     */
    @Bean(name = "myAsyncExecutor")
    public Executor myAsyncExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("async-task-")
                .setDaemon(true)
                .build();
        int threads = Runtime.getRuntime().availableProcessors() + 1;
        return new ThreadPoolExecutor(threads, 2*threads, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1024), threadFactory,
                (r, executor) -> {
                    log.info("my task is running !");
                });
    }




}
