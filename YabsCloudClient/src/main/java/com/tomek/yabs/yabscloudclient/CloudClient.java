package com.tomek.yabs.yabscloudclient;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Tomek on 07.09.2014.
 */
public final class CloudClient {
    private static final String TAG = "CloudClient";
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String DATE_FORMAT_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";
    private static final String DEFAULT_USER_AGENT = "CloudClient/1.0";
    public static final  String DEFAULT_API_ROOT = "http://stackoverflow.com";

    private String username;
    private String password;
    private String apiRoot;
    private String apiRootHost;
    private int apiRootPort;

    private HttpClient httpClient = null;

    public CloudClient(String username, String password) {
        this.username = username;
        this.password = password;

        this.httpClient = new DefaultHttpClient();
    }

    public void updateBeacon(long beaconId) throws CloudClientException {
        String responseString;
        HttpResponse httpResponse;
        StatusLine statusLine;

        try {
            httpResponse = httpClient.execute(new HttpGet(DEFAULT_API_ROOT));
            statusLine = httpResponse.getStatusLine();
            Log.d(TAG,"Line status is:"+statusLine);
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                Log.d(TAG,"Response from HTTP is:"+responseString);
            } else{
                //Closes the connection.
                httpResponse.getEntity().getContent().close();
                Log.e(TAG,"HTTPGet failed");
                throw new CloudClientException(statusLine.getReasonPhrase());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Could not execute HTTP command",e);
            throw new CloudClientException("HTTP client execute failed",e);
        }

    }
}
