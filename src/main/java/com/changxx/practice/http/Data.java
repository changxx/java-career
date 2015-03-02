package com.changxx.practice.http;

import java.io.Serializable;
import java.util.List;

/**
 * @author changxiangxiang
 * @date 2014年8月8日 上午11:08:34
 * @description
 * @since sprint2
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 4119411817278283045L;

    private List<JoneSystem>  system;

    public List<JoneSystem> getSystem() {
        return system;
    }

    public void setSystem(List<JoneSystem> system) {
        this.system = system;
    }

}
