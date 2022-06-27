package com.zxw.service;

import com.zxw.domain.MemberDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2022/6/27 21:19
 */
@Component("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDetails memberDetails = new MemberDetails();
        return memberDetails;
    }
}
