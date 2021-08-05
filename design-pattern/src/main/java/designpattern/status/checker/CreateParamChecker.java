package designpattern.status.checker;

import designpattern.status.ServiceResult;
import designpattern.status.context.StateContext;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/5/20 16:45
 */
@Component
public class CreateParamChecker implements Checker {
    @Override
    public ServiceResult check(StateContext context) {
        return null;
    }
}
