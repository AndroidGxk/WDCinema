package com.bawei.admin.wdcinema.presenter;

import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {
    ResultInfe resultInfe;

    public BasePresenter(ResultInfe resultInfe) {
        this.resultInfe = resultInfe;
    }

    protected abstract Observable observable(Object... args);

    public void request(Object... args) {
        observable(args).compose(new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        }).subscribe(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                resultInfe.success(result);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                resultInfe.errors(throwable);
            }
        });
    }

    public void unBind() {
        resultInfe = null;
    }
}
