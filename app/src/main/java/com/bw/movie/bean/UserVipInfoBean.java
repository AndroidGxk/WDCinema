package com.bw.movie.bean;

import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/29 14:03
 * 邮箱：1724959985@qq.com
 */
public class UserVipInfoBean {
    /**
     * cinemasList : [{"address":"北京市海淀区上地南口华联商厦4F","commentTotal":0,"distance":0,"followCinema":0,"id":18,"logo":"http://mobile.bwstudent.com/images/movie/logo/ctjh.jpg","name":"橙天嘉禾影城北京上地店"}]
     * headPic : http://mobile.bwstudent.com/images/movie/head_pic/2019-01-28/20190128192515.jpg
     * integral : 40
     * movieList : [{"fare":0,"id":4,"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/drjzsdtw/drjzsdtw1.jpg","name":"狄仁杰之四大天王","releaseTime":1566835200000,"summary":"狄仁杰(赵又廷 饰）大破神都龙王案，获御赐亢龙锏，并掌管大理寺，使他成为武则天（刘嘉玲 饰）走向权力之路最大的威胁。武则天为了消灭眼中钉，命令尉迟真金（冯绍峰 饰）集结实力强劲的\u201c异人组\u201d，妄图夺取亢龙锏。在医官沙陀忠（林更新 饰）的协助下，狄仁杰既要守护亢龙锏，又要破获神秘奇案，还要面对武则天的步步紧逼，大唐江山陷入了空前的危机之中\u2026\u2026"},{"fare":0,"id":3,"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/xhssf/xhssf1.jpg","name":"西虹市首富","releaseTime":1564156800000,"summary":"故事发生在《夏洛特烦恼》中的\u201c特烦恼\u201d之城\u201c西虹市\u201d。混迹于丙级业余足球队的守门员王多鱼（沈腾 饰），因比赛失利被开除离队。正处于人生最低谷的他接受了神秘台湾财团\u201c一个月花光十亿资金\u201d的挑战。本以为快乐生活就此开始，王多鱼却第一次感到\u201c花钱特烦恼\u201d！想要人生反转走上巅峰，真的没有那么简单."},{"fare":0,"id":2,"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/mtyj/mtyj1.jpg","name":"摩天营救","releaseTime":1563552000000,"summary":"在香港市中心，世界上最高、结构最复杂的摩天大楼遭到破坏，危机一触即发。威尔·索耶（道恩·强森 饰）的妻子萨拉（内芙·坎贝尔 饰）和孩子们在98层被劫为人质，直接暴露在火线上。威尔，这位战争英雄、前联邦调查局救援队员，作为大楼的安保顾问，却被诬陷纵火和谋杀。他必须奋力营救家人，为自己洗脱罪名，关乎生死存亡的高空任务就此展开。"}]
     * nickName : 古彤
     * phone : 13366247385
     * userSignStatus : 2
     */

    private String headPic;
    private int integral;
    private String nickName;
    private String phone;
    private int userSignStatus;
    private List<CinemasListBean> cinemasList;
    private List<MovieListBean> movieList;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserSignStatus() {
        return userSignStatus;
    }

    public void setUserSignStatus(int userSignStatus) {
        this.userSignStatus = userSignStatus;
    }

    public List<CinemasListBean> getCinemasList() {
        return cinemasList;
    }

    public void setCinemasList(List<CinemasListBean> cinemasList) {
        this.cinemasList = cinemasList;
    }

    public List<MovieListBean> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieListBean> movieList) {
        this.movieList = movieList;
    }

    public static class CinemasListBean {
        /**
         * address : 北京市海淀区上地南口华联商厦4F
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 18
         * logo : http://mobile.bwstudent.com/images/movie/logo/ctjh.jpg
         * name : 橙天嘉禾影城北京上地店
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

    public static class MovieListBean {
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
}
