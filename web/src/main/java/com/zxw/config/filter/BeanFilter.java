package com.zxw.config.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zxw
 * @date 2022/4/12 15:40
 */
@Component
@Slf4j
public class BeanFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            log.info("{}", 123);
            int i = 5 / 0;
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
        chain.doFilter(request, response);
    }
}
