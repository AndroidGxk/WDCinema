package com.bw.movie.presenter;

import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

public class MovieCommentPresenter extends BasePresenter {
    public MovieCommentPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.movieComment((int) args[0], (String) args[1], (int) args[2], (String) args[3]);
    }
}
