package com.bawei.admin.wdcinema.bean;

/**
 * 作者：古祥坤 on 2019/1/26 09:32
 * 邮箱：1724959985@qq.com
 */
public class MovieScheBean {
    /**
     * beginTime : 17:05
     * duration : 118分钟
     * endTime : 19:03
     * id : 3
     * price : 0.13
     * screeningHall : 3厅
     * seatsTotal : 180
     * seatsUseCount : 10
     * status : 1
     */

    private String beginTime;
    private String duration;
    private String endTime;
    private int id;
    private double price;
    private String screeningHall;
    private int seatsTotal;
    private int seatsUseCount;
    private int status;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public int getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(int seatsTotal) {
        this.seatsTotal = seatsTotal;
    }

    public int getSeatsUseCount() {
        return seatsUseCount;
    }

    public void setSeatsUseCount(int seatsUseCount) {
        this.seatsUseCount = seatsUseCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
