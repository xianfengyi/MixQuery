package com.pioneeryi.mixquery.jdbc.model.execute;

/**
 * PrepareStatement中的预编译字段
 */
public class StatementParameter {

    private String className;
    private String value;

    public StatementParameter(Object obj) {
        //getName记录类名，配合server端进行Class.forName()来反查类型
        this.className = obj.getClass().getName();
        this.value = String.valueOf(obj);
    }

    public String getClassName() {
        return className;
    }

    public void setClazz(String className) {
        this.className = className;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
