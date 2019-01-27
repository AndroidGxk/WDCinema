package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/1/25 09:48
 * 邮箱：1724959985@qq.com
 */
public class CinemaPageList{
    /**
     * address : 朝阳区湖景东路11号新奥购物中心B1(东A口)
     * commentTotal : 0
     * distance : 0
     * followCinema : 0
     * id : 5
     * logo : http://mobile.bwstudent.com/images/movie/logo/CGVxx.jpg
     * name : CGV星星影城
     */

    private String address;
    private int commentTotal;
    private int distance;
    private int followCinema;
    private int id;
    private String logo;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getFollowCinema() {
        return followCinema;
    }

    public void setFollowCinema(int followCinema) {
        this.followCinema = followCinema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
