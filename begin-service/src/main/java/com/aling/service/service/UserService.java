package com.aling.service.service;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.aling.common.base.BaseService;
import com.aling.common.cache.AppSessionCache;
import com.aling.common.cache.JetCacheFactory;
import com.aling.common.cache.SysUserId2SessionInfoCache;
import com.aling.common.config.properties.BeginProperties;
import com.aling.common.constant.Constants;
import com.aling.common.constant.RespConstant;
import com.aling.common.entity.Role;
import com.aling.common.entity.User;
import com.aling.common.entity.UserRole;
import com.aling.common.enums.StatusEnum;
import com.aling.common.mapper.UserMapper;
import com.aling.common.service.MenuService;
import com.aling.common.service.RoleService;
import com.aling.common.service.UserRoleService;
import com.aling.common.util.DateUtil;
import com.aling.common.util.EncryptUtil;
import com.aling.common.util.RespUtil;
import com.aling.common.util.StringUtil;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.system.*;
import com.aling.core.exception.AppException;
import com.aling.core.exception.AppSessionException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService extends BaseService{

    public static final String RANDOM_KEY_PREFIX = "RANDOMCODE-";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JetCacheFactory jetCacheFactory;

    @Autowired
    BeginProperties beginProperties;

    @Autowired
    private SysUserId2SessionInfoCache sysUserId2SessionInfoCache;

    @Autowired
    private AppSessionCache appSessionCache;

    //@Cached(name="quereyUser",cacheType = CacheType.REMOTE, expire = 60)
    public ResponseParams<UserRes> queryUser(RequestParams requestParams)  throws AppException {

        //构造出请求参数
        UserReq req = paramToObject(requestParams, UserReq.class);
        //构造出返回值
        ResponseParams<UserRes> responseParams = buildResponseParams(requestParams);
        UserRes userRes = new UserRes();


        //业务内容 这里只是举个例子
        String randomKey = "1111";
        String randomCode = String.valueOf(jetCacheFactory.getCommonCache().get(RANDOM_KEY_PREFIX + randomKey));
        log.info("randomCode:{}", randomCode);

        log.info("开始查询用户。。。。");
        Map<String,String> result = new HashMap<String,String>();
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("USER_NAME","kobe");
        List<User> userList =  userMapper.selectList(ew);
        if(userList !=null && userList.size()>0){
            User user = userList.get(0);
            userRes.setUserName("今天是个好日子");
            userRes.setPhone("18090908080");
            responseParams.setParam(userRes); //包装返回参数
        }
        log.info("查询成功了。。。");
        return RespUtil.success(responseParams); //返回内容
    }

    public Map<String,String> addUser(String userName,String password) throws AppException {
        Map<String,String> map = new HashMap<String,String>();

        try {
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
                throw new AppException("查询用户失败：必填参数不能为空");
            }
            User userCondition = new User();
            userCondition.setUserName(userName);
            User oldUser = userMapper.selectOne(userCondition);
            if (oldUser != null) {
                throw new AppException("新增用户失败：该用户名已被注册");
            }
            saveNewUser(userName,password);
            map.put("success","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("添加用户失败了，请查看代码");
        }

        return  map;
    }


    @Transactional(rollbackFor = Exception.class)
    public User saveNewUser(String userName,String password) throws Exception{
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setUserState(1);
        newUser.setPhone("123456");
        EncryptUtil.encryptPassword(newUser, password);
        newUser.setCreateTime(DateUtil.getNowTime("yyyy-MM-dd hh:mm:ss"));
        userMapper.insert(newUser);
        return newUser;
    }

    /*****************************************************用户登录开始*******************************************/


    /**
     * 用户登录
     * @return
     * @throws AppException
     */
    public ResponseParams<LoginRes> login(RequestParams requestParams) throws AppException {

        UserReq req = paramToObject(requestParams, UserReq.class);
        //构造出返回值
        ResponseParams<LoginRes> responseParams = buildResponseParams(requestParams);

        String userName = req.getUserName();
        String password = req.getPassword();
        String randomKey = req.getRandomKey();
        String code = req.getCode();
        log.info("用户登陆,用户名={},密码={},randomKey={},验证码={}",userName,password,randomKey,code);
        String randomCode = String.valueOf(jetCacheFactory.getCommonCache().get(RANDOM_KEY_PREFIX + randomKey));
        log.debug("randomCode:{}", randomCode);
        if (beginProperties.isNeedCheckLoginRandomCode()) {
            if (StringUtils.isEmpty(code)) {
                throw new AppException("验证码不能为空");
            }
            if (!code.equals(randomCode)) {
                throw new AppException("验证码错误");
            }
        }
        User userCondition = new User();
        userCondition.setUserName(userName);
        User sysUser = userMapper.selectOne(userCondition);
        if (null == sysUser) {
            throw new AppException("您输入的用户名错误");
        }
        if (!StatusEnum.ENABLE.value.equals(String.valueOf(sysUser.getUserState()))) {
            throw new AppException("登录失败：账号被禁用");
        }
        String userId = sysUser.getId();
        Date freezeTime = sysUser.getFreezeTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (freezeTime != null && currentTimeMillis < freezeTime.getTime()) {
            int mins = (int) (freezeTime.getTime() - currentTimeMillis) / 1000 / 60;
            mins = mins <= 1 ? 1 : mins;
            throw new AppException("账号被冻结，请在" + mins + "分钟后重试");
        }
        String encryptPass = "";
        try {
            encryptPass = EncryptUtil.encodeToPasswordHex(sysUser, password);
            log.info("加密后的密码是={}",encryptPass);
        } catch (Exception e) {
            throw new AppException("密码加密失败");
        }

        if (!encryptPass.equals(sysUser.getPassword())) {
            int errorTimes = 0;
            if(null != sysUser.getErrorTimes()){
                errorTimes = sysUser.getErrorTimes();
            }
            int errorChance = Constants.ERROR_CHANCE;
            int freezeMin = Constants.FREEZE_MIN;
            SysUserRes res = new SysUserRes();
            res.setUserId(sysUser.getId());
            if (errorTimes >= errorChance) {
                errorTimes = 1;
            } else {
                errorTimes++;
            }
            res.setErrorTimes(errorTimes);
            String msg;
            if (errorTimes >= errorChance) {
                res.setFreezeTime(DateUtils.addMinutes(new Date(), freezeMin));
                msg = "操作过于频繁，账号将被冻结" + freezeMin + "分钟";
            } else {
                msg = "您输入的用户名密码错误，错误" + (errorChance - errorTimes) + "次后账号将被冻结";
            }
            BeanUtils.copyProperties(res, sysUser);
            sysUser.setId(userId);
            userMapper.updateById(sysUser);
            throw new AppException(msg);
        }
        LoginRes loginRes = userlogin(sysUser);
        try {
            createSessionId(loginRes, "carter-iphone");
        } catch (AppSessionException e) {
            throw new AppException(e.getErrorCode(), e.getErrorMsg());
        }
        responseParams.setParam(loginRes);
        return RespUtil.success(responseParams); //返回内容
    }

    private LoginRes userlogin(User sysUser) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(sysUser.getId());
        sessionInfo.setId(sysUser.getId());
        sessionInfo.setSys(true);
        sessionInfo.setUserName(sysUser.getUserName());
        sysUserId2SessionInfoCache.saveSessionInfo(sessionInfo.getId(), sessionInfo);
        LoginRes loginRes = new LoginRes();
        SysUserRes res = new SysUserRes();
        BeanUtils.copyProperties(sysUser, res, "password", "lastLoginTime");
        if (sysUser.getLastLoginTime() != null) {
            res.setLastLoginTime(DateFormatUtils.format(sysUser.getLastLoginTime(), "yyyy年MM月dd日 HH:mm"));
        } else {
            res.setLastLoginTime("首次登录");
        }
        sysUser.setLastLoginTime(new Date());
        sysUser.setErrorTimes(0);

        userMapper.updateById(sysUser);
         res.setUserId(sysUser.getId());
        loginRes.setSysUserRes(res);
        return loginRes;
    }


    private void createSessionId(LoginRes loginRes, String deviceId) throws AppSessionException {
        String userId = loginRes.getSysUserRes().getUserId();
        SessionInfo sessionInfo = sysUserId2SessionInfoCache.getSessionInfo(userId);
        if (sessionInfo == null) {
            sessionInfo = new SessionInfo();
        }
        sessionInfo.setUserId(userId);
        sessionInfo.setSys(true);
        sessionInfo.setUserName(loginRes.getSysUserRes().getUserName());
        String sessionId = appSessionCache.initSession(sessionInfo,deviceId);
        loginRes.setSessionId(sessionId);
    }

    /*****************************************************用户登录结束*******************************************/

    /*****************************************************用户登出开始*******************************************/


    /**
     * 用户登录
     * @return
     * @throws AppException
     */
    public ResponseParams<LoginRes> logout(RequestParams requestParams) throws AppException {

        log.info("退出登陆={}", JSON.toJSONString(requestParams));
        ResponseParams<LoginRes> response = buildResponseParams(requestParams);
        response.setSignType(requestParams.getSignType());
        response.setEncryptType(requestParams.getEncryptType());
        try {
            SessionInfo sessionInfo = this.getSession(requestParams);
            if (sessionInfo == null) {
                throw new AppException(RespConstant.ERR_SESSION_TIMEOUT, "管理员退出失败：管理员未登录");
            }
            String userId = sessionInfo.getUserId();
            sysUserId2SessionInfoCache.removeSessionInfo(userId);
            return  RespUtil.success(response);
        } catch (Exception e) {
            return super.error(response, RespConstant.ERROR_UNKOWN, e.getMessage());
        }
    }




    /*****************************************************用户登出结束*******************************************/



    /*****************************************************根据用户ID查询菜单等信息开始*******************************************/

    public ResponseParams<SysUserRes> getMenuInfo(RequestParams requestParams) throws AppException {
        log.info("查询用户详情={}", JSON.toJSONString(requestParams));
        ResponseParams<SysUserRes> response = buildResponseParams(requestParams);
        try {
            log.info(String.format("根据参数{%s}获取管理员", JSON.toJSONString(requestParams.getParam())));
            UserReq req = super.paramToObject(requestParams, UserReq.class);
            String userId = "";
            SessionInfo sessionInfo = this.getSession(requestParams);
            if(null == sessionInfo){
                throw new AppException("session为空,请先登录");
            }
            userId = sessionInfo.getUserId();
            if (StringUtil.isEmpty(userId)) {
                throw new AppException("查询管理员失败：userId不能为空");
            }
            User sysUser = userMapper.selectById(userId);
            if (sysUser == null) {
                throw new AppException("管理员不存在");
            }
            SysUserRes res = new SysUserRes();
            BeanUtils.copyProperties(sysUser, res);
            res.setUserId(sysUser.getId());//设置用户ID
            if (sysUser.getLastLoginTime() != null) {
                res.setLastLoginTime(DateFormatUtils.format(sysUser.getLastLoginTime(), "yyyy年MM月dd日 HH:mm"));
            }
            getRoleByUserId(res);
            response.setParam(res);
            return success(response);
        } catch (Exception e) {
            return super.error(response, RespConstant.ERROR_UNKOWN, e.getMessage());
        }
    }

    private void getRoleByUserId(SysUserRes res) {
        if (res != null && !StringUtil.isEmpty(res.getUserId())) {
            // 查询用户绑定角色
            EntityWrapper<UserRole> roleCondition = new EntityWrapper<>();
            roleCondition.eq("USER_ID", res.getUserId());
            List<UserRole> userRoleList = userRoleService.selectList(roleCondition);
            if (userRoleList.size() > 0) {
                String roleId = userRoleList.get(0).getRoleId();
                Role role = roleService.selectById(roleId);
                if (role != null) {
                    // 查询角色绑定菜单
                    List<SysMenuRes> sysMenuList = menuService.queryByRoleId(role.getId());
                    res.setRoleId(role.getId());
                    res.setRoleName(role.getRoleName());
                    res.setSysMenuList(sysMenuList);
                }
            }
        }
    }

/*****************************************************根据用户ID查询菜单等信息结束*******************************************/


}
