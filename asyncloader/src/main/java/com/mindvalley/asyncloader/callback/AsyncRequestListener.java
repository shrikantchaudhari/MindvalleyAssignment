package com.mindvalley.asyncloader.callback;

import com.mindvalley.asyncloader.models.CommonRequestModel;

public interface AsyncRequestListener {

    void onSuccess(CommonRequestModel commonRequestModel);

    void onFailure(CommonRequestModel commonRequestModel, int statusCode, byte[] errorResponse, Throwable e);

}
