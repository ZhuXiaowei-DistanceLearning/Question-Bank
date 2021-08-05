package designpattern.factory;

/**
 * @author zxw
 * @date 2020/11/5 15:27
 */
public class RuleFactory {
    public RuleService createRuleService(String type){
        if(type.equals("alarm")){
            return new AlarmRuleServiceImpl();
        }
        return new ForwardRuleServiceImpl();
    }
}
