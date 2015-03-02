package com.changxx.practice.reflect;

/**
 * @author changxiangxiang
 * @date 2014年9月2日 上午9:58:51
 * @description
 * @since sprint2
 */
public class User {

    private int    id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "User [id=" + id + ", name=" + name + "]";
    }

}
