package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/1/28 19:31
 * 邮箱：1724959985@qq.com
 */
public class UpdateUserInfoBean {
    /**
     * id : 0
     * nickName : 真的是你的益达吗
     * sex : 2
     */

    private int id;
    private String nickName;
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
