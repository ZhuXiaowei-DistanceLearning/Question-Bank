package com.zxw.web.service;

import com.zxw.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.util.Objects;

/**
 * 用户服务类
 */
public interface UserService {

    public Long createUser(UserVO userCreate);
}