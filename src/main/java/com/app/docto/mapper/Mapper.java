package com.app.docto.mapper;

public interface Mapper<T, U> {
    U mapOne(T t);
}
