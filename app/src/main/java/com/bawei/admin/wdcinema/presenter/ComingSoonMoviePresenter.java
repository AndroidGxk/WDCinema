package com.bawei.admin.wdcinema.presenter;

import com.bawei.admin.wdcinema.core.ICoreInfe;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.model.NetworkManager;

import io.reactivex.Observable;

public class ComingSoonMoviePresenter extends BasePresenter {
    public ComingSoonMoviePresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }
    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.comingSoonMovie((int) args[0], (String) args[1], (int) args[2], (int) args[3]);
    }
}
