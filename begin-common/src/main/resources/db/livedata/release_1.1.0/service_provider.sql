--liquibase formatted sql

--changeset wangly:20200122001
INSERT INTO API_SERVICE_PROVIDER (SERVICE_ID, SERVICE_DESC, BEAN_NAME, METHOD, PAGE_URL,IS_CHECK_SESSIONID, IS_CHECK_TOKEN,LOG_FLAG)
VALUES ('admin.appointPlatform.user.test', '第一个测试接口', 'firstTestService', 'queryUser', null , 'Y', 'N','Y');

--changeset wangly:20200122002
INSERT INTO API_SERVICE_PROVIDER (SERVICE_ID, SERVICE_DESC, BEAN_NAME, METHOD, PAGE_URL,IS_CHECK_SESSIONID, IS_CHECK_TOKEN,LOG_FLAG)
VALUES ('admin.appointPlatform.user.login', '用户登录接口', 'userService', 'login', null , 'N', 'N','Y');

--changeset wangly:20200122003
INSERT INTO API_SERVICE_PROVIDER (SERVICE_ID, SERVICE_DESC, BEAN_NAME, METHOD, PAGE_URL,IS_CHECK_SESSIONID, IS_CHECK_TOKEN,LOG_FLAG)
VALUES ('admin.appointPlatform.user.logout', '用户登出接口', 'userService', 'logout', null , 'Y', 'N','Y');

--changeset wangly:20200122004
INSERT INTO API_SERVICE_PROVIDER (SERVICE_ID, SERVICE_DESC, BEAN_NAME, METHOD, PAGE_URL,IS_CHECK_SESSIONID, IS_CHECK_TOKEN,LOG_FLAG)
VALUES ('admin.appointPlatform.user.getMenuInfo', '查询菜单', 'userService', 'getMenuInfo', null , 'Y', 'N','N');
