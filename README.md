# 爱信智慧医疗 - CentralServer

## 技术说明

### 技术架构

* Spring Boot - 2.3.0-RELEASE+
* MySQL - 8.0+
* Netty - 4.1+

### 开发/生产环境

* Ubuntu 18.04 x86_64
  * CPU: 2+
  * RAM: 4GB+
  * ROM: 500GB+
  * Network: 100Mbps+
* Java: 1.8.121+
* MySQL: 8.0+


## 开发说明

### MybatisPlus使用说明

* 在指定 `model` 文件夹下创建实体类，可自动映射
* Dao层 `XxxDao` 可继承 `com.baomidou.mybatisplus.core.mapper.BaseMapper` 接口，无需再手写基础的增删改查方法
* 支持逻辑删除，详见[官方文档](https://mp.baomidou.com/guide/logic-delete.html)
* 支持枚举映射: 在配置文件指定包下创建的枚举类中对自定义属性使用 `com.baomidou.mybatisplus.annotation.EnumValue` 注解，可自动完成与数据库值的映射
* 支持单表分页，详见[官方文档](https://mp.baomidou.com/guide/page.html)

### JSON API

#### HTTP响应

响应对象可继承 `com.hankutech.ax.centralserver.support.base.BaseResponse`，统一响应格式：

```json
{
  "status": "FAILURE",
  "errorCode": 100001,
  "data": {},
  "message": "对一些错误信息进行补充说明"
}
```

其中：
* status: 响应数据状态，暂时包括：SUCCESS，FAILURE，UNAUTHORIZED 和 DENIED 四种
* errorCode: 用来统一定位错误原因，详见[统一错误码列表](#统一错误码列表)
* data: 实际要传输的数据
* message: 状态或错误的补充说明

### 统一错误码列表

* 系统错误

错误码 | 说明
--- | ---
100001 | 系统内部错误


## 业务说明

### 与爱信的socket通信协议





