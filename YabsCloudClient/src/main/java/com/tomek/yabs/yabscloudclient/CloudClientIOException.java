package com.tomek.yabs.yabscloudclient;

/**
 * Created by Tomek on 09.09.2014.
 */
public class CloudClientIOException extends CloudClientException {
    private static final long serialVersionUID = 9188133796027790465L;

    public CloudClientIOException(String detailMessage) {
        super(detailMessage);
    }

    public CloudClientIOException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
