package com.attlassian.hipchatext.models;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Include url and title of the web has the url.
 */
public class Link {
    private String url;

    private String title;

    private transient boolean isLoaded = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean mLoaded) {
        this.isLoaded = mLoaded;
    }
}
