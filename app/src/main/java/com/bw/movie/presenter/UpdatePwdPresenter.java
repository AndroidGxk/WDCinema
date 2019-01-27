package com.bw.movie.presenter;

import android.util.Log;

import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

/**
 * 作者：admin on 2019/1/23 17:14
 * 邮箱：1724959985@qq.com
 */
public class UpdatePwdPresenter extends BasePresenter {
    public UpdatePwdPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        Log.i("GT", "---------------------" + args[0]+"   "+args[1]+"   "+args[2]+"   "+args[3]+"   "+args[4]);
        return iCoreInfe.modifyUserPwd((int) args[0],
                (String) args[1], (String) args[2],
                (String) args[3], (String) args[4]);
    }
}
