package com.mindvalley.asyncloader.models;


import com.mindvalley.asyncloader.callback.AsyncRequestListener;
import com.mindvalley.asyncloader.utilities.RequestDataType;

import static com.mindvalley.asyncloader.common.Constants.REMOTE_DATA_SOURCE;

public abstract class CommonRequestModel {

    private String url;

    private byte[] responseData;

    private RequestDataType requestDataType;

    private AsyncRequestListener asyncRequestListener;

    public String dataSource = REMOTE_DATA_SOURCE;

    protected CommonRequestModel(String url, RequestDataType requestDataType, AsyncRequestListener asyncRequestListener) {
        this.url = url;
        this.requestDataType = requestDataType;
        this.asyncRequestListener = asyncRequestListener;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getResponseData() {
        return responseData;
    }

    public RequestDataType getRequestDataType() {
        return requestDataType;
    }

    public AsyncRequestListener getAsyncRequestListener() {
        return asyncRequestListener;
    }

    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    public abstract CommonRequestModel clone(AsyncRequestListener asyncRequestListener);


}
