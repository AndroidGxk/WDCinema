package com.bawei.admin.wdcinema.presenter;

import com.bawei.admin.wdcinema.core.ICoreInfe;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.model.NetworkManager;

import io.reactivex.Observable;

public class MoviesByIdPresenter extends BasePresenter{
    public MoviesByIdPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.findMoviesById((int)args[0],(String) args[1],(int)args[2]);
    }
}
