package com.bawei.admin.wdcinema.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 作者：admin on 2019/1/23 17:58
 * 邮箱：1724959985@qq.com
 */
@Entity
public class LoginSubBean {
    @Id(autoincrement = true)
    private long gid;
    private long birthday;
    private int id;
    private long lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;
    private String headPic;
    private String sessionId;
    private int userId;
    private String pwd;
    private int statu;
    private String mail;
    @Generated(hash = 1525502545)
    public LoginSubBean(long gid, long birthday, int id, long lastLoginTime,
            String nickName, String phone, int sex, String headPic,
            String sessionId, int userId, String pwd, int statu, String mail) {
        this.gid = gid;
        this.birthday = birthday;
        this.id = id;
        this.lastLoginTime = lastLoginTime;
        this.nickName = nickName;
        this.phone = phone;
        this.sex = sex;
        this.headPic = headPic;
        this.sessionId = sessionId;
        this.userId = userId;
        this.pwd = pwd;
        this.statu = statu;
        this.mail = mail;
    }
    @Generated(hash = 178203177)
    public LoginSubBean() {
    }
    public long getGid() {
        return this.gid;
    }
    public void setGid(long gid) {
        this.gid = gid;
    }
    public long getBirthday() {
        return this.birthday;
    }
    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public int getStatu() {
        return this.statu;
    }
    public void setStatu(int statu) {
        this.statu = statu;
    }
    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

}
