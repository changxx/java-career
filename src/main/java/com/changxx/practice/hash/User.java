package com.changxx.practice.hash;

import java.io.Serializable;

/**
 * @author changxiangxiang
 * @date 2013年11月14日 下午8:57:22
 * @description
 */
public class User implements Serializable {

    private static final long serialVersionUID = -5625796575803909200L;

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
