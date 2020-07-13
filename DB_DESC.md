## 数据库说明文档

### 数据表

表名 | 描述 | 说明
--- | --- | ---
camera | 相机表 | 
device | Iot设备表 | 
person | 人员表 |
event | 事件表 |
device_camera | 设备相机表 |
device_person | 设备人员表 | 

### 表结构

#### camera

字段 | 类型 | 约束/索引 | 说明
--- | --- | --- | ---
camera_id | int | 主键 |
camera_name | varchar | 唯一 |
rtsp_url | varchar |  | RTSP连接地址
ai_type_array | varchar | | AI检测算法类型数组

**参数说明** 

* `ai_type_array` 算法类型数组值包含
  * box=标示安全防护检测和周转箱颜色检测任务
  * face=标示人脸识别检测任务
  * garbage=标示垃圾分类检测任务
  * person=标示有无人员检测任务

#### device

字段 | 类型 | 约束/索引 | 说明
--- | --- | --- | ---
device_id | int | 主键 |
device_name | varchar | 唯一
status | int |  | 设备状态

**参数说明**

* `status` 参数值选项
  * 0 - 正常
  * 1 - 设备离线


#### event

字段 | 类型 | 约束/索引 | 说明
--- | --- | --- | ---
event_id | char | 主键 |
event_type | varchar |  |
event_type_value | int | |

**参数说明**

* event_type与event_type_value是一对多关系，预定义值如下
  * box
    * 1 标示周转箱未盖盖子
    * 2 标示非专用周转箱
    * 3 标示有人误触发
    * 81 标示绿色周转箱无异常
    * 82 标示蓝色周转箱无异常
    * 83 标示灰色周转箱无异常
    * 84 标示红色周转箱无异常
  * face
    * 1 标示人员通过识别
    * 99 标示识别异常
  * garbage
    * 1 标示干垃圾（灰色垃圾袋）
    * 2 标示湿垃圾（黑色垃圾袋）
    * 3 标示可回收垃圾（绿色垃圾袋）
    * 4 标示有害垃圾（红色垃圾袋）
    * 99 标示异常
  * person
    * 1 标示有人
    * 2 没人
    * 99 异常


