package com.example.catnews.utils;

public class NetworkState {

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    private final Status mStatus;
    private final String mMessage;

    public NetworkState(Status status, String message) {
        mStatus = status;
        mMessage = message;
    }

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, "success");
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, "running");
    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    public Status getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }
}
