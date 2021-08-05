package designpattern.factory;

/**
 * @author zxw
 * @date 2020/11/5 15:19
 */
public class RuleController {
    public void createRule(){
        RuleFactory ruleFactory = new RuleFactory();
        RuleService ruleService = ruleFactory.createRuleService("");
        ruleService.createRule();
    }
}
