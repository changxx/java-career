package com.changxx.practice.http.ms;

import java.io.Serializable;
import java.util.List;

/**
 * InteStat
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class InteStat implements Serializable {

    private static final long serialVersionUID = -5146884136609690780L;

    private List<MethodStat> list;

    public List<MethodStat> getList() {
        return list;
    }

    public void setList(List<MethodStat> list) {
        this.list = list;
    }
}
