package top.longmarch.sys.entity;

import java.io.Serializable;

public class SysParams implements Serializable {

    private String title;
    private String headImgUrl;
    private String defaultNickname;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getDefaultNickname() {
        return defaultNickname;
    }

    public void setDefaultNickname(String defaultNickname) {
        this.defaultNickname = defaultNickname;
    }
}
