package com.bawei.admin.wdcinema.bean;

/**
 * 作者：古祥坤 on 2019/1/25 08:50
 * 邮箱：1724959985@qq.com
 */
public class MovieListBean {
    /**
     * fare : 0
     * id : 4
     * imageUrl : http://mobile.bwstudent.com/images/movie/stills/drjzsdtw/drjzsdtw1.jpg
     * name : 狄仁杰之四大天王
     * releaseTime : 1566835200000
     * summary : 狄仁杰(赵又廷 饰）大破神都龙王案，获御赐亢龙锏，并掌管大理寺，使他成为武则天（刘嘉玲 饰）走向权力之路最大的威胁。武则天为了消灭眼中钉，命令尉迟真金（冯绍峰 饰）集结实力强劲的“异人组”，妄图夺取亢龙锏。在医官沙陀忠（林更新 饰）的协助下，狄仁杰既要守护亢龙锏，又要破获神秘奇案，还要面对武则天的步步紧逼，大唐江山陷入了空前的危机之中……
     */

    private int fare;
    private int id;
    private String imageUrl;
    private String name;
    private long releaseTime;
    private String summary;

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
