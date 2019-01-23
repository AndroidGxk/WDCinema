package com.bawei.admin.wdcinema.core;

import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.LoginBean;
import com.bawei.admin.wdcinema.bean.Result;

import java.util.List;

import io.reactivex.Observable;
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

    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<HotMovieBean>>> hotmovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                    @Query("page") int page, @Query("count") int count);
}
