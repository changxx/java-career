package com.changxx.practice.http.ms;

import java.io.Serializable;
import java.util.List;

/**
 * AppResult
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class AppResult implements Serializable {

    private static final long serialVersionUID = 5145724994075768503L;

    private String name;

    private List<AppTag> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppTag> getTags() {
        return tags;
    }

    public void setTags(List<AppTag> tags) {
        this.tags = tags;
    }
}
