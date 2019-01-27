package com.bw.movie.presenter;

import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者：admin on 2019/1/23 17:14
 * 邮箱：1724959985@qq.com
 */
public class UpdateHeadPresenter extends BasePresenter {
    public UpdateHeadPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("headPath", (String)args[2]);
        File file = new File((String) args[2]);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"),
                        file));

        //Log.e("ZMZ","========="+builder. build().toString());

        return iCoreInfe.uploadHeadPic((int) args[0], (String) args[1], builder.build());
    }
}
