package com.tomek.yabs.yabscloudclient;

/**
 * Created by Tomek on 09.09.2014.
 */
public class CloudClientUnauthorizedException extends CloudClientException {

    private static final long serialVersionUID = 10647624101872689L;

    public CloudClientUnauthorizedException(String detailMessage) {
        super(detailMessage);
    }

    public CloudClientUnauthorizedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
