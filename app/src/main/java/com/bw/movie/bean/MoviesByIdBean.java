package com.bw.movie.bean;

import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/25 08:56
 * 邮箱：1724959985@qq.com
 */
public class MoviesByIdBean<T> {
    /**
     * director : 文牧野
     * duration : 117分钟
     * fare : 0.22
     * id : 1
     * imageUrl : http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg
     * name : 我不是药神
     * placeOrigin : 中国
     * starring : 徐峥,周一围,王传君,谭卓
     * summary : 一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药“格列宁”的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以“药神”的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......
     */

    private String director;
    private String duration;
    private double fare;
    private int id;
    private String imageUrl;
    private String name;
    private String placeOrigin;
    private String starring;
    private String summary;
    private int followMovie;
    private String movieTypes;
    private int rank;
    private List<String> posterList;
    private List<MoviesDetailBean> shortFilmList;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
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

    public String getPlaceOrigin() {
        return placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getFollowMovie() {
        return followMovie;
    }

    public void setFollowMovie(int followMovie) {
        this.followMovie = followMovie;
    }

    public String getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(String movieTypes) {
        this.movieTypes = movieTypes;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<String> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<String> posterList) {
        this.posterList = posterList;
    }

    public List<MoviesDetailBean> getShortFilmList() {
        return shortFilmList;
    }

    public void setShortFilmList(List<MoviesDetailBean> shortFilmList) {
        this.shortFilmList = shortFilmList;
    }
}
