package com.zxw.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2022-02-01 17:31
 */
public class SentinelTest {
    public static void main(String[] args) {
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                // 自定义资源名,需要和exit()方法承兑出现
                entry = SphU.entry("HelloWorld");
                // 被保护的业务逻辑
                /*您的业务逻辑 - 开始*/
                System.out.println("hello world");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                // 资源访问组织，被限流或被降级
                /*限流控逻辑处理 - 开始*/
                System.out.println("block!");
                /*限流控逻辑处理 - 结束*/
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 资源名
        rule.setResource("HelloWorld");
        // 限流类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        // 限流阔值
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
