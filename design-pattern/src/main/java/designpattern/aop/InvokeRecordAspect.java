package designpattern.aop;

import org.springframework.core.annotation.Order;

//@Aspect
@Order(1)
//@Component
public class InvokeRecordAspect extends BaseMethodAspect {

    /**
     * 指定切点（处理打上 InvokeRecordAnno 的方法）
     */
    @Override
//    @Pointcut("@annotation(InvokeRecordAnno)")
    protected void pointcut() { }

    /**
     * 指定该切面绑定的方法切面处理器为 InvokeRecordHandler
     */
    @Override
    public Class<? extends BaseMethodAdviceHandler<?>> getAdviceHandlerType() {
        return InvokeRecordHandler.class;
    }
}