package com.bw.movie.presenter;


import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

public class MoviesDetailPresenter extends BasePresenter {
    public MoviesDetailPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        if (args.length > 1) {
            return iCoreInfe.findMoviesDetail((int) args[0], (String) args[1], (int) args[2]);
        } else {
            return iCoreInfe.findMoviesDetail1((int) args[0]);
        }
    }
}
