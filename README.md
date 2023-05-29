# OjSpringBoot
支持C、Java、python在线编译

前端页面网址：https://github.com/WorldBlueSky/OJ_Project-SpringBoot
修改了一下Ajax代码和请求url等，其它前端代码主要采用上述网址

后端实现：
主要是通过前端接收代码，发送给服务器，服务器通过编译器来编译执行代码，返回成功率给前端
shell脚本来实现后端自动编译

项目在线地址：datastop.top:8080/html/login.html


核心项目架构和shell脚本以及初始化sql文件在resource资源目录中，想要阅读可以直接查看

采用DAO架构，分层合理，SpringBoot集成Mybatis实现
