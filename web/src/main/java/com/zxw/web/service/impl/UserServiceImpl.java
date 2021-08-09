package com.zxw.web.service.impl;

import com.zxw.web.dao.UserDAO;
import com.zxw.web.service.UserService;
import com.zxw.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.util.Objects;

/**
 * 用户服务类
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 创建用户
     * 
     * @param userCreate 用户创建
     * @return 用户标识
     */
    @Override
    public Long createUser(UserVO userCreate) {
        return 0L;
    }
}