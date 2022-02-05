package com.zxw.sentinel;
import com.google.common.collect.Lists;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowClusterConfig;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2022-02-01 17:31
 */
public class SentinelTest {
    public static void main(String[] args) {
        initFlowRules();
        initParamFlowRule();
        while (true) {
            Entry entry = null;
            try {
//                ContextUtil.enter("entrance1", "appA");
//                Entry nodeA = SphU.entry("nodeA");
//                if (nodeA != null) {
//                    nodeA.exit();
//                }
//                ContextUtil.exit();
//
//                ContextUtil.enter("entrance2", "appA");
//                nodeA = SphU.entry("nodeA");
//                if (nodeA != null) {
//                    nodeA.exit();
//                }
//                ContextUtil.exit();
                entry = SphU.entryWithPriority("HelloWorld");
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

    private static void initFlowRules() {
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

    public static void initAuthorityRule(){
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setResource("HelloWorld");
        rule.setLimitApp("hello");
        AuthorityRuleManager.loadRules(rules);
    }
    public static void initParamFlowRule(){
        List<ParamFlowRule> rules = new ArrayList<>();
        ParamFlowRule rule = new ParamFlowRule();
        rule.setControlBehavior(1000);
        rule.setMaxQueueingTimeMs(1000);
        rule.setBurstCount(1);
        rule.setDurationInSec(1L);
        rule.setGrade(1);
        rule.setParamIdx(1);
        rule.setCount(1.0D);
        rule.setParamFlowItemList(Lists.newArrayList());
        rule.setClusterMode(false);
        rule.setClusterConfig(new ParamFlowClusterConfig());
        rule.setResource("");
        rule.setLimitApp("");
        rule.setCount(1.0);
        rule.setResource("HelloWorld");
        rule.setLimitApp("default");
        rules.add(rule);
        ParamFlowRuleManager.loadRules(rules);
    }
}
