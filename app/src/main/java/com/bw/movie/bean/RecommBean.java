package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/1/24 14:15
 * 邮箱：1724959985@qq.com
 */
public class RecommBean {
    /**
     * address : 东城区滨河路乙1号雍和航星园74-76号楼
     * commentTotal : 81
     * distance : 0
     * followCinema : 2
     * id : 1
     * logo : http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg
     * name : 青春光线电影院
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
