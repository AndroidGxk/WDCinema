package com.bawei.admin.wdcinema.presenter;

import android.util.Log;

import com.bawei.admin.wdcinema.core.ICoreInfe;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.model.NetworkManager;

import io.reactivex.Observable;

/**
 * 作者：admin on 2019/1/23 17:14
 * 邮箱：1724959985@qq.com
 */
public class UserSignInPresenter extends BasePresenter {
    public UserSignInPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.userSignIn((int) args[0],
                (String) args[1]);
    }
}
