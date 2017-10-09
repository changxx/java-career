package com.changxx.practice.http.ms;

import java.io.Serializable;
import java.util.List;

/**
 * MethodDist
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class MethodDist implements Serializable {

    private static final long serialVersionUID = 6015578134513640639L;

    private List<String> apps;

    public List<String> getApps() {
        return apps;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }
}
