package designpattern.template.strategy;

/**
 * 策略公共父类
 *
 * @author xxx
 * @since xxx
 * @apiNote 主要是将子类共用方法和成员抽离出来
 * */
public abstract class AbstractCompareModeStrategy implements CompareModeStrategy {
    //子类的共用方法，可以放在此类中
}