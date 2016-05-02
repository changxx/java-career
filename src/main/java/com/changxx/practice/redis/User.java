package com.changxx.practice.redis;

/**
 * User
 *
 * @author changxiangxiang
 * @date 16/3/13
 */
public class User extends com.changxx.practice.hash.User {

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
