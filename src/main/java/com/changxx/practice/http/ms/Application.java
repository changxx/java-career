package com.changxx.practice.http.ms;

import java.io.Serializable;

/**
 * Application
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class Application implements Serializable {

    private static final long serialVersionUID = -8205030726138236716L;

    private Integer retCode;

    private AppPage page;

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public AppPage getPage() {
        return page;
    }

    public void setPage(AppPage page) {
        this.page = page;
    }
}
