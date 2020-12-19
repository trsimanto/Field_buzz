package com.towhid.fieldbuzz.fragment.fg_login.resorce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public String message = "";

    @Nullable
    public Throwable throwable = null;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable throwable) {
        this.status = status;
        this.data = data;
        this.throwable = throwable;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, "");
    }
    public static <T> Resource<T> error(@Nullable T throwable) {
        return new Resource<>(Status.ERROR, throwable, "");
    }

    public static <T> Resource<T> error(@NonNull String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> error(@NonNull Throwable throwable, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, throwable);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, "");
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}';
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
