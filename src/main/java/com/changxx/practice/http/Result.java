package com.changxx.practice.http;

import java.io.Serializable;

/**
 * @author changxiangxiang
 * @date 2014年8月8日 上午11:02:34
 * @description
 * @since sprint2
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 5388480131042397327L;

    private Body              body;

    private int               code;

    private String            tip;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

}
