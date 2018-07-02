package com.dodsport.consumer.rxbus;

import okhttp3.ResponseBody;

/**
 *
 * @author Administrator
 * @date 2017/10/31
 */

public class ResponseBodyBean  {

    private ResponseBody responseBody;
    private String type;

    public ResponseBodyBean(ResponseBody responseBody, String type) {
        this.responseBody = responseBody;
        this.type = type;
    }

    public ResponseBodyBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "ResponseBodyBean{" +
                "responseBody=" + responseBody +
                ", type='" + type + '\'' +
                '}';
    }
}
