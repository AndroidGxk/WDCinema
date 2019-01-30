package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/1/30 10:07
 * 邮箱：1724959985@qq.com
 */
public class MeassageListBean {
    /**
     * content : 您已购买电影票,请尽快完成支付,以免影响到您的观影
     * id : 9334
     * pushTime : 1548751379000
     * status : 0
     * title : 系统通知
     * userId : 1779
     */

    private String content;
    private int id;
    private long pushTime;
    private int status;
    private String title;
    private int userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
