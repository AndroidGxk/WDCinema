package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/1/28 09:47
 * 邮箱：1724959985@qq.com
 */
public class UserTicketBean {
    /**
     * amount : 1
     * beginTime : 16:20:00
     * cinemaName : CGV星星影城
     * createTime : 1548595310000
     * endTime : 18:18:00
     * id : 6172
     * movieName : 西虹市首富
     * orderId : 20190127212150945
     * price : 0.13
     * screeningHall : 9号厅
     * status : 1
     * userId : 1779
     */

    private int amount;
    private String beginTime;
    private String cinemaName;
    private long createTime;
    private String endTime;
    private int id;
    private String movieName;
    private String orderId;
    private double price;
    private String screeningHall;
    private int status;
    private int userId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getScreeningHall() {
        return screeningHall;
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
