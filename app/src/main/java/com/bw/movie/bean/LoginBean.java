package com.bw.movie.bean;

/**
 * 作者：admin on 2019/1/23 17:49
 * 邮箱：1724959985@qq.com
 */

public class LoginBean {
    /**
     * sessionId : 15320592619803
     * userId : 3
     * userInfo : {"birthday":320256000000,"id":3,"lastLoginTime":1532059192000,"nickName":"你的益达","phone":"18600151568","sex":1,"headPic":"http://172.17.8.100/images/head_pic/bwjy.jpg"}
     */
    private int id;
    private String sessionId;
    private int userId;
    private LoginSubBean userInfo;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LoginSubBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(LoginSubBean userInfo) {
        this.userInfo = userInfo;
    }
}
