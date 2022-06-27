package com.zxw.config;

import com.zxw.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2022/6/27 21:20
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置JWT的内容增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
//        delegates.add(tulingTokenEnhancer);
//        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);

        //使用密码模式需要配置
        endpoints.authenticationManager(authenticationManager)
                .reuseRefreshTokens(false)  //refresh_token是否重复使用
//                .userDetailsService(myUserDetailService) //刷新令牌授权包含对用户信息的检查
//                .tokenStore(tokenStore)  //指定token存储策略是jwt
//                .accessTokenConverter(jwtAccessTokenConverter)
//                .tokenEnhancer(enhancerChain) //配置tokenEnhancer
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); //支持GET,POST请求
    }

    /**
     * 授权服务器安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //第三方客户端校验token需要带入 clientId 和clientSecret来校验
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("isAuthenticated()");//来获取我们的tokenKey需要带入clientId,clientSecret

        //允许表单认证
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public ClientDetailsService myClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //配置client_id
                .withClient("client")
                //配置client‐secret
//                .secret(passwordEncoder.encode("123123"))
                .secret("123123")
                //配置访问token的有效期
                .accessTokenValiditySeconds(3600)  //配置刷新token的有效期
                .refreshTokenValiditySeconds(864000)  //配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://www.baidu.com") //配置申请的权限范围
                .scopes("all")
                // 配置grant_type，表示授权类型
                // authorization_code: 授权码
                // password： 密码
                // refresh_token: 更新令牌
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
//        clients.withClientDetails(clientDetailsService());
        super.configure(clients);
    }


}
