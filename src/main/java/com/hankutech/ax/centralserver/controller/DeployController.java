package com.hankutech.ax.centralserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Tag(name = "/deploy", description = "部署配置接口")
@RequestMapping(path = "/deploy")
@RestController
public class DeployController {

    @Operation(summary = "获取当前服务端时间")
    @GetMapping(path = "/currentTime")
    public String currentTime() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }




}
