package designpattern.template;

/**
 * @author zxw
 * @date 2021/7/29 18:00
 */
public enum DDportTaskStatus {
    FINISHED(0 ,"");
    private Integer value;
    private String label;

    DDportTaskStatus(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
