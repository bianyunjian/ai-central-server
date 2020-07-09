package com.hankutech.ax.centralserver.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 开启缓存管理
 *
 * @author ZhangXi
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

    public static final String USER_CACHE = "userCache";


}
