package com.bw.movie.presenter;


import com.bw.movie.core.ICoreInfe;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.model.NetworkManager;

import io.reactivex.Observable;

/**
 * 作者：admin on 2019/2/14 14:03
 * 邮箱：1724959985@qq.com
 */
public class CommentReplyPresenter extends BasePresenter {
    public CommentReplyPresenter(ResultInfe resultInfe) {
        super(resultInfe);
    }

    @Override
    protected Observable observable(Object... args) {
        ICoreInfe iCoreInfe = NetworkManager.network().create(ICoreInfe.class);
        return iCoreInfe.commentReply((int) args[0], (String) args[1], (int) args[2], (String) args[3]);
    }
}
