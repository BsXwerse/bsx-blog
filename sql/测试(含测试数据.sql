/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : bsx_blog

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 25/04/2023 21:18:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bsx_article
-- ----------------------------
DROP TABLE IF EXISTS `bsx_article`;
CREATE TABLE `bsx_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint NULL DEFAULT 0 COMMENT '访问量',
  `is_comment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_article
-- ----------------------------
INSERT INTO `bsx_article` VALUES (1, 'java关闭钩子', ' 使用`Runtime.addShutdownHook(Thread hook)`方法，可以注册一个JVM关闭的钩子，这个钩子可以在以下几种场景被调用\r\n\r\n1. 程序正常退出(比如`main`方法执行完毕)\r\n2. 使用`System.exit()`\r\n3. 终端使用`Ctrl+C`触发的中断\r\n4. 系统关闭\r\n5. 使用`Kill -15 pid`命令干掉进程\r\n\r\n同理关闭钩子是`removeShutdownHook`\r\n\r\n使用例：\r\n\r\n```java\r\npublic static void main(String[] args) {\r\n    Thread thread = new Thread(() -> System.out.println(\"Do something1 in Shutdown Hook\"), \"dahai1\");\r\n    Runtime.getRuntime().addShutdownHook(thread);\r\n    Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println(\"Do something2 in Shutdown Hook2\"), \"dahai2\"));\r\n    System.out.println(\"hello world\");\r\n    Runtime.getRuntime().removeShutdownHook(thread);\r\n}\r\n```\r\n\r\n', 'test', 1, 'https://java.p2hp.com/assets/images/java-logo-png.png', '0', '0', 17, '1', NULL, '2023-04-05 20:41:49', -1, '2023-04-25 11:39:48', 0);
INSERT INTO `bsx_article` VALUES (2, 'static执行顺序', '1、先执行父类的静态代码块和静态变量初始化，并且静态代码块和静态变量的执行顺序只跟代码中出现的顺序有关。\r\n2、执行子类的静态代码块和静态变量初始化。\r\n3、执行父类的实例变量初始化\r\n4、执行父类的构造函数\r\n5、执行子类的实例变量初始化\r\n6、执行子类的构造函数\r\n\r\n如果类已经被加载：\r\n则静态代码块和静态变量就不用重复执行，再创建类对象时，只执行与实例相关的变量初始化和构造方法。\r\n\r\n', 'test', 2, 'https://cdn-dynmedia-1.microsoft.com/is/image/microsoftcorp/MSFT-1125-SQL_Server_2022_web_blade_image_RWWaqg:VP1-539x440?resMode=sharp2&op_usm=1.5,0.65,15,0&wid=1920&hei=600&qlt=100&fmt=png-alpha&fit=constrain', '1', '0', 10, '1', NULL, '2023-04-19 20:41:53', -1, '2023-04-25 11:39:48', 0);
INSERT INTO `bsx_article` VALUES (8, 'springboot自定义json序列化器', '### jackson\n\n```java\n@Configuration\npublic class WebConfig implements WebMvcConfigurer{\n    public HttpMessageConverter jacksonHttpMessageConverter() {\n        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter\n                = new MappingJackson2HttpMessageConverter();\n        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();\n        SimpleDateFormat dateFormat = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n        objectMapper.setDateFormat(dateFormat);//日期格式\n        objectMapper.setTimeZone(TimeZone.getTimeZone(\"GMT+8\"));\n        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//忽略null的字段\n        SimpleModule simpleModule = new SimpleModule();\n        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);\n        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);\n        objectMapper.registerModule(simpleModule);//将Long全部转为字符串\n        jackson2HttpMessageConverter.setObjectMapper(objectMapper);\n        return jackson2HttpMessageConverter;\n    }\n\n    @Override\n    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {\n        converters.add(0, jacksonHttpMessageConverter());//加到第一个\n    }\n}\n```\n\n### fastjson\n\n```java\n@Configuration\npublic class WebConfig implements WebMvcConfigurer {\n    public HttpMessageConverter fastJsonHttpMessageConverters() {\n        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();\n        FastJsonConfig fastJsonConfig = new FastJsonConfig();\n        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);\n        fastJsonConfig.setDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);//将Long全部转为字符串\n        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);\n        fastConverter.setFastJsonConfig(fastJsonConfig);\n        HttpMessageConverter<?> converter = fastConverter;\n        return converter;\n    }\n\n    @Override\n    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {\n        converters.add(0, fastJsonHttpMessageConverters());\n    }\n}\n```\n\n', 'public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {', 1, 'http://rsqxyotjk.bkt.clouddn.com/2023/04/23/1fa0a5429d4a4592a494a1eba125982f.jpg', '1', '0', 0, '0', 1, '2023-04-23 08:58:43', -1, '2023-04-25 11:39:48', 0);
INSERT INTO `bsx_article` VALUES (9, 'json序列化器', '### jackson\n\n```java\n@Configuration\npublic class WebConfig implements WebMvcConfigurer{\n    public HttpMessageConverter jacksonHttpMessageConverter() {\n        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter\n                = new MappingJackson2HttpMessageConverter();\n        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();\n        SimpleDateFormat dateFormat = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n        objectMapper.setDateFormat(dateFormat);//日期格式\n        objectMapper.setTimeZone(TimeZone.getTimeZone(\"GMT+8\"));\n        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//忽略null的字段\n        SimpleModule simpleModule = new SimpleModule();\n        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);\n        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);\n        objectMapper.registerModule(simpleModule);//将Long全部转为字符串\n        jackson2HttpMessageConverter.setObjectMapper(objectMapper);\n        return jackson2HttpMessageConverter;\n    }\n\n    @Override\n    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {\n        converters.add(0, jacksonHttpMessageConverter());//加到第一个\n    }\n}\n```\n\n### fastjson\n\n```java\n@Configuration\npublic class WebConfig implements WebMvcConfigurer {\n    public HttpMessageConverter fastJsonHttpMessageConverters() {\n        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();\n        FastJsonConfig fastJsonConfig = new FastJsonConfig();\n        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);\n        fastJsonConfig.setDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);//将Long全部转为字符串\n        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);\n        fastConverter.setFastJsonConfig(fastJsonConfig);\n        HttpMessageConverter<?> converter = fastConverter;\n        return converter;\n    }\n\n    @Override\n    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {\n        converters.add(0, fastJsonHttpMessageConverters());\n    }\n}\n```\n\n', '@Configuration\npublic class WebConfig implements WebMvcConfigurer{', 1, 'http://rsqxyotjk.bkt.clouddn.com/2023/04/23/1617788bbd0a4d05ae9f6f0fe2c112ed.jpg', '1', '0', 1, '0', 1, '2023-04-23 09:01:22', -1, '2023-04-25 11:39:48', 0);
INSERT INTO `bsx_article` VALUES (10, '邻接表', '### 邻接表\n\n```c++\n// 对于每个点k，开一个单链表，存储k所有可以走到的点。h[k]存储这个单链表的头结点\n// h数组下标即为节点编号，h数组储存e数组下标，e数组储存节点编号\nint h[N], e[N], ne[N], idx;\n\n// 添加一条边a->b\nvoid add(int a, int b) {\n    e[idx] = b, ne[idx] = h[a], h[a] = idx ++ ;\n}\n\n// 初始化\nidx = 0;\nArrays.fill(h, -1);\n```\n\n### DFS\n\n```java\nstatic boolean[] st = new boolean[N];\npublic static int dfs(int u) {\n    st[u] = true; // st[u] 表示点u已经被遍历过\n    for (int i = h[u]; i != -1; i = ne[i]) {\n        int j = e[i];\n        if (!st[j]) dfs(j);\n    }\n}\n```\n\n### BFS\n\n```java\nboolean[] st = new boolean[N];\nQueue<Integer> q = new Queue<>();\nst[1] = true; // 表示1号点已经被遍历过\nq.offer(1);\nwhile (!q.isEmpty()) {\n    int t = q.poll();\n    for (int i = h[t]; i != -1; i = ne[i]) {\n        int j = e[i];\n        if (!st[j]) {\n            st[j] = true; // 表示点j已经被遍历过\n            q.offer(j);\n        }\n    }\n}\n```\n\n### 拓扑排序\n\n```c++\n#include <iostream>\n#include <cstring>\n#include <algorithm>\nusing namespace std;\nconst int N = 100010;\nint e[N], ne[N], idx;//邻接表存储图\nint h[N];\nint q[N], hh = 0, tt = -1;//队列保存入度为0的点，也就是能够输出的点，数组模拟队列\nint n, m;//保存图的点数和边数\nint d[N];////保存各个点的入度\n\nvoid add(int a, int b) {\n    e[idx] = b, ne[idx] = h[a], h[a] = idx++;\n}\n\nvoid topsort() {\n    for (int i = 1; i <= n; i ++ ) {//遍历一遍顶点的入度。\n        if (d[i] == 0)//如果入度为 0, 则可以入队列\n            q[ ++ tt] = i;\n    }\n    while (tt >= hh) {//循环处理队列中点的\n        int a = q[hh ++ ];\n        for (int i = h[a]; i != -1; i = ne[i]) {//循环删除 a 发出的边\n            int b = e[i];//a 有一条边指向b\n            d[b] -- ;//删除边后，b的入度减1\n            if(d[b] == 0)//如果b的入度减为 0,则 b 可以输出，入队列\n                q[ ++ tt] = b;\n        }\n    }\n    if (tt == n - 1) {//如果队列中的点的个数与图中点的个数相同，则可以进行拓扑排序\n        for (int i = 0; i < n; i ++ ) {//队列中保存了所有入度为0的点，依次输出\n            cout << q[i] << \" \";\n        }\n    }\n    else//如果队列中的点的个数与图中点的个数不相同，则可以进行拓扑排序\n        cout << -1;//输出-1，代表错误\n}\n\n\nint main(){\n    cin >> n >> m;//保存点的个数和边的个数\n    memset(h, -1, sizeof h);//初始化邻接表\n    while (m -- ) {//依次读入边\n        int a, b;\n        cin >> a >> b;\n        d[b]++;//顶点b的入度+1\n        add(a, b);//添加到邻接表\n    }\n    topsort();//进行拓扑排序\n    return 0;\n}\n\n```\n\n', '对于每个点k，开一个单链表，存储k所有可以走到的点。h[k]存储这个单链表的头结点', 15, 'http://rsqxyotjk.bkt.clouddn.com/2023/04/23/1099da683fe841d484cc8b393f3ca4e8.jpg', '1', '0', 1, '0', 12, '2023-04-23 10:08:30', -1, '2023-04-25 11:39:48', 0);

-- ----------------------------
-- Table structure for bsx_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `bsx_article_tag`;
CREATE TABLE `bsx_article_tag`  (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint NOT NULL DEFAULT 0 COMMENT '标签id',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_article_tag
-- ----------------------------
INSERT INTO `bsx_article_tag` VALUES (1, 4);
INSERT INTO `bsx_article_tag` VALUES (2, 1);
INSERT INTO `bsx_article_tag` VALUES (2, 4);
INSERT INTO `bsx_article_tag` VALUES (3, 4);
INSERT INTO `bsx_article_tag` VALUES (3, 5);
INSERT INTO `bsx_article_tag` VALUES (9, 4);
INSERT INTO `bsx_article_tag` VALUES (10, 7);

-- ----------------------------
-- Table structure for bsx_category
-- ----------------------------
DROP TABLE IF EXISTS `bsx_category`;
CREATE TABLE `bsx_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `pid` bigint NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_category
-- ----------------------------
INSERT INTO `bsx_category` VALUES (1, 'java', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `bsx_category` VALUES (2, 'PHP', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `bsx_category` VALUES (15, 'sql', -1, 'sqlsq', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `bsx_category` VALUES (16, 'vue', -1, 'vvv', '0', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for bsx_comment
-- ----------------------------
DROP TABLE IF EXISTS `bsx_comment`;
CREATE TABLE `bsx_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint NULL DEFAULT NULL COMMENT '文章id',
  `root_id` bigint NULL DEFAULT -1 COMMENT '根评论id',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint NULL DEFAULT -1 COMMENT '回复目标评论id',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_comment
-- ----------------------------
INSERT INTO `bsx_comment` VALUES (34, '0', 1, -1, 'aaaaaaaa', -1, -1, 7, '2023-04-24 14:33:03', 7, '2023-04-24 14:33:03', 0);
INSERT INTO `bsx_comment` VALUES (35, '0', 1, -1, '呵呵呵呵', -1, -1, 12, '2023-04-24 14:33:39', 12, '2023-04-24 14:33:39', 0);

-- ----------------------------
-- Table structure for bsx_link
-- ----------------------------
DROP TABLE IF EXISTS `bsx_link`;
CREATE TABLE `bsx_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `logo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_link
-- ----------------------------
INSERT INTO `bsx_link` VALUES (1, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'sda', 'https://www.baidu.com', '0', NULL, '2022-01-13 08:25:47', NULL, '2022-01-13 08:36:14', 0);
INSERT INTO `bsx_link` VALUES (2, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'dada', 'https://www.qq.com', '1', NULL, '2022-01-13 09:06:10', NULL, '2022-01-13 09:07:09', 0);
INSERT INTO `bsx_link` VALUES (3, 'sa', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'da', 'https://www.taobao.com', '0', NULL, '2022-01-13 09:23:01', NULL, '2022-01-13 09:23:01', 0);

-- ----------------------------
-- Table structure for bsx_tag
-- ----------------------------
DROP TABLE IF EXISTS `bsx_tag`;
CREATE TABLE `bsx_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bsx_tag
-- ----------------------------
INSERT INTO `bsx_tag` VALUES (1, 'Mybatis', NULL, NULL, NULL, '2022-01-11 09:20:50', 0, 'weqwe');
INSERT INTO `bsx_tag` VALUES (2, 'asdas', NULL, '2022-01-11 09:20:55', NULL, '2022-01-11 09:20:55', 1, 'weqw');
INSERT INTO `bsx_tag` VALUES (3, 'weqw', NULL, '2022-01-11 09:21:07', NULL, '2022-01-11 09:21:07', 1, 'qweqwe');
INSERT INTO `bsx_tag` VALUES (4, 'Java', NULL, '2022-01-13 15:22:43', NULL, '2022-01-13 15:22:43', 0, 'sdad');
INSERT INTO `bsx_tag` VALUES (5, 'WAD', NULL, '2022-01-13 15:22:47', NULL, '2022-01-13 15:22:47', 0, 'ASDAD');
INSERT INTO `bsx_tag` VALUES (6, 'sql', 12, '2023-04-23 10:06:19', 12, '2023-04-23 10:06:19', 0, 'sqlsql');
INSERT INTO `bsx_tag` VALUES (7, 'vue', 12, '2023-04-23 10:06:54', 12, '2023-04-23 10:06:54', 0, 'cue\n');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2034 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 0, '2021-11-12 10:46:19', 0, NULL, '系统管理目录', '0');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 0, '2021-11-12 10:46:19', 1, '2022-07-31 15:47:58', '用户管理菜单', '0');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 0, '2021-11-12 10:46:19', 0, NULL, '角色管理菜单', '0');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 0, '2021-11-12 10:46:19', 0, NULL, '菜单管理菜单', '0');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (2017, '内容管理', 0, 4, 'content', NULL, 1, 'M', '0', '0', NULL, 'table', NULL, '2022-01-08 02:44:38', 1, '2022-07-31 12:34:23', '', '0');
INSERT INTO `sys_menu` VALUES (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 1, 'C', '0', '0', 'content:category:list', 'example', NULL, '2022-01-08 02:51:45', NULL, '2022-01-08 02:51:45', '', '0');
INSERT INTO `sys_menu` VALUES (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 1, 'C', '0', '0', 'content:article:list', 'build', NULL, '2022-01-08 02:53:10', NULL, '2022-01-08 02:53:10', '', '0');
INSERT INTO `sys_menu` VALUES (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 1, 'C', '0', '0', 'content:tag:index', 'button', NULL, '2022-01-08 02:55:37', NULL, '2022-01-08 02:55:50', '', '0');
INSERT INTO `sys_menu` VALUES (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 1, 'C', '0', '0', 'content:link:list', '404', NULL, '2022-01-08 02:56:50', NULL, '2022-01-08 02:56:50', '', '0');
INSERT INTO `sys_menu` VALUES (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 1, 'C', '0', '0', 'content:article:writer', 'build', NULL, '2022-01-08 03:39:58', 1, '2022-07-31 22:07:05', '', '0');
INSERT INTO `sys_menu` VALUES (2024, '友链新增', 2022, 0, '', NULL, 1, 'F', '0', '0', 'content:link:add', '#', NULL, '2022-01-16 07:59:17', NULL, '2022-01-16 07:59:17', '', '0');
INSERT INTO `sys_menu` VALUES (2025, '友链修改', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:edit', '#', NULL, '2022-01-16 07:59:44', NULL, '2022-01-16 07:59:44', '', '0');
INSERT INTO `sys_menu` VALUES (2026, '友链删除', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:remove', '#', NULL, '2022-01-16 08:00:05', NULL, '2022-01-16 08:00:05', '', '0');
INSERT INTO `sys_menu` VALUES (2027, '友链查询', 2022, 2, '', NULL, 1, 'F', '0', '0', 'content:link:query', '#', NULL, '2022-01-16 08:04:09', NULL, '2022-01-16 08:04:09', '', '0');
INSERT INTO `sys_menu` VALUES (2028, '导出分类', 2018, 1, '', NULL, 1, 'F', '0', '0', 'content:category:export', '#', NULL, '2022-01-21 07:06:59', NULL, '2022-01-21 07:06:59', '', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '0', '0', 0, '2021-11-12 10:46:19', 0, NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '0', '0', 0, '2021-11-12 10:46:19', 0, '2022-01-01 22:32:58', '普通角色');
INSERT INTO `sys_role` VALUES (11, '嘎嘎嘎', 'aggag', 5, '0', '0', NULL, '2022-01-06 14:07:40', NULL, '2022-01-07 03:48:48', '嘎嘎嘎');
INSERT INTO `sys_role` VALUES (12, '友链审核员', 'link', 1, '0', '0', NULL, '2022-01-16 06:49:30', NULL, '2022-01-16 08:05:09', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (0, 0);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 101);
INSERT INTO `sys_role_menu` VALUES (3, 103);
INSERT INTO `sys_role_menu` VALUES (3, 104);
INSERT INTO `sys_role_menu` VALUES (3, 105);
INSERT INTO `sys_role_menu` VALUES (3, 106);
INSERT INTO `sys_role_menu` VALUES (3, 107);
INSERT INTO `sys_role_menu` VALUES (3, 108);
INSERT INTO `sys_role_menu` VALUES (3, 109);
INSERT INTO `sys_role_menu` VALUES (3, 110);
INSERT INTO `sys_role_menu` VALUES (3, 111);
INSERT INTO `sys_role_menu` VALUES (3, 112);
INSERT INTO `sys_role_menu` VALUES (3, 113);
INSERT INTO `sys_role_menu` VALUES (3, 114);
INSERT INTO `sys_role_menu` VALUES (3, 115);
INSERT INTO `sys_role_menu` VALUES (3, 116);
INSERT INTO `sys_role_menu` VALUES (3, 500);
INSERT INTO `sys_role_menu` VALUES (3, 501);
INSERT INTO `sys_role_menu` VALUES (3, 1001);
INSERT INTO `sys_role_menu` VALUES (3, 1002);
INSERT INTO `sys_role_menu` VALUES (3, 1003);
INSERT INTO `sys_role_menu` VALUES (3, 1004);
INSERT INTO `sys_role_menu` VALUES (3, 1005);
INSERT INTO `sys_role_menu` VALUES (3, 1006);
INSERT INTO `sys_role_menu` VALUES (3, 1007);
INSERT INTO `sys_role_menu` VALUES (3, 1008);
INSERT INTO `sys_role_menu` VALUES (3, 1009);
INSERT INTO `sys_role_menu` VALUES (3, 1010);
INSERT INTO `sys_role_menu` VALUES (3, 1011);
INSERT INTO `sys_role_menu` VALUES (3, 1012);
INSERT INTO `sys_role_menu` VALUES (3, 1017);
INSERT INTO `sys_role_menu` VALUES (3, 1018);
INSERT INTO `sys_role_menu` VALUES (3, 1019);
INSERT INTO `sys_role_menu` VALUES (3, 1020);
INSERT INTO `sys_role_menu` VALUES (3, 1021);
INSERT INTO `sys_role_menu` VALUES (3, 1022);
INSERT INTO `sys_role_menu` VALUES (3, 1023);
INSERT INTO `sys_role_menu` VALUES (3, 1024);
INSERT INTO `sys_role_menu` VALUES (3, 1025);
INSERT INTO `sys_role_menu` VALUES (3, 1026);
INSERT INTO `sys_role_menu` VALUES (3, 1027);
INSERT INTO `sys_role_menu` VALUES (3, 1028);
INSERT INTO `sys_role_menu` VALUES (3, 1029);
INSERT INTO `sys_role_menu` VALUES (3, 1030);
INSERT INTO `sys_role_menu` VALUES (3, 1031);
INSERT INTO `sys_role_menu` VALUES (3, 1032);
INSERT INTO `sys_role_menu` VALUES (3, 1033);
INSERT INTO `sys_role_menu` VALUES (3, 1034);
INSERT INTO `sys_role_menu` VALUES (3, 1035);
INSERT INTO `sys_role_menu` VALUES (3, 1036);
INSERT INTO `sys_role_menu` VALUES (3, 1037);
INSERT INTO `sys_role_menu` VALUES (3, 1038);
INSERT INTO `sys_role_menu` VALUES (3, 1039);
INSERT INTO `sys_role_menu` VALUES (3, 1040);
INSERT INTO `sys_role_menu` VALUES (3, 1041);
INSERT INTO `sys_role_menu` VALUES (3, 1042);
INSERT INTO `sys_role_menu` VALUES (3, 1043);
INSERT INTO `sys_role_menu` VALUES (3, 1044);
INSERT INTO `sys_role_menu` VALUES (3, 1045);
INSERT INTO `sys_role_menu` VALUES (3, 1046);
INSERT INTO `sys_role_menu` VALUES (3, 1047);
INSERT INTO `sys_role_menu` VALUES (3, 1048);
INSERT INTO `sys_role_menu` VALUES (3, 1049);
INSERT INTO `sys_role_menu` VALUES (3, 1050);
INSERT INTO `sys_role_menu` VALUES (3, 1051);
INSERT INTO `sys_role_menu` VALUES (3, 1052);
INSERT INTO `sys_role_menu` VALUES (3, 1053);
INSERT INTO `sys_role_menu` VALUES (3, 1054);
INSERT INTO `sys_role_menu` VALUES (3, 1055);
INSERT INTO `sys_role_menu` VALUES (3, 1056);
INSERT INTO `sys_role_menu` VALUES (3, 1057);
INSERT INTO `sys_role_menu` VALUES (3, 1058);
INSERT INTO `sys_role_menu` VALUES (3, 1059);
INSERT INTO `sys_role_menu` VALUES (3, 1060);
INSERT INTO `sys_role_menu` VALUES (3, 2000);
INSERT INTO `sys_role_menu` VALUES (11, 1);
INSERT INTO `sys_role_menu` VALUES (11, 100);
INSERT INTO `sys_role_menu` VALUES (11, 101);
INSERT INTO `sys_role_menu` VALUES (11, 102);
INSERT INTO `sys_role_menu` VALUES (11, 103);
INSERT INTO `sys_role_menu` VALUES (11, 104);
INSERT INTO `sys_role_menu` VALUES (11, 105);
INSERT INTO `sys_role_menu` VALUES (11, 106);
INSERT INTO `sys_role_menu` VALUES (11, 107);
INSERT INTO `sys_role_menu` VALUES (11, 108);
INSERT INTO `sys_role_menu` VALUES (11, 500);
INSERT INTO `sys_role_menu` VALUES (11, 501);
INSERT INTO `sys_role_menu` VALUES (11, 1001);
INSERT INTO `sys_role_menu` VALUES (11, 1002);
INSERT INTO `sys_role_menu` VALUES (11, 1003);
INSERT INTO `sys_role_menu` VALUES (11, 1004);
INSERT INTO `sys_role_menu` VALUES (11, 1005);
INSERT INTO `sys_role_menu` VALUES (11, 1006);
INSERT INTO `sys_role_menu` VALUES (11, 1007);
INSERT INTO `sys_role_menu` VALUES (11, 1008);
INSERT INTO `sys_role_menu` VALUES (11, 1009);
INSERT INTO `sys_role_menu` VALUES (11, 1010);
INSERT INTO `sys_role_menu` VALUES (11, 1011);
INSERT INTO `sys_role_menu` VALUES (11, 1012);
INSERT INTO `sys_role_menu` VALUES (11, 1013);
INSERT INTO `sys_role_menu` VALUES (11, 1014);
INSERT INTO `sys_role_menu` VALUES (11, 1015);
INSERT INTO `sys_role_menu` VALUES (11, 1016);
INSERT INTO `sys_role_menu` VALUES (11, 1017);
INSERT INTO `sys_role_menu` VALUES (11, 1018);
INSERT INTO `sys_role_menu` VALUES (11, 1019);
INSERT INTO `sys_role_menu` VALUES (11, 1020);
INSERT INTO `sys_role_menu` VALUES (11, 1021);
INSERT INTO `sys_role_menu` VALUES (11, 1022);
INSERT INTO `sys_role_menu` VALUES (11, 1023);
INSERT INTO `sys_role_menu` VALUES (11, 1024);
INSERT INTO `sys_role_menu` VALUES (11, 1025);
INSERT INTO `sys_role_menu` VALUES (11, 1026);
INSERT INTO `sys_role_menu` VALUES (11, 1027);
INSERT INTO `sys_role_menu` VALUES (11, 1028);
INSERT INTO `sys_role_menu` VALUES (11, 1029);
INSERT INTO `sys_role_menu` VALUES (11, 1030);
INSERT INTO `sys_role_menu` VALUES (11, 1031);
INSERT INTO `sys_role_menu` VALUES (11, 1032);
INSERT INTO `sys_role_menu` VALUES (11, 1033);
INSERT INTO `sys_role_menu` VALUES (11, 1034);
INSERT INTO `sys_role_menu` VALUES (11, 1035);
INSERT INTO `sys_role_menu` VALUES (11, 1036);
INSERT INTO `sys_role_menu` VALUES (11, 1037);
INSERT INTO `sys_role_menu` VALUES (11, 1038);
INSERT INTO `sys_role_menu` VALUES (11, 1039);
INSERT INTO `sys_role_menu` VALUES (11, 1040);
INSERT INTO `sys_role_menu` VALUES (11, 1041);
INSERT INTO `sys_role_menu` VALUES (11, 1042);
INSERT INTO `sys_role_menu` VALUES (11, 1043);
INSERT INTO `sys_role_menu` VALUES (11, 1044);
INSERT INTO `sys_role_menu` VALUES (11, 1045);
INSERT INTO `sys_role_menu` VALUES (11, 2000);
INSERT INTO `sys_role_menu` VALUES (11, 2003);
INSERT INTO `sys_role_menu` VALUES (11, 2004);
INSERT INTO `sys_role_menu` VALUES (11, 2005);
INSERT INTO `sys_role_menu` VALUES (11, 2006);
INSERT INTO `sys_role_menu` VALUES (11, 2007);
INSERT INTO `sys_role_menu` VALUES (11, 2008);
INSERT INTO `sys_role_menu` VALUES (11, 2009);
INSERT INTO `sys_role_menu` VALUES (11, 2010);
INSERT INTO `sys_role_menu` VALUES (11, 2011);
INSERT INTO `sys_role_menu` VALUES (11, 2012);
INSERT INTO `sys_role_menu` VALUES (11, 2013);
INSERT INTO `sys_role_menu` VALUES (11, 2014);
INSERT INTO `sys_role_menu` VALUES (12, 2017);
INSERT INTO `sys_role_menu` VALUES (12, 2022);
INSERT INTO `sys_role_menu` VALUES (12, 2024);
INSERT INTO `sys_role_menu` VALUES (12, 2025);
INSERT INTO `sys_role_menu` VALUES (12, 2026);
INSERT INTO `sys_role_menu` VALUES (12, 2027);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048667 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'sg', 'sg333', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', '23412332@qq.com', '18888888888', '1', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi', NULL, '2022-01-05 09:01:56', 1, '2022-01-30 15:37:03', 1);
INSERT INTO `sys_user` VALUES (3, 'sg3', 'weqe', '$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O', '1', '0', NULL, NULL, '0', NULL, NULL, '2022-01-05 13:28:43', NULL, '2022-01-05 13:28:43', 0);
INSERT INTO `sys_user` VALUES (4, 'sg2', 'dsadd', '$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he', '1', '0', '23412332@qq.com', '19098790742', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (5, 'sg2233', 'tteqe', '', '1', '0', NULL, '18246845873', '1', NULL, NULL, '2022-01-06 03:51:13', NULL, '2022-01-06 07:00:50', 0);
INSERT INTO `sys_user` VALUES (6, 'sangeng', 'sangeng', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', '2312321', '17777777777', '0', NULL, NULL, '2022-01-16 06:54:26', NULL, '2022-01-16 07:06:34', 0);
INSERT INTO `sys_user` VALUES (7, 'bsx1', 'jzb1', '$2a$10$JFc7Q./8dS3jnqyesL2mLuIXMieKVXgIvLMtFyq/xCbGdFaHIl6y2', '0', '0', 'BsxHomu@outlook.com', '123', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (8, 'weixin', 'weixin', '$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC', '0', '0', 'weixin@qq.com', NULL, NULL, NULL, -1, '2022-01-30 17:18:44', -1, '2022-01-30 17:18:44', 0);
INSERT INTO `sys_user` VALUES (9, 'bsx2', 'bsx22', '$2a$10$xzDkH3OONpb.5eHgGLGkl.xWZXBjqnnAJL71lV78OIVKjXApegr8i', '0', '0', '123@ww.com', NULL, NULL, 'http://rsqxyotjk.bkt.clouddn.com/2023/04/11/110694b6f67f40c694e4315317779380.jpg', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (10, 'bsx3', 'bsx33', '$2a$10$XEKKWovNOJD3WcTEUMTAhuKgksru9.EvGkfBjirunc0g5I01wDLUK', '0', '0', '1@qq.com', NULL, NULL, 'http://rsqxyotjk.bkt.clouddn.com/2023/04/11/049c8118581e4ea896a7a8cc3888d06e.jpg', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (11, 'user0', 'user0', '$2a$10$ULycTlyWzaAgO9jsInoskuvRqfjCwFmtlUMFWv6HIdPu2RPjbzNNm', '0', '0', 'test@qq.com', NULL, '1', 'http://rsqxyotjk.bkt.clouddn.com/2023/04/21/b7d5441addc541b88b8195cc88d4f646.jpg', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (12, 'user1', 'user1', '$2a$10$WHfih89mnmXxklMhzzfzYuxGcxPqKy5mcGlVa6pjl9rKMOqEwtzOq', '0', '0', '1@q.com', '18728491831', '1', NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (5, 2);
INSERT INTO `sys_user_role` VALUES (6, 12);
INSERT INTO `sys_user_role` VALUES (7, 1);
INSERT INTO `sys_user_role` VALUES (8, 2);
INSERT INTO `sys_user_role` VALUES (9, 2);
INSERT INTO `sys_user_role` VALUES (11, 1);
INSERT INTO `sys_user_role` VALUES (12, 1);

SET FOREIGN_KEY_CHECKS = 1;
