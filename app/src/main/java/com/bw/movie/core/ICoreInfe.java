package com.bw.movie.core;

import com.bw.movie.bean.CineamScheBean;
import com.bw.movie.bean.CinemaPageList;
import com.bw.movie.bean.CinemasListByMovieIdBean;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MovieListBean;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.MoviesByIdBean;
import com.bw.movie.bean.RecommBean;
import com.bw.movie.bean.Result;

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
    Observable<Result<List<FilmReviewBean>>> filmreview(@Query("movieId") int movieId, @Query("page") int page, @Query("count") int count);

    /**
     * 下单
     *
     * @param userId
     * @param sessionId
     * @param scheduleId
     * @param amount
     * @param sign
     * @return
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
}
