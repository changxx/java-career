package com.changxx.practice.rpc.socket.demo1;

import java.io.Serializable;

/**
 * @author changxx on 15-12-22.
 */
public class Domain implements Serializable {

    private static final long serialVersionUID = -5543229199640573274L;

    private Integer id;

    private String name;

    public Domain(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
