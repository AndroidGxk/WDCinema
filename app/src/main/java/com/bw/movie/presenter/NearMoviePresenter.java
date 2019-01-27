package com.bw.movie.presenter;


import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

public class NearMoviePresenter extends BasePresenter {
    public NearMoviePresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.findNearbyCinemas((int) args[0], (String) args[1], (String) args[2], (String) args[3], (int) args[4], (int) args[5]);
    }
}
