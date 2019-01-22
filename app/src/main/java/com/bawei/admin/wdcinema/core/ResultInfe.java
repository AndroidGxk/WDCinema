package com.bawei.admin.wdcinema.core;

public interface ResultInfe<T> {
    void success(T data);

    void errors(Throwable throwable);
}
