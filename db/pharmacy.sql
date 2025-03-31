/*
 Demo Pharmacy dataset

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : pharmacy

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `auth` int NULL DEFAULT NULL COMMENT '身份id',
  `real_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `signature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_admin_username`(`username` ASC) USING BTREE COMMENT 'username只能唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1000042 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 'admin', 'img/avatar/2024/01/10/202401101953562682b5062.jpg', '男', '2000-08-27', '17770038333', '1164302621@qq.com', '江西省/南昌市/青山湖区', '热爱生活的boy', 1, 0, '2024-10-23 22:20:53', NULL);
INSERT INTO `admin` VALUES (110, 'linge', 'e10adc3949ba59abbe56e057f20f883e', 1, 2, 'admin2', 'img/avatar/2024/01/10/202401101953562682b5062.jpg', '男', '2000-08-27', '17770038333', 'kevinlee1724@hotmail.com', NULL, NULL, 1, 0, '2023-10-17 10:14:03', NULL);
INSERT INTO `admin` VALUES (114, 'xiaokun', '25d55ad283aa400af464c76d713c07ad', 1, 2, 'xiaokun', 'img/avatar/defaultAvatar.jpg', '男', NULL, NULL, '12345678@qq.com', NULL, NULL, 1, 1, '2023-10-17 14:40:04', NULL);
INSERT INTO `admin` VALUES (115, 'kunzige', '25d55ad283aa400af464c76d713c07ad', 1, 2, 'kunzige', 'img/avatar/defaultAvatar.jpg', '男', NULL, NULL, '12@qq.ocm', NULL, NULL, 1, 1, '2023-10-17 14:42:26', NULL);
INSERT INTO `admin` VALUES (116, 'kunzigei', '25d55ad283aa400af464c76d713c07ad', 1, 2, 'kunzigei', 'img/avatar/defaultAvatar.jpg', '男', NULL, NULL, '1244@qq.ocm', NULL, NULL, 1, 1, '2023-10-17 14:46:20', NULL);
INSERT INTO `admin` VALUES (1000041, 'kunzigeii', '25d55ad283aa400af464c76d713c07ad', 1, 2, 'kunzigeii', 'img/avatar/defaultAvatar.jpg', '男', NULL, NULL, '1244@qq.ocm', NULL, NULL, 1, 1, '2023-10-17 14:46:50', NULL);

-- ----------------------------
-- Table structure for api_list
-- ----------------------------
DROP TABLE IF EXISTS `api_list`;
CREATE TABLE `api_list`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `folder_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '包名',
  `api_path` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口路径',
  `method` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '操作',
  `auth_level` int NULL DEFAULT NULL COMMENT '需要的权限等级',
  `desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口的作用',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_api_path`(`api_path` ASC) USING BTREE COMMENT '路径唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_list
-- ----------------------------
INSERT INTO `api_list` VALUES (1, 'AccountController', '/verification', 'Get', NULL, '生成验证码图片', 1, 0, '2024-11-22 09:09:19');
INSERT INTO `api_list` VALUES (2, 'AccountController', '/admin/login', 'Post', NULL, '后台登录--账号登录', 1, 0, '2024-11-22 09:11:40');
INSERT INTO `api_list` VALUES (3, 'AccountController', '/admin/email/login', 'Post', NULL, '后台登录--邮箱登录', 1, 0, '2024-11-22 09:14:25');
INSERT INTO `api_list` VALUES (4, 'AccountController', '/get/emailCode/{email}', 'Get', NULL, '获取邮箱验证码', 1, 0, '2024-11-22 09:14:47');
INSERT INTO `api_list` VALUES (5, 'AccountController', '/logout', 'Get', NULL, '退出账号', 1, 0, '2024-11-22 09:15:36');
INSERT INTO `api_list` VALUES (6, 'AccountController', '/user/login', 'Post', NULL, '前台登录--账号登录', 1, 0, '2024-11-22 09:17:11');
INSERT INTO `api_list` VALUES (7, 'AccountController', '/user/register', 'Post', NULL, '前台注册一个账户', 1, 0, '2024-11-22 09:17:40');
INSERT INTO `api_list` VALUES (8, 'AdminController', '/admin/list/{currentPage}/{pageSize},/admin/list/{currentPage}/{pageSize}/{inputContent}', 'Get', NULL, '查找admin表的相关信息展示', 1, 0, '2024-11-22 09:24:37');
INSERT INTO `api_list` VALUES (9, 'AdminController', '/admin/info/{id}', 'Get', NULL, '后台根据登录的管理员的id去获取他的有关信息', 1, 0, '2024-11-22 09:25:11');
INSERT INTO `api_list` VALUES (10, 'CarouselController', '/query/carousels', 'Get', NULL, '后台查询轮播图的信息', 1, 0, '2024-11-22 09:26:13');
INSERT INTO `api_list` VALUES (11, 'CarouselController', '/query/enable/carousels', 'Get', NULL, '首页得到允许展示的图片', 1, 0, '2024-11-22 09:29:03');
INSERT INTO `api_list` VALUES (12, 'CarouselController', '/picture/isShow/{id}', 'Get', NULL, '选择图片是否在主页显示', 1, 0, '2024-11-22 09:29:19');
INSERT INTO `api_list` VALUES (13, 'CarouselController', '/upload/carousel/picture/{id}', 'Post', NULL, '更换轮播图,保存到 img/carousel/ 下', 1, 0, '2024-11-22 09:29:50');
INSERT INTO `api_list` VALUES (14, 'CarouselController', '/upload/carousel/picture', 'Post', NULL, '上传轮播图', 1, 0, '2024-11-22 09:30:44');
INSERT INTO `api_list` VALUES (15, 'DrugRecommendController', '/drug/recommend/{sortId}', 'Get', NULL, '根据分类id获取该分类的药', 1, 0, '2024-11-22 09:32:40');
INSERT INTO `api_list` VALUES (16, 'MedicineController', '/search/with/{currentPage}/{pageSize},/search/with/{currentPage}/{pageSize}/{nameOrId}', 'Get', NULL, '查询药品', 1, 0, '2024-11-22 09:33:46');
INSERT INTO `api_list` VALUES (17, 'MedicineController', '/medicine/add', 'Post', NULL, '添加药品', 1, 0, '2024-11-22 09:34:01');
INSERT INTO `api_list` VALUES (18, 'MedicineController', '/medicine/isShow/{id}', 'Get', NULL, '选择药品是否显示', 1, 0, '2024-11-22 09:34:20');
INSERT INTO `api_list` VALUES (19, 'MedicineController', '/medicine/delete/{id}', 'Get', NULL, '单个删除药品-----逻辑删除', 1, 0, '2024-11-22 09:34:41');
INSERT INTO `api_list` VALUES (20, 'MedicineController', '/medicine/batch/delete', 'Post', NULL, '批量删除药品-----逻辑删除', 1, 0, '2024-11-22 09:35:01');
INSERT INTO `api_list` VALUES (21, 'MedicineController', '/medicine/get/info/{id}', 'Get', NULL, '通过药品的id获取对应药品信息', 1, 0, '2024-11-22 09:35:20');
INSERT INTO `api_list` VALUES (22, 'MedicineController', '/medicine/recommend/{name}', 'Get', NULL, '推荐药品信息展示，根据药品名称获取分类id，再根据分类id获取同一类的药品', 1, 0, '2024-11-22 09:35:43');
INSERT INTO `api_list` VALUES (23, 'MedicineController', '/medicine/info/name/{name}', 'Get', NULL, '通过药品的名称获取对应药品信息', 1, 0, '2024-11-22 09:36:04');
INSERT INTO `api_list` VALUES (24, 'MedicineController', '/update/medicine/info/{id}', 'Post', NULL, '更新药品', 1, 0, '2024-11-22 09:36:23');
INSERT INTO `api_list` VALUES (25, 'MedicineSortController', '/sortList/{currentPage}/{pageSize},/sortList/{currentPage}/{pageSize}/{idOrCategoryOrUploadUser}', 'Get', NULL, '查询全部的分类并分页，然后列表展示', 1, 0, '2024-11-22 09:37:06');
INSERT INTO `api_list` VALUES (26, 'MedicineSortController', '/medicine/sortList', 'Get', NULL, '查询所有分类----用于药品添加和修改时，分类的select标签展示', 1, 0, '2024-11-22 09:37:29');
INSERT INTO `api_list` VALUES (27, 'MedicineSortController', '/sort/isShow/{sortId}', 'Get', NULL, '选择分类是否显示', 1, 0, '2024-11-22 09:37:46');
INSERT INTO `api_list` VALUES (28, 'MedicineSortController', '/sort/delete/{sortId}', 'Get', NULL, '单个删除分类-----逻辑删除', 1, 0, '2024-11-22 09:38:09');
INSERT INTO `api_list` VALUES (29, 'MedicineSortController', '/category/batch/delete', 'Post', NULL, '批量删除分类-----逻辑删除', 1, 0, '2024-11-22 09:38:29');
INSERT INTO `api_list` VALUES (30, 'MedicineSortController', '/category/add', 'Post', NULL, '添加分类', 1, 0, '2024-11-22 09:38:46');
INSERT INTO `api_list` VALUES (31, 'MedicineSortController', '/category/update', 'Post', NULL, '修改分类', 1, 0, '2024-11-22 09:39:02');
INSERT INTO `api_list` VALUES (32, 'MedicineSynopsisController', '/synopsis/info/{id}', 'Get', NULL, '根据药品id获取说明书信息', 1, 0, '2024-11-22 09:40:04');
INSERT INTO `api_list` VALUES (33, 'SortRecommendController', '/category/recommend', 'Get', NULL, '分类推荐', 1, 0, '2024-11-22 09:42:28');
INSERT INTO `api_list` VALUES (34, 'SortRecommendController', '/user/list/{currentPage}/{pageSize},/user/list/{currentPage}/{pageSize}/{inputContent}', 'Get', NULL, '查询普通用户', 1, 0, '2024-11-22 09:43:06');
INSERT INTO `api_list` VALUES (35, 'SortRecommendController', '/front/user/info/{username}', 'Get', NULL, '根据username查找用户，username唯一', 1, 0, '2024-11-22 09:43:33');
INSERT INTO `api_list` VALUES (36, 'SortRecommendController', '/userInfo/disabled/{userId}', 'Get', NULL, '选择是否禁用该用户', 1, 0, '2024-11-22 09:43:54');
INSERT INTO `api_list` VALUES (37, 'SortRecommendController', '/user/batch/disable', 'Post', NULL, '批量禁用用户', 1, 0, '2024-11-22 09:44:16');
INSERT INTO `api_list` VALUES (38, 'SortRecommendController', '/user/upgrade/vip/{userId}', 'Post', NULL, '提升普通用户的权限至VIP', 1, 0, '2024-11-22 09:44:40');
INSERT INTO `api_list` VALUES (39, 'SortRecommendController', '/vip/list/{currentPage}/{pageSize},/vip/list/{currentPage}/{pageSize}/{inputContent}', 'Get', NULL, '查询VIP用户', 1, 0, '2024-11-22 09:45:10');
INSERT INTO `api_list` VALUES (40, 'SortRecommendController', '/vip/downgrade/user/{vipId}', 'Post', NULL, '将VIP的权限降为普通用户', 1, 0, '2024-11-22 09:45:34');

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth`  (
  `auth_id` int NOT NULL AUTO_INCREMENT,
  `authority` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth
-- ----------------------------
INSERT INTO `auth` VALUES (1, '店长（超级管理员）');
INSERT INTO `auth` VALUES (2, '店员（管理员）');
INSERT INTO `auth` VALUES (3, 'VIP用户');
INSERT INTO `auth` VALUES (4, '普通用户');

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `admin_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '上传轮播图的管理员名字',
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '轮播图保存的相对路径',
  `status` int NULL DEFAULT 1 COMMENT '是否使用，默认不使用',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, 'admin', 'img/carousel/2024/11/20/20241120241416153e9bfb2.jpg', 0, 0, '2024-10-26 12:32:16', '2024-11-20 22:14:16');
INSERT INTO `carousel` VALUES (2, 'admin', 'img/carousel/2024/05/13/20240513161014429210fba.jpg', 0, 0, '2024-10-26 13:42:30', '2024-05-13 16:10:14');
INSERT INTO `carousel` VALUES (3, 'admin', 'img/carousel/2024/11/02/202411021659039667a245c.jpg', 1, 0, '2024-10-26 13:44:39', '2024-10-26 13:44:39');
INSERT INTO `carousel` VALUES (4, 'xxxx', 'img/carousel/2024/11/02/20241102165924307197e3d.jpg', 1, 0, '2024-10-26 13:45:09', '2024-10-26 13:45:09');
INSERT INTO `carousel` VALUES (5, '不摇碧莲-张楚岚', 'img/carousel/2024/11/02/20241102165953707e42d3b.jpg', 1, 0, '2024-10-26 16:04:32', '2024-10-26 16:04:32');
INSERT INTO `carousel` VALUES (6, 'admin', 'img/carousel/2024/11/02/202411021700122813aeb9d.jpg', 1, 0, '2024-10-26 16:06:07', '2024-10-26 16:06:07');
INSERT INTO `carousel` VALUES (7, 'admin', 'img/carousel/2024/11/02/20241102170028679f49e05.jpg', 1, 0, '2024-10-26 16:06:43', '2024-10-26 16:06:43');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `order_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `drug_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `comment` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '评论',
  `rate` int NOT NULL COMMENT '评分星数',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_comments`(`username` ASC, `order_id` ASC, `drug_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, 'admin111111', 'Y20241217201240bdc43', '清开灵颗粒', '物流还比较给力，三天就送到了，谢谢老板！', 4, 0, '2024-03-04 14:02:40', NULL);
INSERT INTO `comments` VALUES (9, 'admin111111', 'Y20241217201218558eb', '羚羊清肺丸', '满意，五星好评！', 5, 0, '2024-03-04 14:16:36', NULL);
INSERT INTO `comments` VALUES (10, 'root222222', 'Y202403041636336378', '布洛芬缓释胶囊', '这个药对感冒很管用，每次感冒都在这里买药，吃一天就好了。', 5, 0, '2024-03-04 16:42:01', NULL);
INSERT INTO `comments` VALUES (11, 'root222222', 'Y202403041634497509', '清开灵颗粒', '测试评价功能！', 5, 0, '2024-03-04 16:42:17', NULL);
INSERT INTO `comments` VALUES (12, 'admin111111', 'Y2024121720120627392', '羚羊清肺丸', '发货太慢！', 5, 0, '2024-03-04 16:44:46', NULL);
INSERT INTO `comments` VALUES (13, 'admin111111', 'Y20241217201148dc9ad', '肠胃宁片', '很棒！', 3, 0, '2024-03-04 16:45:10', NULL);
INSERT INTO `comments` VALUES (14, 'admin111111', 'Y20241217201148dc9ad', '克痤隐酮乳膏', '测试评价！', 1, 0, '2024-03-04 16:45:10', NULL);
INSERT INTO `comments` VALUES (15, 'admin111111', 'Y202412132146403059d', '清热解毒胶囊', '奈斯！', 5, 0, '2024-03-04 16:45:22', NULL);
INSERT INTO `comments` VALUES (16, 'admin111111', 'Y202403041633193962', '布洛芬缓释胶囊', '测试构造参数注入是否有问题', 5, 0, '2024-03-07 22:09:01', NULL);
INSERT INTO `comments` VALUES (17, 'admin111111', 'Y202403021507418212', '布洛芬缓释胶囊', '', 5, 0, '2024-05-10 18:50:46', NULL);
INSERT INTO `comments` VALUES (18, 'admin111111', 'Y20241217201108c4c22', '肠胃宁片', '啊啊啊', 5, 0, '2024-05-10 18:52:51', NULL);
INSERT INTO `comments` VALUES (19, 'admin111111', 'Y2024121720101351980', '牛黄清火丸', '', 5, 0, '2024-05-10 18:56:54', NULL);
INSERT INTO `comments` VALUES (20, 'admin111111', 'Y2024121720095472686', '(654-2)消旋山莨菪碱片', '123', 5, 0, '2024-05-10 18:57:09', NULL);
INSERT INTO `comments` VALUES (21, 'yangge', 'Y202306181129085659', '广东凉茶颗粒', '很不错', 5, 0, '2023-06-18 11:34:37', NULL);
INSERT INTO `comments` VALUES (22, 'user', 'Y202309291626047543', '口令芝多肽', '确实挺不错的\n', 5, 0, '2023-09-29 16:44:24', NULL);
INSERT INTO `comments` VALUES (23, 'user', 'Y202310011401471185', '仁和', '真不错', 5, 0, '2023-10-01 14:09:26', NULL);
INSERT INTO `comments` VALUES (24, 'user', 'Y202310290024091955', '康恩贝', '确实挺不错的', 5, 0, '2023-10-29 00:24:57', NULL);

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `drug_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '药名',
  `picture` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '药品图片',
  `price` decimal(20, 2) NULL DEFAULT NULL COMMENT '药品单价',
  `status` int NOT NULL DEFAULT 1 COMMENT '收藏状态（1收藏，0取消）',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (48, 'admin111111', '布洛芬缓释胶囊', 'http://img.800pharm.com/images//20160427/20160427142822_418.jpg', 27.72, 1, 0, '2024-02-01 20:44:19', '2024-05-14 16:31:27');
INSERT INTO `favorites` VALUES (49, 'admin111111', '玉屏风颗粒', 'http://img.800pharm.com/images///pms/upload/images/original/062017/13133/f0fe957be9e8e06302b1dde2a1e5aa46c45d68b9.jpg', 32.00, 1, 1, '2024-11-06 20:44:53', '2024-11-30 20:50:17');
INSERT INTO `favorites` VALUES (51, 'admin111111', '清开灵颗粒', 'http://img.800pharm.com/images///pms/upload/images/original/062017/13133/f0fe957be9e8e06302b1dde2a1e5aa46c45d68b9.jpg', 15.10, 1, 1, '2024-11-11 20:45:41', '2024-03-04 14:36:44');
INSERT INTO `favorites` VALUES (52, 'admin111111', '清热解毒胶囊', 'http://img.800pharm.com/images//20160130/20160130091644_618.jpg', 12.61, 1, 1, '2024-11-11 20:46:13', '2024-11-30 21:02:29');
INSERT INTO `favorites` VALUES (54, 'admin111111', '牛黄清火丸', 'http://img.800pharm.com/images//20160506/20160506163610_389.jpg', 10.20, 1, 1, '2024-10-30 21:02:27', '2024-03-03 21:39:40');
INSERT INTO `favorites` VALUES (55, 'admin111111', '羚羊清肺丸', 'http://img.800pharm.com/images//100204/20141128/20141128093414_422.jpg', 455.00, 1, 1, '2024-11-30 21:02:28', '2024-12-12 17:04:45');
INSERT INTO `favorites` VALUES (56, 'admin111111', '肠胃宁片', 'http://img.800pharm.com/images///pms/upload/images/original/042017/17113/f5dc425e40abbd520b26f0cc2796be06d1225410.jpg', 1500.00, 0, 0, '2024-11-30 21:02:28', '2024-01-09 10:44:19');
INSERT INTO `favorites` VALUES (57, 'admin111111', '藿香正气水', 'https://img1.baidu.com/it/u=1473267288,1468057165&fm=26&fmt=auto', 12.00, 1, 1, '2024-11-02 21:02:29', NULL);
INSERT INTO `favorites` VALUES (58, 'admin111111', '哮喘宁片', 'http://img.800pharm.com/images//20150531/20150531215340_839.jpg', 36.00, 1, 0, '2024-09-15 21:02:32', NULL);
INSERT INTO `favorites` VALUES (59, 'admin111111', '硫酸沙丁胺醇片', 'http://img.800pharm.com/images//20150623/20150623090136_495.jpg', 6.77, 1, 1, '2024-11-30 21:02:33', NULL);
INSERT INTO `favorites` VALUES (60, 'admin111111', '健胃消食片', 'http://img.800pharm.com/images///pms/upload/images/original/032017/14111/c0b7cd90a138af9d4797731d65b43c120cd2a00b.jpg', 18.00, 1, 1, '2024-11-30 21:02:33', NULL);
INSERT INTO `favorites` VALUES (61, 'admin111111', '奥利司他胶囊', 'http://img.800pharm.com/images//20151230/20151230094312_101.jpg', 160.00, 1, 1, '2024-11-30 21:02:34', NULL);
INSERT INTO `favorites` VALUES (62, 'admin111111', '(654-2)消旋山莨菪碱片', 'http://img.800pharm.com/images//20160421/20160421171043_724.jpg', 16.60, 1, 0, '2024-09-01 21:02:34', NULL);
INSERT INTO `favorites` VALUES (63, 'admin111111', '氨茶碱片', 'http://img.800pharm.com/images///pms/upload/images/original/052017/26134/257a23663f57e9b1cdf62ef6ff43ab60054b5ee7.jpg', 6.50, 1, 1, '2024-11-30 21:02:35', NULL);
INSERT INTO `favorites` VALUES (64, 'admin111111', '玉屏风颗粒', 'http://img.800pharm.com/images///pms/upload/images/original/062017/13133/f0fe957be9e8e06302b1dde2a1e5aa46c45d68b9.jpg', 12.00, 1, 0, '2024-05-10 19:18:09', '2024-05-10 19:18:19');
INSERT INTO `favorites` VALUES (65, 'admin111111', '清开灵颗粒', 'http://img.800pharm.com/images///pms/upload/images/original/062017/13133/f0fe957be9e8e06302b1dde2a1e5aa46c45d68b9.jpg', 12.00, 1, 0, '2024-05-10 19:18:13', NULL);
INSERT INTO `favorites` VALUES (66, 'admin111111', '清热解毒胶囊', 'http://img.800pharm.com/images//20160130/20160130091644_618.jpg', 12.00, 1, 0, '2024-05-10 19:18:14', NULL);
INSERT INTO `favorites` VALUES (67, 'admin111111', '藿香正气水1aaa', 'http://img.800pharm.com/images///pms/upload/images/original/072020/22171/407ed9f2a8406bc57b2b2915df67cd69a1990a11.jpg', 12.00, 1, 0, '2024-05-10 19:18:15', NULL);
INSERT INTO `favorites` VALUES (68, 'admin111111', '藿香正气水', 'https://img1.baidu.com/it/u=1473267288,1468057165&fm=26&fmt=auto', 12.00, 1, 0, '2024-05-10 19:18:15', NULL);
INSERT INTO `favorites` VALUES (69, 'admin111111', '牛黄清火丸', 'http://img.800pharm.com/images//20160506/20160506163610_389.jpg', 12.00, 1, 0, '2024-05-10 19:18:16', NULL);
INSERT INTO `favorites` VALUES (70, 'admin111111', '羚羊清肺丸', 'http://img.800pharm.com/images//100204/20141128/20141128093414_422.jpg', 12.00, 1, 1, '2024-05-10 19:18:17', NULL);
INSERT INTO `favorites` VALUES (71, 'user32', '布洛芬缓释胶囊', 'http://img.800pharm.com/images//20160427/20160427142822_418.jpg', 13.00, 1, 0, '2024-05-12 18:07:01', NULL);
INSERT INTO `favorites` VALUES (72, 'user65', '布洛芬缓释胶囊', 'http://img.800pharm.com/images//20160427/20160427142822_418.jpg', 13.00, 1, 0, '2024-05-12 18:39:03', NULL);
INSERT INTO `favorites` VALUES (73, 'yangge', '广东凉茶颗粒', 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, 0, '2023-06-18 11:44:35', NULL);
INSERT INTO `favorites` VALUES (74, 'user', '广东凉茶颗粒', 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, 1, '2023-10-28 22:47:47', NULL);
INSERT INTO `favorites` VALUES (75, 'user', '康恩贝', 'https://img10.360buyimg.com/n7/jfs/t1/92042/31/26413/151478/648c19d2F828a871f/a953fe267b1c1502.jpg', 19.00, 1, 1, '2023-10-29 00:23:31', NULL);
INSERT INTO `favorites` VALUES (76, 'user', '广东凉茶颗粒', 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, 0, '2023-10-31 13:42:35', NULL);

-- ----------------------------
-- Table structure for health_tips
-- ----------------------------
DROP TABLE IF EXISTS `health_tips`;
CREATE TABLE `health_tips`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户账号,对应的用户账号才能修改',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `origin` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '来源',
  `category` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '所属分类',
  `reprint_link` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '原文链接',
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作者',
  `contents` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '正文内容',
  `read_number` bigint NULL DEFAULT 0 COMMENT '阅览次数',
  `status` int NOT NULL DEFAULT 0 COMMENT '是否可见。1首页展示，0不展示',
  `deleted` int NOT NULL DEFAULT 0,
  `gmt_create` datetime NOT NULL COMMENT '创作时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_article`(`title` ASC, `author` ASC, `username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of health_tips
-- ----------------------------
INSERT INTO `health_tips` VALUES (90, 'admin', '如何正确处理感冒发热？', '原创', '感冒发热', '', NULL, '<p><br/>### 感冒与发热的原因<br/><br/>感冒和发热是常见的症状，在日常生活中经常会遇到。感冒是由病毒引起的一种呼吸道感染，主要包括头部、鼻、喉和肺等部位。而发热则是机体对外来病原体的一种自我保护反应，有利于减缓病菌的繁殖速度。<br/><br/>### 如何正确处理感冒发热？<br/><br/>如果您出现了感冒和发热的症状，应该及时采取以下措施：<br/><br/>1. 休息：尽可能多地休息，平时注意身体锻炼，增加身体免疫力。<br/><br/>2. 饮食调理：多喝水，多吃水果蔬菜，适量补充维生素和蛋白质。<br/><br/>3. 疾病治疗：针对个体情况，采用药物治疗或中药治疗，有助于缓解症状。<br/><br/>总之，在感冒和发热期间，我们应该多关注自己的身体状况，及时调整生活方式，避免加重病情。</p>', 0, 1, 0, '2023-06-18 10:36:57', NULL);
INSERT INTO `health_tips` VALUES (91, 'admin', '怎样预防感冒发热？', '原创', '感冒发热', '', NULL, '<p>感冒是由病毒引起的传染病，很容易在人际接触中传播，因此，我们需要采取以下预防措施：<br/><br/>1. 保持室内通风：应该经常开窗，让空气流通，减少病毒在室内的滋生。<br/><br/>2. 多喝水：足够的水分可以增加呼吸道内的湿度，抵抗病毒侵入。<br/><br/>3. 均衡饮食：充足的维生素和矿物质有助于提高身体免疫力。<br/><br/>4. 加强锻炼：适量的运动能够增强机体免疫力，减少感冒的发生率。</p>', 0, 1, 0, '2023-06-18 10:37:27', NULL);
INSERT INTO `health_tips` VALUES (92, 'admin', '常见的感冒发热药物', '原创', '感冒发热', '', NULL, '<p>### 感冒药物<br/><br/>常见的感冒药物包括退热药、镇痛药、抗过敏药、消炎药等。以下简要介绍一些针对感冒症状的主要药物：<br/><br/>1. 对头痛、红肿发热等症状，常用的治疗药品有对乙酰氨基酚、布洛芬等退热药。<br/><br/>2. 针对咳嗽、鼻塞等症状，可选用吗啡类止咳药、氨溴索类祛痰药、盐酸伪麻黄碱类解毒剂等药物。<br/><br/>### 发热药物<br/><br/>发热药物通常包括非甾体抗炎药（NSAIDs）、大环内酯类药以及各种退热药、板蓝根口服液等。以下是一些常用的发热药物：<br/><br/>1. 退烧药：对于病人体温较高的情况，可选用对乙酰氨基酚、布洛芬等退烧药。<br/><br/>2. NSAIDs：这类药物不仅能够缓解发热的症状，还能够减轻感冒所造成的疼痛和不适。<br/><br/>总之，在使用药物之前，应该了解各种药物的适应症和禁忌症，并遵循医生的建议，不得擅自更改用药方案。</p>', 2, 1, 0, '2023-06-18 10:38:11', '2023-06-18 10:40:17');
INSERT INTO `health_tips` VALUES (93, 'admin', '如何缓解肚子疼？', '原创', '肠胃疾病', '', NULL, '<p>肚子疼的常见原因<br/><br/>肚子疼是一种常见的症状，可能与以下疾病有关：<br/><br/>1. 消化道疾病：如胃炎、胃溃疡、肠炎等。<br/><br/>2. 肠道感染：如痢疾、细菌性食物中毒等。<br/><br/>3. 肝胆疾病：如胆囊炎、胆结石等，常伴有黄疸、恶心等症状。<br/><br/>### 如何缓解肚子疼？<br/><br/>如果您遭遇了肚子疼，应该及时采取以下措施：<br/><br/>1. 多喝水：保持足够的水分摄入，有利于促进消化和缓解肚子疼痛。<br/><br/>2. 饮食调理：少吃辛辣刺激性食物，多吃蔬菜水果和清淡易消化的食物，有利于缓解肚子疼痛。<br/><br/>3. 疾病治疗：针对个体情况，采用药物治疗或手术治疗，有助于缓解症状。<br/><br/>总之，在肚子疼期间，我们应该多关注自己的身体状况，及时调整饮食和生活方式，避免加重病情。</p>', 2, 1, 0, '2023-06-18 11:26:00', '2023-06-18 11:58:37');
INSERT INTO `health_tips` VALUES (94, 'admin', '胃酸过多怎么办？', '原创', '肠胃疾病', '', NULL, '<p>胃酸过多的症状<br/><br/>胃酸过多是由于胃酸分泌过多引起的一种消化系统疾病，常表现为：<br/><br/>1. 前胸或上腹部疼痛、灼热感。<br/><br/>2. 反酸、嗳气、恶心、呕吐等。<br/><br/>### 如何缓解胃酸过多？<br/><br/>如果您患有胃酸过多，应该及时采取以下措施：<br/><br/>1. 饮食调理：少吃辛辣刺激性食物，多吃清淡易消化的食物，适当增加蛋白质和维生素摄入。<br/><br/>2. 饮食顺序：先吃淀粉类食物，再吃蛋白质类和脂肪类食物，有利于减少胃酸分泌。<br/><br/>3. 药物治疗：选择能够抑制胃酸分泌的药物，如质子泵抑制剂等。<br/><br/>总之，在胃酸过多期间，我们应该注意适当调整饮食和生活方式，合理用药，避免加重病情。</p>', 1, 1, 0, '2023-06-18 11:26:15', '2023-10-28 23:48:46');
INSERT INTO `health_tips` VALUES (95, 'admin', '腹泻的预防方法', '原创', '肠胃疾病', '', NULL, '<p>如何预防腹泻？<br/><br/>以下是一些腹泻的预防措施<br/><br/>1. 饮食清淡：少吃油腻、辛辣、难消化的食物，多吃易消化的清淡食物，如粥、面条、馒头等。<br/><br/>2. 干净卫生：饭前便后及时洗手，注意环境卫生，不食用过期变质食品。<br/><br/>3. 合理用药：不滥用抗生素、止泻药，如需用药应在医生指导下进行。<br/><br/>总之，在日常生活中我们需要注意饮食卫生，保持良好的生活习惯和心态，减少腹泻的发生率。如若出现了腹泻症状，应及时采取适当措施并及时就医。</p>', 9, 1, 0, '2023-06-18 11:26:34', '2023-10-31 15:40:41');

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `auth` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '身份',
  `admin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '谁',
  `gmt_create` datetime NOT NULL COMMENT '什么时候',
  `do_what` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '做了什么事',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 270 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log_operation
-- ----------------------------
INSERT INTO `log_operation` VALUES (191, '超级管理员', 'admin', '2023-06-18 10:02:09', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (192, '超级管理员', 'admin', '2023-06-18 10:09:17', '批量删除药品--药品编号：[ 10102,10103,10104,10105,10106,10107,10246,10247 ]');
INSERT INTO `log_operation` VALUES (193, '超级管理员', 'admin', '2023-06-18 10:09:48', '批量删除药品--药品编号：[ 10042,10043,10044,10045,10046,10047,10048,10049,10050,10051 ]');
INSERT INTO `log_operation` VALUES (194, '超级管理员', 'admin', '2023-06-18 10:09:55', '删除药品分类（总） - 啊啊啊');
INSERT INTO `log_operation` VALUES (195, '超级管理员', 'admin', '2023-06-18 10:14:38', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (196, '超级管理员', 'admin', '2023-06-18 11:07:12', '添加药品--药品名称：清热解毒口服液，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (197, '超级管理员', 'admin', '2023-06-18 11:14:33', '添加药品--药品名称：广东凉茶颗粒，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (198, '超级管理员', 'admin', '2023-06-18 11:17:01', '添加药品--药品名称：金银花颗粒，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (199, '超级管理员', 'admin', '2023-06-18 11:18:46', '添加药品--药品名称：康恩贝，所属分类：呼吸道类');
INSERT INTO `log_operation` VALUES (200, '超级管理员', 'admin', '2023-06-18 11:19:24', '添加药品--药品名称：仁和，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (201, '超级管理员', 'admin', '2023-06-18 11:21:18', '添加药品--药品名称：天和止咳丸，所属分类：心脑血管');
INSERT INTO `log_operation` VALUES (202, '超级管理员', 'admin', '2023-06-18 11:22:30', '添加药品--药品名称：口令芝多肽，所属分类：皮肤科药');
INSERT INTO `log_operation` VALUES (203, '超级管理员', 'admin', '2023-06-18 11:32:38', '将订单编号 Y202306181129085659 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (204, '超级管理员', 'admin', '2023-06-18 11:46:51', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (205, '超级管理员', 'admin', '2023-09-29 16:28:27', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (206, '超级管理员', 'admin', '2023-09-29 16:42:05', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (207, '超级管理员', 'admin', '2023-09-29 16:42:28', '将订单编号 Y202309291626047543 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (208, '超级管理员', 'admin', '2023-09-29 16:51:28', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (209, '超级管理员', 'admin', '2023-09-30 22:57:14', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (210, '超级管理员', 'admin', '2023-09-30 23:00:18', '将订单编号 Y202309302300008225 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (211, '超级管理员', 'admin', '2023-09-30 23:01:30', '将订单编号 Y202309291625195452 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (212, '超级管理员', 'admin', '2023-09-30 23:25:24', '将订单编号 Y202309302313245328 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (213, '超级管理员', 'admin', '2023-09-30 23:28:52', '将订单编号 Y202309302328348283 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (214, '超级管理员', 'admin', '2023-09-30 23:31:21', '将订单编号 Y202309302330349973 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (215, '超级管理员', 'admin', '2023-10-01 13:38:02', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (216, '超级管理员', 'admin', '2023-10-01 14:03:02', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (217, '超级管理员', 'admin', '2023-10-01 14:08:23', '将订单编号 Y202310011401471185 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (218, '超级管理员', 'admin', '2023-10-01 14:08:57', '将订单编号 Y202310011400557573 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (219, '超级管理员', 'admin', '2023-10-01 14:08:59', '将订单编号 Y202310011400483875 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (220, '超级管理员', 'admin', '2023-10-01 14:09:00', '将订单编号 Y202310011355175241 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (221, '超级管理员', 'admin', '2023-10-01 14:09:01', '将订单编号 Y202310011344272532 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (222, '超级管理员', 'admin', '2023-10-01 14:09:02', '将订单编号 Y202310011341143049 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (223, '超级管理员', 'admin', '2023-10-01 14:09:03', '将订单编号 Y202309302331388179 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (224, '超级管理员', 'admin', '2023-10-01 14:42:30', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (225, '超级管理员', 'admin', '2023-10-01 14:44:38', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (226, '超级管理员', 'admin', '2023-10-02 15:14:06', '修改药品--药品名称：仁和');
INSERT INTO `log_operation` VALUES (227, '超级管理员', 'admin', '2023-10-02 15:32:28', '将订单编号 Y202310021532155497 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (228, '超级管理员', 'admin', '2023-10-02 15:32:34', '修改药品--药品名称：仁和');
INSERT INTO `log_operation` VALUES (229, '超级管理员', 'admin', '2023-10-12 23:08:48', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (230, '超级管理员', 'admin', '2023-10-12 23:09:29', '添加药品--药品名称：测试药品，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (231, '超级管理员', 'admin', '2023-10-12 23:09:46', '删除药品--药品编号：10461，药品名称：测试药品');
INSERT INTO `log_operation` VALUES (232, '超级管理员', 'admin', '2023-10-17 10:14:03', '添加管理员 => 姓名：测试，账号：linge');
INSERT INTO `log_operation` VALUES (233, '超级管理员', 'admin', '2023-10-17 10:21:03', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (234, '超级管理员', 'admin', '2023-10-17 13:15:09', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (235, '超级管理员', 'admin', '2023-10-17 13:19:14', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (236, '超级管理员', 'admin', '2023-10-17 13:24:41', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (237, '超级管理员', 'admin', '2023-10-17 13:29:34', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (238, '超级管理员', 'admin', '2023-10-17 13:47:25', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (239, '超级管理员', 'admin2', '2023-10-17 14:11:11', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (240, '超级管理员', 'admin2', '2023-10-17 14:22:30', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (241, '超级管理员', 'admin2', '2023-10-17 14:27:19', '添加管理员 => 姓名：捆子，账号：kunzi');
INSERT INTO `log_operation` VALUES (242, '管理员', '捆子', '2023-10-17 14:31:01', '修改药品--药品名称：广东凉茶颗粒');
INSERT INTO `log_operation` VALUES (243, '超级管理员', 'admin', '2023-10-17 14:31:16', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (244, '超级管理员', 'admin', '2023-10-17 14:33:42', '添加管理员 => 姓名：xiaojian，账号：xiaojian');
INSERT INTO `log_operation` VALUES (245, '超级管理员', 'admin', '2023-10-17 14:40:07', '添加管理员 => 姓名：xiaokun，账号：xiaokun');
INSERT INTO `log_operation` VALUES (246, '超级管理员', 'admin', '2023-10-17 14:42:26', '添加管理员 => 姓名：kunzige，账号：kunzige');
INSERT INTO `log_operation` VALUES (247, '超级管理员', 'admin', '2023-10-17 14:46:20', '添加管理员 => 姓名：kunzigei，账号：kunzigei');
INSERT INTO `log_operation` VALUES (248, '超级管理员', 'admin', '2023-10-17 14:46:50', '添加管理员 => 姓名：kunzigeii，账号：kunzigeii');
INSERT INTO `log_operation` VALUES (249, '管理员', 'kunzigeii', '2023-10-17 14:47:20', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (250, '管理员', 'kunzigeii', '2023-10-17 14:47:32', '将订单编号 Y202310011505038899 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (251, '管理员', 'kunzigeii', '2023-10-17 14:47:39', '修改药品--药品名称：广东凉茶颗粒');
INSERT INTO `log_operation` VALUES (252, '超级管理员', 'admin', '2023-10-26 12:08:28', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (253, '超级管理员', 'admin', '2023-10-28 22:45:26', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (254, '超级管理员', 'admin', '2023-10-28 22:48:42', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (255, '超级管理员', 'admin', '2023-10-28 23:50:34', '修改药品--药品名称：广东凉茶颗粒');
INSERT INTO `log_operation` VALUES (256, '超级管理员', 'admin', '2023-10-28 23:50:52', '添加药品--药品名称：测试，所属分类：肠胃疾病');
INSERT INTO `log_operation` VALUES (257, '超级管理员', 'admin', '2023-10-28 23:50:57', '删除药品--药品编号：10464，药品名称：测试');
INSERT INTO `log_operation` VALUES (258, '超级管理员', 'admin', '2023-10-28 23:52:16', '将订单编号 Y202310011503487682 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (259, '超级管理员', 'admin', '2023-10-28 23:52:31', '禁用VIP账号：user');
INSERT INTO `log_operation` VALUES (260, '超级管理员', 'admin', '2023-10-28 23:52:34', '启用VIP账号：user');
INSERT INTO `log_operation` VALUES (261, '超级管理员', 'admin', '2023-10-29 00:24:32', '将订单编号 Y202310290024091955 的状态\'未发货\'修改为\'运输中\'');
INSERT INTO `log_operation` VALUES (262, '超级管理员', 'admin', '2023-10-29 00:28:56', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (263, '超级管理员', 'admin', '2023-10-31 13:45:41', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (264, '超级管理员', 'admin', '2023-10-31 15:40:41', '修改了文章（腹泻的预防方法），文章编号：95');
INSERT INTO `log_operation` VALUES (265, '超级管理员', 'admin', '2024-10-04 15:52:26', '登录药店后台管理系统');
INSERT INTO `log_operation` VALUES (266, '超级管理员', 'admin', '2024-10-04 15:54:58', '删除药品分类（总） - 儿科用药');
INSERT INTO `log_operation` VALUES (267, '超级管理员', 'admin', '2024-10-04 15:55:00', '删除药品分类（总） - 风湿关节');
INSERT INTO `log_operation` VALUES (268, '超级管理员', 'admin', '2024-10-04 15:55:02', '删除药品分类（总） - 妇科类');
INSERT INTO `log_operation` VALUES (269, '超级管理员', 'admin', '2024-10-04 15:58:32', '将订单编号 Y202410041557532121 的状态\'未发货\'修改为\'运输中\'');

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '药品编号',
  `picture` varchar(999) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '药品名称',
  `price` decimal(20, 2) NULL DEFAULT NULL COMMENT '药品价格',
  `sort` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '药品分类',
  `second_sort` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '二级分类',
  `standard` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '规格（例如：8丸*15袋（浓缩丸））',
  `description` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '药品的用途描述',
  `stock` bigint NULL DEFAULT NULL COMMENT '库存数量',
  `product_number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品编号',
  `approval_number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '批准文号',
  `manufacturer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `upload_user` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '上传的人是谁',
  `status` int NOT NULL DEFAULT 1 COMMENT '是否显示(1显示，0不显示，默认为1)',
  `is_discount` int NOT NULL DEFAULT 0 COMMENT '是否折扣',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_medicine_name`(`name` ASC) USING BTREE,
  INDEX `id_medicine_sort`(`sort` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10465 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (10454, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', '广东凉茶颗粒', 19.00, '1', '5', NULL, '【组成】\n\n金银花10克、连翘10克、薄荷10克、甘草6克、黄芩10克、板蓝根10克。\n\n【制法】\n\n将以上药材用清水浸泡20分钟，放入炖盅内，加足量清水，炖煮2小时，滤去渣渣即可。\n\n【功效及主治】\n\n本方具有清热解毒、凉血泻火、清肺润喉、开窍醒神的功效。适用于风热感冒、咽喉痛、口腔溃疡、痈疮肿痛等症。\n\n【注意事项】\n\n1.孕妇、哺乳期妇女和小孩慎用。\n\n2.药物过敏者禁用。\n\n3.用药期间忌食辛辣刺激性食物。\n\n4.保持室内空气流通，避免感染。\n\n5.如症状严重或复发，应尽早就医。', 993, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2023-10-28 23:52:16', '2023-10-28 23:52:16');
INSERT INTO `medicine` VALUES (10455, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', '金银花颗粒', 19.00, '1', '5', NULL, '商品名称：葵花复方金银花颗粒商品编号：100002877905商品毛重：125.00g商品产地：中国大陆适用人群：成人适用症状：扁桃体炎药品剂型：颗粒剂类别：中成药使用方法：口服国产/进口：国产', 991, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2024-10-04 15:58:31', '2024-10-04 15:58:32');
INSERT INTO `medicine` VALUES (10456, 'https://img10.360buyimg.com/n7/jfs/t1/92042/31/26413/151478/648c19d2F828a871f/a953fe267b1c1502.jpg', '康恩贝', 19.00, '2', '13', NULL, '品牌： 康恩贝\n商品名称：康恩贝肠炎宁片商品编号：100010619645商品毛重：34.00g商品产地：中国大陆药品剂型：片剂类别：中成药，非处方药使用方法：口服国产/进口：国产', 998, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2023-10-29 00:24:32', '2023-10-29 00:24:32');
INSERT INTO `medicine` VALUES (10457, 'https://img10.360buyimg.com/n7/jfs/t1/207870/39/34204/138598/648c2469F02b4794b/bf026a92a81ddd50.jpg', '仁和', 19.00, '1', '5', NULL, '品牌： 仁和\n商品名称：仁和蒲地蓝消炎片商品编号：100019364985商品毛重：35.00g商品产地：中国大陆类别：非处方药，中成药药品剂型：片剂适用人群：通用使用方法：口服国产/进口：国产', 999, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2023-10-02 15:32:33', '2023-10-02 15:32:34');
INSERT INTO `medicine` VALUES (10459, 'https://img11.360buyimg.com/n1/jfs/t1/165213/6/35221/141183/648c34a0F4c761c5e/9fc340eefd8190fa.jpg', '天和止咳丸', 256.00, '3', '14', NULL, '品牌： 五蜈蚣标（Takabb）\n商品名称：五蜈蚣标止咳丸60包中药味商品编号：100013891106商品毛重：320.00g商品产地：泰国适用症状：咳嗽少痰，其他，咳喘，口干咽燥，痰多适用人群：通用使用方法：含服药品类型：中成药', 999, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2023-06-18 11:21:18', NULL);
INSERT INTO `medicine` VALUES (10460, 'https://img13.360buyimg.com/n1/jfs/t1/96935/9/22591/174073/646db1e0F0790d5b5/8aee4156c01edb1d.jpg', '口令芝多肽', 3199.00, '4', '17', NULL, '品牌： 江户\n商品名称：日本原装进口灵芝多肽降糖抗氧化抗炎天然增强免疫力抗肿瘤抗糖尿病预防高血压抗过敏灵芝仙草软胶囊 单瓶装 日本制商品编号：10075285342063店铺： 江户海外官方旗舰店商品毛重：100.00g商品产地：日本是否保健食品：营养膳食补充剂（非食健字）包装形式：瓶装剂型：胶囊国产/进口：进口', 999, NULL, NULL, NULL, 'admin', 1, 0, 1, 0, '2023-06-18 11:22:30', NULL);

-- ----------------------------
-- Table structure for medicine_sort
-- ----------------------------
DROP TABLE IF EXISTS `medicine_sort`;
CREATE TABLE `medicine_sort`  (
  `sort_id` int NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用来治疗什么的',
  `upload_user` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '上传者',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`sort_id`) USING BTREE,
  UNIQUE INDEX `id_sort_category`(`category` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medicine_sort
-- ----------------------------
INSERT INTO `medicine_sort` VALUES (1, '感冒发热', 'admin', 1, 0, '2024-10-14 11:52:57', '2024-05-12 18:03:24');
INSERT INTO `medicine_sort` VALUES (2, '肠胃疾病', 'admin', 1, 0, '2024-10-14 11:52:57', '2024-01-11 15:30:53');
INSERT INTO `medicine_sort` VALUES (3, '呼吸道类', 'admin', 1, 0, '2024-10-14 11:52:57', '2024-01-11 15:30:54');
INSERT INTO `medicine_sort` VALUES (4, '心脑血管', '懒洋洋', 1, 0, '2024-10-14 11:52:57', '2024-10-14 11:52:57');
INSERT INTO `medicine_sort` VALUES (5, '皮肤科药', '懒洋洋', 1, 0, '2024-10-14 11:52:57', '2024-10-14 11:52:57');
INSERT INTO `medicine_sort` VALUES (6, '肝胆用药', '懒洋洋', 1, 0, '2024-10-14 11:52:57', '2024-10-14 11:52:57');
INSERT INTO `medicine_sort` VALUES (7, '肿瘤科药', 'admin', 1, 0, '2024-10-16 13:40:09', '2024-10-16 13:40:09');

-- ----------------------------
-- Table structure for medicine_sort_second
-- ----------------------------
DROP TABLE IF EXISTS `medicine_sort_second`;
CREATE TABLE `medicine_sort_second`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_sort_id` bigint NULL DEFAULT NULL COMMENT '一级分类的id',
  `second_sort` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '二级分类的名称',
  `amount` int NULL DEFAULT 0 COMMENT '该分类的药品数量',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_second_sort`(`first_sort_id` ASC, `second_sort` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medicine_sort_second
-- ----------------------------
INSERT INTO `medicine_sort_second` VALUES (1, 1, '感冒中药', 0, 1, 1, '2024-12-05 17:11:30', '2024-01-11 20:29:58');
INSERT INTO `medicine_sort_second` VALUES (2, 1, '感冒西药', 0, 1, 1, '2024-12-05 17:11:53', '2024-01-11 20:29:50');
INSERT INTO `medicine_sort_second` VALUES (3, 1, '消暑祛湿', 0, 1, 1, '2024-12-05 17:12:22', NULL);
INSERT INTO `medicine_sort_second` VALUES (4, 1, '中西合剂', 0, 1, 1, '2024-12-05 17:12:41', '2024-01-11 15:25:08');
INSERT INTO `medicine_sort_second` VALUES (5, 1, '清热解毒', 0, 1, 0, '2024-12-05 17:13:26', '2024-01-11 15:28:58');
INSERT INTO `medicine_sort_second` VALUES (6, 1, '解热镇痛', 0, 1, 0, '2024-12-05 17:13:49', '2024-01-11 15:28:57');
INSERT INTO `medicine_sort_second` VALUES (7, 1, '清热滋阴', 0, 1, 0, '2024-12-05 17:14:22', '2024-01-11 15:41:52');
INSERT INTO `medicine_sort_second` VALUES (8, 2, '胃炎', 0, 1, 1, '2024-12-05 17:27:46', NULL);
INSERT INTO `medicine_sort_second` VALUES (9, 2, '消化不良', 0, 1, 1, '2024-12-05 17:28:33', NULL);
INSERT INTO `medicine_sort_second` VALUES (10, 2, '便秘', 0, 1, 1, '2024-12-05 17:28:52', NULL);
INSERT INTO `medicine_sort_second` VALUES (11, 2, '驱虫类药', 0, 1, 0, '2024-12-05 17:29:10', NULL);
INSERT INTO `medicine_sort_second` VALUES (12, 2, '肠胃功能紊乱', 0, 1, 0, '2024-12-05 17:29:30', NULL);
INSERT INTO `medicine_sort_second` VALUES (13, 2, '腹痛腹泻', 0, 1, 0, '2024-12-05 17:30:33', NULL);
INSERT INTO `medicine_sort_second` VALUES (14, 3, '止咳化痰', 0, 1, 0, '2024-12-05 17:31:02', NULL);
INSERT INTO `medicine_sort_second` VALUES (15, 3, '哮喘', 0, 1, 0, '2024-12-05 17:31:46', NULL);
INSERT INTO `medicine_sort_second` VALUES (16, 3, '气管炎', 0, 1, 0, '2024-12-05 17:32:12', NULL);
INSERT INTO `medicine_sort_second` VALUES (17, 4, '高血压', 0, 1, 0, '2024-12-05 17:33:04', NULL);
INSERT INTO `medicine_sort_second` VALUES (18, 4, '冠心病', 0, 1, 1, '2024-12-05 17:33:21', NULL);
INSERT INTO `medicine_sort_second` VALUES (19, 4, '心律失常', 0, 1, 1, '2024-12-05 17:33:36', NULL);
INSERT INTO `medicine_sort_second` VALUES (20, 4, '高血脂', 0, 1, 1, '2024-12-05 17:33:49', NULL);
INSERT INTO `medicine_sort_second` VALUES (21, 4, '中风偏瘫', 0, 1, 1, '2024-12-05 17:34:06', NULL);
INSERT INTO `medicine_sort_second` VALUES (22, 4, '糖尿病', 0, 1, 0, '2024-12-05 17:34:29', NULL);
INSERT INTO `medicine_sort_second` VALUES (23, 4, '低血压', 0, 1, 1, '2024-12-05 17:34:42', NULL);
INSERT INTO `medicine_sort_second` VALUES (24, 4, '外周血管疾病', 0, 1, 0, '2024-12-05 17:35:02', NULL);
INSERT INTO `medicine_sort_second` VALUES (25, 4, '动脉硬化', 0, 1, 0, '2024-12-05 17:35:20', NULL);
INSERT INTO `medicine_sort_second` VALUES (37, 5, '烧烫伤', 0, 1, 0, '2024-12-05 17:39:10', NULL);
INSERT INTO `medicine_sort_second` VALUES (38, 5, '爽肤祛痱', 0, 1, 0, '2024-12-05 17:39:37', NULL);
INSERT INTO `medicine_sort_second` VALUES (39, 5, '冻疮', 0, 1, 0, '2024-12-05 17:39:45', NULL);
INSERT INTO `medicine_sort_second` VALUES (40, 6, '清肝利胆', 0, 1, 0, '2024-12-05 17:41:50', NULL);
INSERT INTO `medicine_sort_second` VALUES (41, 6, '肝炎用药', 0, 1, 0, '2024-12-05 17:42:15', NULL);
INSERT INTO `medicine_sort_second` VALUES (42, 6, '脂肪肝', 0, 1, 0, '2024-12-05 17:42:25', NULL);
INSERT INTO `medicine_sort_second` VALUES (43, 7, '肺癌', 0, 1, 0, '2024-12-05 17:42:41', NULL);
INSERT INTO `medicine_sort_second` VALUES (44, 7, '胃癌', 0, 1, 0, '2024-12-05 17:43:05', NULL);
INSERT INTO `medicine_sort_second` VALUES (45, 8, '风湿骨痛', 0, 1, 0, '2024-12-05 17:43:14', NULL);
INSERT INTO `medicine_sort_second` VALUES (46, 8, '跌打损伤', 0, 1, 0, '2024-12-05 17:43:32', NULL);
INSERT INTO `medicine_sort_second` VALUES (47, 9, '妇科炎症', 0, 1, 0, '2024-12-05 17:43:48', NULL);
INSERT INTO `medicine_sort_second` VALUES (48, 9, '外用避孕', 0, 1, 0, '2024-12-05 17:44:11', NULL);
INSERT INTO `medicine_sort_second` VALUES (49, 10, '咳嗽哮喘', 0, 1, 0, '2024-12-05 17:44:38', NULL);
INSERT INTO `medicine_sort_second` VALUES (50, 10, '驱虫用药', 0, 1, 0, '2024-12-05 17:45:53', NULL);
INSERT INTO `medicine_sort_second` VALUES (52, 10, '感冒发烧', 0, 1, 0, '2024-12-05 17:46:19', NULL);
INSERT INTO `medicine_sort_second` VALUES (54, 13, '肾炎', 0, 1, 0, '2024-12-05 17:46:34', NULL);
INSERT INTO `medicine_sort_second` VALUES (55, 13, '尿毒症', 0, 1, 0, '2024-12-05 17:48:08', NULL);
INSERT INTO `medicine_sort_second` VALUES (56, 14, '眼疲劳', 0, 1, 0, '2024-12-05 17:49:20', NULL);
INSERT INTO `medicine_sort_second` VALUES (57, 14, '近视眼', 0, 1, 0, '2024-12-05 17:49:28', NULL);
INSERT INTO `medicine_sort_second` VALUES (58, 14, '红眼病', 0, 1, 0, '2024-12-05 17:49:39', NULL);
INSERT INTO `medicine_sort_second` VALUES (59, 15, '口腔护理', 0, 1, 0, '2024-12-05 17:49:50', NULL);
INSERT INTO `medicine_sort_second` VALUES (60, 15, '口腔溃疡', 0, 1, 0, '2024-12-05 17:50:16', NULL);
INSERT INTO `medicine_sort_second` VALUES (61, 15, '牙痛', 0, 1, 0, '2024-12-05 17:50:27', NULL);
INSERT INTO `medicine_sort_second` VALUES (62, 15, '牙龈出血', 0, 1, 0, '2024-12-05 17:50:40', NULL);
INSERT INTO `medicine_sort_second` VALUES (63, 16, '抗生素', 0, 1, 0, '2024-12-05 17:51:03', NULL);
INSERT INTO `medicine_sort_second` VALUES (64, 11, '失眠', 0, 1, 0, '2024-12-05 17:52:26', NULL);
INSERT INTO `medicine_sort_second` VALUES (65, 11, '多梦易醒', 0, 1, 0, '2024-12-05 17:53:05', NULL);
INSERT INTO `medicine_sort_second` VALUES (66, 11, '心悸多梦', 0, 1, 0, '2024-12-05 17:53:17', NULL);
INSERT INTO `medicine_sort_second` VALUES (67, 12, 'α-糖苷酶抑制剂', 0, 1, 0, '2024-12-05 17:53:36', NULL);
INSERT INTO `medicine_sort_second` VALUES (68, 12, '复合降糖', 0, 1, 0, '2024-12-05 17:53:45', NULL);
INSERT INTO `medicine_sort_second` VALUES (69, 12, '磺酰脲类', 0, 1, 0, '2024-12-05 17:53:56', NULL);
INSERT INTO `medicine_sort_second` VALUES (70, 12, '胰岛素控制剂', 0, 1, 0, '2024-12-05 17:54:03', NULL);
INSERT INTO `medicine_sort_second` VALUES (71, 12, '糖尿病并发症', 0, 1, 0, '2024-12-05 17:54:12', NULL);
INSERT INTO `medicine_sort_second` VALUES (72, 16, '合成抗菌药', 0, 1, 0, '2024-12-05 17:55:24', NULL);
INSERT INTO `medicine_sort_second` VALUES (73, 16, '抗结核病类', 0, 1, 0, '2024-12-05 17:55:30', NULL);
INSERT INTO `medicine_sort_second` VALUES (74, 16, '抗病毒类', 0, 1, 0, '2024-12-05 17:55:38', NULL);
INSERT INTO `medicine_sort_second` VALUES (75, 1, '测试2', 0, 1, 1, '2024-01-11 18:38:07', '2024-04-28 13:14:44');
INSERT INTO `medicine_sort_second` VALUES (76, 1, '测试3', 0, 1, 1, '2024-01-11 18:47:45', '2024-04-28 13:14:39');

-- ----------------------------
-- Table structure for medicine_synopsis
-- ----------------------------
DROP TABLE IF EXISTS `medicine_synopsis`;
CREATE TABLE `medicine_synopsis`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `main_ingredients` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要成分',
  `traits` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性状',
  `indications` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '适应症/主治功能',
  `standard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格型号',
  `dosage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用法用量',
  `adverse_reactions` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '不良反应',
  `taboo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁 忌',
  `precautions` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注意事项',
  `interaction` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药物互相作用',
  `children_medication` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '儿童用药',
  `old_medication` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '老年患者用药',
  `pregnancy_medication` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '孕妇及哺乳期用药',
  `overdose` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药物过量',
  `pharmacology` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药理毒理',
  `pharmacokinetics` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药代动力学',
  `storage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '贮 藏',
  `box` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包 装',
  `valid_period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有效期',
  `executive_standard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行标准',
  `approval_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批准文号',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10309 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medicine_synopsis
-- ----------------------------
INSERT INTO `medicine_synopsis` VALUES (10001, '通用名称：布洛芬缓释胶囊<br/>', '本品每粒含主要成份布洛芬0.3g。辅料为：糖、淀 粉、硬脂酸、聚乙烯吡咯烷酮。', '本品内容物为白色球形小丸。', '用于缓解轻至中度疼痛如：关节痛、肌肉痛、神经痛、头痛、偏头痛、牙痛、痛经，也用于普通感冒或流行性感冒引起的发热。', '0.3g*20粒', '口服，成人一次1粒，一日2次(早晚各一次)。', '1、少数病人可出现恶心、呕吐、腹痛、腹泻、便秘、胃烧灼感或轻度消化不良、胃肠道溃疡及出血、转氨酶升高、头痛、头晕、耳鸣、视力模糊、精神紧张、嗜睡、下肢水肿或体重骤增。', '1、对其他非甾体抗炎药过敏者禁用。', '1．本品为对症治疗药，自我用药不宜长期或大量使用，用于止痛不得超过5天，用于解热不得超过3天，如症状不缓解，请咨询医师或药师。', '1、本品与其他解热、镇痛、抗炎药物同用时可增加胃肠道不良反应，并可能导致溃疡。 2、本品与肝素、双香豆素类(如华法林)等抗凝药同用时，可导致凝血酶原时间延长，增加出血倾向。 3、本品与地高辛、甲氨蝶呤、口服降血糖药物同用时，能使这些药物的血药浓度增高，不宜同用。 4、本品与呋塞米(呋喃苯胺酸)同用时，后者的排钠和降压作用减弱；与抗高血压药同用时，也降低后者的降压效果。 5、布洛芬与氨基糖苷类、糖皮质激素、抗血小板药物如阿司匹林、环孢霉素、利尿剂、锂、喹诺酮类药物、齐多夫定、选择性5-羟色胺再摄取抑制剂联合使用已有相互作用的报道，应慎用或在医师指导下使用。 6、如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。', '儿童必须在成人监护下使用。', '尚不明确。', '孕妇及哺乳期妇女禁用。', '过量服药可引起头痛、呕吐、倦睡、低血压等，一般症状在停药后即可自行消失。服药超量时应作紧急处理，包括催吐或洗胃、口服活性炭、抗酸药或（和）利尿药，并给予监护及其他支持疗法。', '本品能抑制前列腺素的合成， 具有镇痛、解热和抗炎的作用。且为缓释剂型，可使药物在体内逐渐释放。每服用一次，可持续12小时止痛。', '口服易吸收，与食物同服时吸收减慢，但吸收量不减少。与含铝和镁的抗酸药同服不影响吸收。血浆蛋白结合率为99％。服药后1.2～2.1小时血药浓度达峰值，用量200mg，血药浓度为22～27g/ml，用量400mg时为23～45g/ml，用量600mg时为43～57g/ml。一次给药后t1/2一般为1.8～2小时。服药5小时后关节液浓度与血药浓度相等，以后的12小时内关节液浓度高于血浆浓度。本品在肝内代谢，60%～90%尿排出，100%于24小时内排出，其中约1%为原形物，一部分随粪便排出。', '密封保存。', 'PTP铝箔，0.3g*20s/盒。', '36 个月', '中国药典2010年版二部', '国药准字J123456789', '江西省冰沐凌药业有限公司', 1, 0, '2024-11-17 19:53:37', NULL);

-- ----------------------------
-- Table structure for menu_first
-- ----------------------------
DROP TABLE IF EXISTS `menu_first`;
CREATE TABLE `menu_first`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '一级菜单id',
  `title` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `children` json NOT NULL COMMENT '子菜单',
  `need_level` int NULL DEFAULT NULL COMMENT '需要的权限等级',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_menu_id`(`menu_id` ASC) USING BTREE COMMENT '菜单id唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu_first
-- ----------------------------
INSERT INTO `menu_first` VALUES (1, '1', '后台管理', '{}', 2, 1, 0, '2024-11-22 08:31:45');
INSERT INTO `menu_first` VALUES (2, '2', '药品管理', '{}', 2, 1, 0, '2024-11-22 08:32:55');
INSERT INTO `menu_first` VALUES (3, '3', '订单管理', '{}', 2, 1, 0, '2024-11-22 08:33:16');
INSERT INTO `menu_first` VALUES (4, '4', '用户管理', '{}', 2, 1, 0, '2024-11-22 08:33:31');
INSERT INTO `menu_first` VALUES (5, '5', '首页设置', '{}', 1, 1, 0, '2024-11-22 08:33:46');
INSERT INTO `menu_first` VALUES (6, '6', '资讯推送', '{}', 2, 1, 0, '2024-11-22 08:34:10');

-- ----------------------------
-- Table structure for menu_second
-- ----------------------------
DROP TABLE IF EXISTS `menu_second`;
CREATE TABLE `menu_second`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `father_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '父菜单id',
  `menu_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单id',
  `title` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `path` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '路径',
  `need_level` int NULL DEFAULT NULL COMMENT '需要的权限等级',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_menu_id`(`menu_id` ASC) USING BTREE COMMENT '子菜单id唯一',
  UNIQUE INDEX `id_menu_path`(`path` ASC) USING BTREE COMMENT '路径唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu_second
-- ----------------------------
INSERT INTO `menu_second` VALUES (1, '1', '11', '我的桌面', '/index', 2, 1, 0, '2024-11-22 08:36:20');
INSERT INTO `menu_second` VALUES (2, '1', '12', '操作日志', '/admin/log', 1, 1, 0, '2024-11-22 08:36:44');
INSERT INTO `menu_second` VALUES (3, '1', '13', '统计页面', '/xxxx', 2, 0, 0, '2024-11-22 08:36:59');
INSERT INTO `menu_second` VALUES (4, '2', '21', '药品列表', '/drug/list', 2, 1, 0, '2024-11-22 08:37:27');
INSERT INTO `menu_second` VALUES (5, '2', '22', '分类管理', '/type/management', 2, 1, 0, '2024-11-22 08:37:45');
INSERT INTO `menu_second` VALUES (6, '2', '23', '添加药品', '/drug/add', 2, 0, 1, '2024-11-22 08:38:05');
INSERT INTO `menu_second` VALUES (7, '3', '31', '最新订单', '/orderForm/newOrder', 2, 1, 0, '2024-11-22 08:38:33');
INSERT INTO `menu_second` VALUES (8, '3', '32', '运输中订单', '/orderForm/transitOrder', 2, 1, 0, '2024-11-22 08:38:58');
INSERT INTO `menu_second` VALUES (9, '3', '33', '已确认订单', '/orderForm/confirmedOrder', 2, 1, 0, '2024-11-22 08:39:12');
INSERT INTO `menu_second` VALUES (10, '4', '41', '管理员', '/admin/list', 1, 1, 0, '2024-11-22 08:39:38');
INSERT INTO `menu_second` VALUES (11, '4', '42', '普通用户', '/user/list', 2, 1, 0, '2024-11-22 08:39:54');
INSERT INTO `menu_second` VALUES (12, '4', '43', '会员用户', '/vip/list', 2, 1, 0, '2024-11-22 08:40:08');
INSERT INTO `menu_second` VALUES (13, '5', '51', '健康资讯', '/home/health/tips/recommend', 2, 1, 0, '2024-11-22 08:40:26');
INSERT INTO `menu_second` VALUES (15, '6', '61', '添加资讯', '/drug/news/add', 2, 1, 0, '2024-11-22 08:41:12');
INSERT INTO `menu_second` VALUES (16, '6', '62', '资讯列表', '/drug/news/list', 2, 1, 0, '2024-11-22 08:41:34');
INSERT INTO `menu_second` VALUES (17, '6', '63', '医药常识', '/drug/nous', 2, 1, 1, '2024-11-22 08:41:54');
INSERT INTO `menu_second` VALUES (19, '3', '34', '投诉订单', '/orderForm/complaintOrder', 2, 1, 0, '2024-12-29 13:43:21');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单编号，以Y开头，根据年月日时分秒生成，再加上四位随机生成的',
  `item_amount` int NULL DEFAULT NULL COMMENT '清单药品数量',
  `payment` decimal(20, 2) NULL DEFAULT NULL COMMENT '应付金额',
  `message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `receipt_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receipt_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货地区',
  `details_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态（订单状态（-1：被投诉；1：未发货；2：运输中；3：交易完成，但是还没有评价；4：交易完成；0：取消交易；）',
  `complaint` int NOT NULL DEFAULT 0 COMMENT '投诉状态：默认为0（不投诉）',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投诉理由',
  `back_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '退货理由',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '交易订单生成时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_order_id`(`order_id` ASC) USING BTREE COMMENT '订单编号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (57, 'user', 'Y202309302328348283', 2, 133.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-09-30 23:28:35', '2023-10-01 14:09:19');
INSERT INTO `orders` VALUES (58, 'user', 'Y202309302330349973', 1, 95.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-09-30 23:30:35', '2023-10-01 14:09:18');
INSERT INTO `orders` VALUES (59, 'user', 'Y202309302331388179', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-09-30 23:31:38', '2023-10-01 14:09:18');
INSERT INTO `orders` VALUES (60, 'user', 'Y202310011341143049', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-10-01 13:41:14', '2023-10-01 14:09:18');
INSERT INTO `orders` VALUES (61, 'user', 'Y202310011344272532', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-10-01 13:44:28', '2023-10-01 14:09:18');
INSERT INTO `orders` VALUES (62, 'user', 'Y202310011355175241', 1, 76.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-10-01 13:55:17', '2023-10-01 14:09:17');
INSERT INTO `orders` VALUES (63, 'user', 'Y202310011400483875', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-10-01 14:00:48', '2023-10-01 14:09:17');
INSERT INTO `orders` VALUES (64, 'user', 'Y202310011400557573', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 3, 0, NULL, NULL, 0, '2023-10-01 14:00:55', '2023-10-01 14:09:17');
INSERT INTO `orders` VALUES (65, 'user', 'Y202310011401471185', 1, 95.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 4, 0, NULL, NULL, 0, '2023-10-01 14:01:47', '2023-10-01 14:09:26');
INSERT INTO `orders` VALUES (66, 'user', 'Y202310011445091204', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 1, 0, NULL, NULL, 0, '2023-10-01 14:45:10', NULL);
INSERT INTO `orders` VALUES (67, 'user', 'Y202310011503278825', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 1, 0, NULL, NULL, 0, '2023-10-01 15:03:28', NULL);
INSERT INTO `orders` VALUES (68, 'user', 'Y202310011503487682', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 2, 1, '不行', NULL, 0, '2023-10-01 15:03:49', '2023-10-28 23:52:16');
INSERT INTO `orders` VALUES (69, 'user', 'Y202310011505038899', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 2, 0, NULL, NULL, 0, '2023-10-01 15:05:03', '2023-10-17 14:47:32');
INSERT INTO `orders` VALUES (70, 'user', 'Y202310021532155497', 1, 38.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 2, 0, NULL, NULL, 0, '2023-10-02 15:32:15', '2023-10-02 15:32:28');
INSERT INTO `orders` VALUES (71, 'user', 'Y202310290024091955', 1, 19.00, '要一颗就行', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 4, 0, NULL, NULL, 0, '2023-10-29 00:24:10', '2023-10-29 00:24:57');
INSERT INTO `orders` VALUES (72, 'user', 'Y202310311343277074', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 1, 0, NULL, NULL, 0, '2023-10-31 13:43:27', NULL);
INSERT INTO `orders` VALUES (73, 'user', 'Y202410041557532121', 1, 19.00, '', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 2, 0, NULL, NULL, 0, '2024-10-04 15:57:54', '2024-10-04 15:58:32');

-- ----------------------------
-- Table structure for orders_items
-- ----------------------------
DROP TABLE IF EXISTS `orders_items`;
CREATE TABLE `orders_items`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `drug_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '药名',
  `standard` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '规格',
  `picture` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `price` decimal(20, 2) NULL DEFAULT NULL COMMENT '单价',
  `amount` int NULL DEFAULT NULL COMMENT '数量',
  `gmt_create` datetime NOT NULL COMMENT '生成时间',
  `deleted` int NOT NULL DEFAULT 0,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders_items
-- ----------------------------
INSERT INTO `orders_items` VALUES (46, 'Y202309302328348283', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 4, '2023-09-30 23:28:35', 0, NULL);
INSERT INTO `orders_items` VALUES (47, 'Y202309302328348283', '金银花颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', 19.00, 3, '2023-09-30 23:28:35', 0, NULL);
INSERT INTO `orders_items` VALUES (48, 'Y202309302330349973', '仁和', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/207870/39/34204/138598/648c2469F02b4794b/bf026a92a81ddd50.jpg', 19.00, 5, '2023-09-30 23:30:35', 0, NULL);
INSERT INTO `orders_items` VALUES (49, 'Y202309302331388179', '金银花颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', 19.00, 1, '2023-09-30 23:31:38', 0, NULL);
INSERT INTO `orders_items` VALUES (50, 'Y202310011341143049', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 13:41:14', 0, NULL);
INSERT INTO `orders_items` VALUES (51, 'Y202310011344272532', '仁和', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/207870/39/34204/138598/648c2469F02b4794b/bf026a92a81ddd50.jpg', 19.00, 1, '2023-10-01 13:44:28', 0, NULL);
INSERT INTO `orders_items` VALUES (52, 'Y202310011355175241', '金银花颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', 19.00, 4, '2023-10-01 13:55:17', 0, NULL);
INSERT INTO `orders_items` VALUES (53, 'Y202310011400483875', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 14:00:48', 0, NULL);
INSERT INTO `orders_items` VALUES (54, 'Y202310011400557573', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 14:00:55', 0, NULL);
INSERT INTO `orders_items` VALUES (55, 'Y202310011401471185', '仁和', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/207870/39/34204/138598/648c2469F02b4794b/bf026a92a81ddd50.jpg', 19.00, 5, '2023-10-01 14:01:47', 0, NULL);
INSERT INTO `orders_items` VALUES (56, 'Y202310011445091204', '金银花颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', 19.00, 1, '2023-10-01 14:45:10', 0, NULL);
INSERT INTO `orders_items` VALUES (57, 'Y202310011503278825', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 15:03:28', 0, NULL);
INSERT INTO `orders_items` VALUES (58, 'Y202310011503487682', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 15:03:49', 0, NULL);
INSERT INTO `orders_items` VALUES (59, 'Y202310011505038899', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-01 15:05:03', 0, NULL);
INSERT INTO `orders_items` VALUES (60, 'Y202310021532155497', '仁和', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/207870/39/34204/138598/648c2469F02b4794b/bf026a92a81ddd50.jpg', 19.00, 2, '2023-10-02 15:32:15', 0, NULL);
INSERT INTO `orders_items` VALUES (61, 'Y202310290024091955', '康恩贝', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/92042/31/26413/151478/648c19d2F828a871f/a953fe267b1c1502.jpg', 19.00, 1, '2023-10-29 00:24:10', 0, NULL);
INSERT INTO `orders_items` VALUES (62, 'Y202310311343277074', '广东凉茶颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/221198/32/25861/116702/64538564F8df10b8e/96669230fcbaf0a9.jpg', 19.00, 1, '2023-10-31 13:43:27', 0, NULL);
INSERT INTO `orders_items` VALUES (63, 'Y202410041557532121', '金银花颗粒', NULL, 'https://img10.360buyimg.com/n7/jfs/t1/220344/1/30085/122858/648ab5b3F9d2f9267/9b713038ac97ba2a.jpg', 19.00, 1, '2024-10-04 15:57:54', 0, NULL);

-- ----------------------------
-- Table structure for shopping_address
-- ----------------------------
DROP TABLE IF EXISTS `shopping_address`;
CREATE TABLE `shopping_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `receipt_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receipt_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货地区',
  `details_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `is_default` int NULL DEFAULT NULL COMMENT '标志位：是否为默认地址（1为默认，0普通）',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_address
-- ----------------------------
INSERT INTO `shopping_address` VALUES (1, 'admin111111', 'aaa', '辽宁省/抚顺市/新抚区', 'aaaa', '12345688999', 0, 1, '2024-11-28 20:54:31', '2024-12-06 15:43:32');
INSERT INTO `shopping_address` VALUES (3, 'admin111111', 'aaa', '山西省/太原市/市辖区', 'wafwafa', '44444444445', 0, 1, '2024-11-28 21:25:31', '2024-11-29 20:22:10');
INSERT INTO `shopping_address` VALUES (5, 'admin111111', 'xxxx', '天津市/市辖区/河东区', '不知道', '15615555232', 0, 1, '2024-11-28 21:50:08', '2024-11-29 20:22:08');
INSERT INTO `shopping_address` VALUES (6, 'admin111111', 'xxxx', '辽宁省/抚顺市/东洲区', '不知道', '15615555232', 0, 1, '2024-11-28 21:51:39', '2024-11-28 21:52:29');
INSERT INTO `shopping_address` VALUES (8, 'admin111111', 'xxxxwww', '北京市/市辖区/东城区', '和MV买买买啊啊啊', '12345688999', 0, 1, '2024-11-28 21:59:49', '2024-11-29 17:22:13');
INSERT INTO `shopping_address` VALUES (9, 'admin111111', 'xxxxwww', '北京市/市辖区/东城区', '和MV买买买啊啊啊', '15864555662', 0, 1, '2024-11-28 22:04:58', '2024-11-29 17:22:13');
INSERT INTO `shopping_address` VALUES (10, 'admin111111', '黑胡椒客户', '山西省/阳泉市/平定县', '参与感该博客', '15864555662', 0, 1, '2024-11-28 22:05:26', NULL);
INSERT INTO `shopping_address` VALUES (11, 'admin111111', '高防御gu8救', '山西省/阳泉市/平定县', '参与感该博客', '15864555662', 0, 1, '2024-11-28 22:05:53', NULL);
INSERT INTO `shopping_address` VALUES (13, 'admin111111', '高防御gu8救', '山西省/阳泉市/平定县', '参与感该博客莫某品牌', '15864555662', 0, 1, '2024-11-28 22:14:04', '2024-12-06 17:42:49');
INSERT INTO `shopping_address` VALUES (16, 'admin111111', 'aaa', '内蒙古自治区/赤峰市/元宝山区', '和MV买买买啊啊啊和MV买买买啊啊啊', '44444444445', 0, 1, '2024-11-28 22:33:18', NULL);
INSERT INTO `shopping_address` VALUES (17, 'admin111111', '高防御gu8救', '北京市/市辖区/朝阳区', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '44444444445', 0, 1, '2024-11-28 22:40:59', NULL);
INSERT INTO `shopping_address` VALUES (18, 'admin111111', 'admin', '江西省/南昌市/青山湖区', '南昌理工学院', '17770038333', 0, 0, '2024-11-28 22:52:31', '2024-05-14 16:31:53');
INSERT INTO `shopping_address` VALUES (19, 'admin111111', 'admin00', '河南省/洛阳市/西工区', '南昌理工学院000', '17770038333', 0, 0, '2024-11-29 20:22:53', '2024-05-10 19:22:02');
INSERT INTO `shopping_address` VALUES (20, 'admin111111', '喜羊羊', '天津市/市辖区/和平区', '羊村', '15864555662', 1, 0, '2024-11-29 20:24:27', '2024-05-14 16:31:53');
INSERT INTO `shopping_address` VALUES (21, 'admin111111', 'xxxx', '新疆维吾尔自治区/哈密市/巴里坤哈萨克自治县', '嗷嗷我滴老家哦豁叽叽叽', '44444444445', 0, 1, '2024-11-29 20:33:04', '2024-12-10 08:32:27');
INSERT INTO `shopping_address` VALUES (22, 'admin111111', '喜羊羊', '内蒙古自治区/包头市/市辖区', '嘎嘣丢很烦', '15864555662', 0, 1, '2024-12-06 16:47:23', '2024-12-06 16:48:41');
INSERT INTO `shopping_address` VALUES (23, 'admin111111', '懒羊羊', '北京市/市辖区/石景山区', '啊我滴五环啊啊嗷嗷', '12345688990', 0, 0, '2024-12-10 08:35:03', '2024-05-10 19:22:04');
INSERT INTO `shopping_address` VALUES (24, 'admin111111', 'sb123', '内蒙古自治区/赤峰市/松山区', '不知道', '12345688999', 0, 1, '2024-12-13 15:39:54', '2024-01-12 20:08:42');
INSERT INTO `shopping_address` VALUES (25, 'root222222', '喜羊羊', '北京市/市辖区/东城区', '不知道', '12345688999', 1, 0, '2024-03-04 16:34:35', NULL);
INSERT INTO `shopping_address` VALUES (26, 'user65', '喜羊羊', '天津市/市辖区/和平区', 'dawwwwwwwwwwww', '15864555662', 1, 0, '2024-05-12 18:38:35', NULL);
INSERT INTO `shopping_address` VALUES (27, 'yangge', '羊羊羊', '北京市/市辖区/东城区', 'China', '13245788495', 0, 0, '2023-06-18 11:27:49', NULL);
INSERT INTO `shopping_address` VALUES (28, 'user', 'yang', '广东省/广州市/从化区', '繁华街33号', '17607605333', 1, 0, '2023-09-29 16:22:46', NULL);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户的账号',
  `drug_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '加入购物车的药品名称',
  `amount` int NULL DEFAULT NULL COMMENT '数量',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` datetime NOT NULL COMMENT '添加时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (82, 'user', '金银花颗粒', 4, 1, '2023-10-01 13:54:31', '2023-10-01 13:55:01');
INSERT INTO `shopping_cart` VALUES (83, 'user', '广东凉茶颗粒', 1, 1, '2023-10-01 14:00:29', NULL);
INSERT INTO `shopping_cart` VALUES (84, 'user', '仁和', 5, 1, '2023-10-01 14:01:34', '2023-10-01 14:01:37');
INSERT INTO `shopping_cart` VALUES (85, 'user', '金银花颗粒', 2, 1, '2023-10-01 14:45:06', '2023-10-01 14:45:06');
INSERT INTO `shopping_cart` VALUES (86, 'user', '仁和', 2, 1, '2023-10-02 15:25:25', '2023-10-02 15:32:12');
INSERT INTO `shopping_cart` VALUES (87, 'user', '广东凉茶颗粒', 1, 1, '2023-10-28 22:48:04', NULL);
INSERT INTO `shopping_cart` VALUES (88, 'user', '康恩贝', 1, 1, '2023-10-29 00:23:37', NULL);
INSERT INTO `shopping_cart` VALUES (89, 'user', '金银花颗粒', 1, 1, '2024-10-04 15:57:40', NULL);

-- ----------------------------
-- Table structure for sort_recommend
-- ----------------------------
DROP TABLE IF EXISTS `sort_recommend`;
CREATE TABLE `sort_recommend`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
  `category` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名称',
  `sort_id` int NULL DEFAULT NULL COMMENT '对应的分类id',
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sort_recommend
-- ----------------------------
INSERT INTO `sort_recommend` VALUES (1, '感冒发热', 1, '2024-10-29 12:34:52', NULL);
INSERT INTO `sort_recommend` VALUES (2, '肠胃疾病', 2, '2024-10-29 12:35:11', NULL);
INSERT INTO `sort_recommend` VALUES (3, '呼吸道类', 3, '2024-10-29 12:35:44', NULL);
INSERT INTO `sort_recommend` VALUES (4, '心脑血管', 4, '2024-10-29 12:37:40', NULL);
INSERT INTO `sort_recommend` VALUES (5, '外用常备', 5, '2024-10-29 12:37:56', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `auth` int NULL DEFAULT NULL COMMENT '身份权限',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态(1启用，0禁用)',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除(1为删除)',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_user_username`(`username` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1000042 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user', 'e10adc3949ba59abbe56e057f20f883e', 3, 1, 1, 0, '2024-09-20 14:11:03', '2023-10-28 23:52:34');
INSERT INTO `user` VALUES (110, 'linge', 'e10adc3949ba59abbe56e057f20f883e', 4, 1, 1, 0, '2023-06-18 11:27:22', NULL);
INSERT INTO `user` VALUES (1000038, 'xiaokun', '25d55ad283aa400af464c76d713c07ad', NULL, 1, 1, 0, '2023-10-17 14:40:07', NULL);
INSERT INTO `user` VALUES (1000039, 'kunzige', '25d55ad283aa400af464c76d713c07ad', NULL, 1, 1, 0, '2023-10-17 14:42:26', NULL);
INSERT INTO `user` VALUES (1000040, 'kunzigei', '25d55ad283aa400af464c76d713c07ad', NULL, 1, 1, 0, '2023-10-17 14:45:31', NULL);
INSERT INTO `user` VALUES (1000041, 'kunzigeii', '25d55ad283aa400af464c76d713c07ad', NULL, 1, 1, 0, '2023-10-17 14:46:50', NULL);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '对应的用户id',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_userInfo_nick`(`nick_name` ASC) USING BTREE,
  UNIQUE INDEX `id_userInfo_userId`(`user_id` ASC) USING BTREE COMMENT '用户id唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1000035 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 1, 'img/avatar/2024/01/10/20240110211349791bdc757.jpg', 'yang123', 'admin', '男', '2000-01-01', '17770038333', '1164302621@qq.com', '广东省/广州市/市辖区', 1);
INSERT INTO `user_info` VALUES (2, 2, 'img/avatar/2024/11/27/202411271439062990b5490.jpg', '懒洋洋', '懒羊羊', '男', '2000-07-28', '18070234494', '2819690558@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (3, 3, 'img/avatar/defaultAvatar.png', '用户0', '用户0', '男', '2000-07-28', '18855678273', '10000@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (4, 4, 'img/avatar/defaultAvatar.png', '用户1', '用户1', '男', '2000-07-28', '18309288190', '10001@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (5, 5, 'img/avatar/defaultAvatar.png', '用户2', '用户2', '男', '2000-07-28', '18775917314', '10002@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (6, 6, 'img/avatar/defaultAvatar.png', '用户3', '用户3', '女', '2000-07-28', '1887303820', '10003@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (7, 7, 'img/avatar/defaultAvatar.png', '用户4', '用户4', '女', '2000-07-28', '1895915764', '10004@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (8, 8, 'img/avatar/defaultAvatar.png', '用户5', '用户5', '女', '2000-07-28', '18827505866', '10005@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (9, 9, 'img/avatar/defaultAvatar.png', '用户6', '用户6', '男', '2000-07-28', '1865343728', '10006@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (10, 10, 'img/avatar/defaultAvatar.png', '用户7', '用户7', '男', '2000-07-28', '18384931555', '10007@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (11, 11, 'img/avatar/defaultAvatar.png', '用户8', '用户8', '男', '2000-07-28', '18738689837', '10008@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (12, 12, 'img/avatar/defaultAvatar.png', '用户9', '用户9', '男', '2000-07-28', '18191166622', '10009@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (13, 13, 'img/avatar/defaultAvatar.png', '用户10', '用户10', '女', '2000-07-28', '18736282689', '100010@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (14, 14, 'img/avatar/defaultAvatar.png', '用户11', '用户11', '女', '2000-07-28', '1860474311', '100011@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (15, 15, 'img/avatar/defaultAvatar.png', '用户12', '用户12', '男', '2000-07-28', '18103799128', '100012@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (16, 16, 'img/avatar/defaultAvatar.png', '用户13', '用户13', '男', '2000-07-28', '18704689997', '100013@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (17, 17, 'img/avatar/defaultAvatar.png', '用户14', '用户14', '女', '2000-07-28', '18300947853', '100014@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (18, 18, 'img/avatar/defaultAvatar.png', '用户15', '用户15', '男', '2000-07-28', '18145231991', '100015@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (19, 19, 'img/avatar/defaultAvatar.png', '用户16', '用户16', '女', '2000-07-28', '18437804860', '100016@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (1000004, 1000004, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, '2000-07-28', NULL, 'aaa@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (1000006, 1000006, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, '2000-07-28', NULL, 'aaa@qq.com', '', 1);
INSERT INTO `user_info` VALUES (1000007, 1000007, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, '2000-07-28', NULL, 'aaa@qq.com', '', 1);
INSERT INTO `user_info` VALUES (1000008, 1000008, 'img/avatar/defaultAvatar.png', 'jhfrdrhr', 'dgrdgaa', '男', '2000-01-01', '12345688999', 'aaa@qq.com', '天津市/市辖区/和平区', 1);
INSERT INTO `user_info` VALUES (1000009, 1000009, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, '2000-07-28', NULL, 'aaa@qq.com', '江西省/南昌市/青山湖区', 1);
INSERT INTO `user_info` VALUES (1000010, 1000010, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000011, 1000011, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000012, 1000012, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000013, 1000013, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000014, 1000014, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000015, 1000015, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000016, 1000016, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000017, 1000017, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000018, 1000018, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000019, 1000019, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000020, 1000020, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000021, 1000021, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000022, 1000022, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000023, 1000023, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000024, 1000024, 'img/avatar/2024/05/12/20240512180139130168618.jpg', '无敌霸王龙', '喜羊羊', '男', '2000-01-27', '12345688999', 'aaa@qq.com', '天津市/市辖区/和平区', 1);
INSERT INTO `user_info` VALUES (1000025, 1000025, 'img/avatar/2024/05/12/202405121813325547be173.jpg', '美羊羊', '美羊羊', '男', '2003-01-11', '15864555662', 'aaa@qq.com', '内蒙古自治区/包头市/东河区', 1);
INSERT INTO `user_info` VALUES (1000026, 1000026, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000027, 1000027, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000028, 1000028, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000029, 1000029, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000030, 1000030, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, 'aaa@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000031, 1000031, 'img/avatar/2024/05/12/20240512183812712d1ff1a.jpg', '无敌霸王龙123', '喜羊羊', '男', '1999-11-03', '17770038555', '1111@qq.com', '山西省/阳泉市/市辖区', 1);
INSERT INTO `user_info` VALUES (1000032, 1000032, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, '1164302621@qq.com', NULL, 1);
INSERT INTO `user_info` VALUES (1000033, 1000033, 'img/avatar/2024/05/13/20240513102254623e85654.jpg', 'QQ小冰', 'QQ小冰', '男', '1999-12-17', '17770038555', '1164302621@qq.com', '北京市/市辖区/东城区', 1);
INSERT INTO `user_info` VALUES (1000034, 1000034, 'img/avatar/defaultAvatar.png', NULL, NULL, NULL, NULL, NULL, '12778393@qq.com', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
