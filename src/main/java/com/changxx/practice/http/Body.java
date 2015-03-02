package com.changxx.practice.http;

import java.io.Serializable;

/**
 * @author changxiangxiang
 * @date 2014年8月8日 上午11:04:20
 * @description
 * @since sprint2
 */
public class Body implements Serializable {

    private static final long serialVersionUID = 702376417562555691L;

    private int               count;

    private Data              data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
