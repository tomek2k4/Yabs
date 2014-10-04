package com.tomek.yabs.yabscloudclient;

/**
 * Created by Tomek on 07.09.2014.
 */
public class CloudClientException extends RuntimeException {
    private static final long serialVersionUID = 727445951300074292L;

    public CloudClientException(String detailMessage) {
        super(detailMessage);
    }

    public CloudClientException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
