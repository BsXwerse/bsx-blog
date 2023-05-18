## bsx-blog
### 前后端分离博客项目
#### 介绍
该项目基于springboot，redis，springsecurity，mysql开发

分为前台和后台，前台可查看博文，评论，注册等

后台可发布博文，管理用户等

演示：

![image](https://github.com/BsXwerse/bsx-blog/blob/master/sample.gif)

#### 使用
准备环境，jdk8，maven，redis，mysql

下载项目到本地

mysql建库bsx_blog，运行sql文件夹下的测试

执行build脚本，win执行 .bat，linux&mac执行 .sh

得到bsx-blog-admin.zip和bsx-blog-user.zip，分别为后台和前台

解压后application.yml修改mysql用户密码，redis密码

运行startup脚本启动，stop脚本停止

>前端工程在front-end文件夹下
