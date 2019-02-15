package com.bw.movie.core;

import com.bw.movie.bean.CineamComListBean;
import com.bw.movie.bean.CineamScheBean;
import com.bw.movie.bean.CinemaPageList;
import com.bw.movie.bean.CinemasListByMovieIdBean;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.FindCommentReply;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MeassageListBean;
import com.bw.movie.bean.MovieListBean;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.MoviesByIdBean;
import com.bw.movie.bean.RecommBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UpdateUserInfoBean;
import com.bw.movie.bean.UserTicketBean;
import com.bw.movie.bean.UserVipInfoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICoreInfe {
    /**
     * 注册接口
     */
    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<Result> registerUser(@Field("nickName") String nickName, @Field("phone") String phone, @Field("pwd") String pwd,
                                    @Field("pwd2") String pwd2, @Field("sex") int sex, @Field("birthday") String birthday,
                                    @Field("imei") String imei, @Field("ua") String ua, @Field("screenSize") String screenSize,
                                    @Field("os") String os, @Field("email") String email);

    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginBean>> login(@Field("phone") String phone,
                                        @Field("pwd") String pwd);

    /**
     * 查询热门电影
     */
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<HotMovieBean>>> hotmovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                    @Query("page") int page, @Query("count") int count);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserPwd")
    Observable<Result> modifyUserPwd(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                     @Field("oldPwd") String oldPwd, @Field("newPwd") String newPwd,
                                     @Field("newPwd2") String newPwd2);

    /**
     * 修改用户头像
     */
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> uploadHeadPic(@Header("userId") int userId, @Header("sessionId") String sessionId, @Body MultipartBody body);

    /**
     * 正在上映
     */
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<HotMovieBean>>> releaseMovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                        @Query("page") int page, @Query("count") int count);

    /**
     * 即将上映
     */
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<HotMovieBean>>> comingSoonMovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                           @Query("page") int page, @Query("count") int count);

    /**
     * 推荐影院
     */
    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<RecommBean>>> findRecommendCinemas(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                              @Query("page") int page, @Query("count") int count);

    /**
     * 附近影院
     */
    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<RecommBean>>> findNearbyCinemas(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                           @Query("longitude") String longitude, @Query("latitude") String latitude,
                                                           @Query("page") int page, @Query("count") int count);

    /**
     * 关注电影列表
     */
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<MovieListBean>>> findMoviePageList(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                              @Query("page") int page, @Query("count") int count);

    /**
     * 根据电影ID查询电影信息
     */
    @GET("movie/v1/findMoviesById")
    Observable<Result<MoviesByIdBean>> findMoviesById(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                      @Query("movieId") int movieId);

    /**
     * 查看电影详情
     */
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MoviesByIdBean>> findMoviesDetail(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                        @Query("movieId") int movieId);

    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MoviesByIdBean>> findMoviesDetail1(@Query("movieId") int movieId);

    /**
     * 关注影院列表
     */
    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<CinemaPageList>>> findCinemaPageList(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                                @Query("page") int page, @Query("count") int count);

    /**
     * App反馈
     */
    @POST("tool/v1/verify/recordFeedBack")
    Observable<Result<List<CinemaPageList>>> recordFeedBack(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                            @Query("content") String content);

    /**
     * 用户签到
     */
    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId, @Header("sessionId") String sessionId);

    /**
     * 影院当前排期
     */
    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<CineamScheBean>>> findMovieListByCinemaId(@Query("cinemaId") int cinemaId);

    /**
     * 电影排期列表
     */
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<MovieScheBean>>> findMovieScheduleList(@Query("cinemasId") int cinemasId, @Query("movieId") int movieId);

    /**
     * 影片评论
     */
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<FilmReviewBean>>> filmreview(@Header("userId") int userId, @Header("sessionId") String sessionId, @Query("movieId") int movieId, @Query("page") int page, @Query("count") int count);

    /**
     * 下单
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/buyMovieTicket")
    Observable<Result> buyMovieTicket(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                      @Field("scheduleId") int scheduleId, @Field("amount") int amount, @Field("sign") String sign);

    /**
     * 支付
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/pay")
    Observable<Result> pay(@Header("userId") int userId, @Header("sessionId") String sessionId,
                           @Field("payType") int payType, @Field("orderId") String orderId);

    /**
     * 根据电影ID查询当前排片该电影的影院列表
     */
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<CinemasListByMovieIdBean>>> cinemasListByMovieId(@Query("movieId") int movieId);

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST("user/v1/weChatBindingLogin")
    Observable<Result<LoginBean>> weChatBindingLogin(@Field("code") String code);

    /**
     * 查看购票记录
     */
    @GET("user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<UserTicketBean>>> findUserBuyTicketRecordList(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                                         @Query("page") int page, @Query("count") int count,
                                                                         @Query("status") int status);

    /**
     * 修改用户昵称
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserInfo")
    Observable<Result<UpdateUserInfoBean>> modifyUserInfo(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                          @Field("nickName") String nickName, @Field("sex") int sex, @Field("email") String email);

    /**
     * 影院关注
     */
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                    @Query("cinemaId") int cinemaId);

    /**
     * 影院取消关注
     */
    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                          @Query("cinemaId") int cinemaId);

    /**
     * 查询会员首页信息
     */
    @GET("user/v1/verify/findUserHomeInfo")
    Observable<Result<UserVipInfoBean>> findUserHomeInfo(@Header("userId") int userId,
                                                         @Header("sessionId") String sessionId);

    /**
     * 关注电影
     */
    @GET("movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId);

    /**
     * 取消关注电影
     */
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);

    /**
     * 用户对影片的评论
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/movieComment")
    Observable<Result> movieComment(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                    @Field("movieId") int movieId, @Field("commentContent") String commentContent);

    /**
     * 点赞
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/movieCommentGreat")
    Observable<Result> movieCommentGreat(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                         @Field("commentId") int movieId);

    /**
     * 消息通知列表
     */
    @GET("tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<MeassageListBean>>> findAllSysMsgList(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                                 @Query("page") int page, @Query("count") int count);

    /**
     * 影院评论列表
     */
    @GET("cinema/v1/findAllCinemaComment")
    Observable<Result<List<CineamComListBean>>> findAllCinemaComment(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                                     @Query("cinemaId") int cinemaId,
                                                                     @Query("page") int page, @Query("count") int count);

    /**
     * 回复评论的评论
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/commentReply")
    Observable<Result> commentReply(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                    @Field("commentId") int commentId, @Field("replyContent") String replyContent);

    /**
     * 回复评论的评论列表
     */
    @GET("movie/v1/findCommentReply")
    Observable<Result<List<FindCommentReply>>> findCommentReply(
            @Header("userId") int userId, @Header("sessionId") String sessionId,
            @Query("commentId") int commentId, @Query("page") int page, @Query("count") int count);

    /**
     * 改变推送消息状态
     */
    @GET("tool/v1/verify/changeSysMsgStatus")
    Observable<Result> changeSysMsgStatus(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                          @Query("id") int id);

    /**
     * 推送消息未读数量
     */
    @GET("tool/v1/verify/findUnreadMessageCount")
    Observable<Result> findUnreadMessageCount(@Header("userId") int userId, @Header("sessionId") String sessionId);
}
