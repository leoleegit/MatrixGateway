# MatrixGateway
基于Spring Cloud微服务化开发平台，具有统一授权、认证后台管理系统，其中包含具备用户管理、资源权限管理、网关API 管理等多个模块。核心技术采用Spring Boot （2.3.4.RELEASE）、Spring Cloud (Hoxton.SR8) 以及Spring Cloud Alibaba 2.2.4.RELEASE 相关核心组件，采用Nacos注册和配置中心，前端采用 Ant Design Pro。 
项目旨在低耦合为新旧项目快速提供 统一授权、认证， 负载均衡， 日志（Access Log）， 监控，熔断 等服务。

### 架构摘要
#### 前端
基于AntDesign React 搭建，优化前端架构和功能布局，支撑中台服务化的应用开发。

####[Matrix Gateway UI](https://github.com/leoleegit/MatrixGatewayUI) 

#### JWT鉴权
通过`JWT`的方式来进行用户认证和信息传递，保证服务之间用户无状态的传递。

#### 监控
利用Spring Boot Admin 来监控各个独立Service的运行状态；利用Hystrix Dashboard来实时查看接口的运行状态和调用频率等。

#### 负载均衡
将服务保留的rest进行代理和网关控制，除了平常经常使用的nginx外，Spring Cloud系列的zuul和ribbon，可以帮我们进行正常的网关管控和负载均衡。 

#### 服务注册与调用
基于`Nacos`来实现的服务注册与调用，在Spring Cloud中使用Feign调用服务。

#### 熔断与流控
集成阿里`Sentinel`进行接口流量控制，通过熔断和降级处理避免服务之间的调用“雪崩”。

## 功能截图

### 基本功能
# 功能截图
![登录模块](http://matrix.xsocket.cn/media/file/bucket/acbd002a-f788-47fa-a113-4af2c2301b28/login_01.png "会员模块.png")

![用户模块](http://matrix.xsocket.cn/media/file/bucket/8e6872d2-6621-44bf-b48f-5864f05c4a47/index_01.png "会员模块.png")

![菜单模块](http://matrix.xsocket.cn/media/file/bucket/789e25ec-57d3-493c-ab20-7b94073e9e85/index_02.png "菜单模块.png")

![角色授权](http://matrix.xsocket.cn/media/file/bucket/293f827a-03d2-4ff1-a133-f9f2fd2ff5aa/index_03.png "角色授权.png")

![角色类型](http://matrix.xsocket.cn/media/file/bucket/9b08066b-797a-44cc-8b5c-1d8e9595249d/index_04.png "角色类型.png") 