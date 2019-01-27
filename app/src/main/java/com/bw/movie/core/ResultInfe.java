package com.bw.movie.core;

public interface ResultInfe<T> {
    void success(T data);

    void errors(Throwable throwable);
}
