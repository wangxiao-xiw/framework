INSERT INTO t_${projectName}_users VALUES (900000000, 'admin@admin.com', 'ybS2mYKpZZUqHHAgqhsyhKpc5Vc=', '系统管理员', '系统管理员', '1', '123456', '', '', '', NULL, '2015-12-04 15:04:46.43', '2015-11-07 11:52:22.511', false);

INSERT INTO t_${projectName}_role VALUES (100001, '超级管理员', '超级管理员', '2015-11-27', '2015-12-05', false);

INSERT INTO t_${projectName}_function VALUES (100000, NULL, '权限管理', 'root', 0, false, 1, false, 1, '权限管理根节点', '2015-11-24', '2015-11-24', false);
INSERT INTO t_${projectName}_function VALUES (100001, 100000, '个人信息管理', 'usermanager', 1, false, 1, false, 1, '个人信息管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100002, 100001, '修改密码', 'modifyPassword', 2, false, 1, true, 2, '修改密码', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100003, 100000, '系统配置管理', 'systemconfig', 1, true, 2, false, 1, '系统配置管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100004, 100003, '数据字典配置', 'dataDictionaryManager', 2, true, 1, true, 2, '数据字典配置', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100005, 100000, '权限管理', 'authorization', 1, true, 3, false, 1, '权限管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100006, 100005, '权限管理', 'functionManager', 2, true, 1, true, 2, '权限管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100007, 100005, '角色管理', 'roleManager', 2, true, 2, true, 2, '角色管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100008, 100005, '用户管理', 'userManager', 2, true, 3, true, 2, '用户管理', '2015-11-25', '2015-11-25', false);
INSERT INTO t_${projectName}_function VALUES (100009, 100008, '分配角色', '/rs/user/authUserRoles', 3, true, 2, false, 3, '分配角色', '2015-12-05', '2015-12-05', false);

INSERT INTO t_${projectName}_userrole VALUES (100001, 100001, 900000000);

INSERT INTO t_${projectName}_functionrole VALUES (100001, 100001, 100001);
INSERT INTO t_${projectName}_functionrole VALUES (100002, 100001, 100002);
INSERT INTO t_${projectName}_functionrole VALUES (100003, 100001, 100003);
INSERT INTO t_${projectName}_functionrole VALUES (100004, 100001, 100004);
INSERT INTO t_${projectName}_functionrole VALUES (100005, 100001, 100005);
INSERT INTO t_${projectName}_functionrole VALUES (100006, 100001, 100006);
INSERT INTO t_${projectName}_functionrole VALUES (100007, 100001, 100007);
INSERT INTO t_${projectName}_functionrole VALUES (100008, 100001, 100008);
INSERT INTO t_${projectName}_functionrole VALUES (100009, 100001, 100009);

INSERT INTO t_${projectName}_dictionary_type VALUES (10000000, '管理平台', '${projectName}', '2015-11-08 18:08:34.017', '2015-11-24 00:00:00', NULL, false, false, NULL, 1446977314017);
INSERT INTO t_${projectName}_dictionary_type VALUES (10000001, '姓别', 'sex', '2015-11-08 18:08:57.114', '2015-11-18 00:00:00', NULL, true, false, 10000000, 1446978449978);
INSERT INTO t_${projectName}_dictionary_type VALUES (10000002, '功能类型', 'functionType', '2015-11-24 15:19:33.091', '2015-11-24 00:00:00', NULL, true, false, 10000000, 1448351100369);


INSERT INTO t_${projectName}_dictionary_data VALUES (1000001, 'sex', '0', '0', 'woman', NULL, '女', false, NULL, '2015-11-08 18:27:15.384', '2015-11-08 18:27:15.384');
INSERT INTO t_${projectName}_dictionary_data VALUES (1000002, 'sex', '1', '1', 'man', NULL, '男', false, NULL, '2015-11-08 18:27:29.944', '2015-11-08 18:27:29.944');
INSERT INTO t_${projectName}_dictionary_data VALUES (1000003, 'functionType', '1', '1', 'module', NULL, '模块', false, NULL, '2015-11-24 15:44:11.626', '2015-11-24 15:44:11.626');
INSERT INTO t_${projectName}_dictionary_data VALUES (1000004, 'functionType', '2', '2', 'menu', NULL, '菜单', false, NULL, '2015-11-24 15:44:36.326', '2015-11-24 15:44:36.326');
INSERT INTO t_${projectName}_dictionary_data VALUES (1000005, 'functionType', '3', '3', 'button', NULL, '按钮', false, NULL, '2015-11-24 15:45:00.322', '2015-11-24 15:45:00.322');
