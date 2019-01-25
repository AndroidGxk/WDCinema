package com.bawei.admin.wdcinema.core;

import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.LoginBean;
import com.bawei.admin.wdcinema.bean.MovieListBean;
import com.bawei.admin.wdcinema.bean.MoviesDetail;
import com.bawei.admin.wdcinema.bean.RecommBean;
import com.bawei.admin.wdcinema.bean.Result;

import java.io.File;
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
    @FormUrlEncoded
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> uploadHeadPic(@Header("userId") int userId, @Header("sessionId") String sessionId, @Field("image") String image);

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
    Observable<Result<MoviesDetail>> findMoviesById(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                    @Query("movieId") int movieId);

    /**
     * 查看电影详情
     */
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MoviesDetail>> findMoviesDetail(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                      @Query("movieId") int movieId);
}
