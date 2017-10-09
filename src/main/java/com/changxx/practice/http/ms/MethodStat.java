package com.changxx.practice.http.ms;

import java.io.Serializable;

/**
 * MethodStat
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class MethodStat implements Serializable {

    private static final long serialVersionUID = -4770858387400069997L;

    private String method;

    private Integer consumerSuccess;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getConsumerSuccess() {
        return consumerSuccess;
    }

    public void setConsumerSuccess(Integer consumerSuccess) {
        this.consumerSuccess = consumerSuccess;
    }
}
