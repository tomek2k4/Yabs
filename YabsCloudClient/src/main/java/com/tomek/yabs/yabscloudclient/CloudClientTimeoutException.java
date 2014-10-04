package com.tomek.yabs.yabscloudclient;

/**
 * Created by Tomek on 09.09.2014.
 */
public class CloudClientTimeoutException extends CloudClientIOException {

    private static final long serialVersionUID = 5106556658859353651L;

    public CloudClientTimeoutException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CloudClientTimeoutException(String detailMessage) {
        super(detailMessage);
    }
}
