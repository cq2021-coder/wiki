package com.cq.wiki.service;

import com.cq.wiki.domain.User;
import com.cq.wiki.domain.UserExample;
import com.cq.wiki.exception.BusinessException;
import com.cq.wiki.exception.BusinessExceptionCode;
import com.cq.wiki.mapper.UserMapper;
import com.cq.wiki.req.UserLoginReq;
import com.cq.wiki.req.UserQueryReq;
import com.cq.wiki.req.UserResetPasswordReq;
import com.cq.wiki.req.UserSaveReq;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.resp.UserLoginResp;
import com.cq.wiki.resp.UserQueryResp;
import com.cq.wiki.util.CopyUtil;
import com.cq.wiki.util.SnowFlake;
import com.cq.wiki.util.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public UserLoginResp login(UserLoginReq req){
        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)){
            //用户名不存在
            LOG.info("用户名不存在,{}",req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else {
            if (userDb.getPassword().equals(req.getPassword())){
                //登录成功
                UserLoginResp userLoginResp = CopyUtil.copy(userDb,UserLoginResp.class);
                userLoginResp.setToken(TokenUtil.signToken(userDb));
                LOG.info("token为：{}",userLoginResp.getToken());
                return userLoginResp;
            }
            else {
                //密码错误
                LOG.info("密码错误，输入密码：{},数据库密码：{}",req.getPassword(),userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        /*List<UserResp> respList = new ArrayList<>();
        for (User user : userList) {
            UserResp userResp = CopyUtil.copy(user,UserResp.class);
            respList.add(userResp);
        }*/

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(userList, UserQueryResp.class));

        return pageResp;
    }

    public List<UserQueryResp> all(){
        return CopyUtil.copyList(userMapper.selectByExample(new UserExample()), UserQueryResp.class);
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req){
        User user = CopyUtil.copy(req,User.class);
        if (ObjectUtils.isEmpty(req.getId())){
            if (ObjectUtils.isEmpty(selectByLoginName(req.getLoginName()))) {
                //新增
                user.setId(snowFlake.nextId()/10000);
                userMapper.insert(user);
            }
            else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }
        else {
            //更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 修改密码
     */
    public void resetPassword(UserResetPasswordReq req){
        User user = CopyUtil.copy(req,User.class);
        if (req.getId()!=null) {
            userMapper.updateByPrimaryKeySelective(user);
        }
        else {
            throw new BusinessException(BusinessExceptionCode.RESET_USER_ERROR);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询登录名
     */
    public User selectByLoginName (String loginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }
        else {
            return userList.get(0);
        }
    }

}
