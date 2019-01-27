package com.bw.movie.presenter;


import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

/**
 * 作者：admin on 2019/1/23 17:14
 * 邮箱：1724959985@qq.com
 */
public class RegisPresenter extends BasePresenter{
    public RegisPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.registerUser((String)args[0],(String)args[1],(String)args[2]
                ,(String)args[3],(int)args[4],(String) args[5],(String)args[6],(String)args[7],
                (String)args[8],(String)args[9],(String)args[10]);
    }
}
