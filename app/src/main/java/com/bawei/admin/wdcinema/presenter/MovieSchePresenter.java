package com.bawei.admin.wdcinema.presenter;

import com.bawei.admin.wdcinema.core.ICoreInfe;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.model.NetworkManager;

import io.reactivex.Observable;

public class MovieSchePresenter extends BasePresenter {
    public MovieSchePresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }
    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.findMovieScheduleList((int) args[0], (int) args[1]);
    }
}
