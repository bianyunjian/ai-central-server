package com.hankutech.ax.centralserver.config;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis Plus 扩展配置
 *
 * @author ZhangXi
 */
@Slf4j
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfiguration {

    /**
     * 分页插件
     *
     * 使用方式：
     * 1. 创建一个{@link IPage} 示例，配置好分页参数
     * 2. 通过 {@link BaseMapper#selectPage(IPage, Wrapper)} 方法查询结果
     *
     * @return {@link PaginationInterceptor}
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
