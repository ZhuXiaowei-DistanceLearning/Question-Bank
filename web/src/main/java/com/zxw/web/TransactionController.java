package com.zxw.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2022/8/14 17:44
 */
@RequestMapping("/mvvc")
@RestController
@Slf4j
public class TransactionController {
    @GetMapping
    @Transactional
    public void test() {
        try {
            log.info("qwe");
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
