package com.changxx.practice.sync.enums;

/**
 * @author changxiangxiang
 * @date 2014年10月28日 下午2:40:53
 * @description
 */
public enum SyncType {

    CLOCK(1, "打卡"), LEAVE(2, "请假"), OVERTIME(3, "加班");

    private Integer code;
    private String  name;

    private SyncType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
