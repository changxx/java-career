package com.changxx.practice.http.ms;

import java.io.Serializable;

/**
 * AppTag
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class AppTag implements Serializable {

    private static final long serialVersionUID = -1159862386183460522L;

    private String tagName;

    private String description;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
