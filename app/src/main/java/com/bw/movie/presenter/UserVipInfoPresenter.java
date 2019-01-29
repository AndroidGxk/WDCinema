package com.bw.movie.presenter;


import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

public class UserVipInfoPresenter extends BasePresenter {
    public UserVipInfoPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.findUserHomeInfo((int) args[0], (String) args[1]);
    }
}
