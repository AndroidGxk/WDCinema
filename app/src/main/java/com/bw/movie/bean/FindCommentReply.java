package com.bw.movie.bean;

/**
 * 作者：古祥坤 on 2019/2/14 20:48
 * 邮箱：1724959985@qq.com
 */
public class FindCommentReply {
    /**
     * commentTime : 1532590303000
     * replyHeadPic : http://172.17.8.100/images/head_pic/bwjy.jpg
     * replyContent : 我还没看
     * replyUserId : 6
     * replyUserName : 谁的益达
     */

    private long commentTime;
    private String replyHeadPic;
    private String replyContent;
    private int replyUserId;
    private String replyUserName;

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public String getReplyHeadPic() {
        return replyHeadPic;
    }

    public void setReplyHeadPic(String replyHeadPic) {
        this.replyHeadPic = replyHeadPic;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }
}
