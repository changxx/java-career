package com.changxx.practice.serializable;

import java.io.Serializable;

/**
 * @author changxx on 15-11-25.
 */
public class Domain implements Serializable {

    public Domain(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
